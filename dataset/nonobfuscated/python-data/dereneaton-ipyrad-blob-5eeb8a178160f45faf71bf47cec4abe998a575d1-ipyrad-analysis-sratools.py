











from __future__ import print_function
import os
import sys
import time
import glob
import shutil
import datetime
import pandas as pd
import multiprocessing
import subprocess as sps
import ipyparallel as ipp
from ipyrad.assemble.util import IPyradWarningExit, progressbar



MISSING_IMPORTS = 


ACCESSION_ID = 



class SRA(object):
    

    def __init__(self, 
        accession,
        workdir="sra-fastq-data",
        ):

        
        for binary in ['fastq-dump', 'esearch']:
            if not sps.call("type " + binary, 
                        shell=True,
                        stdout=sps.PIPE,
                        stderr=sps.PIPE) == 0:
                raise IPyradWarningExit(MISSING_IMPORTS)

        
        self.accession = accession
        self.workdir = os.path.abspath(os.path.expanduser(workdir))
        self.is_sample = False
        self.is_project = False
        self._oldtmpdir = None

        
        self._ipcluster = {
            "cluster_id": "", 
            "profile": "default",
            "engines": "Local", 
            "quiet": 0, 
            "timeout": 60, 
            "cores": 0, 
            "threads" : 2,
            "pids": {},
            }

        
        if any([i in self.accession for i in ["SRR", "ERR", "DRR"]]):        
            self.is_sample = True
        elif any([i in self.accession for i in ["SRP", "ERP", "DRP"]]):
            self.is_project = True
        else:
            raise IPyradWarningExit(ACCESSION_ID)



    
    def run(self, 
        force=False, 
        ipyclient=None, 
        name_fields=30, 
        name_separator="_", 
        dry_run=False):
        


        
        
        try:
            
            if not os.path.exists(self.workdir):
                os.makedirs(self.workdir)

            
            
            self._set_vdbconfig_path()

            
            if ipyclient:
                self._ipcluster["pids"] = {}
                for eid in ipyclient.ids:
                    engine = ipyclient[eid]
                    if not engine.outstanding:
                        pid = engine.apply(os.getpid).get()
                        self._ipcluster["pids"][eid] = pid               

            
            self._submit_jobs(
                force=force, 
                ipyclient=ipyclient, 
                name_fields=name_fields, 
                name_separator=name_separator,
                dry_run=dry_run,
                )

        except IPyradWarningExit as inst:
            print(inst)
        
        except KeyboardInterrupt:
            print("keyboard interrupt...")
        except Exception as inst:
            print("Exception in run() - {}".format(inst))
        finally:
            
            self._restore_vdbconfig_path()

            
            
            
            sradir = os.path.join(self.workdir, "sra")
            if os.path.exists(sradir) and (not os.listdir(sradir)):
                shutil.rmtree(sradir)
            else:
                
                try:
                    print(FAILED_DOWNLOAD.format(os.listdir(sradir)))
                except OSError as inst:
                    
                    raise IPyradWarningExit("Download failed. Exiting.")
                
                for srr in os.listdir(sradir):
                    isrr = srr.split(".")[0]
                    ipath = os.path.join(self.workdir, "*_{}*.gz".format(isrr))
                    ifile = glob.glob(ipath)[0]
                    if os.path.exists(ifile):
                        os.remove(ifile)
                
                shutil.rmtree(sradir)

            
            if ipyclient:
                
                try:
                    ipyclient.abort()
                    time.sleep(0.5)
                    for engine_id, pid in self._ipcluster["pids"].items():
                        if ipyclient.queue_status()[engine_id]["tasks"]:
                            os.kill(pid, 2)
                        time.sleep(0.1)
                except ipp.NoEnginesRegistered:
                    pass
                
                if not ipyclient.outstanding:
                    ipyclient.purge_everything()
                
                else:
                    ipyclient.shutdown(hub=True, block=False)
                    ipyclient.close()
                    print("\nwarning: ipcluster shutdown and must be restarted")
                    


    def _submit_jobs(self, 
        force, 
        ipyclient, 
        name_fields, 
        name_separator, 
        dry_run):
        


        
        df = self.fetch_runinfo(range(31), quiet=True)
        sys.stdout.flush()

        
        if ipyclient:
            lb = ipyclient.load_balanced_view()

        
        
        if name_fields:
            
            fields = [int(i)-1 for i in fields_checker(name_fields)]
            
            df['Accession'] = pd.Series(df[df.columns[fields[0]]], index=df.index)
            for field in fields[1:]:
                df.Accession += name_separator + df[df.columns[field]]
            df.Accession = [i.replace(" ", "_") for i in df.Accession]    
            
            if not df.Accession.shape[0] == df.Accession.unique().shape[0]:
                raise IPyradWarningExit("names are not unique:\n{}"\
                    .format(df.Accession))

        
        else:
            if len(set(df.SampleName)) != len(df.SampleName):
                accs = (i+"-"+j for i, j in zip(df.SampleName, df.Run))
                df.Accession = accs
            else:
                df.Accession = df.SampleName

        if dry_run:
            print("\rThe following files will be written to: {}".format(self.workdir))
            print("{}\n".format(df.Accession))
        else:
            
            asyncs = []
            for idx in df.index:

                
                srr = df.Run[idx]
                outname = df.Accession[idx]
                paired = df.spots_with_mates.values.astype(int).nonzero()[0].any()
                fpath = os.path.join(self.workdir, outname+".fastq.gz")

                
                skip = False
                if force:
                    if os.path.exists(fpath):
                        os.remove(fpath)
                else:
                    if os.path.exists(fpath):                
                        skip = True
                        sys.stdout.flush()
                        print("[skip] file already exists: {}".format(fpath))

                
                tidx = df.Accession.shape[0]
                
                    

                
                if not skip:
                    args = (self, srr, outname, paired)
                    if ipyclient:
                        async = lb.apply_async(call_fastq_dump_on_SRRs, *args)
                        asyncs.append(async)
                    else:
                        print("Downloading file {}/{}: {}".format(idx+1, tidx, fpath))
                        call_fastq_dump_on_SRRs(*args)
                        sys.stdout.flush()

            
            if ipyclient:
                tots = df.Accession.shape[0]
                printstr = " Downloading fastq files | {} | "
                start = time.time()
                while 1:
                    elapsed = datetime.timedelta(seconds=int(time.time()-start))
                    ready = sum([i.ready() for i in asyncs])
                    progressbar(tots, ready, printstr.format(elapsed), spacer="")
                    time.sleep(0.1)
                    if tots == ready:
                        print("")
                        break
                self._report(tots)

                
                for async in asyncs:
                    if not async.successful():
                        raise IPyradWarningExit(async.result())



    def _report(self, N):
        print("{} fastq files downloaded to {}".format(N, self.workdir))


    @property
    def fetch_fields(self):
        fields = pd.DataFrame(data=[COLNAMES, range(1, len(COLNAMES)+1)]).T
        fields.columns=['field', 'index']
        return fields


    def fetch_runinfo(self, fields=None, quiet=False):
        

        if not quiet:
            print("\rFetching project data...", end="")

        
        if fields == None:  
            fields = range(30)
        fields = fields_checker(fields)

        
        es_cmd = [
            "esearch", 
            "-db", "sra", 
            "-query", self.accession,
        ]

        ef_cmd = [
            "efetch", 
            "--format", "runinfo",
        ]

        cut_cmd = [
            "cut", 
            "-d", ",", 
            "-f", ",".join(fields),
        ]

        
        proc1 = sps.Popen(es_cmd, stderr=sps.STDOUT, stdout=sps.PIPE)
        proc2 = sps.Popen(ef_cmd, stdin=proc1.stdout, stderr=sps.STDOUT, stdout=sps.PIPE)
        proc3 = sps.Popen(cut_cmd, stdin=proc2.stdout, stderr=sps.STDOUT, stdout=sps.PIPE)
        o, e = proc3.communicate()
        proc2.stdout.close()
        proc1.stdout.close()
        
        if o:
            vals = o.strip().split("\n")
            names = vals[0].split(",")
            items = [i.split(",") for i in vals[1:] if i not in ["", vals[0]]]
            return pd.DataFrame(items, columns=names)
        else:
            raise IPyradWarningExit("no samples found in {}".format(self.accession))


    def _set_vdbconfig_path(self):

        
        proc = sps.Popen(
            ['vdb-config', '-p'], 
            stderr=sps.STDOUT, stdout=sps.PIPE)
        o, e = proc.communicate()
        self._oldtmpdir = o.split("root>")[1][:-2]

        
        proc = sps.Popen(
            ['vdb-config', '-s', 
            'repository/user/main/public/root='+self.workdir], 
            stderr=sps.STDOUT, stdout=sps.PIPE)
        o, e = proc.communicate()
        


    def _restore_vdbconfig_path(self):
        
        if not self._oldtmpdir:
            self._oldtmpdir = os.path.join(os.path.expanduser("~"), "ncbi")
        proc = sps.Popen(
            ['vdb-config', '-s', 
            'repository/user/main/public/root='+self._oldtmpdir],
            stderr=sps.STDOUT, stdout=sps.PIPE)
        o, e = proc.communicate()
        



def call_fastq_dump_on_SRRs(self, srr, outname, paired):
    


    
    fd_cmd = [
        "fastq-dump", srr,
        "--accession", outname,
        "--outdir", self.workdir, 
        "--gzip",
        ]
    if paired:
        fd_cmd += ["--split-files"]

    
    proc = sps.Popen(fd_cmd, stderr=sps.STDOUT, stdout=sps.PIPE)
    o, e = proc.communicate()

    
    
    
    srafile = os.path.join(self.workdir, "sra", srr+".sra")
    if os.path.exists(srafile):
        os.remove(srafile)



def fields_checker(fields):
    

    
    if isinstance(fields, int):
        fields = str(fields)
    if isinstance(fields, str):
        if "," in fields:
            fields = [str(i) for i in fields.split(",")]
        else:
            fields = [str(fields)]
    elif isinstance(fields, (tuple, list)):
        fields = [str(i) for i in fields]
    else:
        raise IPyradWarningExit("fields not properly formatted")

    
    fields = [i for i in fields if i != '0']

    return fields


FAILED_DOWNLOAD = 



COLNAMES = [
 'Run',
 'ReleaseDate',
 'LoadDate',
 'spots',
 'bases',
 'spots_with_mates',
 'avgLength',
 'size_MB',
 'AssemblyName',
 'download_path',
 'Experiment',
 'LibraryName',
 'LibraryStrategy',
 'LibrarySelection',
 'LibrarySource',
 'LibraryLayout',
 'InsertSize',
 'InsertDev',
 'Platform',
 'Model',
 'SRAStudy',
 'BioProject',
 'Study_Pubmed_id',
 'ProjectID',
 'Sample',
 'BioSample',
 'SampleType',
 'TaxID',
 'ScientificName',
 'SampleName',
 'g1k_pop_code',
 'source',
 'g1k_analysis_group',
 'Subject_ID',
 'Sex',
 'Disease',
 'Tumor',
 'Affection_Status',
 'Analyte_Type',
 'Histological_Type',
 'Body_Site',
 'CenterName',
 'Submission',
 'dbgap_study_accession',
 'Consent',
 'RunHash',
 'ReadHash',
 ]

