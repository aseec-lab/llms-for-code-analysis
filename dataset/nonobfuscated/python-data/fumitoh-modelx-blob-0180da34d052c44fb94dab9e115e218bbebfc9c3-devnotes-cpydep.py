

















import ctypes
import inspect
from types import FunctionType

def _alter_code(code, **attrs):
    


    PyCode_New = ctypes.pythonapi.PyCode_New

    PyCode_New.argtypes = (
        ctypes.c_int,
        ctypes.c_int,
        ctypes.c_int,
        ctypes.c_int,
        ctypes.c_int,
        ctypes.py_object,
        ctypes.py_object,
        ctypes.py_object,
        ctypes.py_object,
        ctypes.py_object,
        ctypes.py_object,
        ctypes.py_object,
        ctypes.py_object,
        ctypes.c_int,
        ctypes.py_object)

    PyCode_New.restype = ctypes.py_object

    args = [
        [code.co_argcount, 'co_argcount'],
        [code.co_kwonlyargcount, 'co_kwonlyargcount'],
        [code.co_nlocals, 'co_nlocals'],
        [code.co_stacksize, 'co_stacksize'],
        [code.co_flags, 'co_flags'],
        [code.co_code, 'co_code'],
        [code.co_consts, 'co_consts'],
        [code.co_names, 'co_names'],
        [code.co_varnames, 'co_varnames'],
        [code.co_freevars, 'co_freevars'],
        [code.co_cellvars, 'co_cellvars'],
        [code.co_filename, 'co_filename'],
        [code.co_name, 'co_name'],
        [code.co_firstlineno, 'co_firstlineno'],
        [code.co_lnotab, 'co_lnotab']]

    for arg in args:
        if arg[1] in attrs:
            arg[0] = attrs[arg[1]]

    return PyCode_New(
        args[0][0],  
        args[1][0],  
        args[2][0],  
        args[3][0],  
        args[4][0],  
        args[5][0],  
        args[6][0],  
        args[7][0],  
        args[8][0],  
        args[9][0],  
        args[10][0],  
        args[11][0],  
        args[12][0],  
        args[13][0],  
        args[14][0])  


def _create_cell(value):

    PyCell_New = ctypes.pythonapi.PyCell_New
    PyCell_New.argtypes = (ctypes.py_object,)
    PyCell_New.restype = ctypes.py_object

    return PyCell_New(value)


def _create_closure(*values):
    return tuple(_create_cell(val) for val in values)


def alter_freevars(func, globals_=None, **vars):
    


    if globals_ is None:
        globals_ = func.__globals__

    frees = tuple(vars.keys())
    oldlocs = func.__code__.co_names
    newlocs = tuple(name for name in oldlocs if name not in frees)

    code = _alter_code(func.__code__,
                       co_freevars=frees,
                       co_names=newlocs,
                       co_flags=func.__code__.co_flags | inspect.CO_NESTED)
    closure = _create_closure(*vars.values())

    return FunctionType(code, globals_, closure=closure)

