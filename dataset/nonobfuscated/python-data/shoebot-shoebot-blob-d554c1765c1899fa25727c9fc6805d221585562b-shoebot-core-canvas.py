































from collections import deque
from abc import ABCMeta, abstractproperty
import sys
import locale
import gettext
from shoebot.core.drawqueue import DrawQueue

APP = 'shoebot'
DIR = sys.prefix + '/share/shoebot/locale'
locale.setlocale(locale.LC_ALL, '')
gettext.bindtextdomain(APP, DIR)

gettext.textdomain(APP)
_ = gettext.gettext

CENTER = 'center'
CORNER = 'corner'

TOP_LEFT = 1
BOTTOM_LEFT = 2


class Canvas(object):
    __metaclass__ = ABCMeta

    DEFAULT_SIZE = 400, 400
    DEFAULT_MODE = CENTER

    

    def __init__(self, sink):
        
        self.sink = sink

        self.finished = False
        self.color_range = 1
        self.color_mode = 1
        self.path_mode = CORNER
        self.size = None
        self.reset_canvas()

    def set_bot(self, bot):
        

        self.bot = bot
        self.sink.set_bot(bot)

    def get_input_device(self):
        

        return None

    def initial_drawqueue(self):
        

        return DrawQueue()

    def initial_transform(self):
        

        pass

    @abstractproperty
    def reset_drawqueue(self):
        pass

    @abstractproperty
    def reset_transform(self):
        pass

    def reset_canvas(self):
        self.reset_transform()
        self.reset_drawqueue()
        self.matrix_stack = deque()

    def settings(self, **kwargs):
        

        for k, v in kwargs.items():
            setattr(self, k, v)

    def size_or_default(self):
        

        if not self.size:
            self.size = self.DEFAULT_SIZE
        return self.size

    def set_size(self, size):
        

        if self.size is None:
            self.size = size
            return size
        else:
            return self.size

    def get_width(self):
        if self.size is not None:
            return self.size[0]
        else:
            return self.DEFAULT_SIZE[0]

    def get_height(self):
        if self.size is not None:
            return self.size[1]
        else:
            return self.DEFAULT_SIZE[1]

    def snapshot(self, target, defer=True, file_number=None):
        

        output_func = self.output_closure(target, file_number)
        if defer:
            self._drawqueue.append(output_func)
        else:
            self._drawqueue.append_immediate(output_func)

    def flush(self, frame):
        

        self.sink.render(self.size_or_default(), frame, self._drawqueue)
        self.reset_drawqueue()

    def deferred_render(self, render_func):
        

        self._drawqueue.append(render_func)

    width = property(get_width)
    height = property(get_height)
