







import numpy as np






BJHK = [0.1922, 5.2634, 0.0203, -4.2810]
BJH = [0.7013, 6.2321, -5.2769]
BJK = [0.1935, 5.2676, -4.2650]
BHK = [0.7108, 18.5256, -17.5197]
BJ = [5.5599, 0.6320]
BH = [6.7509, 0.5269]
BK = [7.0739, 0.4971]

VJHK = [-0.0053,3.5326, 1.3141, -3.8331]
VJH = [0.2948, 4.2168, -3.2251]
VJK = [0.0631, 3.7103, -2.7004]
VHK = [1.4044, 12.1719,-11.2331]
VJ = [3.8512, 0.7671]
VH = [4.8834, 0.6895]
VK = [5.1466, 0.6682]

RJHK = [0.0606, 2.7823, 0.8922, -2.6713]
RJH = [0.3678, 3.4181, -2.4434]
RJK = [0.1063, 2.9828, -1.9846]
RHK = [0.4826, 10.3926, -9.4021]
RJ = [2.8217, 0.8101]
RH = [3.9934, 0.7154]
RK = [4.3327, 0.6862]

IJHK = [0.0560, 2.0812, 0.4074, -1.4889]
IJH = [0.2453, 2.4061, -1.4236]
IJK = [0.0994, 2.1576, -1.1622]
IHK = [0.1403, 8.0510, -7.0394]
IJ = [1.5585, 0.8979]
IH = [2.6453, 0.8138]
IK = [2.9959, 0.7839]


SDSSU_JHK = [3.5675, 5.4894, -2.8007, -1.8600]
SDSSU_JH = [4.2568, 5.3802, -4.5899]
SDSSU_JK = [3.8707, 4.6353, -3.8165]
SDSSU_HK = [10.4029, 0.6696, -0.1827]
SDSSU_J = [7.4786, 0.6779]
SDSSU_H = [10.2512, 0.4984]
SDSSU_K = [10.7584, 0.4662]

SDSSG_JHK = [0.9922, 4.3197, -1.6916, -1.6751]
SDSSG_JH = [0.6890, 4.4356, -3.4537]
SDSSG_JK = [1.5487, 4.1286, -3.2193]
SDSSG_HK = [4.3634, 2.7802, -1.9456]
SDSSG_J = [2.4949, 0.9537]
SDSSG_H = [4.7010, 0.8227]
SDSSG_K = [5.2323, 0.7899]

SDSSR_JHK = [0.6975, 2.9782, -0.8809, -1.1230]
SDSSR_JH = [1.0935, 2.9289, -1.9766]
SDSSR_JK = [0.7277, 2.7746, -1.8005]
SDSSR_HK = [5.7039, 1.4010, -0.7037]
SDSSR_J = [3.0033, 0.8713]
SDSSR_H = [5.6142, 0.7069]
SDSSR_K = [5.8755, 0.6913]

SDSSI_JHK = [0.8875, 2.3210, -0.6825, -0.6724]
SDSSI_JH = [0.9052, 2.3750, -1.4074]
SDSSI_JK = [0.8117, 2.1503, -1.1763]
SDSSI_HK = [6.2356, 2.7331, -2.1008]
SDSSI_J = [2.1593, 0.9168]
SDSSI_H = [6.4280, 0.6295]
SDSSI_K = [5.8109, 0.6773]

SDSSZ_JHK = [0.8346, 1.7668, -0.1778, -0.6084]
SDSSZ_JH = [0.9037, 1.8245, -0.8472]
SDSSZ_JK = [0.9220, 1.7158, -0.7411]
SDSSZ_HK = [4.3827, 2.4788, -1.7118]
SDSSZ_J = [1.5408, 0.9557]
SDSSZ_H = [6.1351, 0.6509]
SDSSZ_K = [6.6213, 0.6183]






def convert_constants(jmag, hmag, kmag,
                      cjhk,
                      cjh, cjk, chk,
                      cj, ch, ck):
    


    if jmag is not None:

        if hmag is not None:

            if kmag is not None:

                return cjhk[0] + cjhk[1]*jmag + cjhk[2]*hmag + cjhk[3]*kmag

            else:

                return cjh[0] + cjh[1]*jmag + cjh[2]*hmag

        else:

            if kmag is not None:

                return cjk[0] + cjk[1]*jmag + cjk[2]*kmag

            else:

                return cj[0] + cj[1]*jmag

    else:

        if hmag is not None:

            if kmag is not None:

                return chk[0] + chk[1]*hmag + chk[2]*kmag

            else:

                return ch[0] + ch[1]*hmag

        else:

            if kmag is not None:

                return ck[0] + ck[1]*kmag

            else:

                return np.nan






def jhk_to_bmag(jmag, hmag, kmag):
    


    return convert_constants(jmag,hmag,kmag,
                             BJHK,
                             BJH, BJK, BHK,
                             BJ, BH, BK)



def jhk_to_vmag(jmag,hmag,kmag):
    


    return convert_constants(jmag,hmag,kmag,
                             VJHK,
                             VJH, VJK, VHK,
                             VJ, VH, VK)



def jhk_to_rmag(jmag,hmag,kmag):
    


    return convert_constants(jmag,hmag,kmag,
                             RJHK,
                             RJH, RJK, RHK,
                             RJ, RH, RK)



def jhk_to_imag(jmag,hmag,kmag):
    


    return convert_constants(jmag,hmag,kmag,
                             IJHK,
                             IJH, IJK, IHK,
                             IJ, IH, IK)






def jhk_to_sdssu(jmag,hmag,kmag):
    


    return convert_constants(jmag,hmag,kmag,
                             SDSSU_JHK,
                             SDSSU_JH, SDSSU_JK, SDSSU_HK,
                             SDSSU_J, SDSSU_H, SDSSU_K)



def jhk_to_sdssg(jmag,hmag,kmag):
    


    return convert_constants(jmag,hmag,kmag,
                             SDSSG_JHK,
                             SDSSG_JH, SDSSG_JK, SDSSG_HK,
                             SDSSG_J, SDSSG_H, SDSSG_K)



def jhk_to_sdssr(jmag,hmag,kmag):
    


    return convert_constants(jmag,hmag,kmag,
                             SDSSR_JHK,
                             SDSSR_JH, SDSSR_JK, SDSSR_HK,
                             SDSSR_J, SDSSR_H, SDSSR_K)



def jhk_to_sdssi(jmag,hmag,kmag):
    


    return convert_constants(jmag,hmag,kmag,
                             SDSSI_JHK,
                             SDSSI_JH, SDSSI_JK, SDSSI_HK,
                             SDSSI_J, SDSSI_H, SDSSI_K)



def jhk_to_sdssz(jmag,hmag,kmag):
    


    return convert_constants(jmag,hmag,kmag,
                             SDSSZ_JHK,
                             SDSSZ_JH, SDSSZ_JK, SDSSZ_HK,
                             SDSSZ_J, SDSSZ_H, SDSSZ_K)
















































def absolute_gaia_magnitude(gaia_mag,
                            gaia_parallax_mas,
                            gaia_mag_err=None,
                            gaia_parallax_err_mas=None):
    


    
    
    
    
    d_pc = np.abs(1000.0/gaia_parallax_mas)

    
    if gaia_parallax_err_mas is not None:
        d_pc_err = (
            (1000.0/(gaia_parallax_mas*gaia_parallax_mas)) *
            gaia_parallax_err_mas
        )
    else:
        d_pc_err = None

    
    
    
    
    M_G = 5 - 5.0*np.log10(d_pc) + gaia_mag

    
    if d_pc_err is not None and gaia_mag_err is not None:

        M_G_err = np.sqrt(
            ((5.0/(d_pc * np.log(10.0)))**2 * (d_pc_err)**2) +
            gaia_mag_err*gaia_mag_err
        )
    else:
        M_G_err = None


    if M_G_err is not None:
        return M_G, M_G_err
    else:
        return M_G
