NUMBER = 1
TEXT = 2
BOOLEAN = 3
BUTTON = 4

DEFAULT_STEPS = 256.0


def clamp(minvalue, value, maxvalue):
    return max(minvalue, min(value, maxvalue))


class Variable(object):
    


    def __init__(self, name, type, **kwargs):
        

        self.name = name
        if not isinstance(name, basestring):
            raise AttributeError("Variable name must be a string")
        if kwargs.get("step") and kwargs.get("steps"):
            raise AttributeError("Can only set step or steps")  
        self.type = type or NUMBER
        self.min = None
        self.max = None
        self.step = None or kwargs.get("step")
        self.steps = kwargs.get("steps", DEFAULT_STEPS)
        if self.type == NUMBER:
            self.min = kwargs.get("min", 0.0)
            self.max = kwargs.get("max", 100.0)
            if self.step is None:
                diff = max(self.min, self.max) - min(self.min, self.max)
                self.step = (diff / float(self.steps))
            self.default = kwargs.get("default")
            if self.default is None:
                self.default = self.min
        elif self.type == TEXT:
            self.default = kwargs.get("default", "bonjour")
        elif self.type == BOOLEAN:
            self.default = kwargs.get("default", True)
        elif self.type == BUTTON:
            self.default = kwargs.get("default", self.name)
        else:
            raise AttributeError("Variables must be of type NUMBER, TEXT, BOOLEAN or BUTTON")
        self.value = kwargs.get("value", self.default)
        if self.value is None and self.default is not None:
            self.value = self.default

    def sanitize(self, val):
        

        if self.type == NUMBER:
            try:
                return clamp(self.min, self.max, float(val))
            except ValueError:
                return 0.0
        elif self.type == TEXT:
            try:
                return unicode(str(val), "utf_8", "replace")
            except:
                return ""
        elif self.type == BOOLEAN:
            if unicode(val).lower() in ("true", "1", "yes"):
                return True
            else:
                return False

    def compliesTo(self, v):
        

        if self.type == v.type:
            if self.type == NUMBER:
                if self.value < self.min or self.value > self.max:
                    return False
            return True
        return False

    def __repr__(self):
        return "Variable(name=%s, type=%s, default=%s, min=%s, max=%s, value=%s)" % \
               (self.name, self.type, self.default, self.min, self.max, self.value)
