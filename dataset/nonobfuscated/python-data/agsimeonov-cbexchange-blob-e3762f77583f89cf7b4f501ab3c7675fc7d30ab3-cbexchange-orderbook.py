

from json import dumps
from threading import Thread
from time import sleep

from cbexchange.market import MarketClient
from cbexchange.websock import WSClient


class OrderBook(object):
  

  WS_URI = 'wss://ws-feed.exchange.coinbase.com'
  API_URI = 'https://api.exchange.coinbase.com'
  PRODUCT_ID = 'BTC-USD'

  def __init__(self, ws_uri=None, api_uri=None, product_id=None):
    self.API_URI = api_uri or self.API_URI
    self.WS_URI = ws_uri or self.WS_URI
    self.PRODUCT_ID = product_id or self.PRODUCT_ID
    self.ws_client = WSClient(ws_uri=self.WS_URI, ws_product_id=self.PRODUCT_ID)
    self.ws_client.connect()
    client = MarketClient(api_uri=self.API_URI)
    self.book = client.get_product_order_book(3, self.PRODUCT_ID)
    self.sequence = self.book['sequence']
    self.die = False
    self.pause = False

    asks = {}
    for entry in self.book['asks']:
      asks[entry[2]] = {'price':entry[0], 'size':entry[1]}
    self.book['asks'] = asks

    bids = {}
    for entry in self.book['bids']:
      bids[entry[2]] = {'price':entry[0], 'size':entry[1]}
    self.book['bids'] = bids

    Thread(target=self._real_time_thread, args=[]).start()

  def _get_key(self, message):
    if message['side'] == 'buy':
      return 'bids'
    else:
      return 'asks'

  def _get_order_id(self, message):
    return message['order_id']

  def _handle_open(self, message):
    key = self._get_key(message)
    self.book[key][self._get_order_id(message)] = {
      'price':message['price'],
      'size':message['remaining_size']
    }

  def _handle_match(self, message):
    maker = message['maker_order_id']
    key = self._get_key(message)
    if maker in self.book[key]:
      left = float(self.book[key][maker]['size'])
      right = float(message['size'])
      self.book[key][maker]['size'] = str(left - right)

  def _handle_done(self, message):
    if message['order_type'] == 'market':
      return
    key = self._get_key(message)
    self.book[key].pop(self._get_order_id(message), None)

  def _handle_change(self, message):
    if message['price'] is None: 
      return
    if 'new_funds' in message:   
      return
    key = self._get_key(message)
    order_id = self._get_order_id(message)
    if order_id in self.book[key]:
      self.book[key][order_id]['size'] = message['remaining_size']

  def _real_time_thread(self):
    

    while self.ws_client.connected():
      if self.die:
        break
      
      if self.pause:
        sleep(5)
        continue

      message = self.ws_client.receive()

      if message is None:
        break

      message_type = message['type']

      if message_type  == 'error':
        continue
      if message['sequence'] <= self.sequence:
        continue

      if message_type == 'open':
        self._handle_open(message)
      elif message_type == 'match':
        self._handle_match(message)
      elif message_type == 'done':
        self._handle_done(message)
      elif message_type == 'change':
        self._handle_change(message)
      else:
        continue

    self.ws_client.disconnect()

  def __enter__(self):
    return self
  
  def __exit__(self, type, value, traceback):
    self.end()

  def __str__(self):
    if self.book:
      return dumps(self.book, indent=2, sort_keys=True)
    else:
      return str(self.book)

  def end(self):
    

    self.die = True

  def pause(self):
    

    self.pause = True
  
  def resume(self):
    

    self.pause = False

  def get_order_book(self):
    

    return self.book
