






from __future__ import print_function





import logging
from astrobase import log_sub, log_fmt, log_date_fmt

DEBUG = False
if DEBUG:
    level = logging.DEBUG
else:
    level = logging.INFO
LOGGER = logging.getLogger(__name__)
logging.basicConfig(
    level=level,
    style=log_sub,
    format=log_fmt,
    datefmt=log_date_fmt,
)

LOGDEBUG = LOGGER.debug
LOGINFO = LOGGER.info
LOGWARNING = LOGGER.warning
LOGERROR = LOGGER.error
LOGEXCEPTION = LOGGER.exception






import os.path
import os

import numpy as np



import astropy.time as astime



from jplephem.spk import SPK







modpath = os.path.abspath(os.path.dirname(__file__))
planetdatafile = os.path.join(modpath,'data/de430.bsp')




try:

    
    jplkernel = SPK.open(planetdatafile)
    HAVEKERNEL = True

except Exception as e:

    
    def on_download_chunk(transferred,blocksize,totalsize):
        progress = transferred*blocksize/float(totalsize)*100.0
        print('{progress:.1f}%'.format(progress=progress),end='\r')

    
    spkurl = (
        'https://naif.jpl.nasa.gov/'
        'pub/naif/generic_kernels/spk/planets/de430.bsp'
    )

    LOGINFO('JPL kernel de430.bsp not found. Downloading from:\n\n%s\n' %
            spkurl)
    try:
        from urllib import urlretrieve
    except Exception as e:
        from urllib.request import urlretrieve

    localf, headerr = urlretrieve(
        spkurl,planetdatafile,reporthook=on_download_chunk
    )
    if os.path.exists(localf):
        print('\nDone.')
        jplkernel = SPK.open(planetdatafile)
        HAVEKERNEL = True
    else:
        print('failed to download the JPL kernel!')
        HAVEKERNEL = False








CLIGHT_KPS = 299792.458


JD1800 = 2378495.0
JD2000 = 2451545.0
JD2000INT = 2451545
JD2050 = 2469807.5


MAS_P_YR_TO_RAD_P_DAY = 1.3273475e-11
ARCSEC_TO_RADIANS = 4.84813681109536e-6
KM_P_AU = 1.49597870691e8
SEC_P_DAY = 86400.0





EMRAT = 81.30056941599857






def precess_coordinates(ra, dec,
                        epoch_one, epoch_two,
                        jd=None,
                        mu_ra=0.0,
                        mu_dec=0.0,
                        outscalar=False):
    


    raproc, decproc = np.radians(ra), np.radians(dec)

    if ((mu_ra != 0.0) and (mu_dec != 0.0) and jd):

        jd_epoch_one = JD2000 + (epoch_one - epoch_two)*365.25
        raproc = (
            raproc +
            (jd - jd_epoch_one)*mu_ra*MAS_P_YR_TO_RAD_P_DAY/np.cos(decproc)
        )
        decproc = decproc + (jd - jd_epoch_one)*mu_dec*MAS_P_YR_TO_RAD_P_DAY

    ca = np.cos(raproc)
    cd = np.cos(decproc)
    sa = np.sin(raproc)
    sd = np.sin(decproc)

    if epoch_one != epoch_two:

        t1 = 1.0e-3 * (epoch_two - epoch_one)
        t2 = 1.0e-3 * (epoch_one - 2000.0)

        a = ( t1*ARCSEC_TO_RADIANS * (23062.181 + t2*(139.656 + 0.0139*t2) +
                                      t1*(30.188 - 0.344*t2+17.998*t1)) )
        b = t1*t1*ARCSEC_TO_RADIANS*(79.280 + 0.410*t2 + 0.205*t1) + a
        c = (
            ARCSEC_TO_RADIANS*t1*(20043.109 - t2*(85.33 + 0.217*t2) +
                                  t1*(-42.665 - 0.217*t2 - 41.833*t2))
        )
        sina, sinb, sinc = np.sin(a), np.sin(b), np.sin(c)
        cosa, cosb, cosc = np.cos(a), np.cos(b), np.cos(c)

        precmatrix = np.matrix([[cosa*cosb*cosc - sina*sinb,
                                 sina*cosb + cosa*sinb*cosc,
                                 cosa*sinc],
                                [-cosa*sinb - sina*cosb*cosc,
                                 cosa*cosb - sina*sinb*cosc,
                                 -sina*sinc],
                                [-cosb*sinc,
                                 -sinb*sinc,
                                 cosc]])

        precmatrix = precmatrix.transpose()

        x = (np.matrix([cd*ca, cd*sa, sd])).transpose()

        x2 = precmatrix * x

        outra = np.arctan2(x2[1],x2[0])
        outdec = np.arcsin(x2[2])


        outradeg = np.rad2deg(outra)
        outdecdeg = np.rad2deg(outdec)

        if outradeg < 0.0:
            outradeg = outradeg + 360.0

        if outscalar:
            return float(outradeg), float(outdecdeg)
        else:
            return outradeg, outdecdeg

    else:

        
        
        
        
        return np.degrees(raproc), np.degrees(decproc)







def _single_true(iterable):
    


    
    iterator = iter(iterable)

    
    has_true = any(iterator)

    
    has_another_true = any(iterator)

    return has_true and not has_another_true



def get_epochs_given_midtimes_and_period(
        t_mid,
        period,
        err_t_mid=None,
        t0_fixed=None,
        t0_percentile=None,
        verbose=False
):
    


    kwargarr = np.array([isinstance(err_t_mid,np.ndarray),
                         t0_fixed,
                         t0_percentile])
    if not _single_true(kwargarr) and not np.all(~kwargarr.astype(bool)):
        raise AssertionError(
            'can have at most one of err_t_mid, t0_fixed, t0_percentile')

    t_mid = t_mid[np.isfinite(t_mid)]
    N_midtimes = len(t_mid)

    if t0_fixed:
        t0 = t0_fixed
    elif isinstance(err_t_mid,np.ndarray):
        
        t0_avg = np.average(t_mid, weights=1/err_t_mid**2)
        t0_options = np.arange(min(t_mid), max(t_mid)+period, period)
        t0 = t0_options[np.argmin(np.abs(t0_options - t0_avg))]
    else:
        if not t0_percentile:
            
            
            
            if N_midtimes % 2 == 1:
                t0 = np.median(t_mid)
            else:
                t0 = t_mid[int(N_midtimes/2)]
        else:
            t0 = np.sort(t_mid)[int(N_midtimes*t0_percentile/100)]

    epoch = (t_mid - t0)/period

    
    int_epoch = np.round(epoch, 0)

    if verbose:
        LOGINFO('epochs before rounding')
        LOGINFO('\n{:s}'.format(repr(epoch)))
        LOGINFO('epochs after rounding')
        LOGINFO('\n{:s}'.format(repr(int_epoch)))

    return int_epoch, t0







def unixtime_to_jd(unix_time):
    


    
    jdutc = astime.Time(unix_time, format='unix', scale='utc')
    return jdutc.jd



def datetime_to_jd(dt):
    


    jdutc = astime.Time(dt, format='datetime',scale='utc')
    return jdutc.jd



def jd_to_datetime(jd, returniso=False):
    


    tt = astime.Time(jd, format='jd', scale='utc')

    if returniso:
        return tt.iso
    else:
        return tt.datetime



def jd_now():
    

    return astime.Time.now().jd



def jd_to_mjd(jd):
    


    return jd - 2400000.5



def mjd_to_jd(mjd):
    


    return mjd + 2400000.5






def jd_corr(jd,
            ra, dec,
            obslon=None,
            obslat=None,
            obsalt=None,
            jd_type='bjd'):
    


    if not HAVEKERNEL:
        LOGERROR('no JPL kernel available, can\'t continue!')
        return

    
    
    

    
    rarad = np.radians(ra)
    decrad = np.radians(dec)
    cosra = np.cos(rarad)
    sinra = np.sin(rarad)
    cosdec = np.cos(decrad)
    sindec = np.sin(decrad)

    
    src_unitvector = np.array([cosdec*cosra,cosdec*sinra,sindec])

    
    
    if (obslon is None) or (obslat is None) or (obsalt is None):
        t = astime.Time(jd, scale='utc', format='jd')
    else:
        t = astime.Time(jd, scale='utc', format='jd',
                        location=('%.5fd' % obslon,
                                  '%.5fd' % obslat,
                                  obsalt))

    
    
    
    barycenter_earthmoon = jplkernel[0,3].compute(t.tdb.jd)

    
    
    
    
    
    
    moonvector = (jplkernel[3,301].compute(t.tdb.jd) -
                  jplkernel[3,399].compute(t.tdb.jd))

    
    
    
    pos_earth = (barycenter_earthmoon - moonvector * 1.0/(1.0+EMRAT))

    if jd_type == 'bjd':

        
        
        
        
        
        
        
        correction_seconds = np.dot(pos_earth.T, src_unitvector)/CLIGHT_KPS
        correction_days = correction_seconds/SEC_P_DAY

    elif jd_type == 'hjd':

        

        
        
        pos_sun = jplkernel[0,10].compute(t.tdb.jd)

        
        
        sun_earth_vec = pos_earth - pos_sun

        
        correction_seconds = np.dot(sun_earth_vec.T, src_unitvector)/CLIGHT_KPS
        correction_days = correction_seconds/SEC_P_DAY

    
    new_jd = t.tdb.jd + correction_days

    return new_jd
