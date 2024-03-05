
from __future__ import print_function
import numpy as np
import pandas as pd
import sys
from itertools import product
import copy


class ipfn(object):

    def __init__(self, original, aggregates, dimensions, weight_col='total',
                 convergence_rate=0.01, max_iteration=500, verbose=0, rate_tolerance=1e-8):
        

        self.original = original
        self.aggregates = aggregates
        self.dimensions = dimensions
        self.weight_col = weight_col
        self.conv_rate = convergence_rate
        self.max_itr = max_iteration
        self.verbose = verbose
        self.rate_tolerance = rate_tolerance

    @staticmethod
    def index_axis_elem(dims, axes, elems):
        inc_axis = 0
        idx = ()
        for dim in range(dims):
            if (inc_axis < len(axes)):
                if (dim == axes[inc_axis]):
                    idx += (elems[inc_axis],)
                    inc_axis += 1
                else:
                    idx += (np.s_[:],)
        return idx

    def ipfn_np(self, m, aggregates, dimensions, weight_col='total'):
        

        steps = len(aggregates)
        dim = len(m.shape)
        product_elem = []
        tables = [m]
        
        
        for inc in range(steps - 1):
            tables.append(np.array(np.zeros(m.shape)))
        original = copy.copy(m)

        
        for inc in range(steps):
            if inc == (steps - 1):
                table_update = m
                table_current = tables[inc]
            else:
                table_update = tables[inc + 1]
                table_current = tables[inc]
            for dimension in dimensions[inc]:
                product_elem.append(range(m.shape[dimension]))
            for item in product(*product_elem):
                idx = self.index_axis_elem(dim, dimensions[inc], item)
                table_current_slice = table_current[idx]
                mijk = table_current_slice.sum()
                
                xijk = aggregates[inc]
                xijk = xijk[item]
                if mijk == 0:
                    
                    
                    
                    table_update[idx] = table_current_slice
                else:
                    
                    
                    table_update[idx] = table_current_slice * 1.0 * xijk / mijk
                
                
                
                
            product_elem = []

        
        max_conv = 0
        for inc in range(steps):
            
            for dimension in dimensions[inc]:
                product_elem.append(range(m.shape[dimension]))
            for item in product(*product_elem):
                idx = self.index_axis_elem(dim, dimensions[inc], item)
                ori_ijk = aggregates[inc][item]
                m_slice = m[idx]
                m_ijk = m_slice.sum()
                
                if abs(m_ijk / ori_ijk - 1) > max_conv:
                    max_conv = abs(m_ijk / ori_ijk - 1)

            product_elem = []

        return m, max_conv

    def ipfn_df(self, df, aggregates, dimensions, weight_col='total'):
        


        steps = len(aggregates)
        tables = [df]
        for inc in range(steps - 1):
            tables.append(df.copy())
        original = df.copy()

        
        inc = 0
        for features in dimensions:
            if inc == (steps - 1):
                table_update = df
                table_current = tables[inc]
            else:
                table_update = tables[inc + 1]
                table_current = tables[inc]

            tmp = table_current.groupby(features)[weight_col].sum()
            xijk = aggregates[inc]

            feat_l = []
            for feature in features:
                feat_l.append(np.unique(table_current[feature]))
            table_update.set_index(features, inplace=True)
            table_current.set_index(features, inplace=True)

            for feature in product(*feat_l):
                den = tmp.loc[feature]
                
                if den == 0:
                    table_update.loc[feature, weight_col] =\
                        table_current.loc[feature, weight_col] *\
                        xijk.loc[feature]
                else:
                    table_update.loc[feature, weight_col] = \
                        table_current.loc[feature, weight_col].astype(float) * \
                        xijk.loc[feature] / den

            table_update.reset_index(inplace=True)
            table_current.reset_index(inplace=True)
            inc += 1
            feat_l = []

        
        max_conv = 0
        inc = 0
        for features in dimensions:
            tmp = df.groupby(features)[weight_col].sum()
            ori_ijk = aggregates[inc]
            temp_conv = max(abs(tmp / ori_ijk - 1))
            if temp_conv > max_conv:
                max_conv = temp_conv
            inc += 1

        return df, max_conv

    def iteration(self):
        


        i = 0
        conv = np.inf
        old_conv = -np.inf
        conv_list = []
        m = self.original

        
        if isinstance(self.original, pd.DataFrame):
            ipfn_method = self.ipfn_df
        elif isinstance(self.original, np.ndarray):
            ipfn_method = self.ipfn_np
            self.original = self.original.astype('float64')
        else:
            print('Data input instance not recognized')
            sys.exit(0)
        while ((i <= self.max_itr and conv > self.conv_rate) and
               (i <= self.max_itr and abs(conv - old_conv) > self.rate_tolerance)):
            old_conv = conv
            m, conv = ipfn_method(m, self.aggregates, self.dimensions, self.weight_col)
            conv_list.append(conv)
            i += 1
        converged = 1
        if i <= self.max_itr:
            if not conv > self.conv_rate:
                print('ipfn converged: convergence_rate below threshold')
            elif not abs(conv - old_conv) > self.rate_tolerance:
                print('ipfn converged: convergence_rate not updating or below rate_tolerance')
        else:
            print('Maximum iterations reached')
            converged = 0

        
        if self.verbose == 0:
            return m
        elif self.verbose == 1:
            return m, converged
        elif self.verbose == 2:
            return m, converged, pd.DataFrame({'iteration': range(i), 'conv': conv_list}).set_index('iteration')
        else:
            print('wrong verbose input, return None')
            sys.exit(0)


if __name__ == '__main__':

    
    
    
    
    
    
    
    
    
    
    
    
    

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    
    

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    
    m = np.array([1., 2., 1., 3., 5., 5., 6., 2., 2., 1., 7., 2.,
                  5., 4., 2., 5., 5., 5., 3., 8., 7., 2., 7., 6.], )
    dma_l = [501, 501, 501, 501, 501, 501, 501, 501, 501, 501, 501, 501,
             502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502]
    size_l = [1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4,
              1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4]

    age_l = ['20-25', '30-35', '40-45',
             '20-25', '30-35', '40-45',
             '20-25', '30-35', '40-45',
             '20-25', '30-35', '40-45',
             '20-25', '30-35', '40-45',
             '20-25', '30-35', '40-45',
             '20-25', '30-35', '40-45',
             '20-25', '30-35', '40-45']

    df = pd.DataFrame()
    df['dma'] = dma_l
    df['size'] = size_l
    df['age'] = age_l
    df['total'] = m

    xipp = df.groupby('dma')['total'].sum()
    xpjp = df.groupby('size')['total'].sum()
    xppk = df.groupby('age')['total'].sum()
    xijp = df.groupby(['dma', 'size'])['total'].sum()
    xpjk = df.groupby(['size', 'age'])['total'].sum()
    

    xipp.loc[501] = 52
    xipp.loc[502] = 48

    xpjp.loc[1] = 20
    xpjp.loc[2] = 30
    xpjp.loc[3] = 35
    xpjp.loc[4] = 15

    xppk.loc['20-25'] = 35
    xppk.loc['30-35'] = 40
    xppk.loc['40-45'] = 25

    xijp.loc[501] = [9, 17, 19, 7]
    xijp.loc[502] = [11, 13, 16, 8]

    xpjk.loc[1] = [7, 9, 4]
    xpjk.loc[2] = [8, 12, 10]
    xpjk.loc[3] = [15, 12, 8]
    xpjk.loc[4] = [5, 7, 3]

    ipfn_df = ipfn(df, [xipp, xpjp, xppk, xijp, xpjk],
                   [['dma'], ['size'], ['age'], ['dma', 'size'], ['size', 'age']], 'total')
    df = ipfn_df.iteration()

    print(df)
    print(df.groupby('size')['total'].sum(), xpjp)
