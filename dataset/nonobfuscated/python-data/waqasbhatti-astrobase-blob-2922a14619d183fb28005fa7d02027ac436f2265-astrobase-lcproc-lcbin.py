










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






try:
    import cPickle as pickle
except Exception as e:
    import pickle

import os
import os.path
import glob
import multiprocessing as mp

from tornado.escape import squeeze



from functools import reduce
from operator import getitem
def _dict_get(datadict, keylist):
    return reduce(getitem, keylist, datadict)







NCPUS = mp.cpu_count()







from astrobase.lcproc import get_lcformat
from astrobase.lcmath import (
    normalize_magseries,
    time_bin_magseries_with_errs,
)







def timebinlc(lcfile,
              binsizesec,
              outdir=None,
              lcformat='hat-sql',
              lcformatdir=None,
              timecols=None,
              magcols=None,
              errcols=None,
              minbinelems=7):

    


    try:
        formatinfo = get_lcformat(lcformat,
                                  use_lcformat_dir=lcformatdir)
        if formatinfo:
            (dfileglob, readerfunc,
             dtimecols, dmagcols, derrcols,
             magsarefluxes, normfunc) = formatinfo
        else:
            LOGERROR("can't figure out the light curve format")
            return None
    except Exception as e:
        LOGEXCEPTION("can't figure out the light curve format")
        return None

    
    
    if timecols is None:
        timecols = dtimecols
    if magcols is None:
        magcols = dmagcols
    if errcols is None:
        errcols = derrcols

    
    lcdict = readerfunc(lcfile)

    
    
    
    if ( (isinstance(lcdict, (list, tuple))) and
         (isinstance(lcdict[0], dict)) ):
        lcdict = lcdict[0]

    
    if 'binned' in lcdict:
        LOGERROR('this light curve appears to be binned already, skipping...')
        return None

    lcdict['binned'] = {}

    for tcol, mcol, ecol in zip(timecols, magcols, errcols):

        
        if '.' in tcol:
            tcolget = tcol.split('.')
        else:
            tcolget = [tcol]
        times = _dict_get(lcdict, tcolget)

        if '.' in mcol:
            mcolget = mcol.split('.')
        else:
            mcolget = [mcol]
        mags = _dict_get(lcdict, mcolget)

        if '.' in ecol:
            ecolget = ecol.split('.')
        else:
            ecolget = [ecol]
        errs = _dict_get(lcdict, ecolget)

        
        if normfunc is None:
            ntimes, nmags = normalize_magseries(
                times, mags,
                magsarefluxes=magsarefluxes
            )

            times, mags, errs = ntimes, nmags, errs

        
        binned = time_bin_magseries_with_errs(times,
                                              mags,
                                              errs,
                                              binsize=binsizesec,
                                              minbinelems=minbinelems)

        
        lcdict['binned'][mcol] = {'times':binned['binnedtimes'],
                                  'mags':binned['binnedmags'],
                                  'errs':binned['binnederrs'],
                                  'nbins':binned['nbins'],
                                  'timebins':binned['jdbins'],
                                  'binsizesec':binsizesec}


    
    

    if outdir is None:
        outdir = os.path.dirname(lcfile)

    outfile = os.path.join(outdir, '%s-binned%.1fsec-%s.pkl' %
                           (squeeze(lcdict['objectid']).replace(' ','-'),
                            binsizesec, lcformat))

    with open(outfile, 'wb') as outfd:
        pickle.dump(lcdict, outfd, protocol=pickle.HIGHEST_PROTOCOL)

    return outfile



def timebinlc_worker(task):
    


    lcfile, binsizesec, kwargs = task

    try:
        binnedlc = timebinlc(lcfile, binsizesec, **kwargs)
        LOGINFO('%s binned using %s sec -> %s OK' %
                (lcfile, binsizesec, binnedlc))
        return binnedlc
    except Exception as e:
        LOGEXCEPTION('failed to bin %s using binsizesec = %s' % (lcfile,
                                                                 binsizesec))
        return None



def parallel_timebin(lclist,
                     binsizesec,
                     maxobjects=None,
                     outdir=None,
                     lcformat='hat-sql',
                     lcformatdir=None,
                     timecols=None,
                     magcols=None,
                     errcols=None,
                     minbinelems=7,
                     nworkers=NCPUS,
                     maxworkertasks=1000):
    


    if outdir and not os.path.exists(outdir):
        os.mkdir(outdir)

    if maxobjects is not None:
        lclist = lclist[:maxobjects]

    tasks = [(x, binsizesec, {'outdir':outdir,
                              'lcformat':lcformat,
                              'lcformatdir':lcformatdir,
                              'timecols':timecols,
                              'magcols':magcols,
                              'errcols':errcols,
                              'minbinelems':minbinelems}) for x in lclist]

    pool = mp.Pool(nworkers, maxtasksperchild=maxworkertasks)
    results = pool.map(timebinlc_worker, tasks)
    pool.close()
    pool.join()

    resdict = {os.path.basename(x):y for (x,y) in zip(lclist, results)}

    return resdict



def parallel_timebin_lcdir(lcdir,
                           binsizesec,
                           maxobjects=None,
                           outdir=None,
                           lcformat='hat-sql',
                           lcformatdir=None,
                           timecols=None,
                           magcols=None,
                           errcols=None,
                           minbinelems=7,
                           nworkers=NCPUS,
                           maxworkertasks=1000):
    

    try:
        formatinfo = get_lcformat(lcformat,
                                  use_lcformat_dir=lcformatdir)
        if formatinfo:
            (fileglob, readerfunc,
             dtimecols, dmagcols, derrcols,
             magsarefluxes, normfunc) = formatinfo
        else:
            LOGERROR("can't figure out the light curve format")
            return None
    except Exception as e:
        LOGEXCEPTION("can't figure out the light curve format")
        return None

    lclist = sorted(glob.glob(os.path.join(lcdir, fileglob)))

    return parallel_timebin(lclist,
                            binsizesec,
                            maxobjects=maxobjects,
                            outdir=outdir,
                            lcformat=lcformat,
                            timecols=timecols,
                            magcols=magcols,
                            errcols=errcols,
                            minbinelems=minbinelems,
                            nworkers=nworkers,
                            maxworkertasks=maxworkertasks)
