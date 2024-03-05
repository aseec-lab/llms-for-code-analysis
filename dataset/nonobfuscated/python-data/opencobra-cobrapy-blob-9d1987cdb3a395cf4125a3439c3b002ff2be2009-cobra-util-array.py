

from __future__ import absolute_import

from collections import namedtuple

import numpy as np
import pandas as pd
from six import iteritems


try:
    from scipy.sparse import dok_matrix, lil_matrix
except ImportError:
    dok_matrix, lil_matrix = None, None


def create_stoichiometric_matrix(model, array_type='dense', dtype=None):
    

    if array_type not in ('DataFrame', 'dense') and not dok_matrix:
        raise ValueError('Sparse matrices require scipy')

    if dtype is None:
        dtype = np.float64

    array_constructor = {
        'dense': np.zeros, 'dok': dok_matrix, 'lil': lil_matrix,
        'DataFrame': np.zeros,
    }

    n_metabolites = len(model.metabolites)
    n_reactions = len(model.reactions)
    array = array_constructor[array_type]((n_metabolites, n_reactions),
                                          dtype=dtype)

    m_ind = model.metabolites.index
    r_ind = model.reactions.index

    for reaction in model.reactions:
        for metabolite, stoich in iteritems(reaction.metabolites):
            array[m_ind(metabolite), r_ind(reaction)] = stoich

    if array_type == 'DataFrame':
        metabolite_ids = [met.id for met in model.metabolites]
        reaction_ids = [rxn.id for rxn in model.reactions]
        return pd.DataFrame(array, index=metabolite_ids, columns=reaction_ids)

    else:
        return array


def nullspace(A, atol=1e-13, rtol=0):
    

    A = np.atleast_2d(A)
    u, s, vh = np.linalg.svd(A)
    tol = max(atol, rtol * s[0])
    nnz = (s >= tol).sum()
    ns = vh[nnz:].conj().T
    return ns


def constraint_matrices(model, array_type='dense', include_vars=False,
                        zero_tol=1e-6):
    

    if array_type not in ('DataFrame', 'dense') and not dok_matrix:
        raise ValueError('Sparse matrices require scipy')

    array_builder = {
        'dense': np.array, 'dok': dok_matrix, 'lil': lil_matrix,
        'DataFrame': pd.DataFrame,
    }[array_type]

    Problem = namedtuple("Problem",
                         ["equalities", "b", "inequalities", "bounds",
                          "variable_fixed", "variable_bounds"])
    equality_rows = []
    inequality_rows = []
    inequality_bounds = []
    b = []

    for const in model.constraints:
        lb = -np.inf if const.lb is None else const.lb
        ub = np.inf if const.ub is None else const.ub
        equality = (ub - lb) < zero_tol
        coefs = const.get_linear_coefficients(model.variables)
        coefs = [coefs[v] for v in model.variables]
        if equality:
            b.append(lb if abs(lb) > zero_tol else 0.0)
            equality_rows.append(coefs)
        else:
            inequality_rows.append(coefs)
            inequality_bounds.append([lb, ub])

    var_bounds = np.array([[v.lb, v.ub] for v in model.variables])
    fixed = var_bounds[:, 1] - var_bounds[:, 0] < zero_tol

    results = Problem(
        equalities=array_builder(equality_rows),
        b=np.array(b),
        inequalities=array_builder(inequality_rows),
        bounds=array_builder(inequality_bounds),
        variable_fixed=np.array(fixed),
        variable_bounds=array_builder(var_bounds))

    return results
