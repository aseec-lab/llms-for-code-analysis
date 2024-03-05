










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
import sys
import os.path
import glob
import multiprocessing as mp
from concurrent.futures import ProcessPoolExecutor

from tornado.escape import squeeze



from functools import reduce
from operator import getitem
def _dict_get(datadict, keylist):
    return reduce(getitem, keylist, datadict)

import numpy as np

try:
    from tqdm import tqdm
    TQDM = True
except Exception as e:
    TQDM = False
    pass






NCPUS = mp.cpu_count()







from astrobase.lcmath import normalize_magseries
from astrobase.varclass import varfeatures

from astrobase.lcproc import get_lcformat







def get_varfeatures(lcfile,
                    outdir,
                    timecols=None,
                    magcols=None,
                    errcols=None,
                    mindet=1000,
                    lcformat='hat-sql',
                    lcformatdir=None):
    


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

    try:

        
        lcdict = readerfunc(lcfile)

        
        
        
        if ( (isinstance(lcdict, (list, tuple))) and
             (isinstance(lcdict[0], dict)) ):
            lcdict = lcdict[0]

        resultdict = {'objectid':lcdict['objectid'],
                      'info':lcdict['objectinfo'],
                      'lcfbasename':os.path.basename(lcfile)}


        
        if normfunc is not None:
            lcdict = normfunc(lcdict)

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


            
            finind = np.isfinite(times) & np.isfinite(mags) & np.isfinite(errs)

            
            if mags[finind].size < mindet:

                LOGINFO('not enough LC points: %s in normalized %s LC: %s' %
                        (mags[finind].size, mcol, os.path.basename(lcfile)))
                resultdict[mcol] = None

            else:

                
                lcfeatures = varfeatures.all_nonperiodic_features(
                    times, mags, errs
                )
                resultdict[mcol] = lcfeatures

        
        
        

        try:
            magmads = np.zeros(len(magcols))
            for mind, mcol in enumerate(magcols):
                if '.' in mcol:
                    mcolget = mcol.split('.')
                else:
                    mcolget = [mcol]

                magmads[mind] = resultdict[mcol]['mad']

            
            bestmagcolind = np.where(magmads == np.min(magmads))[0]
            resultdict['bestmagcol'] = magcols[bestmagcolind]

        except Exception as e:
            resultdict['bestmagcol'] = None

        outfile = os.path.join(outdir,
                               'varfeatures-%s.pkl' %
                               squeeze(resultdict['objectid']).replace(' ','-'))

        with open(outfile, 'wb') as outfd:
            pickle.dump(resultdict, outfd, protocol=4)

        return outfile

    except Exception as e:

        LOGEXCEPTION('failed to get LC features for %s because: %s' %
                     (os.path.basename(lcfile), e))
        return None



def _varfeatures_worker(task):
    


    try:
        (lcfile, outdir, timecols, magcols, errcols,
         mindet, lcformat, lcformatdir) = task
        return get_varfeatures(lcfile, outdir,
                               timecols=timecols,
                               magcols=magcols,
                               errcols=errcols,
                               mindet=mindet,
                               lcformat=lcformat,
                               lcformatdir=lcformatdir)

    except Exception as e:
        return None


def serial_varfeatures(lclist,
                       outdir,
                       maxobjects=None,
                       timecols=None,
                       magcols=None,
                       errcols=None,
                       mindet=1000,
                       lcformat='hat-sql',
                       lcformatdir=None):
    


    if maxobjects:
        lclist = lclist[:maxobjects]

    tasks = [(x, outdir, timecols, magcols, errcols,
              mindet, lcformat, lcformatdir)
             for x in lclist]

    for task in tqdm(tasks):
        result = _varfeatures_worker(task)

    return result



def parallel_varfeatures(lclist,
                         outdir,
                         maxobjects=None,
                         timecols=None,
                         magcols=None,
                         errcols=None,
                         mindet=1000,
                         lcformat='hat-sql',
                         lcformatdir=None,
                         nworkers=NCPUS):
    

    
    if not os.path.exists(outdir):
        os.makedirs(outdir)

    if maxobjects:
        lclist = lclist[:maxobjects]

    tasks = [(x, outdir, timecols, magcols, errcols, mindet,
              lcformat, lcformatdir) for x in lclist]

    with ProcessPoolExecutor(max_workers=nworkers) as executor:
        resultfutures = executor.map(varfeatures_worker, tasks)

    results = [x for x in resultfutures]
    resdict = {os.path.basename(x):y for (x,y) in zip(lclist, results)}

    return resdict



def parallel_varfeatures_lcdir(lcdir,
                               outdir,
                               fileglob=None,
                               maxobjects=None,
                               timecols=None,
                               magcols=None,
                               errcols=None,
                               recursive=True,
                               mindet=1000,
                               lcformat='hat-sql',
                               lcformatdir=None,
                               nworkers=NCPUS):
    


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

    if not fileglob:
        fileglob = dfileglob

    
    LOGINFO('searching for %s light curves in %s ...' % (lcformat, lcdir))

    if recursive is False:
        matching = glob.glob(os.path.join(lcdir, fileglob))

    else:
        
        if sys.version_info[:2] > (3,4):

            matching = glob.glob(os.path.join(lcdir,
                                              '**',
                                              fileglob),
                                 recursive=True)

        
        else:

            
            walker = os.walk(lcdir)
            matching = []

            for root, dirs, _files in walker:
                for sdir in dirs:
                    searchpath = os.path.join(root,
                                              sdir,
                                              fileglob)
                    foundfiles = glob.glob(searchpath)

                    if foundfiles:
                        matching.extend(foundfiles)


    
    if matching and len(matching) > 0:

        LOGINFO('found %s light curves, getting varfeatures...' %
                len(matching))

        return parallel_varfeatures(matching,
                                    outdir,
                                    maxobjects=maxobjects,
                                    timecols=timecols,
                                    magcols=magcols,
                                    errcols=errcols,
                                    mindet=mindet,
                                    lcformat=lcformat,
                                    lcformatdir=lcformatdir,
                                    nworkers=nworkers)

    else:

        LOGERROR('no light curve files in %s format found in %s' % (lcformat,
                                                                    lcdir))
        return None
