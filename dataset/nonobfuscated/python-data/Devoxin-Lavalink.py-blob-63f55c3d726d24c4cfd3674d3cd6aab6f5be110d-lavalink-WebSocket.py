import asyncio
import json
import logging

import websockets

from .Events import TrackStuckEvent, TrackExceptionEvent, TrackEndEvent, StatsUpdateEvent

log = logging.getLogger(__name__)


class WebSocket:
    def __init__(self, lavalink, host, password, ws_port, ws_retry, shard_count):
        self._lavalink = lavalink

        self._ws = None
        self._queue = []

        self._ws_retry = ws_retry
        self._password = password
        self._host = host
        self._port = ws_port
        self._uri = 'ws://{}:{}'.format(self._host, self._port)
        self._shards = shard_count

        self._shutdown = False

        self._loop = self._lavalink.loop
        self._loop.create_task(self.connect())

    @property
    def connected(self):
        

        return self._ws is not None and self._ws.open

    async def connect(self):
        

        await self._lavalink.bot.wait_until_ready()

        if self._ws and self._ws.open:
            log.debug('WebSocket still open, closing...')
            await self._ws.close()

        user_id = self._lavalink.bot.user.id
        shard_count = self._lavalink.bot.shard_count or self._shards

        headers = {
            'Authorization': self._password,
            'Num-Shards': shard_count,
            'User-Id': str(user_id)
        }
        log.debug('Preparing to connect to Lavalink')
        log.debug('    with URI: {}'.format(self._uri))
        log.debug('    with headers: {}'.format(str(headers)))
        log.info('Connecting to Lavalink...')

        try:
            self._ws = await websockets.connect(self._uri, loop=self._loop, extra_headers=headers)
        except OSError as error:
            log.exception('Failed to connect to Lavalink: {}'.format(str(error)))
        else:
            log.info('Connected to Lavalink!')
            self._loop.create_task(self.listen())
            version = self._ws.response_headers.get('Lavalink-Major-Version', 2)
            try:
                self._lavalink._server_version = int(version)
            except ValueError:
                self._lavalink._server_version = 2
            log.info('Lavalink server version is {}'.format(version))
            if self._queue:
                log.info('Replaying {} queued events...'.format(len(self._queue)))
                for task in self._queue:
                    await self.send(**task)

    async def _attempt_reconnect(self):
        

        log.info('Connection closed; attempting to reconnect in 30 seconds')
        for a in range(0, self._ws_retry):
            await asyncio.sleep(30)
            log.info('Reconnecting... (Attempt {})'.format(a + 1))
            await self.connect()

            if self._ws.open:
                return True
        return False

    async def listen(self):
        

        while not self._shutdown:
            try:
                data = json.loads(await self._ws.recv())
            except websockets.ConnectionClosed as error:
                log.warning('Disconnected from Lavalink: {}'.format(str(error)))
                for g in self._lavalink.players._players.copy().keys():
                    ws = self._lavalink.bot._connection._get_websocket(int(g))
                    await ws.voice_state(int(g), None)

                self._lavalink.players.clear()

                if self._shutdown:
                    break

                if await self._attempt_reconnect():
                    return

                log.warning('Unable to reconnect to Lavalink!')
                break

            op = data.get('op', None)
            log.debug('Received WebSocket data {}'.format(str(data)))

            if not op:
                return log.debug('Received WebSocket message without op {}'.format(str(data)))

            if op == 'event':
                log.debug('Received event of type {}'.format(data['type']))
                player = self._lavalink.players[int(data['guildId'])]
                event = None

                if data['type'] == 'TrackEndEvent':
                    event = TrackEndEvent(player, data['track'], data['reason'])
                elif data['type'] == 'TrackExceptionEvent':
                    event = TrackExceptionEvent(player, data['track'], data['error'])
                elif data['type'] == 'TrackStuckEvent':
                    event = TrackStuckEvent(player, data['track'], data['thresholdMs'])

                if event:
                    await self._lavalink.dispatch_event(event)
            elif op == 'playerUpdate':
                await self._lavalink.update_state(data)
            elif op == 'stats':
                self._lavalink.stats._update(data)
                await self._lavalink.dispatch_event(StatsUpdateEvent(self._lavalink.stats))

        log.debug('Closing WebSocket...')
        await self._ws.close()

    async def send(self, **data):
        

        if self._ws and self._ws.open:
            log.debug('Sending payload {}'.format(str(data)))
            await self._ws.send(json.dumps(data))
        else:
            self._queue.append(data)
            log.debug('Send called before WebSocket ready; queueing payload {}'.format(str(data)))

    def destroy(self):
        self._shutdown = True
