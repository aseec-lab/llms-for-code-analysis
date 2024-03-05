



from __future__ import division

__all__ = ['CRC_Permittivity_data', 'permittivity_IAPWS', 'Permittivity']

import os
import numpy as np
import pandas as pd
from thermo.utils import N_A, epsilon_0, k
from thermo.utils import TDependentProperty

folder = os.path.join(os.path.dirname(__file__), 'Electrolytes')


CRC_Permittivity_data = pd.read_csv(os.path.join(folder, 'Permittivity (Dielectric Constant) of Liquids.tsv'),
                                    sep='\t', index_col=0)
_CRC_Permittivity_data_values = CRC_Permittivity_data.values


def permittivity_IAPWS(T, rho):
    r

    dipole = 6.138E-30 
    polarizability = 1.636E-40 
    MW = 0.018015268 
    ih = [1, 1, 1, 2, 3, 3, 4, 5, 6, 7, 10]
    jh = [0.25, 1, 2.5, 1.5, 1.5, 2.5, 2, 2, 5, 0.5, 10]
    Nh = [0.978224486826, -0.957771379375, 0.237511794148, 0.714692244396,
          -0.298217036956, -0.108863472196, 0.949327488264E-1, 
          -.980469816509E-2, 0.165167634970E-4, 0.937359795772E-4, 
          -0.12317921872E-9]
    
    delta = rho/322.
    tau = 647.096/T
    
    g = (1 + sum([Nh[h]*delta**ih[h]*tau**jh[h] for h in range(11)])
        + 0.196096504426E-2*delta*(T/228. - 1)**-1.2)
    
    A = N_A*dipole**2*(rho/MW)*g/epsilon_0/k/T
    B = N_A*polarizability*(rho/MW)/3./epsilon_0
    epsilon = (1. + A + 5.*B + (9. + 2.*A + 18.*B + A**2 + 10.*A*B + 9.*B**2
        )**0.5)/(4. - 4.*B)
    return epsilon


CRC = 'CRC'
CRC_CONSTANT = 'CRC_CONSTANT'
permittivity_methods = [CRC, CRC_CONSTANT]




class Permittivity(TDependentProperty):
    r

    name = 'relative permittivity'
    units = '-'
    interpolation_T = None
    

    interpolation_property = None
    

    interpolation_property_inv = None
    

    tabular_extrapolation_permitted = True
    

    property_min = 1
    

    property_max = 1000
    


    ranked_methods = [CRC, CRC_CONSTANT]
    


    def __init__(self, CASRN=''):
        self.CASRN = CASRN

        self.Tmin = None
        

        self.Tmax = None
        


        self.tabular_data = {}
        

        self.tabular_data_interpolators = {}
        


        self.sorted_valid_methods = []
        

        self.user_methods = []
        


        self.all_methods = set()
        


        self.load_all_methods()

    def load_all_methods(self):
        r

        methods = []
        Tmins, Tmaxs = [], []
        if self.CASRN in CRC_Permittivity_data.index:
            methods.append(CRC_CONSTANT)
            _, self.CRC_CONSTANT_T, self.CRC_permittivity, A, B, C, D, Tmin, Tmax = _CRC_Permittivity_data_values[CRC_Permittivity_data.index.get_loc(self.CASRN)].tolist()
            self.CRC_Tmin = Tmin
            self.CRC_Tmax = Tmax
            self.CRC_coeffs = [0 if np.isnan(x) else x for x in [A, B, C, D] ]
            if not np.isnan(Tmin):
                Tmins.append(Tmin); Tmaxs.append(Tmax)
            if self.CRC_coeffs[0]:
                methods.append(CRC)
        self.all_methods = set(methods)
        if Tmins and Tmaxs:
            self.Tmin = min(Tmins)
            self.Tmax = max(Tmaxs)

    def calculate(self, T, method):
        r

        if method == CRC:
            A, B, C, D = self.CRC_coeffs
            epsilon = A + B*T + C*T**2 + D*T**3
        elif method == CRC_CONSTANT:
            epsilon = self.CRC_permittivity
        elif method in self.tabular_data:
            epsilon = self.interpolate(T, method)
        return epsilon

    def test_method_validity(self, T, method):
        r

        validity = True
        if method == CRC:
            if T < self.CRC_Tmin or T > self.CRC_Tmax:
                validity = False
        elif method == CRC_CONSTANT:
            
            if T < self.CRC_CONSTANT_T - 20 or T > self.CRC_CONSTANT_T + 20:
                validity = False
        elif method in self.tabular_data:
            
            if not self.tabular_extrapolation_permitted:
                Ts, properties = self.tabular_data[method]
                if T < Ts[0] or T > Ts[-1]:
                    validity = False
        else:
            raise Exception('Method not valid')
        return validity






















