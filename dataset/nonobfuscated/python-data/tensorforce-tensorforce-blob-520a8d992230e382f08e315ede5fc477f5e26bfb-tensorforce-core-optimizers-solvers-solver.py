














import tensorflow as tf
from tensorforce import util
import tensorforce.core.optimizers.solvers


class Solver(object):
    


    def __init__(self):
        

        
        self.solve = tf.make_template(name_='solver', func_=self.tf_solve)

    def tf_solve(self, fn_x, *args):
        

        raise NotImplementedError

    @staticmethod
    def from_config(config, kwargs=None):
        

        return util.get_object(
            obj=config,
            predefined=tensorforce.core.optimizers.solvers.solvers,
            kwargs=kwargs
        )
