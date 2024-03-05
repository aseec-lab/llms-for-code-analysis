


class MPEventLoopRunner(object):

    


    def __init__(self, communicationChannel):
        self.communicationChannel = communicationChannel
        self.nruns = 0

    def __repr__(self):
        return '{}(communicationChannel = {!r}'.format(
            self.__class__.__name__,
            self.communicationChannel
        )

    def begin(self):
        

        pass

    def run(self, eventLoop):
        


        self.nruns += 1
        return self.communicationChannel.put(eventLoop)

    def run_multiple(self, eventLoops):
        


        self.nruns += len(eventLoops)
        return self.communicationChannel.put_multiple(eventLoops)

    def poll(self):
        

        ret = self.communicationChannel.receive_finished()
        self.nruns -= len(ret)
        return ret

    def receive_one(self):
        

        if self.nruns == 0:
            return None
        ret = self.communicationChannel.receive_one()
        if ret is not None:
            self.nruns -= 1
        return ret

    def receive(self):
        

        ret = self.communicationChannel.receive_all()
        self.nruns -= len(ret)
        if self.nruns > 0:
            import logging
            logger = logging.getLogger(__name__)
            logger.warning(
                'too few results received: {} results received, {} more expected'.format(
                    len(ret), self.nruns))
        elif self.nruns < 0:
            import logging
            logger = logging.getLogger(__name__)
            logger.warning(
                'too many results received: {} results received, {} too many'.format(
                    len(ret), -self.nruns))
        return ret

    def end(self):
        


        results = self.communicationChannel.receive()

        if self.nruns != len(results):
            import logging
            logger = logging.getLogger(__name__)
            
            logger.warning(
                'too few results received: {} results received, {} expected'.format(
                    len(results),
                    self.nruns
                ))

        return results


