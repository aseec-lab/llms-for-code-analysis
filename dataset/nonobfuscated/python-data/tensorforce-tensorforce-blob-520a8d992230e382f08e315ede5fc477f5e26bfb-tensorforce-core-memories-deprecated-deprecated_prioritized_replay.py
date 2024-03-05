















from __future__ import absolute_import
from __future__ import print_function
from __future__ import division

import random
from six.moves import xrange
import numpy as np
from collections import namedtuple

from tensorforce import util, TensorForceError
from tensorforce.core.memories import Memory

_SumRow = namedtuple('SumRow', ['item', 'priority'])




class SumTree(object):
    


    def __init__(self, capacity):
        self._capacity = capacity

        
        self._memory = [0] * (capacity - 1)
        self._position = 0
        self._actual_capacity = 2 * self._capacity - 1

    def put(self, item, priority=None):
        

        if not self._isfull():
            self._memory.append(None)
        position = self._next_position_then_increment()
        old_priority = 0 if self._memory[position] is None \
            else (self._memory[position].priority or 0)
        row = _SumRow(item, priority)
        self._memory[position] = row
        self._update_internal_nodes(
            position, (row.priority or 0) - old_priority)

    def move(self, external_index, new_priority):
        

        index = external_index + (self._capacity - 1)
        return self._move(index, new_priority)

    def _move(self, index, new_priority):
        

        item, old_priority = self._memory[index]
        old_priority = old_priority or 0
        self._memory[index] = _SumRow(item, new_priority)
        self._update_internal_nodes(index, new_priority - old_priority)

    def _update_internal_nodes(self, index, delta):
        

        
        while index > 0:
            index = (index - 1) // 2
            self._memory[index] += delta

    def _isfull(self):
        return len(self) == self._capacity

    def _next_position_then_increment(self):
        

        start = self._capacity - 1
        position = start + self._position
        self._position = (self._position + 1) % self._capacity
        return position

    def _sample_with_priority(self, p):
        

        parent = 0
        while True:
            left = 2 * parent + 1
            if left >= len(self._memory):
                
                return parent

            left_p = self._memory[left] if left < self._capacity - 1 \
                else (self._memory[left].priority or 0)
            if p <= left_p:
                parent = left
            else:
                if left + 1 >= len(self._memory):
                    raise RuntimeError('Right child is expected to exist.')
                p -= left_p
                parent = left + 1

    def sample_minibatch(self, batch_size):
        

        pool_size = len(self)
        if pool_size == 0:
            return []

        delta_p = self._memory[0] / batch_size
        chosen_idx = []
        
        if abs(self._memory[0]) < util.epsilon:
            chosen_idx = np.random.randint(self._capacity - 1, self._capacity - 1 + len(self), size=batch_size).tolist()
        else:
            for i in xrange(batch_size):
                lower = max(i * delta_p, 0)
                upper = min((i + 1) * delta_p, self._memory[0])
                p = random.uniform(lower, upper)
                chosen_idx.append(self._sample_with_priority(p))
        return [(i, self._memory[i]) for i in chosen_idx]

    def __len__(self):
        

        return len(self._memory) - (self._capacity - 1)

    def __getitem__(self, index):
        return self._memory[self._capacity - 1:][index]

    def __getslice__(self, start, end):
        self.memory[self._capacity - 1:][start:end]



class PrioritizedReplay(Memory):
    


    def __init__(self, states_spec, actions_spec, capacity, prioritization_weight=1.0, prioritization_constant=0.0):
        super(PrioritizedReplay, self).__init__(states_spec=states_spec, actions_spec=actions_spec)
        self.capacity = capacity
        self.prioritization_weight = prioritization_weight
        self.prioritization_constant = prioritization_constant
        self.internals_spec = None
        self.batch_indices = None

        
        self.observations = SumTree(capacity)

        
        self.none_priority_index = 0

        
        self.last_observation = None

    def add_observation(self, states, internals, actions, terminal, reward):
        if self.internals_spec is None and internals is not None:
            self.internals_spec = [(internal.shape, internal.dtype) for internal in internals]

        if self.last_observation is not None:
            observation = self.last_observation + (states, internals)

            
            if self.observations._isfull():
                if self.none_priority_index <= 0:
                    raise TensorForceError(
                        "Trying to replace unseen observations: "
                        "Memory is at capacity and contains only unseen observations."
                    )
                self.none_priority_index -= 1

            self.observations.put(observation, None)

        self.last_observation = (states, internals, actions, terminal, reward)

    def get_batch(self, batch_size, next_states=False):
        

        if batch_size > len(self.observations):
            raise TensorForceError(
                "Requested batch size is larger than observations in memory: increase config.first_update.")

        
        states = {name: np.zeros((batch_size,) + tuple(state['shape']), dtype=util.np_dtype(
            state['type'])) for name, state in self.states_spec.items()}
        internals = [np.zeros((batch_size,) + shape, dtype)
                     for shape, dtype in self.internals_spec]
        actions = {name: np.zeros((batch_size,) + tuple(action['shape']), dtype=util.np_dtype(action['type'])) for name, action in self.actions_spec.items()}
        terminal = np.zeros((batch_size,), dtype=util.np_dtype('bool'))
        reward = np.zeros((batch_size,), dtype=util.np_dtype('float'))
        if next_states:
            next_states = {name: np.zeros((batch_size,) + tuple(state['shape']), dtype=util.np_dtype(
                state['type'])) for name, state in self.states_spec.items()}
            next_internals = [np.zeros((batch_size,) + shape, dtype)
                              for shape, dtype in self.internals_spec]

        
        unseen_indices = list(xrange(
            self.none_priority_index + self.observations._capacity - 1,
            len(self.observations) + self.observations._capacity - 1)
        )
        self.batch_indices = unseen_indices[:batch_size]

        
        remaining = batch_size - len(self.batch_indices)
        if remaining:
            samples = self.observations.sample_minibatch(remaining)
            sample_indices = [i for i, o in samples]
            self.batch_indices += sample_indices

        
        np.random.shuffle(self.batch_indices)

        
        for n, index in enumerate(self.batch_indices):
            observation, _ = self.observations._memory[index]

            for name, state in states.items():
                state[n] = observation[0][name]
            for k, internal in enumerate(internals):
                internal[n] = observation[1][k]
            for name, action in actions.items():
                action[n] = observation[2][name]
            terminal[n] = observation[3]
            reward[n] = observation[4]
            if next_states:
                for name, next_state in next_states.items():
                    next_state[n] = observation[5][name]
                for k, next_internal in enumerate(next_internals):
                    next_internal[n] = observation[6][k]

        if next_states:
            return dict(
                states=states,
                internals=internals,
                actions=actions,
                terminal=terminal,
                reward=reward,
                next_states=next_states,
                next_internals=next_internals
            )
        else:
            return dict(
                states=states,
                internals=internals,
                actions=actions,
                terminal=terminal,
                reward=reward
            )

    def update_batch(self, loss_per_instance):
        

        if self.batch_indices is None:
            raise TensorForceError("Need to call get_batch before each update_batch call.")
        
        

        for index, loss in zip(self.batch_indices, loss_per_instance):
            
            new_priority = (np.abs(loss) + self.prioritization_constant) ** self.prioritization_weight
            self.observations._move(index, new_priority)
            self.none_priority_index += 1
