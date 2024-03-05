from . channel_order import ChannelOrder
from .. colors import gamma as _gamma
from .. project import attributes, clock, data_maker, fields
import threading, time


class DriverBase(object):
    


    
    
    
    
    
    set_device_brightness = None

    pre_recursion = fields.default_converter

    @classmethod
    def construct(cls, project, **desc):
        

        return cls(maker=project.maker, **desc)

    def __init__(self, num=0, width=0, height=0, c_order="RGB",
                 gamma=None, maker=data_maker.MAKER, **kwds):
        attributes.set_reserved(self, 'driver', **kwds)

        if num == 0:
            num = width * height
            if num == 0:
                raise ValueError("Either num, or width and height are needed!")

        self.numLEDs = num
        gamma = gamma or _gamma.DEFAULT
        self.gamma = gamma

        if isinstance(c_order, str):
            c_order = ChannelOrder.make(c_order)

        self.c_order = c_order
        self.perm = ChannelOrder.ORDERS.index(c_order)

        self.pixel_positions = None

        self.width = width
        self.height = height
        self.maker = maker
        self._buf = self._make_buffer()

        self.lastUpdate = 0
        self.brightness_lock = threading.Lock()
        self._brightness = 255
        self._waiting_brightness = None
        self.clock = clock.Clock()

    def set_pixel_positions(self, pixel_positions):
        

        pass

    def set_colors(self, colors, pos):
        

        self._colors = colors
        self._pos = pos

        end = self._pos + self.numLEDs
        if end > len(self._colors):
            raise ValueError('Needed %d colors but found %d' % (
                end, len(self._colors)))

    def set_project(self, project):
        self.clock = project.clock

    def start(self):
        


    def stop(self):
        


    def cleanup(self):
        


    def join(self, timeout=None):
        


    def bufByteCount(self):
        

        return 3 * self.numLEDs

    def sync(self):
        


    def _compute_packet(self):
        


    def _send_packet(self):
        


    def update_colors(self):
        

        start = self.clock.time()

        with self.brightness_lock:
            
            brightness, self._waiting_brightness = (
                self._waiting_brightness, None)

        if brightness is not None:
            self._brightness = brightness
            if self.set_device_brightness:
                self.set_device_brightness(brightness)

        self._compute_packet()
        self._send_packet()

        self.lastUpdate = self.clock.time() - start

    def set_brightness(self, brightness):
        

        with self.brightness_lock:
            self._waiting_brightness = brightness

    def _render(self):
        

        if self.set_device_brightness:
            level = 1.0
        else:
            level = self._brightness / 255.0
        gam, (r, g, b) = self.gamma.get, self.c_order
        for i in range(min(self.numLEDs, len(self._buf) / 3)):
            c = [int(level * x) for x in self._colors[i + self._pos]]
            self._buf[i * 3:(i + 1) * 3] = gam(c[r]), gam(c[g]), gam(c[b])

    def _make_buffer(self):
        return self.maker.bytes(self.bufByteCount())
