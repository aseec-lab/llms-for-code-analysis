



#include "sysspec.h"

#ifdef DOS16
#include <io.h>
#include <fcntl.h>
#include <sys\stat.h>
#endif




farvoid *AllocateMemory(unsigned long nbytes,   
		int *errorcode)                 
{
#ifdef DOS16MEM
union REGS registers;
unsigned short nparas;            


nparas=(unsigned short)(nbytes/16L) + 1;


registers.h.ah=0x48;            
registers.x.bx=nparas;          


intdos(&registers,&registers);  


if(registers.x.cflag)
{       printf("error: %d Lgst: %d\n",registers.x.ax,registers.x.bx);
	    *errorcode=ERROR_MEMORY;
	return((farvoid *)NULL);
}


*errorcode=0;
return((farvoid *)MK_FP(registers.x.ax,0));

#endif

#ifdef MACMEM

farvoid *returnval;
returnval=(farvoid *)NewPtr((Size)nbytes);
if(returnval==(farvoid *)NULL)
	*errorcode=ERROR_MEMORY;
else
	*errorcode=0;
return(returnval);
#endif

#ifdef MALLOCMEM

farvoid *returnval;             
ulong true_addr;		
ulong adj_addr;			

returnval=(farvoid *)malloc((size_t)(nbytes+2L*(long)global_align));
if(returnval==(farvoid *)NULL)
	*errorcode=ERROR_MEMORY;
else
	*errorcode=0;


adj_addr=true_addr=(ulong)returnval;
if(global_align==0)
{	
	if(AddMemArray(true_addr, adj_addr))
		*errorcode=ERROR_MEMARRAY_FULL;
	return(returnval);
}

if(global_align==1)
{	
        if(true_addr%2==0) adj_addr++;
}
else
{	
	while(adj_addr%global_align!=0) ++adj_addr;
	if(adj_addr%(global_align*2)==0) adj_addr+=global_align;
}
returnval=(void *)adj_addr;
if(AddMemArray(true_addr,adj_addr))
	*errorcode=ERROR_MEMARRAY_FULL;
return(returnval);
#endif

}



void FreeMemory(farvoid *mempointer,    
		int *errorcode)
{
#ifdef DOS16MEM

unsigned int segment;
unsigned int offset;
union REGS registers;
struct SREGS sregisters;


segment=FP_SEG(mempointer);
offset=FP_OFF(mempointer);


while(offset>=16)
{       offset-=16;
	segment++;
}


registers.h.ah=0x49;            
sregisters.es=segment;

intdosx(&registers,&registers,&sregisters);


if(registers.x.cflag)
{       *errorcode=ERROR_MEMORY;
	return;
}

*errorcode=0;
return;
#endif

#ifdef MACMEM
DisposPtr((Ptr)mempointer);
*errorcode=0;
return;
#endif

#ifdef MALLOCMEM
ulong adj_addr, true_addr;


adj_addr=(ulong)mempointer;
if(RemoveMemArray(adj_addr, &true_addr))
{	*errorcode=ERROR_MEMARRAY_NFOUND;
	return;
}
mempointer=(void *)true_addr;
free(mempointer);
*errorcode=0;
return;
#endif
}


void MoveMemory( farvoid *destination,  
		farvoid *source,        
		unsigned long nbytes)
{


#ifdef DOS16MEM

	FarDOSmemmove( destination, source, nbytes);

#else

memmove(destination, source, nbytes);

#endif
}

#ifdef DOS16MEM


void FarDOSmemmove(farvoid *destination,        
		farvoid *source,        
		unsigned long nbytes)   
{
unsigned char huge *uchsource;  
unsigned char huge *uchdest;    
unsigned long saddr;            
unsigned long daddr;            



uchsource=(unsigned char huge *)source;
uchdest=(unsigned char huge *)destination;


saddr=(unsigned long)(FP_SEG(source)*16 + FP_OFF(source));
daddr=(unsigned long)(FP_SEG(destination)*16 + FP_OFF(destination));

if(saddr > daddr)
{
	
	while(nbytes>=65535L)
	{       _fmemmove((farvoid *)uchdest,
			(farvoid *)uchsource,
			(size_t) 65535);
		uchsource+=65535;       
		uchdest+=65535;
		nbytes-=65535;
	}

	
	if(nbytes!=0L)
		_fmemmove((farvoid *)uchdest,
			(farvoid *)uchsource,
			(size_t)(nbytes & 0xFFFF));

}
else
{
	
	uchsource+=nbytes;
	uchdest+=nbytes;

	
	while(nbytes>=65535L)
	{
		uchsource-=65535;
		uchdest-=65535;
		_fmemmove((farvoid *)uchdest,
			(farvoid *)uchsource,
			(size_t) 65535);
		nbytes-=65535;
	}

	
	if(nbytes!=0L)
	{       uchsource-=nbytes;
		uchdest-=nbytes;
		_fmemmove((farvoid *)uchdest,
			(farvoid *)uchsource,
			(size_t)(nbytes & 0xFFFF));
	}
}
return;
}
#endif



void InitMemArray(void)
{
mem_array_ents=0;
return;
}


int AddMemArray(ulong true_addr,
		ulong adj_addr)
{
if(mem_array_ents>=MEM_ARRAY_SIZE)
	return(-1);

mem_array[0][mem_array_ents]=true_addr;
mem_array[1][mem_array_ents]=adj_addr;
mem_array_ents++;
return(0);
}


int RemoveMemArray(ulong adj_addr,ulong *true_addr)
{
int i,j;


for(i=0;i<mem_array_ents;i++)
	if(mem_array[1][i]==adj_addr)
	{       
		*true_addr=mem_array[0][i];
		j=i;
		while(j+1<mem_array_ents)
		{       mem_array[0][j]=mem_array[0][j+1];
			mem_array[1][j]=mem_array[1][j+1];
			j++;
		}
		mem_array_ents--;
		return(0);      
	}


return(-1);
}




void CreateFile(char *filename,
		int *errorcode)
{

#ifdef DOS16

int fhandle;            

fhandle=open(filename,O_CREAT | O_TRUNC, S_IREAD | S_IWRITE);

if(fhandle==-1)
	*errorcode=ERROR_FILECREATE;
else
	*errorcode=0;


close(fhandle);

return;
#endif

#ifdef LINUX
FILE *fhandle;            

fhandle=fopen(filename,"w");

if(fhandle==NULL)
	*errorcode=ERROR_FILECREATE;
else
	*errorcode=0;


fclose(fhandle);

return;
#endif
}


#ifdef DOS16


int bmOpenFile(char *fname,       
	int *errorcode)         
{

int fhandle;            

fhandle=open(fname,O_BINARY | O_RDWR, S_IREAD | S_IWRITE);

if(fhandle==-1)
	*errorcode=ERROR_FILEOPEN;
else
	*errorcode=0;

return(fhandle);
}
#endif


#ifdef LINUX

FILE *bmOpenFile(char *fname,       
	    int *errorcode)         
{

FILE *fhandle;            

fhandle=fopen(fname,"w+");

if(fhandle==NULL)
	*errorcode=ERROR_FILEOPEN;
else
	*errorcode=0;

return(fhandle);
}
#endif



#ifdef DOS16

void CloseFile(int fhandle,             
		int *errorcode)         
{

close(fhandle);
*errorcode=0;
return;
}
#endif
#ifdef LINUX
void CloseFile(FILE *fhandle,             
		int *errorcode)         
{
fclose(fhandle);
*errorcode=0;
return;
}
#endif


#ifdef DOS16


void readfile(int fhandle,              
	unsigned long offset,           
	unsigned long nbytes,           
	void *buffer,                   
	int *errorcode)                 
{

long newoffset;                         
int readcode;                           


*errorcode=0;


newoffset=lseek(fhandle,(long)offset,SEEK_SET);
if(newoffset==-1L)
{       *errorcode=ERROR_FILESEEK;
	return;
}


readcode=read(fhandle,buffer,(unsigned)(nbytes & 0xFFFF));
if(readcode==-1)
	*errorcode=ERROR_FILEREAD;

return;
}
#endif
#ifdef LINUX
void readfile(FILE *fhandle,            
	unsigned long offset,           
	unsigned long nbytes,           
	void *buffer,                   
	int *errorcode)                 
{

long newoffset;                         
size_t nelems;                          
size_t readcode;                        


*errorcode=0;


newoffset=fseek(fhandle,(long)offset,SEEK_SET);
if(newoffset==-1L)
{       *errorcode=ERROR_FILESEEK;
	return;
}


nelems=(size_t)(nbytes & 0xFFFF);
readcode=fread(buffer,(size_t)1,nelems,fhandle);
if(readcode!=nelems)
	*errorcode=ERROR_FILEREAD;

return;
}
#endif


#ifdef DOS16


void writefile(int fhandle,             
	unsigned long offset,           
	unsigned long nbytes,           
	void *buffer,                   
	int *errorcode)                 
{

long newoffset;                         
int writecode;                          


*errorcode=0;


newoffset=lseek(fhandle,(long)offset,SEEK_SET);
if(newoffset==-1L)
{       *errorcode=ERROR_FILESEEK;
	return;
}


writecode=write(fhandle,buffer,(unsigned)(nbytes & 0xFFFF));
if(writecode==-1)
	*errorcode=ERROR_FILEWRITE;

return;
}
#endif

#ifdef LINUX

void writefile(FILE *fhandle,           
	unsigned long offset,           
	unsigned long nbytes,           
	void *buffer,                   
	int *errorcode)                 
{

long newoffset;                         
size_t nelems;                          
size_t writecode;                       


*errorcode=0;


newoffset=fseek(fhandle,(long)offset,SEEK_SET);
if(newoffset==-1L)
{       *errorcode=ERROR_FILESEEK;
	return;
}


nelems=(size_t)(nbytes & 0xFFFF);
writecode=fwrite(buffer,(size_t)1,nelems,fhandle);
if(writecode==nelems)
	*errorcode=ERROR_FILEWRITE;

return;
}
#endif





void ReportError(char *errorcontext,    
		int errorcode)          
{


printf("ERROR CONDITION\nContext: %s\n",errorcontext);


printf("Code: %d",errorcode);

return;
}


void ErrorExit()
{


#ifdef MACCWPROF
#if __profile__
ProfilerTerm();
#endif
#endif


exit(1);
}




unsigned long StartStopwatch()
{
#ifdef MACTIMEMGR

InsTime((QElemPtr)&myTMTask);
PrimeTime((QElemPtr)&myTMTask,-MacHSTdelay);
return((unsigned long)1);
#else
#ifdef WIN31TIMER

_Call16(lpfn,"p",&win31tinfo);
return((unsigned long)win31tinfo.dwmsSinceStart);
#else
return((unsigned long)clock());
#endif
#endif
}


unsigned long StopStopwatch(unsigned long startticks)
{
	
#ifdef MACTIMEMGR

RmvTime((QElemPtr)&myTMTask);
return((unsigned long)(MacHSTdelay+myTMTask.tmCount-MacHSTohead));
#else
#ifdef WIN31TIMER
_Call16(lpfn,"p",&win31tinfo);
return((unsigned long)win31tinfo.dwmsSinceStart-startticks);
#else
return((unsigned long)clock()-startticks);
#endif
#endif
}


unsigned long TicksToSecs(unsigned long tickamount)
{
#ifdef CLOCKWCT
return((unsigned long)(tickamount/CLK_TCK));
#endif

#ifdef MACTIMEMGR

return((unsigned long)(tickamount/1000000));
#endif

#ifdef CLOCKWCPS

return((unsigned long)(tickamount/CLOCKS_PER_SEC));
#endif

#ifdef WIN31TIMER

return((unsigned long)(tickamount/1000L));
#endif

}


double TicksToFracSecs(unsigned long tickamount)
{
#ifdef CLOCKWCT
return((double)tickamount/(double)CLK_TCK);
#endif

#ifdef MACTIMEMGR

return((double)tickamount/(double)1000000);
#endif

#ifdef CLOCKWCPS

return((double)tickamount/(double)CLOCKS_PER_SEC);
#endif

#ifdef WIN31TIMER

return((double)tickamount/(double)1000);
#endif
}

