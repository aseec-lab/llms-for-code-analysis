





#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <strings.h>
#include <math.h>
#include "nmglobal.h"
#include "nbench1.h"
#include "wordcat.h"

#ifdef DEBUG
static int numsort_status=0;
static int stringsort_status=0;
#endif





void DoNumSort(void)
{
SortStruct *numsortstruct;      
farlong *arraybase;     
long accumtime;         
double iterations;      
char *errorcontext;     
int systemerror;        


numsortstruct=&global_numsortstruct;


errorcontext="CPU:Numeric Sort";


if(numsortstruct->adjust==0)
{
	
	numsortstruct->numarrays=1;
	while(1)
	{
		
		arraybase=(farlong *)AllocateMemory(sizeof(long) *
			numsortstruct->numarrays * numsortstruct->arraysize,
			&systemerror);
		if(systemerror)
		{       ReportError(errorcontext,systemerror);
			FreeMemory((farvoid *)arraybase,
				  &systemerror);
			ErrorExit();
		}

		
		if(DoNumSortIteration(arraybase,
			numsortstruct->arraysize,
			numsortstruct->numarrays)>global_min_ticks)
			break;          

		FreeMemory((farvoid *)arraybase,&systemerror);
		if(numsortstruct->numarrays++>NUMNUMARRAYS)
		{       printf("CPU:NSORT -- NUMNUMARRAYS hit.\n");
			ErrorExit();
		}
	}
}
else
{       
	arraybase=(farlong *)AllocateMemory(sizeof(long) *
		numsortstruct->numarrays * numsortstruct->arraysize,
		&systemerror);
	if(systemerror)
	{       ReportError(errorcontext,systemerror);
		FreeMemory((farvoid *)arraybase,
			  &systemerror);
		ErrorExit();
	}

}

accumtime=0L;
iterations=(double)0.0;

do {
	accumtime+=DoNumSortIteration(arraybase,
		numsortstruct->arraysize,
		numsortstruct->numarrays);
	iterations+=(double)1.0;
} while(TicksToSecs(accumtime)<numsortstruct->request_secs);


FreeMemory((farvoid *)arraybase,&systemerror);

numsortstruct->sortspersec=iterations *
	(double)numsortstruct->numarrays / TicksToFracSecs(accumtime);

if(numsortstruct->adjust==0)
	numsortstruct->adjust=1;

#ifdef DEBUG
if (numsort_status==0) printf("Numeric sort: OK\n");
numsort_status=0;
#endif
return;
}


static ulong DoNumSortIteration(farlong *arraybase,
		ulong arraysize,
		uint numarrays)
{
ulong elapsed;          
ulong i;

LoadNumArrayWithRand(arraybase,arraysize,numarrays);


elapsed=StartStopwatch();


for(i=0;i<numarrays;i++)
	NumHeapSort(arraybase+i*arraysize,0L,arraysize-1L);


elapsed=StopStopwatch(elapsed);
#ifdef DEBUG
{
	for(i=0;i<arraysize-1;i++)
	{       
		if(arraybase[i+1]<arraybase[i])
		{       printf("Sort Error\n");
			numsort_status=1;
                        break;
		}
	}
}
#endif

return(elapsed);
}


static void LoadNumArrayWithRand(farlong *array,     
		ulong arraysize,
		uint numarrays)         
{
long i;                 
farlong *darray;        


randnum((n_int32)13);


for(i=0L;i<arraysize;i++)
        
	array[i]=randnum((n_int32)0);


darray=array;
while(--numarrays)
{       darray+=arraysize;
	for(i=0L;i<arraysize;i++)
		darray[i]=array[i];
}

return;
}


static void NumHeapSort(farlong *array,
	ulong bottom,           
	ulong top)              
{
ulong temp;                     
ulong i;                        


for(i=(top/2L); i>0; --i)
	NumSift(array,i,top);


for(i=top; i>0; --i)
{       NumSift(array,bottom,i);
	temp=*array;                    
	*array=*(array+i);
	*(array+i)=temp;
}
return;
}


static void NumSift(farlong *array,     
	ulong i,                
	ulong j)                
{
unsigned long k;
long temp;                              

while((i+i)<=j)
{
	k=i+i;
	if(k<j)
		if(array[k]<array[k+1L])
			++k;
	if(array[i]<array[k])
	{
		temp=array[k];
		array[k]=array[i];
		array[i]=temp;
		i=k;
	}
	else
		i=j+1;
}
return;
}




void DoStringSort(void)
{

SortStruct *strsortstruct;      
faruchar *arraybase;            
long accumtime;                 
double iterations;              
char *errorcontext;             
int systemerror;                


strsortstruct=&global_strsortstruct;


errorcontext="CPU:String Sort";


if(strsortstruct->adjust==0)
{
	
	strsortstruct->numarrays=1;
	while(1)
	{
		
		arraybase=(faruchar *)AllocateMemory((strsortstruct->arraysize+100L) *
			(long)strsortstruct->numarrays,&systemerror);
		if(systemerror)
		{       ReportError(errorcontext,systemerror);
			ErrorExit();
		}

		
		if(DoStringSortIteration(arraybase,
			strsortstruct->numarrays,
			strsortstruct->arraysize)>global_min_ticks)
			break;          

		FreeMemory((farvoid *)arraybase,&systemerror);
		strsortstruct->numarrays+=1;
	}
}
else
{
	
	arraybase=(faruchar *)AllocateMemory((strsortstruct->arraysize+100L) *
		(long)strsortstruct->numarrays,&systemerror);
	if(systemerror)
	{       ReportError(errorcontext,systemerror);
		ErrorExit();
	}
}

accumtime=0L;
iterations=(double)0.0;

do {
	accumtime+=DoStringSortIteration(arraybase,
				strsortstruct->numarrays,
				strsortstruct->arraysize);
	iterations+=(double)strsortstruct->numarrays;
} while(TicksToSecs(accumtime)<strsortstruct->request_secs);


FreeMemory((farvoid *)arraybase,&systemerror);
strsortstruct->sortspersec=iterations / (double)TicksToFracSecs(accumtime);
if(strsortstruct->adjust==0)
	strsortstruct->adjust=1;
#ifdef DEBUG
if (stringsort_status==0) printf("String sort: OK\n");
stringsort_status=0;
#endif
return;
}


static ulong DoStringSortIteration(faruchar *arraybase,
		uint numarrays,ulong arraysize)
{
farulong *optrarray;            
unsigned long elapsed;          
unsigned long nstrings;         
int syserror;                   
unsigned int i;                 
farulong *tempobase;            
faruchar *tempsbase;            


optrarray=LoadStringArray(arraybase,numarrays,&nstrings,arraysize);


tempobase=optrarray;
tempsbase=arraybase;


elapsed=StartStopwatch();


for(i=0;i<numarrays;i++)
{       StrHeapSort(tempobase,tempsbase,nstrings,0L,nstrings-1);
	tempobase+=nstrings;    
	tempsbase+=arraysize+100;
}


elapsed=StopStopwatch(elapsed);

#ifdef DEBUG
{
	unsigned long i;
	for(i=0;i<nstrings-1;i++)
	{       
		if(str_is_less(optrarray,arraybase,nstrings,i+1,i))
		{       printf("Sort Error\n");
			stringsort_status=1;
                        break;
		}
	}
}
#endif


FreeMemory((farvoid *)optrarray,&syserror);


return(elapsed);
}


static farulong *LoadStringArray(faruchar *strarray, 
	uint numarrays,                 
	ulong *nstrings,                
	ulong arraysize)                
{
faruchar *tempsbase;            
farulong *optrarray;            
farulong *tempobase;            
unsigned long curroffset;       
int fullflag;                   
unsigned char stringlength;     
unsigned char i;                
unsigned long j;                
unsigned int k;                 
unsigned int l;                 
int systemerror;                



randnum((n_int32)13);


*nstrings=0L;
curroffset=0L;
fullflag=0;

do
{
	
        
	stringlength=(unsigned char)((1+abs_randwc((n_int32)76)) & 0xFFL);
	if((unsigned long)stringlength+curroffset+1L>=arraysize)
	{       stringlength=(unsigned char)((arraysize-curroffset-1L) &
				0xFF);
		fullflag=1;     
	}

	
	*(strarray+curroffset)=stringlength;
	curroffset++;

	
	for(i=0;i<stringlength;i++)
	{       *(strarray+curroffset)=
		        
			(unsigned char)(abs_randwc((n_int32)0xFE));
		curroffset++;
	}

	
	*nstrings+=1L;

} while(fullflag==0);


k=1;
tempsbase=strarray;
while(k<numarrays)
{       tempsbase+=arraysize+100;         
	for(l=0;l<arraysize;l++)
		tempsbase[l]=strarray[l];
	k++;
}


optrarray=(farulong *)AllocateMemory(*nstrings * sizeof(unsigned long) *
		numarrays,
		&systemerror);
if(systemerror)
{       ReportError("CPU:Stringsort",systemerror);
	FreeMemory((void *)strarray,&systemerror);
	ErrorExit();
}


curroffset=0;
for(j=0;j<*nstrings;j++)
{       *(optrarray+j)=curroffset;
	curroffset+=(unsigned long)(*(strarray+curroffset))+1L;
}


k=1;
tempobase=optrarray;
while(k<numarrays)
{       tempobase+=*nstrings;
	for(l=0;l<*nstrings;l++)
		tempobase[l]=optrarray[l];
	k++;
}


return(optrarray);
}


static void stradjust(farulong *optrarray,      
	faruchar *strarray,                     
	ulong nstrings,                         
	ulong i,                                
	uchar l)                                
{
unsigned long nbytes;           
unsigned long j;                
int direction;                  
unsigned char adjamount;        


direction=(int)l - (int)*(strarray+*(optrarray+i));
adjamount=(unsigned char)abs(direction);


if(i==(nstrings-1L))
{       *(strarray+*(optrarray+i))=l;
	return;
}


nbytes=*(optrarray+nstrings-1L) +
	(unsigned long)*(strarray+*(optrarray+nstrings-1L)) + 1L -
	*(optrarray+i+1L);


MoveMemory((farvoid *)(strarray+*(optrarray+i)+l+1),
	(farvoid *)(strarray+*(optrarray+i+1)),
	(unsigned long)nbytes);


for(j=i+1;j<nstrings;j++)
	if(direction<0)
		*(optrarray+j)=*(optrarray+j)-adjamount;
	else
		*(optrarray+j)=*(optrarray+j)+adjamount;


*(strarray+*(optrarray+i))=l;
return;
}


static void StrHeapSort(farulong *optrarray, 
	faruchar *strarray,             
	ulong numstrings,               
	ulong bottom,                   
	ulong top)                      
{
unsigned char temp[80];                 
unsigned char tlen;                     
unsigned long i;                        



for(i=(top/2L); i>0; --i)
	strsift(optrarray,strarray,numstrings,i,top);


for(i=top; i>0; --i)
{
	strsift(optrarray,strarray,numstrings,0,i);

	
	tlen=*strarray;
	MoveMemory((farvoid *)&temp[0], 
		(farvoid *)strarray,
		(unsigned long)(tlen+1));


	
	tlen=*(strarray+*(optrarray+i));
	stradjust(optrarray,strarray,numstrings,0,tlen);
	MoveMemory((farvoid *)strarray,
		(farvoid *)(strarray+*(optrarray+i)),
		(unsigned long)(tlen+1));

	
	tlen=temp[0];
	stradjust(optrarray,strarray,numstrings,i,tlen);
	MoveMemory((farvoid *)(strarray+*(optrarray+i)),
		(farvoid *)&temp[0],
		(unsigned long)(tlen+1));

}
return;
}


static int str_is_less(farulong *optrarray, 
	faruchar *strarray,                     
	ulong numstrings,                       
	ulong a, ulong b)                       
{
int slen;               


slen=(int)*(strarray+*(optrarray+a));
if(slen > (int)*(strarray+*(optrarray+b)))
	slen=(int)*(strarray+*(optrarray+b));

slen=strncmp((char *)(strarray+*(optrarray+a)),
		(char *)(strarray+*(optrarray+b)),slen);

if(slen==0)
{
	
	if(*(strarray+*(optrarray+a)) >
		*(strarray+*(optrarray+b)))
		return(TRUE);
	return(FALSE);
}

if(slen<0) return(TRUE);        

return(FALSE);                  
}


static void strsift(farulong *optrarray,        
	faruchar *strarray,                     
	ulong numstrings,                       
	ulong i, ulong j)                       
{
unsigned long k;                
unsigned char temp[80];
unsigned char tlen;             


while((i+i)<=j)
{
	k=i+i;
	if(k<j)
		if(str_is_less(optrarray,strarray,numstrings,k,k+1L))
			++k;
	if(str_is_less(optrarray,strarray,numstrings,i,k))
	{
		
		tlen=*(strarray+*(optrarray+k));
		MoveMemory((farvoid *)&temp[0],
			(farvoid *)(strarray+*(optrarray+k)),
			(unsigned long)(tlen+1));

		
		tlen=*(strarray+*(optrarray+i));
		stradjust(optrarray,strarray,numstrings,k,tlen);
		MoveMemory((farvoid *)(strarray+*(optrarray+k)),
			(farvoid *)(strarray+*(optrarray+i)),
			(unsigned long)(tlen+1));

		
		tlen=temp[0];
		stradjust(optrarray,strarray,numstrings,i,tlen);
		MoveMemory((farvoid *)(strarray+*(optrarray+i)),
			(farvoid *)&temp[0],
			(unsigned long)(tlen+1));
		i=k;
	}
	else
		i=j+1;
}
return;
}




void DoBitops(void)
{
BitOpStruct *locbitopstruct;    
farulong *bitarraybase;         
farulong *bitoparraybase;       
ulong nbitops;                  
ulong accumtime;                
double iterations;              
char *errorcontext;             
int systemerror;                
int ticks;


locbitopstruct=&global_bitopstruct;


errorcontext="CPU:Bitfields";


if(locbitopstruct->adjust==0)
{
	bitarraybase=(farulong *)AllocateMemory(locbitopstruct->bitfieldarraysize *
		sizeof(ulong),&systemerror);
	if(systemerror)
	{       ReportError(errorcontext,systemerror);
		ErrorExit();
	}

	
	locbitopstruct->bitoparraysize=30L;

	while(1)
	{
		
		bitoparraybase=(farulong *)AllocateMemory(locbitopstruct->bitoparraysize*2L*
			sizeof(ulong),
			&systemerror);
		if(systemerror)
		{       ReportError(errorcontext,systemerror);
			FreeMemory((farvoid *)bitarraybase,&systemerror);
			ErrorExit();
		}
		
		ticks=DoBitfieldIteration(bitarraybase,
					   bitoparraybase,
					   locbitopstruct->bitoparraysize,
					   &nbitops);
#ifdef DEBUG
#ifdef LINUX
	        if (locbitopstruct->bitoparraysize==30L){
		  
		  FILE *file;
		  unsigned long *running_base; 
		  long counter;
		  file=fopen("debugbit.dat","w");
		  running_base=bitarraybase;
		  for (counter=0;counter<(long)(locbitopstruct->bitfieldarraysize);counter++){
#ifdef LONG64
		    fprintf(file,"%08X",(unsigned int)(*running_base&0xFFFFFFFFL));
		    fprintf(file,"%08X",(unsigned int)((*running_base>>32)&0xFFFFFFFFL));
		    if ((counter+1)%4==0) fprintf(file,"\n");
#else
		    fprintf(file,"%08lX",*running_base);
		    if ((counter+1)%8==0) fprintf(file,"\n");
#endif
		    running_base=running_base+1;
		  }
		  fclose(file);
		  printf("\nWrote the file debugbit.dat, you may want to compare it to debugbit.good\n");
		}
#endif
#endif

		if (ticks>global_min_ticks) break;      

		FreeMemory((farvoid *)bitoparraybase,&systemerror);
		locbitopstruct->bitoparraysize+=100L;
	}
}
else
{
	
	bitarraybase=(farulong *)AllocateMemory(locbitopstruct->bitfieldarraysize *
		sizeof(ulong),&systemerror);
	if(systemerror)
	{       ReportError(errorcontext,systemerror);
		ErrorExit();
	}
	bitoparraybase=(farulong *)AllocateMemory(locbitopstruct->bitoparraysize*2L*
		sizeof(ulong),
		&systemerror);
	if(systemerror)
	{       ReportError(errorcontext,systemerror);
		FreeMemory((farvoid *)bitarraybase,&systemerror);
		ErrorExit();
	}
}


accumtime=0L;
iterations=(double)0.0;
do {
	accumtime+=DoBitfieldIteration(bitarraybase,
			bitoparraybase,
			locbitopstruct->bitoparraysize,&nbitops);
	iterations+=(double)nbitops;
} while(TicksToSecs(accumtime)<locbitopstruct->request_secs);


FreeMemory((farvoid *)bitarraybase,&systemerror);
FreeMemory((farvoid *)bitoparraybase,&systemerror);
locbitopstruct->bitopspersec=iterations /TicksToFracSecs(accumtime);
if(locbitopstruct->adjust==0)
	locbitopstruct->adjust=1;

return;
}


static ulong DoBitfieldIteration(farulong *bitarraybase,
		farulong *bitoparraybase,
		long bitoparraysize,
		ulong *nbitops)
{
long i;                         
ulong bitoffset;                
ulong elapsed;                  

*nbitops=0L;



randnum((n_int32)13);
for (i=0;i<global_bitopstruct.bitfieldarraysize;i++)
{
#ifdef LONG64
	*(bitarraybase+i)=(ulong)0x5555555555555555;
#else
	*(bitarraybase+i)=(ulong)0x55555555;
#endif
}
randnum((n_int32)13);


for (i=0;i<bitoparraysize;i++)
{
	
        
	*(bitoparraybase+i+i)=bitoffset=abs_randwc((n_int32)262140);

	
	
	*nbitops+=*(bitoparraybase+i+i+1L)=abs_randwc((n_int32)262140-bitoffset);
}


elapsed=StartStopwatch();


for(i=0;i<bitoparraysize;i++)
{
	switch(i % 3)
	{

		case 0: 
			ToggleBitRun(bitarraybase,
				*(bitoparraybase+i+i),
				*(bitoparraybase+i+i+1),
				1);
			break;

		case 1: 
			ToggleBitRun(bitarraybase,
				*(bitoparraybase+i+i),
				*(bitoparraybase+i+i+1),
				0);
			break;

		case 2: 
			FlipBitRun(bitarraybase,
				*(bitoparraybase+i+i),
				*(bitoparraybase+i+i+1));
			break;
	}
}


return(StopStopwatch(elapsed));
}



static void ToggleBitRun(farulong *bitmap, 
		ulong bit_addr,         
		ulong nbits,            
		uint val)               
{
unsigned long bindex;   
unsigned long bitnumb;  

while(nbits--)
{
#ifdef LONG64
	bindex=bit_addr>>6;     
	bitnumb=bit_addr % 64;   
#else
	bindex=bit_addr>>5;     
	bitnumb=bit_addr % 32;  
#endif
	if(val)
		bitmap[bindex]|=(1L<<bitnumb);
	else
		bitmap[bindex]&=~(1L<<bitnumb);
	bit_addr++;
}
return;
}


static void FlipBitRun(farulong *bitmap,        
		ulong bit_addr,                 
		ulong nbits)                    
{
unsigned long bindex;   
unsigned long bitnumb;  

while(nbits--)
{
#ifdef LONG64
	bindex=bit_addr>>6;     
	bitnumb=bit_addr % 64;  
#else
	bindex=bit_addr>>5;     
	bitnumb=bit_addr % 32;  
#endif
	bitmap[bindex]^=(1L<<bitnumb);
	bit_addr++;
}

return;
}




void DoEmFloat(void)
{
EmFloatStruct *locemfloatstruct;        
InternalFPF *abase;             
InternalFPF *bbase;             
InternalFPF *cbase;             
ulong accumtime;                
double iterations;              
ulong tickcount;                
char *errorcontext;             
int systemerror;                
ulong loops;                    


locemfloatstruct=&global_emfloatstruct;


errorcontext="CPU:Floating Emulation";



#ifdef DEBUG
#endif

abase=(InternalFPF *)AllocateMemory(locemfloatstruct->arraysize*sizeof(InternalFPF),
		&systemerror);
if(systemerror)
{       ReportError(errorcontext,systemerror);
	ErrorExit();
}

bbase=(InternalFPF *)AllocateMemory(locemfloatstruct->arraysize*sizeof(InternalFPF),
		&systemerror);
if(systemerror)
{       ReportError(errorcontext,systemerror);
	FreeMemory((farvoid *)abase,&systemerror);
	ErrorExit();
}

cbase=(InternalFPF *)AllocateMemory(locemfloatstruct->arraysize*sizeof(InternalFPF),
		&systemerror);
if(systemerror)
{       ReportError(errorcontext,systemerror);
	FreeMemory((farvoid *)abase,&systemerror);
	FreeMemory((farvoid *)bbase,&systemerror);
	ErrorExit();
}


SetupCPUEmFloatArrays(abase,bbase,cbase,locemfloatstruct->arraysize);


if(locemfloatstruct->adjust==0)
{
	locemfloatstruct->loops=0;

	
	for(loops=1;loops<CPUEMFLOATLOOPMAX;loops+=loops)
	{       tickcount=DoEmFloatIteration(abase,bbase,cbase,
			locemfloatstruct->arraysize,
			loops);
		if(tickcount>global_min_ticks)
		{       locemfloatstruct->loops=loops;
			break;
		}
	}
}


if(locemfloatstruct->loops==0)
{       printf("CPU:EMFPU -- CMPUEMFLOATLOOPMAX limit hit\n");
	FreeMemory((farvoid *)abase,&systemerror);
	FreeMemory((farvoid *)bbase,&systemerror);
	FreeMemory((farvoid *)cbase,&systemerror);
	ErrorExit();
}


accumtime=0L;
iterations=(double)0.0;
do {
	accumtime+=DoEmFloatIteration(abase,bbase,cbase,
			locemfloatstruct->arraysize,
			locemfloatstruct->loops);
	iterations+=(double)1.0;
} while(TicksToSecs(accumtime)<locemfloatstruct->request_secs);



FreeMemory((farvoid *)abase,&systemerror);
FreeMemory((farvoid *)bbase,&systemerror);
FreeMemory((farvoid *)cbase,&systemerror);

locemfloatstruct->emflops=(iterations*(double)locemfloatstruct->loops)/
		(double)TicksToFracSecs(accumtime);
if(locemfloatstruct->adjust==0)
	locemfloatstruct->adjust=1;

#ifdef DEBUG
printf("----------------------------------------------------------------------------\n");
#endif
return;
}




void DoFourier(void)
{
FourierStruct *locfourierstruct;        
fardouble *abase;               
fardouble *bbase;               
unsigned long accumtime;        
double iterations;              
char *errorcontext;             
int systemerror;                


locfourierstruct=&global_fourierstruct;


errorcontext="FPU:Transcendental";


if(locfourierstruct->adjust==0)
{
	locfourierstruct->arraysize=100L;       
	while(1)
	{

		abase=(fardouble *)AllocateMemory(locfourierstruct->arraysize*sizeof(double),
				&systemerror);
		if(systemerror)
		{       ReportError(errorcontext,systemerror);
			ErrorExit();
		}

		bbase=(fardouble *)AllocateMemory(locfourierstruct->arraysize*sizeof(double),
				&systemerror);
		if(systemerror)
		{       ReportError(errorcontext,systemerror);
			FreeMemory((void *)abase,&systemerror);
			ErrorExit();
		}
		
		if(DoFPUTransIteration(abase,bbase,
			locfourierstruct->arraysize)>global_min_ticks)
			break;          

		
		FreeMemory((farvoid *)abase,&systemerror);
		FreeMemory((farvoid *)bbase,&systemerror);
		locfourierstruct->arraysize+=50L;
	}
}
else
{       
	abase=(fardouble *)AllocateMemory(locfourierstruct->arraysize*sizeof(double),
			&systemerror);
	if(systemerror)
	{       ReportError(errorcontext,systemerror);
		ErrorExit();
	}

	bbase=(fardouble *)AllocateMemory(locfourierstruct->arraysize*sizeof(double),
			&systemerror);
	if(systemerror)
	{       ReportError(errorcontext,systemerror);
		FreeMemory((void *)abase,&systemerror);
		ErrorExit();
	}
}

accumtime=0L;
iterations=(double)0.0;
do {
	accumtime+=DoFPUTransIteration(abase,bbase,locfourierstruct->arraysize);
	iterations+=(double)locfourierstruct->arraysize*(double)2.0-(double)1.0;
} while(TicksToSecs(accumtime)<locfourierstruct->request_secs);



FreeMemory((farvoid *)abase,&systemerror);
FreeMemory((farvoid *)bbase,&systemerror);

locfourierstruct->fflops=iterations/(double)TicksToFracSecs(accumtime);

if(locfourierstruct->adjust==0)
	locfourierstruct->adjust=1;

return;
}


static ulong DoFPUTransIteration(fardouble *abase,      
			fardouble *bbase,               
			ulong arraysize)                
{
double omega;           
unsigned long i;        
unsigned long elapsed;  


elapsed=StartStopwatch();



*abase=TrapezoidIntegrate((double)0.0,
			(double)2.0,
			200,
			(double)0.0,    
			0 )/(double)2.0;


omega=(double)3.1415926535897932;

for(i=1;i<arraysize;i++)
{

	
	*(abase+i)=TrapezoidIntegrate((double)0.0,
			(double)2.0,
			200,
			omega * (double)i,
			1);

	
	*(bbase+i)=TrapezoidIntegrate((double)0.0,
			(double)2.0,
			200,
			omega * (double)i,
			2);

}
#ifdef DEBUG
{
  int i;
  printf("\nA[i]=\n");
  for (i=0;i<arraysize;i++) printf("%7.3g ",abase[i]);
  printf("\nB[i]=\n(undefined) ");
  for (i=1;i<arraysize;i++) printf("%7.3g ",bbase[i]);
}
#endif

return(StopStopwatch(elapsed));
}


static double TrapezoidIntegrate( double x0,            
			double x1,              
			int nsteps,             
			double omegan,          
			int select)
{
double x;               
double dx;              
double rvalue;          



x=x0;


dx=(x1 - x0) / (double)nsteps;


rvalue=thefunction(x0,omegan,select)/(double)2.0;


if(nsteps!=1)
{       --nsteps;               
	while(--nsteps )
	{
		x+=dx;
		rvalue+=thefunction(x,omegan,select);
	}
}

rvalue=(rvalue+thefunction(x1,omegan,select)/(double)2.0)*dx;

return(rvalue);
}


static double thefunction(double x,             
		double omegan,          
		int select)             
{


switch(select)
{
	case 0: return(pow(x+(double)1.0,x));

	case 1: return(pow(x+(double)1.0,x) * cos(omegan * x));

	case 2: return(pow(x+(double)1.0,x) * sin(omegan * x));
}


return(0.0);
}




void DoAssign(void)
{
AssignStruct *locassignstruct;  
farlong *arraybase;
char *errorcontext;
int systemerror;
ulong accumtime;
double iterations;


locassignstruct=&global_assignstruct;


errorcontext="CPU:Assignment";


if(locassignstruct->adjust==0)
{
	
	locassignstruct->numarrays=1;
	while(1)
	{
		
		arraybase=(farlong *) AllocateMemory(sizeof(long)*
			ASSIGNROWS*ASSIGNCOLS*locassignstruct->numarrays,
			 &systemerror);
		if(systemerror)
		{       ReportError(errorcontext,systemerror);
			FreeMemory((farvoid *)arraybase,
			  &systemerror);
			ErrorExit();
		}

		
		if(DoAssignIteration(arraybase,
			locassignstruct->numarrays)>global_min_ticks)
			break;          

		FreeMemory((farvoid *)arraybase, &systemerror);
		locassignstruct->numarrays++;
	}
}
else
{       
	arraybase=(farlong *)AllocateMemory(sizeof(long)*
		ASSIGNROWS*ASSIGNCOLS*locassignstruct->numarrays,
		 &systemerror);
	if(systemerror)
	{       ReportError(errorcontext,systemerror);
		FreeMemory((farvoid *)arraybase,
		  &systemerror);
		ErrorExit();
	}
}


accumtime=0L;
iterations=(double)0.0;

do {
	accumtime+=DoAssignIteration(arraybase,
		locassignstruct->numarrays);
	iterations+=(double)1.0;
} while(TicksToSecs(accumtime)<locassignstruct->request_secs);


FreeMemory((farvoid *)arraybase,&systemerror);

locassignstruct->iterspersec=iterations *
	(double)locassignstruct->numarrays / TicksToFracSecs(accumtime);

if(locassignstruct->adjust==0)
	locassignstruct->adjust=1;

return;

}


static ulong DoAssignIteration(farlong *arraybase,
	ulong numarrays)
{
longptr abase;                  
ulong elapsed;          
ulong i;


abase.ptrs.p=arraybase;


LoadAssignArrayWithRand(arraybase,numarrays);


elapsed=StartStopwatch();


for(i=0;i<numarrays;i++)
{       
        
	Assignment(*abase.ptrs.ap);
	abase.ptrs.p+=ASSIGNROWS*ASSIGNCOLS;
}


return(StopStopwatch(elapsed));
}


static void LoadAssignArrayWithRand(farlong *arraybase,
	ulong numarrays)
{
longptr abase,abase1;   
ulong i;


abase.ptrs.p=arraybase;
abase1.ptrs.p=arraybase;


LoadAssign(*(abase.ptrs.ap));
if(numarrays>1)
	for(i=1;i<numarrays;i++)
	  {     
	        
	        abase1.ptrs.p+=ASSIGNROWS*ASSIGNCOLS;
		CopyToAssign(*(abase.ptrs.ap),*(abase1.ptrs.ap));
	}

return;
}


static void LoadAssign(farlong arraybase[][ASSIGNCOLS])
{
ushort i,j;



randnum((n_int32)13);

for(i=0;i<ASSIGNROWS;i++)
  for(j=0;j<ASSIGNROWS;j++){
    
    arraybase[i][j]=abs_randwc((n_int32)5000000);
  }

return;
}


static void CopyToAssign(farlong arrayfrom[ASSIGNROWS][ASSIGNCOLS],
		farlong arrayto[ASSIGNROWS][ASSIGNCOLS])
{
ushort i,j;

for(i=0;i<ASSIGNROWS;i++)
	for(j=0;j<ASSIGNCOLS;j++)
		arrayto[i][j]=arrayfrom[i][j];

return;
}


static void Assignment(farlong arraybase[][ASSIGNCOLS])
{
short assignedtableau[ASSIGNROWS][ASSIGNCOLS];


calc_minimum_costs(arraybase);


while(first_assignments(arraybase,assignedtableau)!=ASSIGNROWS)
{         second_assignments(arraybase,assignedtableau);
}

#ifdef DEBUG
{
	int i,j;
	printf("\nColumn choices for each row\n");
	for(i=0;i<ASSIGNROWS;i++)
	{
	        printf("R%03d: ",i);
		for(j=0;j<ASSIGNCOLS;j++)
			if(assignedtableau[i][j]==1)
				printf("%03d ",j);
	}
}
#endif

return;
}


static void calc_minimum_costs(long tableau[][ASSIGNCOLS])
{
ushort i,j;              
long currentmin;        

for(i=0;i<ASSIGNROWS;i++)
{
	currentmin=MAXPOSLONG;  
	for(j=0;j<ASSIGNCOLS;j++)
		if(tableau[i][j]<currentmin)
			currentmin=tableau[i][j];

	for(j=0;j<ASSIGNCOLS;j++)
		tableau[i][j]-=currentmin;
}


for(j=0;j<ASSIGNCOLS;j++)
{
	currentmin=MAXPOSLONG;  
	for(i=0;i<ASSIGNROWS;i++)
		if(tableau[i][j]<currentmin)
			currentmin=tableau[i][j];

	
	if(currentmin!=0)
		for(i=0;i<ASSIGNROWS;i++)
			tableau[i][j]-=currentmin;
}

return;
}


static int first_assignments(long tableau[][ASSIGNCOLS],
		short assignedtableau[][ASSIGNCOLS])
{
ushort i,j,k;                   
ushort numassigns;              
ushort totnumassigns;           
ushort numzeros;                
int selected=0;                 


for(i=0;i<ASSIGNROWS;i++)
	for(j=0;j<ASSIGNCOLS;j++)
		assignedtableau[i][j]=0;

totnumassigns=0;
do {
	numassigns=0;
	
	for(i=0;i<ASSIGNROWS;i++)
	{       numzeros=0;
		for(j=0;j<ASSIGNCOLS;j++)
			if(tableau[i][j]==0L)
				if(assignedtableau[i][j]==0)
				{       numzeros++;
					selected=j;
				}
		if(numzeros==1)
		{       numassigns++;
			totnumassigns++;
			assignedtableau[i][selected]=1;
			for(k=0;k<ASSIGNROWS;k++)
				if((k!=i) &&
				   (tableau[k][selected]==0))
					assignedtableau[k][selected]=2;
		}
	}
	
	for(j=0;j<ASSIGNCOLS;j++)
	{       numzeros=0;
		for(i=0;i<ASSIGNROWS;i++)
			if(tableau[i][j]==0L)
				if(assignedtableau[i][j]==0)
				{       numzeros++;
					selected=i;
				}
		if(numzeros==1)
		{       numassigns++;
			totnumassigns++;
			assignedtableau[selected][j]=1;
			for(k=0;k<ASSIGNCOLS;k++)
				if((k!=j) &&
				   (tableau[selected][k]==0))
					assignedtableau[selected][k]=2;
		}
	}
	
} while(numassigns!=0);


if(totnumassigns==ASSIGNROWS) return(totnumassigns);


for(i=0;i<ASSIGNROWS;i++)
{       selected=-1;
	for(j=0;j<ASSIGNCOLS;j++)
		if((tableau[i][j]==0L) &&
		   (assignedtableau[i][j]==0))
		{       selected=j;
			break;
		}
	if(selected!=-1)
	{       assignedtableau[i][selected]=1;
		totnumassigns++;
		for(k=0;k<ASSIGNCOLS;k++)
			if((k!=selected) &&
			   (tableau[i][k]==0L))
				assignedtableau[i][k]=2;
		for(k=0;k<ASSIGNROWS;k++)
			if((k!=i) &&
			   (tableau[k][selected]==0L))
				assignedtableau[k][selected]=2;
	}
}

return(totnumassigns);
}


static void second_assignments(long tableau[][ASSIGNCOLS],
		short assignedtableau[][ASSIGNCOLS])
{
int i,j;                                
short linesrow[ASSIGNROWS];
short linescol[ASSIGNCOLS];
long smallest;                          
ushort numassigns;                      
ushort newrows;                         

for(i=0;i<ASSIGNROWS;i++)
	linesrow[i]=0;
for(i=0;i<ASSIGNCOLS;i++)
	linescol[i]=0;


for(i=0;i<ASSIGNROWS;i++)
{       numassigns=0;
	for(j=0;j<ASSIGNCOLS;j++)
		if(assignedtableau[i][j]==1)
		{       numassigns++;
			break;
		}
	if(numassigns==0) linesrow[i]=1;
}

do {

	newrows=0;
	
	for(i=0;i<ASSIGNROWS;i++)
	{       if(linesrow[i]==1)
			for(j=0;j<ASSIGNCOLS;j++)
				if(tableau[i][j]==0)
					linescol[j]=1;
	}

	
	for(j=0;j<ASSIGNCOLS;j++)
		if(linescol[j]==1)
			for(i=0;i<ASSIGNROWS;i++)
				if((assignedtableau[i][j]==1) &&
					(linesrow[i]!=1))
				{
					linesrow[i]=1;
					newrows++;
				}
} while(newrows!=0);


smallest=MAXPOSLONG;
for(i=0;i<ASSIGNROWS;i++)
	if(linesrow[i]!=0)
		for(j=0;j<ASSIGNCOLS;j++)
			if(linescol[j]!=1)
				if(tableau[i][j]<smallest)
					smallest=tableau[i][j];


for(i=0;i<ASSIGNROWS;i++)
	if(linesrow[i]!=0)
		for(j=0;j<ASSIGNCOLS;j++)
			if(linescol[j]!=1)
				tableau[i][j]-=smallest;


for(i=0;i<ASSIGNROWS;i++)
	if(linesrow[i]==0)
		for(j=0;j<ASSIGNCOLS;j++)
			if(linescol[j]==1)
				tableau[i][j]+=smallest;

return;
}




void DoIDEA(void)
{
IDEAStruct *locideastruct;      
int i;
IDEAkey Z,DK;
u16 userkey[8];
ulong accumtime;
double iterations;
char *errorcontext;
int systemerror;
faruchar *plain1;               
faruchar *crypt1;               
faruchar *plain2;               


locideastruct=&global_ideastruct;


errorcontext="CPU:IDEA";



randnum((n_int32)3);


for (i=0;i<8;i++)
        
	userkey[i]=(u16)(abs_randwc((n_int32)60000) & 0xFFFF);
for(i=0;i<KEYLEN;i++)
	Z[i]=0;


en_key_idea(userkey,Z);
de_key_idea(Z,DK);


plain1=(faruchar *)AllocateMemory(locideastruct->arraysize,&systemerror);
if(systemerror)
{
	ReportError(errorcontext,systemerror);
	ErrorExit();
}

crypt1=(faruchar *)AllocateMemory(locideastruct->arraysize,&systemerror);
if(systemerror)
{
	ReportError(errorcontext,systemerror);
	FreeMemory((farvoid *)plain1,&systemerror);
	ErrorExit();
}

plain2=(faruchar *)AllocateMemory(locideastruct->arraysize,&systemerror);
if(systemerror)
{
	ReportError(errorcontext,systemerror);
	FreeMemory((farvoid *)plain1,&systemerror);
	FreeMemory((farvoid *)crypt1,&systemerror);
	ErrorExit();
}

for(i=0;i<locideastruct->arraysize;i++)
	plain1[i]=(uchar)(abs_randwc(255) & 0xFF);


if(locideastruct->adjust==0)
{
	
	for(locideastruct->loops=100L;
	  locideastruct->loops<MAXIDEALOOPS;
	  locideastruct->loops+=10L)
		if(DoIDEAIteration(plain1,crypt1,plain2,
		  locideastruct->arraysize,
		  locideastruct->loops,
		  Z,DK)>global_min_ticks) break;
}


accumtime=0L;
iterations=(double)0.0;

do {
	accumtime+=DoIDEAIteration(plain1,crypt1,plain2,
		locideastruct->arraysize,
		locideastruct->loops,Z,DK);
	iterations+=(double)locideastruct->loops;
} while(TicksToSecs(accumtime)<locideastruct->request_secs);


FreeMemory((farvoid *)plain1,&systemerror);
FreeMemory((farvoid *)crypt1,&systemerror);
FreeMemory((farvoid *)plain2,&systemerror);
locideastruct->iterspersec=iterations / TicksToFracSecs(accumtime);

if(locideastruct->adjust==0)
	locideastruct->adjust=1;

return;

}


static ulong DoIDEAIteration(faruchar *plain1,
			faruchar *crypt1,
			faruchar *plain2,
			ulong arraysize,
			ulong nloops,
			IDEAkey Z,
			IDEAkey DK)
{
register ulong i;
register ulong j;
ulong elapsed;
#ifdef DEBUG
int status=0;
#endif


elapsed=StartStopwatch();


for(i=0;i<nloops;i++)
{
	for(j=0;j<arraysize;j+=(sizeof(u16)*4))
		cipher_idea((u16 *)(plain1+j),(u16 *)(crypt1+j),Z);       

	for(j=0;j<arraysize;j+=(sizeof(u16)*4))
		cipher_idea((u16 *)(crypt1+j),(u16 *)(plain2+j),DK);      
}

#ifdef DEBUG
for(j=0;j<arraysize;j++)
	if(*(plain1+j)!=*(plain2+j)){
		printf("IDEA Error! \n");
                status=1;
                }
if (status==0) printf("IDEA: OK\n");
#endif


return(StopStopwatch(elapsed));
}


static u16 mul(register u16 a, register u16 b)
{
register u32 p;
if(a)
{       if(b)
	{       p=(u32)(a*b);
		b=low16(p);
		a=(u16)(p>>16);
		return(b-a+(b<a));
	}
	else
		return(1-a);
}
else
	return(1-b);
}


static u16 inv(u16 x)
{
u16 t0, t1;
u16 q, y;

if(x<=1)
	return(x);      
t1=0x10001 / x;
y=0x10001 % x;
if(y==1)
	return(low16(1-t1));
t0=1;
do {
	q=x/y;
	x=x%y;
	t0+=q*t1;
	if(x==1) return(t0);
	q=y/x;
	y=y%x;
	t1+=q*t0;
} while(y!=1);
return(low16(1-t1));
}


static void en_key_idea(u16 *userkey, u16 *Z)
{
int i,j;


for(j=0;j<8;j++)
	Z[j]=*userkey++;
for(i=0;j<KEYLEN;j++)
{       i++;
	Z[i+7]=(Z[i&7]<<9)| (Z[(i+1) & 7] >> 7);
	Z+=i&8;
	i&=7;
}
return;
}


static void de_key_idea(IDEAkey Z, IDEAkey DK)
{
IDEAkey TT;
int j;
u16 t1, t2, t3;
u16 *p;
p=(u16 *)(TT+KEYLEN);

t1=inv(*Z++);
t2=-*Z++;
t3=-*Z++;
*--p=inv(*Z++);
*--p=t3;
*--p=t2;
*--p=t1;

for(j=1;j<ROUNDS;j++)
{       t1=*Z++;
	*--p=*Z++;
	*--p=t1;
	t1=inv(*Z++);
	t2=-*Z++;
	t3=-*Z++;
	*--p=inv(*Z++);
	*--p=t2;
	*--p=t3;
	*--p=t1;
}
t1=*Z++;
*--p=*Z++;
*--p=t1;
t1=inv(*Z++);
t2=-*Z++;
t3=-*Z++;
*--p=inv(*Z++);
*--p=t3;
*--p=t2;
*--p=t1;

for(j=0,p=TT;j<KEYLEN;j++)
{       *DK++=*p;
	*p++=0;
}

return;
}





static void cipher_idea(u16 in[4],
		u16 out[4],
		register IDEAkey Z)
{
register u16 x1, x2, x3, x4, t1, t2;

int r=ROUNDS;

x1=*in++;
x2=*in++;
x3=*in++;
x4=*in;

do {
	MUL(x1,*Z++);
	x2+=*Z++;
	x3+=*Z++;
	MUL(x4,*Z++);

	t2=x1^x3;
	MUL(t2,*Z++);
	t1=t2+(x2^x4);
	MUL(t1,*Z++);
	t2=t1+t2;

	x1^=t1;
	x4^=t2;

	t2^=x2;
	x2=x3^t1;
	x3=t2;
} while(--r);
MUL(x1,*Z++);
*out++=x1;
*out++=x3+*Z++;
*out++=x2+*Z++;
MUL(x4,*Z);
*out=x4;
return;
}




void DoHuffman(void)
{
HuffStruct *lochuffstruct;      
char *errorcontext;
int systemerror;
ulong accumtime;
double iterations;
farchar *comparray;
farchar *decomparray;
farchar *plaintext;


lochuffstruct=&global_huffstruct;


errorcontext="CPU:Huffman";


plaintext=(farchar *)AllocateMemory(lochuffstruct->arraysize,&systemerror);
if(systemerror)
{       ReportError(errorcontext,systemerror);
	ErrorExit();
}
comparray=(farchar *)AllocateMemory(lochuffstruct->arraysize,&systemerror);
if(systemerror)
{       ReportError(errorcontext,systemerror);
	FreeMemory(plaintext,&systemerror);
	ErrorExit();
}
decomparray=(farchar *)AllocateMemory(lochuffstruct->arraysize,&systemerror);
if(systemerror)
{       ReportError(errorcontext,systemerror);
	FreeMemory(plaintext,&systemerror);
	FreeMemory(comparray,&systemerror);
	ErrorExit();
}

hufftree=(huff_node *)AllocateMemory(sizeof(huff_node) * 512,
	&systemerror);
if(systemerror)
{       ReportError(errorcontext,systemerror);
	FreeMemory(plaintext,&systemerror);
	FreeMemory(comparray,&systemerror);
	FreeMemory(decomparray,&systemerror);
	ErrorExit();
}



randnum((n_int32)13);
create_text_block(plaintext,lochuffstruct->arraysize-1,(ushort)500);
plaintext[lochuffstruct->arraysize-1L]='\0';
plaintextlen=lochuffstruct->arraysize;


if(lochuffstruct->adjust==0)
{
	
	for(lochuffstruct->loops=100L;
	  lochuffstruct->loops<MAXHUFFLOOPS;
	  lochuffstruct->loops+=10L)
		if(DoHuffIteration(plaintext,
			comparray,
			decomparray,
		  lochuffstruct->arraysize,
		  lochuffstruct->loops,
		  hufftree)>global_min_ticks) break;
}


accumtime=0L;
iterations=(double)0.0;

do {
	accumtime+=DoHuffIteration(plaintext,
		comparray,
		decomparray,
		lochuffstruct->arraysize,
		lochuffstruct->loops,
		hufftree);
	iterations+=(double)lochuffstruct->loops;
} while(TicksToSecs(accumtime)<lochuffstruct->request_secs);


FreeMemory((farvoid *)plaintext,&systemerror);
FreeMemory((farvoid *)comparray,&systemerror);
FreeMemory((farvoid *)decomparray,&systemerror);
FreeMemory((farvoid *)hufftree,&systemerror);
lochuffstruct->iterspersec=iterations / TicksToFracSecs(accumtime);

if(lochuffstruct->adjust==0)
	lochuffstruct->adjust=1;

}


static void create_text_line(farchar *dt,
			long nchars)
{
long charssofar;        
long tomove;            
char myword[40];        
farchar *wordptr;       

charssofar=0;

do {


wordptr=wordcatarray[abs_randwc((n_int32)WORDCATSIZE)];
MoveMemory((farvoid *)myword,
	(farvoid *)wordptr,
	(unsigned long)strlen(wordptr)+1);


tomove=strlen(myword)+1;
myword[tomove-1]=' ';


if((tomove+charssofar)>nchars)
	tomove=nchars-charssofar;

MoveMemory((farvoid *)dt,(farvoid *)myword,(unsigned long)tomove);
charssofar+=tomove;
dt+=tomove;


} while(charssofar<nchars);

return;
}


static void create_text_block(farchar *tb,
			ulong tblen,
			ushort maxlinlen)
{
ulong bytessofar;       
ulong linelen;          

bytessofar=0L;
do {


linelen=abs_randwc(maxlinlen-6)+6;
if((linelen+bytessofar)>tblen)
	linelen=tblen-bytessofar;

if(linelen>1)
{
	create_text_line(tb,linelen);
}
tb+=linelen-1;          
*tb++='\n';

bytessofar+=linelen;

} while(bytessofar<tblen);

}


static ulong DoHuffIteration(farchar *plaintext,
	farchar *comparray,
	farchar *decomparray,
	ulong arraysize,
	ulong nloops,
	huff_node *hufftree)
{
int i;                          
long j;                         
int root;                       
float lowfreq1, lowfreq2;       
int lowidx1, lowidx2;           
long bitoffset;                 
long textoffset;                
long maxbitoffset;              
long bitstringlen;              
int c;                          
char bitstring[30];             
ulong elapsed;                  
#ifdef DEBUG
int status=0;
#endif


elapsed=StartStopwatch();


while(nloops--)
{


for(i=0;i<256;i++)
{
	hufftree[i].freq=(float)0.0;
	hufftree[i].c=(unsigned char)i;
}

for(j=0;j<arraysize;j++)
	hufftree[(int)plaintext[j]].freq+=(float)1.0;

for(i=0;i<256;i++)
	if(hufftree[i].freq != (float)0.0)
		hufftree[i].freq/=(float)arraysize;


bzero((char *)&(hufftree[256]),sizeof(huff_node)*256);

for(i=0;i<512;i++)
{       if(hufftree[i].freq==(float)0.0)
		hufftree[i].parent=EXCLUDED;
	else
		hufftree[i].parent=hufftree[i].left=hufftree[i].right=-1;
}


root=255;                       
while(1)
{
	lowfreq1=(float)2.0; lowfreq2=(float)2.0;
	lowidx1=-1; lowidx2=-1;
	
	for(i=0;i<=root;i++)
		if(hufftree[i].parent<0)
			if(hufftree[i].freq<lowfreq1)
			{       lowfreq1=hufftree[i].freq;
				lowidx1=i;
			}

	
	if(lowidx1==-1) break;

	
	for(i=0;i<=root;i++)
		if((hufftree[i].parent<0) && (i!=lowidx1))
			if(hufftree[i].freq<lowfreq2)
			{       lowfreq2=hufftree[i].freq;
				lowidx2=i;
			}

	
	if(lowidx2==-1) break;

	
	root++;                 
	hufftree[lowidx1].parent=root;
	hufftree[lowidx2].parent=root;
	hufftree[root].freq=lowfreq1+lowfreq2;
	hufftree[root].left=lowidx1;
	hufftree[root].right=lowidx2;
	hufftree[root].parent=-2;       
}


bitoffset=0L;                           
for(i=0;i<arraysize;i++)
{
	c=(int)plaintext[i];                 
	
	bitstringlen=0;
	while(hufftree[c].parent!=-2)
	{       if(hufftree[hufftree[c].parent].left==c)
			bitstring[bitstringlen]='0';
		else
			bitstring[bitstringlen]='1';
		c=hufftree[c].parent;
		bitstringlen++;
	}

	
	while(bitstringlen--)
	{       SetCompBit((u8 *)comparray,(u32)bitoffset,bitstring[bitstringlen]);
		bitoffset++;
	}
}


maxbitoffset=bitoffset;
bitoffset=0;
textoffset=0;
do {
	i=root;
	while(hufftree[i].left!=-1)
	{       if(GetCompBit((u8 *)comparray,(u32)bitoffset)==0)
			i=hufftree[i].left;
		else
			i=hufftree[i].right;
		bitoffset++;
	}
	decomparray[textoffset]=hufftree[i].c;

#ifdef DEBUG
	if(hufftree[i].c != plaintext[textoffset])
	{
		
		printf("Error at textoffset %ld\n",textoffset);
		status=1;
	}
#endif
	textoffset++;
} while(bitoffset<maxbitoffset);

}       


#ifdef DEBUG
  if (status==0) printf("Huffman: OK\n");
#endif
return(StopStopwatch(elapsed));
}


static void SetCompBit(u8 *comparray,
		u32 bitoffset,
		char bitchar)
{
u32 byteoffset;
int bitnumb;


byteoffset=bitoffset>>3;
bitnumb=bitoffset % 8;


if(bitchar=='1')
	comparray[byteoffset]|=(1<<bitnumb);
else
	comparray[byteoffset]&=~(1<<bitnumb);

return;
}


static int GetCompBit(u8 *comparray,
		u32 bitoffset)
{
u32 byteoffset;
int bitnumb;


byteoffset=bitoffset>>3;
bitnumb=bitoffset % 8;


return((1<<bitnumb) & comparray[byteoffset] );
}




void DoNNET(void)
{
NNetStruct *locnnetstruct;      
char *errorcontext;
ulong accumtime;
double iterations;


locnnetstruct=&global_nnetstruct;


errorcontext="CPU:NNET";



randnum((n_int32)3);


if(read_data_file()!=0)
   ErrorExit();



if(locnnetstruct->adjust==0)
{
	
	for(locnnetstruct->loops=1L;
	  locnnetstruct->loops<MAXNNETLOOPS;
	  locnnetstruct->loops++)
	  {     
		randnum((n_int32)3);
		if(DoNNetIteration(locnnetstruct->loops)
			>global_min_ticks) break;
	  }
}


accumtime=0L;
iterations=(double)0.0;

do {
	    
	randnum((n_int32)3);    
	accumtime+=DoNNetIteration(locnnetstruct->loops);
	iterations+=(double)locnnetstruct->loops;
} while(TicksToSecs(accumtime)<locnnetstruct->request_secs);


locnnetstruct->iterspersec=iterations / TicksToFracSecs(accumtime);

if(locnnetstruct->adjust==0)
	locnnetstruct->adjust=1;


return;
}


static ulong DoNNetIteration(ulong nloops)
{
ulong elapsed;          
int patt;


elapsed=StartStopwatch();
while(nloops--)
{
	randomize_wts();
	zero_changes();
	iteration_count=1;
	learned = F;
	numpasses = 0;
	while (learned == F)
	{
		for (patt=0; patt<numpats; patt++)
		{
			worst_error = 0.0;      
			move_wt_changes();      
			do_forward_pass(patt);
			do_back_pass(patt);
			iteration_count++;
		}
		numpasses ++;
		learned = check_out_error();
	}
#ifdef DEBUG
printf("Learned in %d passes\n",numpasses);
#endif
}
return(StopStopwatch(elapsed));
}


static void  do_mid_forward(int patt)
{
double  sum;
int     neurode, i;

for (neurode=0;neurode<MID_SIZE; neurode++)
{
	sum = 0.0;
	for (i=0; i<IN_SIZE; i++)
	{       
		sum += mid_wts[neurode][i]*in_pats[patt][i];
	}
	
	sum = 1.0/(1.0+exp(-sum));
	mid_out[neurode] = sum;
}
return;
}


static void  do_out_forward()
{
double sum;
int neurode, i;

for (neurode=0; neurode<OUT_SIZE; neurode++)
{
	sum = 0.0;
	for (i=0; i<MID_SIZE; i++)
	{       
		sum += out_wts[neurode][i]*mid_out[i];
	}
	
	sum = 1.0/(1.0+exp(-sum));
	out_out[neurode] = sum;
}
return;
}





static void  do_forward_pass(int patt)
{
do_mid_forward(patt);   
do_out_forward();       

return;
}


static void do_out_error(int patt)
{
int neurode;
double error,tot_error, sum;

tot_error = 0.0;
sum = 0.0;
for (neurode=0; neurode<OUT_SIZE; neurode++)
{
	out_error[neurode] = out_pats[patt][neurode] - out_out[neurode];
	
	error = out_error[neurode];
	if (error <0.0)
	{
		sum += -error;
		if (-error > tot_error)
			tot_error = -error; 
	}
	else
	{
		sum += error;
		if (error > tot_error)
			tot_error = error; 
	}
}
avg_out_error[patt] = sum/OUT_SIZE;
tot_out_error[patt] = tot_error;
return;
}


static void  worst_pass_error()
{
double error,sum;

int i;

error = 0.0;
sum = 0.0;
for (i=0; i<numpats; i++)
{
	if (tot_out_error[i] > error) error = tot_out_error[i];
	sum += avg_out_error[i];
}
worst_error = error;
average_error = sum/numpats;
return;
}


static void do_mid_error()
{
double sum;
int neurode, i;

for (neurode=0; neurode<MID_SIZE; neurode++)
{
	sum = 0.0;
	for (i=0; i<OUT_SIZE; i++)
		sum += out_wts[i][neurode]*out_error[i];

	
	mid_error[neurode] = mid_out[neurode]*(1-mid_out[neurode])*sum;
}
return;
}


static void adjust_out_wts()
{
int weight, neurode;
double learn,delta,alph;

learn = BETA;
alph  = ALPHA;
for (neurode=0; neurode<OUT_SIZE; neurode++)
{
	for (weight=0; weight<MID_SIZE; weight++)
	{
		
		delta = learn * out_error[neurode] * mid_out[weight];

		
		delta += alph * out_wt_change[neurode][weight];
		out_wts[neurode][weight] += delta;

		
		out_wt_cum_change[neurode][weight] += delta;
	}
}
return;
}


static void adjust_mid_wts(int patt)
{
int weight, neurode;
double learn,alph,delta;

learn = BETA;
alph  = ALPHA;
for (neurode=0; neurode<MID_SIZE; neurode++)
{
	for (weight=0; weight<IN_SIZE; weight++)
	{
		
		delta = learn * mid_error[neurode] * in_pats[patt][weight];

		
		delta += alph * mid_wt_change[neurode][weight];
		mid_wts[neurode][weight] += delta;

		
		mid_wt_cum_change[neurode][weight] += delta;
	}
}
return;
}


void  do_back_pass(int patt)
{

do_out_error(patt);
do_mid_error();
adjust_out_wts();
adjust_mid_wts(patt);

return;
}



static void move_wt_changes()
{
int i,j;

for (i = 0; i<MID_SIZE; i++)
	for (j = 0; j<IN_SIZE; j++)
	{
		mid_wt_change[i][j] = mid_wt_cum_change[i][j];
		
		mid_wt_cum_change[i][j] = 0.0;
	}

for (i = 0; i<OUT_SIZE; i++)
	for (j=0; j<MID_SIZE; j++)
	{
		out_wt_change[i][j] = out_wt_cum_change[i][j];
		out_wt_cum_change[i][j] = 0.0;
	}

return;
}


static int check_out_error()
{
int result,i,error;

result  = T;
error   = F;
worst_pass_error();     


for (i=0; i<numpats; i++)
{


	if (worst_error >= STOP) result = F;
	if (tot_out_error[i] >= 16.0) error = T;
}

if (error == T) result = ERR;


#ifdef DEBUG


#endif

return(result);
}



static void zero_changes()
{
int i,j;

for (i = 0; i<MID_SIZE; i++)
{
	for (j=0; j<IN_SIZE; j++)
	{
		mid_wt_change[i][j] = 0.0;
		mid_wt_cum_change[i][j] = 0.0;
	}
}

for (i = 0; i< OUT_SIZE; i++)
{
	for (j=0; j<MID_SIZE; j++)
	{
		out_wt_change[i][j] = 0.0;
		out_wt_cum_change[i][j] = 0.0;
	}
}
return;
}



static void randomize_wts()
{
int neurode,i;
double value;



for (neurode = 0; neurode<MID_SIZE; neurode++)
{
	for(i=0; i<IN_SIZE; i++)
	{
	        
		value=(double)abs_randwc((n_int32)100000);
		value=value/(double)100000.0 - (double) 0.5;
		mid_wts[neurode][i] = value/2;
	}
}
for (neurode=0; neurode<OUT_SIZE; neurode++)
{
	for(i=0; i<MID_SIZE; i++)
	{
	        
		value=(double)abs_randwc((n_int32)100000);
		value=value/(double)10000.0 - (double) 0.5;
		out_wts[neurode][i] = value/2;
	}
}

return;
}



static int read_data_file()
{
FILE *infile;

int xinsize,yinsize,youtsize;
int patt, element, i, row;
int vals_read;
int val1,val2,val3,val4,val5,val6,val7,val8;



infile = fopen(inpath, "r");
if (infile == NULL)
{
	printf("\n CPU:NNET--error in opening file!");
	return -1 ;
}
vals_read =fscanf(infile,"%d  %d  %d",&xinsize,&yinsize,&youtsize);
if (vals_read != 3)
{
	printf("\n CPU:NNET -- Should read 3 items in line one; did read %d",vals_read);
	return -1;
}
vals_read=fscanf(infile,"%d",&numpats);
if (vals_read !=1)
{
	printf("\n CPU:NNET -- Should read 1 item in line 2; did read %d",vals_read);
	return -1;
}
if (numpats > MAXPATS)
	numpats = MAXPATS;

for (patt=0; patt<numpats; patt++)
{
	element = 0;
	for (row = 0; row<yinsize; row++)
	{
		vals_read = fscanf(infile,"%d  %d  %d  %d  %d",
			&val1, &val2, &val3, &val4, &val5);
		if (vals_read != 5)
		{
			printf ("\n CPU:NNET -- failure in reading input!");
			return -1;
		}
		element=row*xinsize;

		in_pats[patt][element] = (double) val1; element++;
		in_pats[patt][element] = (double) val2; element++;
		in_pats[patt][element] = (double) val3; element++;
		in_pats[patt][element] = (double) val4; element++;
		in_pats[patt][element] = (double) val5; element++;
	}
	for (i=0;i<IN_SIZE; i++)
	{
		if (in_pats[patt][i] >= 0.9)
			in_pats[patt][i] = 0.9;
		if (in_pats[patt][i] <= 0.1)
			in_pats[patt][i] = 0.1;
	}
	element = 0;
	vals_read = fscanf(infile,"%d  %d  %d  %d  %d  %d  %d  %d",
		&val1, &val2, &val3, &val4, &val5, &val6, &val7, &val8);

	out_pats[patt][element] = (double) val1; element++;
	out_pats[patt][element] = (double) val2; element++;
	out_pats[patt][element] = (double) val3; element++;
	out_pats[patt][element] = (double) val4; element++;
	out_pats[patt][element] = (double) val5; element++;
	out_pats[patt][element] = (double) val6; element++;
	out_pats[patt][element] = (double) val7; element++;
	out_pats[patt][element] = (double) val8; element++;
}



fclose(infile);
return(0);
}












void DoLU(void)
{
LUStruct *loclustruct;  
char *errorcontext;
int systemerror;
fardouble *a;
fardouble *b;
fardouble *abase;
fardouble *bbase;
LUdblptr ptra;
int n;
int i;
ulong accumtime;
double iterations;


loclustruct=&global_lustruct;


errorcontext="FPU:LU";


a=(fardouble *)AllocateMemory(sizeof(double) * LUARRAYCOLS * LUARRAYROWS,
		&systemerror);
b=(fardouble *)AllocateMemory(sizeof(double) * LUARRAYROWS,
		&systemerror);
n=LUARRAYROWS;


LUtempvv=(fardouble *)AllocateMemory(sizeof(double)*LUARRAYROWS,
	&systemerror);


ptra.ptrs.p=a;                  
build_problem(*ptra.ptrs.ap,n,b);


if(loclustruct->adjust==0)
{
	loclustruct->numarrays=0;
	for(i=1;i<=MAXLUARRAYS;i++)
	{
		abase=(fardouble *)AllocateMemory(sizeof(double) *
			LUARRAYCOLS*LUARRAYROWS*(i+1),&systemerror);
		if(systemerror)
		{       ReportError(errorcontext,systemerror);
			LUFreeMem(a,b,(fardouble *)NULL,(fardouble *)NULL);
			ErrorExit();
		}
		bbase=(fardouble *)AllocateMemory(sizeof(double) *
			LUARRAYROWS*(i+1),&systemerror);
		if(systemerror)
		{       ReportError(errorcontext,systemerror);
			LUFreeMem(a,b,abase,(fardouble *)NULL);
			ErrorExit();
		}
		if(DoLUIteration(a,b,abase,bbase,i)>global_min_ticks)
		{       loclustruct->numarrays=i;
			break;
		}
		
		FreeMemory((farvoid *)abase,&systemerror);
		FreeMemory((farvoid *)bbase,&systemerror);
	}
	
	if(loclustruct->numarrays==0)
	{       printf("FPU:LU -- Array limit reached\n");
		LUFreeMem(a,b,abase,bbase);
		ErrorExit();
	}
}
else
{       
	abase=(fardouble *)AllocateMemory(sizeof(double) *
		LUARRAYCOLS*LUARRAYROWS*loclustruct->numarrays,
		&systemerror);
	if(systemerror)
	{       ReportError(errorcontext,systemerror);
		LUFreeMem(a,b,(fardouble *)NULL,(fardouble *)NULL);
		ErrorExit();
	}
	bbase=(fardouble *)AllocateMemory(sizeof(double) *
		LUARRAYROWS*loclustruct->numarrays,&systemerror);
	if(systemerror)
	{
		ReportError(errorcontext,systemerror);
		LUFreeMem(a,b,abase,(fardouble *)NULL);
		ErrorExit();
	}
}

accumtime=0L;
iterations=(double)0.0;

do {
	accumtime+=DoLUIteration(a,b,abase,bbase,
		loclustruct->numarrays);
	iterations+=(double)loclustruct->numarrays;
} while(TicksToSecs(accumtime)<loclustruct->request_secs);


loclustruct->iterspersec=iterations / TicksToFracSecs(accumtime);

if(loclustruct->adjust==0)
	loclustruct->adjust=1;

LUFreeMem(a,b,abase,bbase);
return;
}


static void LUFreeMem(fardouble *a, fardouble *b,
			fardouble *abase,fardouble *bbase)
{
int systemerror;

FreeMemory((farvoid *)a,&systemerror);
FreeMemory((farvoid *)b,&systemerror);
FreeMemory((farvoid *)LUtempvv,&systemerror);

if(abase!=(fardouble *)NULL) FreeMemory((farvoid *)abase,&systemerror);
if(bbase!=(fardouble *)NULL) FreeMemory((farvoid *)bbase,&systemerror);
return;
}


static ulong DoLUIteration(fardouble *a,fardouble *b,
		fardouble *abase, fardouble *bbase,
		ulong numarrays)
{
fardouble *locabase;
fardouble *locbbase;
LUdblptr ptra;  
ulong elapsed;
ulong j,i;              



for(j=0;j<numarrays;j++)
{       locabase=abase+j*LUARRAYROWS*LUARRAYCOLS;
	locbbase=bbase+j*LUARRAYROWS;
	for(i=0;i<LUARRAYROWS*LUARRAYCOLS;i++)
		*(locabase+i)=*(a+i);
	for(i=0;i<LUARRAYROWS;i++)
		*(locbbase+i)=*(b+i);
}


elapsed=StartStopwatch();
for(i=0;i<numarrays;i++)
{       locabase=abase+i*LUARRAYROWS*LUARRAYCOLS;
	locbbase=bbase+i*LUARRAYROWS;
	ptra.ptrs.p=locabase;
	lusolve(*ptra.ptrs.ap,LUARRAYROWS,locbbase);
}

return(StopStopwatch(elapsed));
}


static void build_problem(double a[][LUARRAYCOLS],
		int n,
		double b[LUARRAYROWS])
{
long i,j,k,k1;  
double rcon;     



randnum((n_int32)13);


for(i=0;i<n;i++)
{       
	b[i]=(double)(abs_randwc((n_int32)100)+(n_int32)1);
	for(j=0;j<n;j++)
		if(i==j)
		        
			a[i][j]=(double)(abs_randwc((n_int32)1000)+(n_int32)1);
		else
			a[i][j]=(double)0.0;
}

#ifdef DEBUG
printf("Problem:\n");
for(i=0;i<n;i++)
{

	printf("%.0f/%.0f=%.2f\t",b[i],a[i][i],b[i]/a[i][i]);

}
#endif



for(i=0;i<8*n;i++)
{
	
 
	
        
        
	k=abs_randwc((n_int32)n);
	k1=abs_randwc((n_int32)n);
	if(k!=k1)
	{
		if(k<k1) rcon=(double)1.0;
			else rcon=(double)-1.0;
		for(j=0;j<n;j++)
			a[k][j]+=a[k1][j]*rcon;;
		b[k]+=b[k1]*rcon;
	}
}

return;
}



static int ludcmp(double a[][LUARRAYCOLS],
		int n,
		int indx[],
		int *d)
{

double big;     
double sum;
double dum;     
int i,j,k;      
int imax=0;     
double tiny;    

tiny=(double)1.0e-20;

*d=1;           

for(i=0;i<n;i++)
{       big=(double)0.0;
	for(j=0;j<n;j++)
		if((double)fabs(a[i][j]) > big)
			big=fabs(a[i][j]);
	
	if(big==(double)0.0) return(0);
	LUtempvv[i]=1.0/big;
}


for(j=0;j<n;j++)
{       if(j!=0)
		for(i=0;i<j;i++)
		{       sum=a[i][j];
			if(i!=0)
				for(k=0;k<i;k++)
					sum-=(a[i][k]*a[k][j]);
			a[i][j]=sum;
		}
	big=(double)0.0;
	for(i=j;i<n;i++)
	{       sum=a[i][j];
		if(j!=0)
			for(k=0;k<j;k++)
				sum-=a[i][k]*a[k][j];
		a[i][j]=sum;
		dum=LUtempvv[i]*fabs(sum);
		if(dum>=big)
		{       big=dum;
			imax=i;
		}
	}
	if(j!=imax)             
	{       for(k=0;k<n;k++)
		{       dum=a[imax][k];
			a[imax][k]=a[j][k];
			a[j][k]=dum;
		}
		*d=-*d;         
		dum=LUtempvv[imax];
		LUtempvv[imax]=LUtempvv[j]; 
		LUtempvv[j]=dum;
	}
	indx[j]=imax;
	
	if(a[j][j]==(double)0.0)
		a[j][j]=tiny;

	if(j!=(n-1))
	{       dum=1.0/a[j][j];
		for(i=j+1;i<n;i++)
			a[i][j]=a[i][j]*dum;
	}
}

return(1);
}


static void lubksb( double a[][LUARRAYCOLS],
		int n,
		int indx[LUARRAYROWS],
		double b[LUARRAYROWS])
{

int i,j;        
int ip;         
int ii;
double sum;


ii=-1;
for(i=0;i<n;i++)
{       ip=indx[i];
	sum=b[ip];
	b[ip]=b[i];
	if(ii!=-1)
		for(j=ii;j<i;j++)
			sum=sum-a[i][j]*b[j];
	else
		
		if(sum!=(double)0.0)
			ii=i;
	b[i]=sum;
}

for(i=(n-1);i>=0;i--)
{
	sum=b[i];
	if(i!=(n-1))
		for(j=(i+1);j<n;j++)
			sum=sum-a[i][j]*b[j];
	b[i]=sum/a[i][i];
}
return;
}


static int lusolve(double a[][LUARRAYCOLS],
		int n,
		double b[LUARRAYROWS])
{
int indx[LUARRAYROWS];
int d;
#ifdef DEBUG
int i,j;
#endif

if(ludcmp(a,n,indx,&d)==0) return(0);


lubksb(a,n,indx,b);

#ifdef DEBUG
printf("Solution:\n");
for(i=0;i<n;i++)
{
  for(j=0;j<n;j++){
  
  }
  printf("%6.2f\t",b[i]);
  
}
printf("\n");
#endif

return(1);
}
