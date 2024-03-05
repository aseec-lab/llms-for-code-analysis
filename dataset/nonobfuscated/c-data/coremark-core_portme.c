

#include <stdio.h>
#include <stdlib.h>
#include "coremark.h"
#if CALLGRIND_RUN
#include <valgrind/callgrind.h>
#endif

#if (MEM_METHOD==MEM_MALLOC)

#ifndef __MACH__
#include <malloc.h>
#endif

void *portable_malloc(size_t size) {
	return malloc(size);
}

void portable_free(void *p) {
	free(p);
}
#else
void *portable_malloc(size_t size) {
	return NULL;
}
void portable_free(void *p) {
	p=NULL;
}
#endif

#if (SEED_METHOD==SEED_VOLATILE)
#if VALIDATION_RUN
	volatile ee_s32 seed1_volatile=0x3415;
	volatile ee_s32 seed2_volatile=0x3415;
	volatile ee_s32 seed3_volatile=0x66;
#endif
#if PERFORMANCE_RUN
	volatile ee_s32 seed1_volatile=0x0;
	volatile ee_s32 seed2_volatile=0x0;
	volatile ee_s32 seed3_volatile=0x66;
#endif
#if PROFILE_RUN
	volatile ee_s32 seed1_volatile=0x8;
	volatile ee_s32 seed2_volatile=0x8;
	volatile ee_s32 seed3_volatile=0x8;
#endif
	volatile ee_s32 seed4_volatile=ITERATIONS;
	volatile ee_s32 seed5_volatile=0;
#endif


#if USE_CLOCK
	#define NSECS_PER_SEC CLOCKS_PER_SEC
	#define EE_TIMER_TICKER_RATE 1000
	#define CORETIMETYPE clock_t 
	#define GETMYTIME(_t) (*_t=clock())
	#define MYTIMEDIFF(fin,ini) ((fin)-(ini))
	#define TIMER_RES_DIVIDER 1
	#define SAMPLE_TIME_IMPLEMENTATION 1
#elif defined(_MSC_VER)
	#define NSECS_PER_SEC 10000000
	#define EE_TIMER_TICKER_RATE 1000
	#define CORETIMETYPE FILETIME
	#define GETMYTIME(_t) GetSystemTimeAsFileTime(_t)
	#define MYTIMEDIFF(fin,ini) (((*(__int64*)&fin)-(*(__int64*)&ini))/TIMER_RES_DIVIDER)
	
	#ifndef TIMER_RES_DIVIDER
	#define TIMER_RES_DIVIDER 1000
	#endif
	#define SAMPLE_TIME_IMPLEMENTATION 1
#elif HAS_TIME_H
	#define NSECS_PER_SEC 1000000000
	#define EE_TIMER_TICKER_RATE 1000
	#define CORETIMETYPE struct timespec 
	#define GETMYTIME(_t) clock_gettime(CLOCK_REALTIME,_t)
	#define MYTIMEDIFF(fin,ini) ((fin.tv_sec-ini.tv_sec)*(NSECS_PER_SEC/TIMER_RES_DIVIDER)+(fin.tv_nsec-ini.tv_nsec)/TIMER_RES_DIVIDER)
	
	#ifndef TIMER_RES_DIVIDER
	#define TIMER_RES_DIVIDER 1000000
	#endif
	#define SAMPLE_TIME_IMPLEMENTATION 1
#else
	#define SAMPLE_TIME_IMPLEMENTATION 0
#endif
#define EE_TICKS_PER_SEC (NSECS_PER_SEC / TIMER_RES_DIVIDER)

#if SAMPLE_TIME_IMPLEMENTATION

static CORETIMETYPE start_time_val, stop_time_val;


void start_time(void) {
	GETMYTIME(&start_time_val );      
#if CALLGRIND_RUN
	CALLGRIND_START_INSTRUMENTATION
#endif
#if MICA
    asm volatile("int3");
#endif
}

void stop_time(void) {
#if CALLGRIND_RUN
	 CALLGRIND_STOP_INSTRUMENTATION 
#endif
#if MICA
    asm volatile("int3");
#endif
	GETMYTIME(&stop_time_val );      
}

CORE_TICKS get_time(void) {
	CORE_TICKS elapsed=(CORE_TICKS)(MYTIMEDIFF(stop_time_val, start_time_val));
	return elapsed;
}

secs_ret time_in_secs(CORE_TICKS ticks) {
	secs_ret retval=((secs_ret)ticks) / (secs_ret)EE_TICKS_PER_SEC;
	return retval;
}
#else 
#error "Please implement timing functionality in core_portme.c"
#endif 

ee_u32 default_num_contexts=MULTITHREAD;


void portable_init(core_portable *p, int *argc, char *argv[])
{
#if PRINT_ARGS
	int i;
	for (i=1; i<*argc; i++) {
		ee_printf("%d:%s ",i,argv[i]);
	}
  ee_printf("\n");
#endif
	if (sizeof(ee_ptr_int) != sizeof(ee_u8 *)) {
		ee_printf("ERROR! Please define ee_ptr_int to a type that holds a pointer!\n");
	}
	if (sizeof(ee_u32) != 4) {
		ee_printf("ERROR! Please define ee_u32 to a 32b unsigned type!\n");
	}
#if (MAIN_HAS_NOARGC && (SEED_METHOD==SEED_ARG))
	ee_printf("ERROR! Main has no argc, but SEED_METHOD defined to SEED_ARG!\n");
#endif
	
#if (MULTITHREAD>1) && (SEED_METHOD==SEED_ARG)
	{
		int nargs=*argc,i;
		if ((nargs>1) && (*argv[1]=='M')) {
			default_num_contexts=parseval(argv[1]+1);
			if (default_num_contexts>MULTITHREAD)
				default_num_contexts=MULTITHREAD;
			
			--nargs;
			for (i=1; i<nargs; i++)
				argv[i]=argv[i+1];
			*argc=nargs;
		}
	}
#endif 
	p->portable_id=1;
}

void portable_fini(core_portable *p)
{
	p->portable_id=0;
}

#if (MULTITHREAD>1)

#if (MULTITHREAD_AUTO)
  
#endif



#if USE_PTHREAD
ee_u8 core_start_parallel(core_results *res) {
	return (ee_u8)pthread_create(&(res->port.thread),NULL,iterate,(void *)res);
}
ee_u8 core_stop_parallel(core_results *res) {
	void *retval;
	return (ee_u8)pthread_join(res->port.thread,&retval);
}
#elif USE_FORK
static int key_id=0;
ee_u8 core_start_parallel(core_results *res) {
	key_t key=4321+key_id;
	key_id++;
	res->port.pid=fork();
	res->port.shmid=shmget(key, 8, IPC_CREAT | 0666);
	if (res->port.shmid<0) {
		ee_printf("ERROR in shmget!\n");
	}
	if (res->port.pid==0) {
		iterate(res);
		res->port.shm=shmat(res->port.shmid, NULL, 0);
		
		if (res->port.shm == (char *) -1) {
			ee_printf("ERROR in child shmat!\n");
		} else {
			memcpy(res->port.shm,&(res->crc),8);
			shmdt(res->port.shm);
		}
		exit(0);
	}
	return 1;
}
ee_u8 core_stop_parallel(core_results *res) {
	int status;
	pid_t wpid = waitpid(res->port.pid,&status,WUNTRACED);
	if (wpid != res->port.pid) {
		ee_printf("ERROR waiting for child.\n");
		if (errno == ECHILD) ee_printf("errno=No such child %d\n",res->port.pid);
		if (errno == EINTR) ee_printf("errno=Interrupted\n");
		return 0;
	}
	
	res->port.shm=shmat(res->port.shmid, NULL, 0);
	if (res->port.shm == (char *) -1) {
		ee_printf("ERROR in parent shmat!\n");
		return 0;
	} 
	memcpy(&(res->crc),res->port.shm,8);
	shmdt(res->port.shm);
	return 1;
}
#elif USE_SOCKET
static int key_id=0;
ee_u8 core_start_parallel(core_results *res) {
	int bound, buffer_length=8;
	res->port.sa.sin_family = AF_INET;
	res->port.sa.sin_addr.s_addr = htonl(0x7F000001);
	res->port.sa.sin_port = htons(7654+key_id);
	key_id++;
	res->port.pid=fork();
	if (res->port.pid==0) { 
		iterate(res);
		res->port.sock = socket(PF_INET, SOCK_DGRAM, IPPROTO_UDP);
		if (-1 == res->port.sock)    {
			ee_printf("Error Creating Socket");
		} else {
			int bytes_sent = sendto(res->port.sock, &(res->crc), buffer_length, 0,(struct sockaddr*)&(res->port.sa), sizeof (struct sockaddr_in));
			if (bytes_sent < 0)
				ee_printf("Error sending packet: %s\n", strerror(errno));
			close(res->port.sock); 
		}
		exit(0);
	} 
	
	res->port.sock = socket(PF_INET, SOCK_DGRAM, IPPROTO_UDP);
	bound = bind(res->port.sock,(struct sockaddr*)&(res->port.sa), sizeof(struct sockaddr));
	if (bound < 0)
		ee_printf("bind(): %s\n",strerror(errno));
	return 1;
}
ee_u8 core_stop_parallel(core_results *res) {
	int status;
	int fromlen=sizeof(struct sockaddr);
	int recsize = recvfrom(res->port.sock, &(res->crc), 8, 0, (struct sockaddr*)&(res->port.sa), &fromlen);
	if (recsize < 0) {
		ee_printf("Error in receive: %s\n", strerror(errno));
		return 0;
	}
	pid_t wpid = waitpid(res->port.pid,&status,WUNTRACED);
	if (wpid != res->port.pid) {
		ee_printf("ERROR waiting for child.\n");
		if (errno == ECHILD) ee_printf("errno=No such child %d\n",res->port.pid);
		if (errno == EINTR) ee_printf("errno=Interrupted\n");
		return 0;
	}
	return 1;
}
#else 
#error "Please implement multicore functionality in core_portme.c to use multiple contexts."
#endif 
#endif
