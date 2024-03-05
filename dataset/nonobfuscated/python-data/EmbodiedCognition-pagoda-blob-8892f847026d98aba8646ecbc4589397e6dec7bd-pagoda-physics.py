


from __future__ import division

import collections
import numpy as np
import ode


BodyState = collections.namedtuple(
    'BodyState', 'name position quaternion linear_velocity angular_velocity')


class Registrar(type):
    


    def __init__(cls, name, bases, dct):
        if not hasattr(cls, '_registry'):
            cls._registry = {}
        else:
            key = name.lower()
            for i in range(3, len(name) + 1):
                cls._registry[key[:i]] = cls
        super(Registrar, cls).__init__(name, bases, dct)

    def build(cls, key, *args, **kwargs):
        return cls._registry[key.lower()](*args, **kwargs)


class Body(Registrar(str('Base'), (), {})):
    


    def __init__(self, name, world, density=1000., mass=None, **shape):
        self.name = name
        self.world = world
        self.shape = shape

        m = ode.Mass()
        self.init_mass(m, density, mass)
        self.ode_body = ode.Body(world.ode_world)
        self.ode_body.setMass(m)
        self.ode_geom = getattr(ode, 'Geom%s' % self.__class__.__name__)(
            world.ode_space, **shape)
        self.ode_geom.setBody(self.ode_body)

    def __str__(self):
        return '{0.__class__.__name__} {0.name} at {1}'.format(
            self, self.position.round(3))

    @property
    def mass(self):
        

        return self.ode_body.getMass()

    @property
    def state(self):
        

        return BodyState(self.name,
                         tuple(self.position),
                         tuple(self.quaternion),
                         tuple(self.linear_velocity),
                         tuple(self.angular_velocity))

    @state.setter
    def state(self, state):
        

        assert self.name == state.name, \
            'state name "{}" != body name "{}"'.format(state.name, self.name)
        self.position = state.position
        self.quaternion = state.quaternion
        self.linear_velocity = state.linear_velocity
        self.angular_velocity = state.angular_velocity

    @property
    def position(self):
        

        return np.array(self.ode_body.getPosition())

    @position.setter
    def position(self, position):
        

        self.ode_body.setPosition(tuple(position))

    @property
    def rotation(self):
        

        return np.array(self.ode_body.getRotation()).reshape((3, 3))

    @rotation.setter
    def rotation(self, rotation):
        

        if isinstance(rotation, np.ndarray):
            rotation = rotation.ravel()
        self.ode_body.setRotation(tuple(rotation))

    @property
    def quaternion(self):
        

        return np.array(self.ode_body.getQuaternion())

    @quaternion.setter
    def quaternion(self, quaternion):
        self.ode_body.setQuaternion(tuple(quaternion))

    @property
    def linear_velocity(self):
        

        return np.array(self.ode_body.getLinearVel())

    @linear_velocity.setter
    def linear_velocity(self, velocity):
        

        self.ode_body.setLinearVel(tuple(velocity))

    @property
    def angular_velocity(self):
        

        return np.array(self.ode_body.getAngularVel())

    @angular_velocity.setter
    def angular_velocity(self, velocity):
        

        self.ode_body.setAngularVel(tuple(velocity))

    @property
    def force(self):
        

        return np.array(self.ode_body.getForce())

    @force.setter
    def force(self, force):
        

        self.ode_body.setForce(tuple(force))

    @property
    def torque(self):
        

        return np.array(self.ode_body.getTorque())

    @torque.setter
    def torque(self, torque):
        

        self.ode_body.setTorque(tuple(torque))

    @property
    def is_kinematic(self):
        

        return self.ode_body.isKinematic()

    @is_kinematic.setter
    def is_kinematic(self, is_kinematic):
        

        if is_kinematic:
            self.ode_body.setKinematic()
        else:
            self.ode_body.setDynamic()

    @property
    def follows_gravity(self):
        

        return self.ode_body.getGravityMode()

    @follows_gravity.setter
    def follows_gravity(self, follows_gravity):
        

        self.ode_body.setGravityMode(follows_gravity)

    def rotate_to_body(self, x):
        

        return np.dot(x, self.rotation)

    def body_to_world(self, position):
        

        return np.array(self.ode_body.getRelPointPos(tuple(position)))

    def world_to_body(self, position):
        

        return np.array(self.ode_body.getPosRelPoint(tuple(position)))

    def relative_offset_to_world(self, offset):
        

        return np.array(self.body_to_world(offset * self.dimensions / 2))

    def add_force(self, force, relative=False, position=None, relative_position=None):
        

        b = self.ode_body
        if relative_position is not None:
            op = b.addRelForceAtRelPos if relative else b.addForceAtRelPos
            op(force, relative_position)
        elif position is not None:
            op = b.addRelForceAtPos if relative else b.addForceAtPos
            op(force, position)
        else:
            op = b.addRelForce if relative else b.addForce
            op(force)

    def add_torque(self, torque, relative=False):
        

        op = self.ode_body.addRelTorque if relative else self.ode_body.addTorque
        op(torque)

    def join_to(self, joint, other_body=None, **kwargs):
        

        self.world.join(joint, self, other_body, **kwargs)

    def connect_to(self, joint, other_body, offset=(0, 0, 0), other_offset=(0, 0, 0),
                   **kwargs):
        

        anchor = self.world.move_next_to(self, other_body, offset, other_offset)
        self.world.join(joint, self, other_body, anchor=anchor, **kwargs)


class Box(Body):
    @property
    def lengths(self):
        return self.shape['lengths']

    @property
    def dimensions(self):
        return np.array(self.lengths).squeeze()

    @property
    def volume(self):
        return np.prod(self.lengths)

    def init_mass(self, m, density, mass):
        if mass:
            density = mass / self.volume
        m.setBox(density, *self.lengths)


class Sphere(Body):
    @property
    def radius(self):
        return self.shape['radius']

    @property
    def dimensions(self):
        d = 2 * self.radius
        return np.array([d, d, d]).squeeze()

    @property
    def volume(self):
        return 4 / 3 * np.pi * self.radius ** 3

    def init_mass(self, m, density, mass):
        if mass:
            density = mass / self.volume
        m.setSphere(density, self.radius)


class Cylinder(Body):
    @property
    def radius(self):
        return self.shape['radius']

    @property
    def length(self):
        return self.shape['length']

    @property
    def dimensions(self):
        d = 2 * self.radius
        return np.array([d, d, self.length]).squeeze()

    @property
    def volume(self):
        return self.length * np.pi * self.radius ** 2

    def init_mass(self, m, density, mass):
        if mass:
            density = mass / self.volume
        m.setCylinder(density, 3, self.radius, self.length)


class Capsule(Body):
    @property
    def radius(self):
        return self.shape['radius']

    @property
    def length(self):
        return self.shape['length']

    @property
    def dimensions(self):
        d = 2 * self.radius
        return np.array([d, d, d + self.length]).squeeze()

    @property
    def volume(self):
        return 4 / 3 * np.pi * self.radius ** 3 + \
            self.length * np.pi * self.radius ** 2

    def init_mass(self, m, density, mass):
        if mass:
            density = mass / self.volume
        m.setCapsule(density, 3, self.radius, self.length)


def _get_params(target, param, dof):
    

    return [target.getParam(getattr(ode, 'Param{}{}'.format(param, s)))
            for s in ['', '2', '3'][:dof]]


def _set_params(target, param, values, dof):
    

    if not isinstance(values, (list, tuple, np.ndarray)):
        values = [values] * dof
    assert dof == len(values)
    for s, value in zip(['', '2', '3'][:dof], values):
        target.setParam(getattr(ode, 'Param{}{}'.format(param, s)), value)


class Joint(Registrar(str('Base'), (), {})):
    


    ADOF = 0
    LDOF = 0

    @property
    def feedback(self):
        

        return self.ode_obj.getFeedback()

    @property
    def positions(self):
        

        return [self.ode_obj.getPosition(i) for i in range(self.LDOF)]

    @property
    def position_rates(self):
        

        return [self.ode_obj.getPositionRate(i) for i in range(self.LDOF)]

    @property
    def angles(self):
        

        return [self.ode_obj.getAngle(i) for i in range(self.ADOF)]

    @property
    def angle_rates(self):
        

        return [self.ode_obj.getAngleRate(i) for i in range(self.ADOF)]

    @property
    def axes(self):
        

        return [np.array(self.ode_obj.getAxis(i))
                for i in range(self.ADOF or self.LDOF)]

    @axes.setter
    def axes(self, axes):
        

        assert self.ADOF == len(axes) or self.LDOF == len(axes)
        for i, axis in enumerate(axes):
            if axis is not None:
                self.ode_obj.setAxis(i, 0, axis)

    @property
    def lo_stops(self):
        

        return _get_params(self.ode_obj, 'LoStop', self.ADOF + self.LDOF)

    @lo_stops.setter
    def lo_stops(self, lo_stops):
        

        _set_params(self.ode_obj, 'LoStop', lo_stops, self.ADOF + self.LDOF)

    @property
    def hi_stops(self):
        

        return _get_params(self.ode_obj, 'HiStop', self.ADOF + self.LDOF)

    @hi_stops.setter
    def hi_stops(self, hi_stops):
        

        _set_params(self.ode_obj, 'HiStop', hi_stops, self.ADOF + self.LDOF)

    @property
    def velocities(self):
        

        return _get_params(self.ode_obj, 'Vel', self.ADOF + self.LDOF)

    @velocities.setter
    def velocities(self, velocities):
        

        _set_params(self.ode_obj, 'Vel', velocities, self.ADOF + self.LDOF)

    @property
    def max_forces(self):
        

        return _get_params(self.ode_obj, 'FMax', self.ADOF + self.LDOF)

    @max_forces.setter
    def max_forces(self, max_forces):
        

        _set_params(self.ode_obj, 'FMax', max_forces, self.ADOF + self.LDOF)

    @property
    def erps(self):
        

        return _get_params(self.ode_obj, 'ERP', self.ADOF + self.LDOF)

    @erps.setter
    def erps(self, erps):
        

        _set_params(self.ode_obj, 'ERP', erps, self.ADOF + self.LDOF)

    @property
    def cfms(self):
        

        return _get_params(self.ode_obj, 'CFM', self.ADOF + self.LDOF)

    @cfms.setter
    def cfms(self, cfms):
        

        _set_params(self.ode_obj, 'CFM', cfms, self.ADOF + self.LDOF)

    @property
    def stop_cfms(self):
        

        return _get_params(self.ode_obj, 'StopCFM', self.ADOF + self.LDOF)

    @stop_cfms.setter
    def stop_cfms(self, stop_cfms):
        

        _set_params(self.ode_obj, 'StopCFM', stop_cfms, self.ADOF + self.LDOF)

    @property
    def stop_erps(self):
        

        return _get_params(self.ode_obj, 'StopERP', self.ADOF + self.LDOF)

    @stop_erps.setter
    def stop_erps(self, stop_erps):
        

        _set_params(self.ode_obj, 'StopERP', stop_erps, self.ADOF + self.LDOF)

    def enable_feedback(self):
        

        self.ode_obj.setFeedback(True)

    def disable_feedback(self):
        

        self.ode_obj.setFeedback(False)


class Dynamic(Joint):
    


    def __init__(self, name, world, body_a, body_b=None, feedback=False, dof=3,
                 jointgroup=None):
        self.name = name
        self.ode_obj = self.MOTOR_FACTORY(world.ode_world, jointgroup=jointgroup)
        self.ode_obj.attach(body_a.ode_body, body_b.ode_body if body_b else None)
        self.ode_obj.setNumAxes(dof)
        self.cfms = 1e-8
        if feedback:
            self.enable_feedback()
        else:
            self.disable_feedback()


class AMotor(Dynamic):
    


    MOTOR_FACTORY = ode.AMotor

    def __init__(self, *args, **kwargs):
        mode = kwargs.pop('mode', 'user')
        if isinstance(mode, str):
            mode = ode.AMotorEuler if mode.lower() == 'euler' else ode.AMotorUser
        super(AMotor, self).__init__(*args, **kwargs)
        self.ode_obj.setMode(mode)

    @property
    def ADOF(self):
        

        return self.ode_obj.getNumAxes()

    @property
    def axes(self):
        

        return [np.array(self.ode_obj.getAxis(i)) for i in range(self.ADOF)]

    @axes.setter
    def axes(self, axes):
        

        assert len(axes) == self.ADOF
        for i, ax in enumerate(axes):
            if ax is None:
                continue
            if not isinstance(ax, dict):
                ax = dict(axis=ax)
            self.ode_obj.setAxis(i, ax.get('rel', 0), ax['axis'])

    def add_torques(self, torques):
        

        self.ode_obj.addTorques(*torques)


class LMotor(Dynamic):
    


    MOTOR_FACTORY = ode.LMotor

    @property
    def LDOF(self):
        

        return self.ode_obj.getNumAxes()


class Kinematic(Joint):
    


    def __init__(self, name, world, body_a, body_b=None, anchor=None,
                 feedback=False, jointgroup=None, amotor=True, lmotor=True):
        self.name = name

        build = getattr(ode, '{}Joint'.format(self.__class__.__name__))
        self.ode_obj = build(world.ode_world, jointgroup=jointgroup)
        self.ode_obj.attach(body_a.ode_body, body_b.ode_body if body_b else None)
        if anchor is not None:
            self.ode_obj.setAnchor(tuple(anchor))
            self.ode_obj.setParam(ode.ParamCFM, 0)

        self.amotor = None
        if self.ADOF > 0 and amotor:
            self.amotor = AMotor(name=name + ':amotor',
                                 world=world,
                                 body_a=body_a,
                                 body_b=body_b,
                                 feedback=feedback,
                                 jointgroup=jointgroup,
                                 dof=self.ADOF,
                                 mode='euler' if self.ADOF == 3 else 'user')

        self.lmotor = None
        if self.LDOF > 0 and lmotor:
            self.lmotor = LMotor(name=name + ':lmotor',
                                 world=world,
                                 body_a=body_a,
                                 body_b=body_b,
                                 feedback=feedback,
                                 jointgroup=jointgroup,
                                 dof=self.LDOF)

        if feedback:
            self.enable_feedback()
        else:
            self.disable_feedback()

    def __str__(self):
        return self.name

    @property
    def anchor(self):
        

        return np.array(self.ode_obj.getAnchor())

    @property
    def anchor2(self):
        

        return np.array(self.ode_obj.getAnchor2())

    def add_torques(self, *torques):
        

        self.amotor.add_torques(*torques)


class Fixed(Kinematic):
    ADOF = 0
    LDOF = 0


class Slider(Kinematic):
    ADOF = 0
    LDOF = 1

    @property
    def positions(self):
        

        return [self.ode_obj.getPosition()]

    @property
    def position_rates(self):
        

        return [self.ode_obj.getPositionRate()]

    @property
    def axes(self):
        

        return [np.array(self.ode_obj.getAxis())]

    @axes.setter
    def axes(self, axes):
        

        self.lmotor.axes = [axes[0]]
        self.ode_obj.setAxis(tuple(axes[0]))


class Hinge(Kinematic):
    ADOF = 1
    LDOF = 0

    @property
    def angles(self):
        

        return [self.ode_obj.getAngle()]

    @property
    def angle_rates(self):
        

        return [self.ode_obj.getAngleRate()]

    @property
    def axes(self):
        

        return [np.array(self.ode_obj.getAxis())]

    @axes.setter
    def axes(self, axes):
        

        self.amotor.axes = [axes[0]]
        self.ode_obj.setAxis(tuple(axes[0]))


class Piston(Kinematic):
    ADOF = 1
    LDOF = 1

    @property
    def axes(self):
        

        return [np.array(self.ode_obj.getAxis())]

    @axes.setter
    def axes(self, axes):
        self.amotor.axes = [axes[0]]
        self.lmotor.axes = [axes[0]]
        self.ode_obj.setAxis(axes[0])


class Universal(Kinematic):
    ADOF = 2
    LDOF = 0

    @property
    def axes(self):
        

        return [np.array(self.ode_obj.getAxis1()),
                np.array(self.ode_obj.getAxis2())]

    @axes.setter
    def axes(self, axes):
        self.amotor.axes = [axes[0], axes[1]]
        setters = [self.ode_obj.setAxis1, self.ode_obj.setAxis2]
        for axis, setter in zip(axes, setters):
            if axis is not None:
                setter(tuple(axis))

    @property
    def angles(self):
        

        return [self.ode_obj.getAngle1(), self.ode_obj.getAngle2()]

    @property
    def angle_rates(self):
        

        return [self.ode_obj.getAngle1Rate(), self.ode_obj.getAngle2Rate()]


class Ball(Kinematic):
    ADOF = 3
    LDOF = 0

    def __init__(self, name, *args, **kwargs):
        super(Ball, self).__init__(name, *args, **kwargs)

        
        
        keys = 'name world body_a body_b feedback dof jointgroup'.split()
        self.alimit = AMotor(name + ':alimit', *args, dof=self.ADOF, mode='euler',
                             **{k: v for k, v in kwargs.items() if k in keys})

    @property
    def angles(self):
        return self.alimit.angles

    @property
    def angle_rates(self):
        return self.alimit.angle_rates

    @property
    def axes(self):
        return self.alimit.axes

    @axes.setter
    def axes(self, axes):
        if len(axes) == 2:
            axes = dict(rel=1, axis=axes[0]), None, dict(rel=2, axis=axes[1])
        self.amotor.axes = axes
        self.alimit.axes = axes

    @property
    def lo_stops(self):
        return self.alimit.lo_stops

    @lo_stops.setter
    def lo_stops(self, lo_stops):
        self.alimit.lo_stops = lo_stops

    @property
    def hi_stops(self):
        return self.alimit.hi_stops

    @hi_stops.setter
    def hi_stops(self, hi_stops):
        self.alimit.hi_stops = hi_stops


def make_quaternion(theta, *axis):
    

    x, y, z = axis
    r = np.sqrt(x * x + y * y + z * z)
    st = np.sin(theta / 2.)
    ct = np.cos(theta / 2.)
    return [x * st / r, y * st / r, z * st / r, ct]


def center_of_mass(bodies):
    

    x = np.zeros(3.)
    t = 0.
    for b in bodies:
        m = b.mass
        x += b.body_to_world(m.c) * m.mass
        t += m.mass
    return x / t


class World(object):
    


    def __init__(self, dt=1. / 60, max_angular_speed=20):
        self.ode_world = ode.World()
        self.ode_world.setMaxAngularSpeed(max_angular_speed)
        self.ode_space = ode.QuadTreeSpace((0, 0, 0), (100, 100, 20), 10)
        self.ode_floor = ode.GeomPlane(self.ode_space, (0, 0, 1), 0)
        self.ode_contactgroup = ode.JointGroup()

        self.frame_no = 0
        self.dt = dt
        self.elasticity = 0.1
        self.friction = 2000
        self.gravity = 0, 0, -9.81
        self.cfm = 1e-6
        self.erp = 0.7

        self._bodies = {}
        self._joints = {}

    @property
    def gravity(self):
        

        return self.ode_world.getGravity()

    @gravity.setter
    def gravity(self, gravity):
        

        return self.ode_world.setGravity(gravity)

    @property
    def cfm(self):
        

        return self.ode_world.getCFM()

    @cfm.setter
    def cfm(self, cfm):
        

        return self.ode_world.setCFM(cfm)

    @property
    def erp(self):
        

        return self.ode_world.getERP()

    @erp.setter
    def erp(self, erp):
        

        return self.ode_world.setERP(erp)

    @property
    def bodies(self):
        

        for k in sorted(self._bodies):
            yield self._bodies[k]

    @property
    def joints(self):
        

        for k in sorted(self._joints):
            yield self._joints[k]

    def get_body(self, key):
        

        return self._bodies.get(key, key)

    def get_joint(self, key):
        

        return self._joints.get(key, None)

    def create_body(self, shape, name=None, **kwargs):
        

        shape = shape.lower()
        if name is None:
            for i in range(1 + len(self._bodies)):
                name = '{}{}'.format(shape, i)
                if name not in self._bodies:
                    break
        self._bodies[name] = Body.build(shape, name, self, **kwargs)
        return self._bodies[name]

    def join(self, shape, body_a, body_b=None, name=None, **kwargs):
        

        ba = self.get_body(body_a)
        bb = self.get_body(body_b)
        shape = shape.lower()
        if name is None:
            name = '{}^{}^{}'.format(ba.name, shape, bb.name if bb else '')
        self._joints[name] = Joint.build(
            shape, name, self, body_a=ba, body_b=bb, **kwargs)
        return self._joints[name]

    def move_next_to(self, body_a, body_b, offset_a, offset_b):
        

        ba = self.get_body(body_a)
        bb = self.get_body(body_b)
        if ba is None:
            return bb.relative_offset_to_world(offset_b)
        if bb is None:
            return ba.relative_offset_to_world(offset_a)
        anchor = ba.relative_offset_to_world(offset_a)
        offset = bb.relative_offset_to_world(offset_b)
        bb.position = bb.position + anchor - offset
        return anchor

    def get_body_states(self):
        

        return [b.state for b in self.bodies]

    def set_body_states(self, states):
        

        for state in states:
            self.get_body(state.name).state = state

    def step(self, substeps=2):
        

        self.frame_no += 1
        dt = self.dt / substeps
        for _ in range(substeps):
            self.ode_contactgroup.empty()
            self.ode_space.collide(None, self.on_collision)
            self.ode_world.step(dt)

    def needs_reset(self):
        

        return False

    def reset(self):
        

        pass

    def on_key_press(self, key, modifiers, keymap):
        

        if key == keymap.ENTER:
            self.reset()
            return True

    def are_connected(self, body_a, body_b):
        

        return bool(ode.areConnected(
            self.get_body(body_a).ode_body,
            self.get_body(body_b).ode_body))

    def on_collision(self, args, geom_a, geom_b):
        

        body_a = geom_a.getBody()
        body_b = geom_b.getBody()
        if ode.areConnected(body_a, body_b) or \
           (body_a and body_a.isKinematic()) or \
           (body_b and body_b.isKinematic()):
            return
        for c in ode.collide(geom_a, geom_b):
            c.setBounce(self.elasticity)
            c.setMu(self.friction)
            ode.ContactJoint(self.ode_world, self.ode_contactgroup, c).attach(
                geom_a.getBody(), geom_b.getBody())
