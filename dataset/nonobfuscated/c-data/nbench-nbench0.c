




#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include <time.h>
#include <math.h>
#include "nmglobal.h"
#include "nbench0.h"
#include "hardware.h"


#ifdef MAC
void main(void)
#else
int main(int argc, char *argv[])
#endif
{
int i;                  
time_t time_and_date;   
struct tm *loctime;
double bmean;           
double bstdev;          
double lx_memindex;     
double lx_intindex;     
double lx_fpindex;      
double intindex;        
double fpindex;         
ulong bnumrun;          

#ifdef MAC
        MaxApplZone();
#endif

#ifdef MACTIMEMGR

MacHSTdelay=600*1000*1000;      

memset((char *)&myTMTask,0,sizeof(TMTask));


PrimeTime((QElemPtr)&myTMTask,-MacHSTdelay);
RmvTime((QElemPtr)&myTMTask);
MacHSTohead=MacHSTdelay+myTMTask.tmCount;
#endif

#ifdef WIN31TIMER

win31tinfo.dwSize=(DWORD)sizeof(TIMERINFO);

if((hThlp=LoadLibrary("TOOLHELP.DLL"))<32)
{       printf("Error loading TOOLHELP\n");
        exit(0);
}
if(!(lpfn=GetProcAddress(hThlp,"TimerCount")))
{       printf("TOOLHELP error\n");
        exit(0);
}
#endif


global_min_ticks=MINIMUM_TICKS;
global_min_seconds=MINIMUM_SECONDS;
global_allstats=0;
global_custrun=0;
global_align=8;
write_to_file=0;
lx_memindex=(double)1.0;        
lx_intindex=(double)1.0;
lx_fpindex=(double)1.0;
intindex=(double)1.0;
fpindex=(double)1.0;
mem_array_ents=0;               


for(i=0;i<NUMTESTS;i++)
        tests_to_do[i]=1;


set_request_secs();     
global_numsortstruct.adjust=0;
global_numsortstruct.arraysize=NUMARRAYSIZE;

global_strsortstruct.adjust=0;
global_strsortstruct.arraysize=STRINGARRAYSIZE;

global_bitopstruct.adjust=0;
global_bitopstruct.bitfieldarraysize=BITFARRAYSIZE;

global_emfloatstruct.adjust=0;
global_emfloatstruct.arraysize=EMFARRAYSIZE;

global_fourierstruct.adjust=0;

global_assignstruct.adjust=0;

global_ideastruct.adjust=0;
global_ideastruct.arraysize=IDEAARRAYSIZE;

global_huffstruct.adjust=0;
global_huffstruct.arraysize=HUFFARRAYSIZE;

global_nnetstruct.adjust=0;

global_lustruct.adjust=0;


#ifdef MAC
UCommandLine();
#endif


if(argc>1)
        for(i=1;i<argc;i++)
                if(parse_arg(argv[i])==-1)
                {       display_help(argv[0]);
                        exit(0);
                }

#ifdef LINUX
output_string("\nBYTEmark* Native Mode Benchmark ver. 2 (10/95)\n");
output_string("Index-split by Andrew D. Balsa (11/97)\n");
output_string("Linux/Unix* port by Uwe F. Mayer (12/96,11/97)\n");
#else
output_string("BBBBBB   YYY   Y  TTTTTTT  EEEEEEE\n");
output_string("BBB   B  YYY   Y    TTT    EEE\n");
output_string("BBB   B  YYY   Y    TTT    EEE\n");
output_string("BBBBBB    YYY Y     TTT    EEEEEEE\n");
output_string("BBB   B    YYY      TTT    EEE\n");
output_string("BBB   B    YYY      TTT    EEE\n");
output_string("BBBBBB     YYY      TTT    EEEEEEE\n\n");
output_string("\nBYTEmark (tm) Native Mode Benchmark ver. 2 (10/95)\n");
#endif

if(global_allstats)
{
                output_string("\n");
                output_string("============================== ALL STATISTICS ===============================\n");
        time(&time_and_date);
        loctime=localtime(&time_and_date);
        sprintf(buffer,"**Date and time of benchmark run: %s",asctime(loctime));
        output_string(buffer);
        sprintf(buffer,"**Sizeof: char:%u short:%u int:%u long:%u u8:%u u16:%u u32:%u n_int32:%u\n",
                (unsigned int)sizeof(char),
                (unsigned int)sizeof(short),
                (unsigned int)sizeof(int),
                (unsigned int)sizeof(long),
                (unsigned int)sizeof(u8),
                (unsigned int)sizeof(u16),
                (unsigned int)sizeof(u32),
                (unsigned int)sizeof(n_int32));
        output_string(buffer);
                output_string("=============================================================================\n");
}


#ifdef LINUX
output_string("\nTEST                : Iterations/sec.  : Old Index   : New Index\n");
output_string("                    :                  : Pentium 90* : AMD K6/233*\n");
output_string("--------------------:------------------:-------------:------------\n");
#endif

for(i=0;i<NUMTESTS;i++)
{
        if(tests_to_do[i])
        {       sprintf(buffer,"%s    :",ftestnames[i]);
                                output_string(buffer);
                if (0!=bench_with_confidence(i,
                        &bmean,
                        &bstdev,
                        &bnumrun)){
		  output_string("\n** WARNING: The current test result is NOT 95 % statistically certain.\n");
		  output_string("** WARNING: The variation among the individual results is too large.\n");
		  output_string("                    :");
		}
#ifdef LINUX
                sprintf(buffer," %15.5g  :  %9.2f  :  %9.2f\n",
                        bmean,bmean/bindex[i],bmean/lx_bindex[i]);
#else
		sprintf(buffer,"  Iterations/sec.: %13.2f  Index: %6.2f\n",
                        bmean,bmean/bindex[i]);
#endif
                output_string(buffer);
		
		if((i==4)||(i==8)||(i==9)){
		  
		  fpindex=fpindex*(bmean/bindex[i]);
		  
		  lx_fpindex=lx_fpindex*(bmean/lx_bindex[i]);
		}
		else{
		  
		  intindex=intindex*(bmean/bindex[i]);
		  if((i==0)||(i==3)||(i==6)||(i==7))
		    
		    lx_intindex=lx_intindex*(bmean/lx_bindex[i]);
		  else
		    
		    lx_memindex=lx_memindex*(bmean/lx_bindex[i]);
		}

                if(global_allstats)
                {
                        sprintf(buffer,"  Absolute standard deviation: %g\n",bstdev);
                        output_string(buffer);
			if (bmean>(double)1e-100){
			  
			  sprintf(buffer,"  Relative standard deviation: %g %%\n",
				  (double)100*bstdev/bmean);
			  output_string(buffer);
			}
                        sprintf(buffer,"  Number of runs: %lu\n",bnumrun);
                        output_string(buffer);
                        show_stats(i);
                        sprintf(buffer,"Done with %s\n\n",ftestnames[i]);
                        output_string(buffer);
                }
        }
}



if(global_custrun==0)
{
        output_string("==========================ORIGINAL BYTEMARK RESULTS==========================\n");
        sprintf(buffer,"INTEGER INDEX       : %.3f\n",
                       pow(intindex,(double).142857));
        output_string(buffer);
        sprintf(buffer,"FLOATING-POINT INDEX: %.3f\n",
                        pow(fpindex,(double).33333));
        output_string(buffer);
        output_string("Baseline (MSDOS*)   : Pentium* 90, 256 KB L2-cache, Watcom* compiler 10.0\n");
#ifdef LINUX
        output_string("==============================LINUX DATA BELOW===============================\n");
	hardware(write_to_file, global_ofile);
        sprintf(buffer,"MEMORY INDEX        : %.3f\n",
                       pow(lx_memindex,(double).3333333333));
        output_string(buffer);
        sprintf(buffer,"INTEGER INDEX       : %.3f\n",
                       pow(lx_intindex,(double).25));
        output_string(buffer);
        sprintf(buffer,"FLOATING-POINT INDEX: %.3f\n",
                        pow(lx_fpindex,(double).3333333333));
        output_string(buffer);
        output_string("Baseline (LINUX)    : AMD K6/233*, 512 KB L2-cache, gcc 2.7.2.3, libc-5.4.38\n");
#endif
output_string("* Trademarks are property of their respective holder.\n");
}

exit(0);
}


static int parse_arg(char *argptr)
{
int i;          
FILE *cfile;    


if(*argptr++!='-') return(-1);


for(i=0;i<strlen(argptr);i++)
        argptr[i]=(char)toupper((int)argptr[i]);


switch(*argptr++)
{
        case '?':       return(-1);     

        case 'V': global_allstats=1; return(0); 

        case 'C':                       
                
                cfile=fopen(argptr,"r");
                if(cfile==(FILE *)NULL)
                {       printf("**Error opening file: %s\n",argptr);
                        return(-1);
                }
                read_comfile(cfile);    
                fclose(cfile);
                break;
        default:
                return(-1);
}
return(0);
}


void display_help(char *progname)
{
        printf("Usage: %s [-v] [-c<FILE>]\n",progname);
        printf(" -v = verbose\n");
        printf(" -c = input parameters thru command file <FILE>\n");
        exit(0);
}



static void read_comfile(FILE *cfile)
{
char inbuf[40];
char *eptr;             
int i;                  


while(fgets(inbuf,39,cfile)!=(char *)NULL)
{
        
        if(strlen(inbuf)>0)
                inbuf[strlen(inbuf)-1]='\0';

        
        if((eptr=strchr(inbuf,(int)'='))==(char *)NULL)
        {       printf("**COMMAND FILE ERROR at LINE:\n %s\n",
                        inbuf);
                goto skipswitch;        
        }

        
        *eptr++='\0';
        strtoupper((char *)&inbuf[0]);
        i=MAXPARAM;
        do {
                if(strcmp(inbuf,paramnames[i])==0)
                        break;
        } while(--i>=0);

        if(i<0)
        {       printf("**COMMAND FILE ERROR -- UNKNOWN PARAM: %s",
                        inbuf);
                goto skipswitch;
        }

        
        switch(i)
        {
                case PF_GMTICKS:        
                        global_min_ticks=(ulong)atol(eptr);
                        break;

                case PF_MINSECONDS:     
                        global_min_seconds=(ulong)atol(eptr);
                        set_request_secs();
                        break;

                case PF_ALLSTATS:       
                        global_allstats=getflag(eptr);
                        break;

                case PF_OUTFILE:        
                        strcpy(global_ofile_name,eptr);
                        global_ofile=fopen(global_ofile_name,"a");
                        
                        if(global_ofile==(FILE *)NULL)
                        {       printf("**Error opening output file: %s\n",
                                        global_ofile_name);
                                ErrorExit();
                        }
                        write_to_file=-1;
                        break;

                case PF_CUSTOMRUN:      
                        global_custrun=getflag(eptr);
                        for(i=0;i<NUMTESTS;i++)
                                tests_to_do[i]=1-global_custrun;
                        break;

                case PF_DONUM:          
                        tests_to_do[TF_NUMSORT]=getflag(eptr);
                        break;

                case PF_NUMNUMA:        
                        global_numsortstruct.numarrays=
                                (ushort)atoi(eptr);
                        global_numsortstruct.adjust=1;
                        break;

                case PF_NUMASIZE:       
                        global_numsortstruct.arraysize=
                                (ulong)atol(eptr);
                        break;

                case PF_NUMMINS:        
                        global_numsortstruct.request_secs=
                                (ulong)atol(eptr);
                        break;

                case PF_DOSTR:          
                        tests_to_do[TF_SSORT]=getflag(eptr);
                        break;

                case PF_STRASIZE:       
                        global_strsortstruct.arraysize=
                                (ulong)atol(eptr);
                        break;

                case PF_NUMSTRA:        
                        global_strsortstruct.numarrays=
                                (ushort)atoi(eptr);
                        global_strsortstruct.adjust=1;
                        break;

                case PF_STRMINS:        
                        global_strsortstruct.request_secs=
                                (ulong)atol(eptr);
                        break;

                case PF_DOBITF: 
                        tests_to_do[TF_BITOP]=getflag(eptr);
                        break;

                case PF_NUMBITOPS:      
                        global_bitopstruct.bitoparraysize=
                                (ulong)atol(eptr);
                        global_bitopstruct.adjust=1;
                        break;

                case PF_BITFSIZE:       
                        global_bitopstruct.bitfieldarraysize=
                                (ulong)atol(eptr);
                        break;

                case PF_BITMINS:        
                        global_bitopstruct.request_secs=
                                (ulong)atol(eptr);
                        break;

                case PF_DOEMF:          
                        tests_to_do[TF_FPEMU]=getflag(eptr);
                        break;

                case PF_EMFASIZE:       
                        global_emfloatstruct.arraysize=
                                (ulong)atol(eptr);
                        break;

                case PF_EMFLOOPS:       
                        global_emfloatstruct.loops=
                                (ulong)atol(eptr);
                        break;

                case PF_EMFMINS:        
                        global_emfloatstruct.request_secs=
                                (ulong)atol(eptr);
                        break;

                case PF_DOFOUR: 
                        tests_to_do[TF_FFPU]=getflag(eptr);
                        break;

                case PF_FOURASIZE:      
                        global_fourierstruct.arraysize=
                                (ulong)atol(eptr);
                        global_fourierstruct.adjust=1;
                        break;

                case PF_FOURMINS:       
                        global_fourierstruct.request_secs=
                                (ulong)atol(eptr);
                        break;

                case PF_DOASSIGN:       
                        tests_to_do[TF_ASSIGN]=getflag(eptr);
                        break;

                case PF_AARRAYS:        
                        global_assignstruct.numarrays=
                                (ulong)atol(eptr);
                        break;

                case PF_ASSIGNMINS:     
                        global_assignstruct.request_secs=
                                (ulong)atol(eptr);
                        break;

                case PF_DOIDEA: 
                        tests_to_do[TF_IDEA]=getflag(eptr);
                        break;

                case PF_IDEAASIZE:      
                        global_ideastruct.arraysize=
                                (ulong)atol(eptr);
                        break;

                case PF_IDEALOOPS:      
                        global_ideastruct.loops=
                                (ulong)atol(eptr);
                        break;

                case PF_IDEAMINS:       
                        global_ideastruct.request_secs=
                                (ulong)atol(eptr);
                        break;

                case PF_DOHUFF: 
                        tests_to_do[TF_HUFF]=getflag(eptr);
                        break;

                case PF_HUFFASIZE:      
                        global_huffstruct.arraysize=
                                (ulong)atol(eptr);
                        break;

                case PF_HUFFLOOPS:      
                        global_huffstruct.loops=
                                (ulong)atol(eptr);
                        global_huffstruct.adjust=1;
                        break;

                case PF_HUFFMINS:       
                        global_huffstruct.request_secs=
                                (ulong)atol(eptr);
                        break;

                case PF_DONNET: 
                        tests_to_do[TF_NNET]=getflag(eptr);
                        break;

                case PF_NNETLOOPS:      
                        global_nnetstruct.loops=
                                (ulong)atol(eptr);
                        global_nnetstruct.adjust=1;
                        break;

                case PF_NNETMINS:       
                        global_nnetstruct.request_secs=
                                (ulong)atol(eptr);
                        break;

                case PF_DOLU:           
                        tests_to_do[TF_LU]=getflag(eptr);
                        break;

                case PF_LUNARRAYS:      
                        global_lustruct.numarrays=
                                (ulong)atol(eptr);
                        global_lustruct.adjust=1;
                        break;

                case PF_LUMINS: 
                        global_lustruct.request_secs=
                                (ulong)atol(eptr);
                        break;

                                case PF_ALIGN:          
                                                global_align=atoi(eptr);
                                                break;
        }
skipswitch:
        continue;
}       

return;
}


static int getflag(char *cptr)
{
        if(toupper((int)*cptr)=='T') return(1);
return(0);
}


static void strtoupper(char *s)
{

do {

        *s=(char)toupper((int)*s);
        s++;
} while(*s!=(char)'\0');
return;
}


static void set_request_secs(void)
{

global_numsortstruct.request_secs=global_min_seconds;
global_strsortstruct.request_secs=global_min_seconds;
global_bitopstruct.request_secs=global_min_seconds;
global_emfloatstruct.request_secs=global_min_seconds;
global_fourierstruct.request_secs=global_min_seconds;
global_assignstruct.request_secs=global_min_seconds;
global_ideastruct.request_secs=global_min_seconds;
global_huffstruct.request_secs=global_min_seconds;
global_nnetstruct.request_secs=global_min_seconds;
global_lustruct.request_secs=global_min_seconds;

return;
}



static int bench_with_confidence(int fid,       
        double *mean,                   
        double *stdev,                  
        ulong *numtries)                
{
double myscores[30];            
double c_half_interval;         
int i;                          
          


for (i=0;i<5;i++)
{       (*funcpointer[fid])();
        myscores[i]=getscore(fid);
#ifdef DEBUG
	printf("score # %d = %g\n", i, myscores[i]);
#endif
}
*numtries=5;            




while(1)
{
        
        if (0!=calc_confidence(myscores,
		*numtries,
                &c_half_interval,
                mean,
                stdev)) return(-1);

        
        if(c_half_interval/ (*mean) <= (double)0.05)
                break;

#ifdef OLDCODE
#undef OLDCODE
#endif
#ifdef OLDCODE


	      
	      do {
		      if(*numtries==10)
			      return(-1);
		      (*funcpointer[fid])();
		      *numtries+=1;
		      newscore=getscore(fid);
	      } while(seek_confidence(myscores,&newscore,
		      &c_half_interval,mean,stdev)==0);
#endif
	
	if(*numtries==30) return(-1);
	(*funcpointer[fid])();
	myscores[*numtries]=getscore(fid);
#ifdef DEBUG
	printf("score # %ld = %g\n", *numtries, myscores[*numtries]);
#endif
	*numtries+=1;
}

return(0);
}

#ifdef OLDCODE

  
  static int seek_confidence( double scores[5],
  		double *newscore,
  		double *c_half_interval,
  		double *smean,
  		double *sdev)
  {
  double sdev_to_beat;    
  double temp;            
  int is_beaten;          
  int i;                  

  
  calc_confidence(scores,c_half_interval,smean,sdev);
  sdev_to_beat=*sdev;
  is_beaten=-1;

  
  for(i=0;i<5;i++)
  {
  	temp=scores[i];
  	scores[i]=*newscore;
  	calc_confidence(scores,c_half_interval,smean,sdev);
  	scores[i]=temp;
  	if(sdev_to_beat>*sdev)
  	{       is_beaten=i;
  		sdev_to_beat=*sdev;
  	}
  }

  if(is_beaten!=-1)
  {       scores[is_beaten]=*newscore;
  	return(-1);
  }
  return(0);
  }
#endif


static int calc_confidence(double scores[], 
		int num_scores,             
                double *c_half_interval,    
                double *smean,              
                double *sdev)               
{

double student_t[30]={0.0 , 12.706 , 4.303 , 3.182 , 2.776 , 2.571 ,
                             2.447 , 2.365 , 2.306 , 2.262 , 2.228 ,
                             2.201 , 2.179 , 2.160 , 2.145 , 2.131 ,
                             2.120 , 2.110 , 2.101 , 2.093 , 2.086 ,
                             2.080 , 2.074 , 2.069 , 2.064 , 2.060 ,
		             2.056 , 2.052 , 2.048 , 2.045 };
int i;          
if ((num_scores<2) || (num_scores>30)) {
  output_string("Internal error: calc_confidence called with an illegal number of scores\n");
  return(-1);
}

*smean=(double)0.0;
for(i=0;i<num_scores;i++){
  *smean+=scores[i];
}
*smean/=(double)num_scores;


*sdev=(double)0.0;
for(i=0;i<num_scores;i++) {
  *sdev+=(scores[i]-(*smean))*(scores[i]-(*smean));
}
*sdev/=(double)(num_scores-1);
*sdev=sqrt(*sdev);


*c_half_interval=student_t[num_scores-1] * (*sdev) / sqrt((double)num_scores);
return(0);
}


static double getscore(int fid)
{


switch(fid)
{
        case TF_NUMSORT:
                return(global_numsortstruct.sortspersec);
        case TF_SSORT:
                return(global_strsortstruct.sortspersec);
        case TF_BITOP:
                return(global_bitopstruct.bitopspersec);
        case TF_FPEMU:
                return(global_emfloatstruct.emflops);
        case TF_FFPU:
                return(global_fourierstruct.fflops);
        case TF_ASSIGN:
                return(global_assignstruct.iterspersec);
        case TF_IDEA:
                return(global_ideastruct.iterspersec);
        case TF_HUFF:
                return(global_huffstruct.iterspersec);
        case TF_NNET:
                return(global_nnetstruct.iterspersec);
        case TF_LU:
                return(global_lustruct.iterspersec);
}
return((double)0.0);
}


static void output_string(char *buffer)
{

printf("%s",buffer);
if(write_to_file!=0)
        fprintf(global_ofile,"%s",buffer);
return;
}


static void show_stats (int bid)
{
char buffer[80];        

switch(bid)
{
        case TF_NUMSORT:                
                sprintf(buffer,"  Number of arrays: %d\n",
                        global_numsortstruct.numarrays);
                output_string(buffer);
                sprintf(buffer,"  Array size: %ld\n",
                        global_numsortstruct.arraysize);
                output_string(buffer);
                break;

        case TF_SSORT:          
                sprintf(buffer,"  Number of arrays: %d\n",
                        global_strsortstruct.numarrays);
                output_string(buffer);
                sprintf(buffer,"  Array size: %ld\n",
                        global_strsortstruct.arraysize);
                output_string(buffer);
                break;

        case TF_BITOP:          
                sprintf(buffer,"  Operations array size: %ld\n",
                        global_bitopstruct.bitoparraysize);
                output_string(buffer);
                sprintf(buffer,"  Bitfield array size: %ld\n",
                        global_bitopstruct.bitfieldarraysize);
                output_string(buffer);
                break;

        case TF_FPEMU:          
                sprintf(buffer,"  Number of loops: %lu\n",
                        global_emfloatstruct.loops);
                output_string(buffer);
                sprintf(buffer,"  Array size: %lu\n",
                        global_emfloatstruct.arraysize);
                output_string(buffer);
                break;

        case TF_FFPU:           
                sprintf(buffer,"  Number of coefficients: %lu\n",
                        global_fourierstruct.arraysize);
                output_string(buffer);
                break;

        case TF_ASSIGN:
                sprintf(buffer,"  Number of arrays: %lu\n",
                        global_assignstruct.numarrays);
                output_string(buffer);
                break;

        case TF_IDEA:
                sprintf(buffer,"  Array size: %lu\n",
                        global_ideastruct.arraysize);
                output_string(buffer);
                sprintf(buffer," Number of loops: %lu\n",
                        global_ideastruct.loops);
                output_string(buffer);
                break;

        case TF_HUFF:
                sprintf(buffer,"  Array size: %lu\n",
                        global_huffstruct.arraysize);
                output_string(buffer);
                sprintf(buffer,"  Number of loops: %lu\n",
                        global_huffstruct.loops);
                output_string(buffer);
                break;

        case TF_NNET:
                sprintf(buffer,"  Number of loops: %lu\n",
                        global_nnetstruct.loops);
                output_string(buffer);
                break;

        case TF_LU:
                sprintf(buffer,"  Number of arrays: %lu\n",
                        global_lustruct.numarrays);
                output_string(buffer);
                break;
}
return;
}



#ifdef MAC


void UCommandLine(void)
{
printf("Enter command line\n:");
gets((char *)Uargbuff);
UParse();
return;
}


void UParse(void)
{
unsigned char *ptr;

argc=0;         
Udummy[0]='*';  
Udummy[1]='\0';
argv[argc++]=(char *)Udummy;

ptr=Uargbuff;           
while(*ptr)
{
        if(isspace(*ptr))
        {       ++ptr;
                continue;
        }
        if(argc<20) argv[argc++]=(char *)ptr;
        ptr=UField(ptr);
}
return;
}

unsigned char *UField(unsigned char *ptr)
{
while(*ptr)
{       if(isspace(*ptr))
        {       *ptr=(unsigned char)NULL;
                return(++ptr);
        }
        ++ptr;
}
return(ptr);
}
#endif
