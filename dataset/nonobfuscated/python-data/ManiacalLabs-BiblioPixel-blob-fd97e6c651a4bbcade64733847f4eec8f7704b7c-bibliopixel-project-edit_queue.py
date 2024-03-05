import functools, queue, traceback
from .. util import log


class EditQueue(queue.Queue):
    


    def put_edit(self, f, *args, **kwds):
        

        self.put_nowait(functools.partial(f, *args, **kwds))

    def get_and_run_edits(self):
        

        if self.empty():
            return

        edits = []
        while True:
            try:
                edits.append(self.get_nowait())
            except queue.Empty:
                break

        for e in edits:
            try:
                e()
            except:
                log.error('Error on edit %s', e)
                traceback.print_exc()
