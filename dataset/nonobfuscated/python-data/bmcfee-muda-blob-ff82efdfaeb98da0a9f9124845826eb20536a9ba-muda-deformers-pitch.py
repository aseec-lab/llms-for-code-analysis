





import librosa
import pyrubberband as pyrb
import re
import numpy as np
import six
from copy import deepcopy

from ..base import BaseTransformer

__all__ = ['PitchShift', 'RandomPitchShift', 'LinearPitchShift']


def transpose(label, n_semitones):
    


    
    match = re.match(six.text_type('(?P<note>[A-G][b
                     six.text_type(label))

    if not match:
        return label

    note = match.group('note')

    new_note = librosa.midi_to_note(librosa.note_to_midi(note) + n_semitones,
                                    octave=False)

    return new_note + match.group('mod')


class AbstractPitchShift(BaseTransformer):
    


    def __init__(self):
        


        BaseTransformer.__init__(self)

        
        self._register('key_mode|chord|chord_harte', self.deform_note)
        self._register('pitch_contour', self.deform_contour)
        self._register('pitch_hz', self.deform_hz)
        self._register('pitch_midi', self.deform_midi)
        self._register('chord_roman|pitch_class', self.deform_tonic)

    def states(self, jam):
        mudabox = jam.sandbox.muda
        state = dict(tuning=librosa.estimate_tuning(y=mudabox._audio['y'],
                                                    sr=mudabox._audio['sr']))
        yield state

    @staticmethod
    def audio(mudabox, state):
        mudabox._audio['y'] = pyrb.pitch_shift(mudabox._audio['y'],
                                               mudabox._audio['sr'],
                                               state['n_semitones'])

    @staticmethod
    def deform_contour(annotation, state):
        scale = 2.0**(state['n_semitones']/12.0)
        for obs in annotation.pop_data():
            annotation.append(time=obs.time, duration=obs.duration,
                              confidence=obs.confidence,
                              value={'index':obs.value['index'],
                                     'frequency':scale*obs.value['frequency'],
                                     'voiced':obs.value['voiced']})

    @staticmethod
    def deform_hz(annotation, state):
        scale = 2.0**(state['n_semitones']/12.0)
        for obs in annotation.pop_data():
                    annotation.append(time=obs.time, duration=obs.duration,
                                      confidence=obs.confidence,
                                      value=scale * obs.value)

    @staticmethod
    def deform_midi(annotation, state):
        for obs in annotation.pop_data():
            annotation.append(time=obs.time, duration=obs.duration,
                              confidence=obs.confidence,
                              value=obs.value + state['n_semitones'])

    @staticmethod
    def deform_tonic(annotation, state):
        
        if -0.5 < (state['tuning'] + state['n_semitones']) <= 0.5:
            
            
            return

        for obs in annotation.pop_data():
            value = deepcopy(obs.value)
            value['tonic'] = transpose(value['tonic'], state['n_semitones'])
            annotation.append(time=obs.time, duration=obs.duration,
                              confidence=obs.confidence,
                              value=value)

    @staticmethod
    def deform_note(annotation, state):
        
        if -0.5 < (state['tuning'] + state['n_semitones']) <= 0.5:
            
            
            return

        for obs in annotation.pop_data():
            annotation.append(time=obs.time, duration=obs.duration,
                              confidence=obs.confidence,
                              value=transpose(obs.value, state['n_semitones']))


class PitchShift(AbstractPitchShift):
    


    def __init__(self, n_semitones=1):
        AbstractPitchShift.__init__(self)
        self.n_semitones = np.atleast_1d(n_semitones).flatten().tolist()

    def states(self, jam):
        for state in AbstractPitchShift.states(self, jam):
            for semitones in self.n_semitones:
                state['n_semitones'] = semitones
                yield state


class RandomPitchShift(AbstractPitchShift):
    

    def __init__(self, n_samples=3, mean=0.0, sigma=1.0):
        AbstractPitchShift.__init__(self)

        if sigma <= 0:
            raise ValueError('sigma must be strictly positive')

        if n_samples <= 0:
            raise ValueError('n_samples must be None or positive')

        self.n_samples = n_samples
        self.mean = float(mean)
        self.sigma = float(sigma)

    def states(self, jam):
        
        for state in AbstractPitchShift.states(self, jam):
            for _ in range(self.n_samples):
                state['n_semitones'] = np.random.normal(loc=self.mean,
                                                        scale=self.sigma,
                                                        size=None)
                yield state


class LinearPitchShift(AbstractPitchShift):
    

    def __init__(self, n_samples=3, lower=-1, upper=1):
        AbstractPitchShift.__init__(self)

        if upper <= lower:
            raise ValueError('upper must be strictly larger than lower')

        if n_samples <= 0:
            raise ValueError('n_samples must be strictly positive')

        self.n_samples = n_samples
        self.lower = float(lower)
        self.upper = float(upper)

    def states(self, jam):
        shifts = np.linspace(self.lower,
                             self.upper,
                             num=self.n_samples,
                             endpoint=True)

        for state in AbstractPitchShift.states(self, jam):
            for n_semitones in shifts:
                state['n_semitones'] = n_semitones
                yield state
