


#include "coremark.h"


static ee_u16 list_known_crc[]   = { (ee_u16)0xd4b0,
                                   (ee_u16)0x3340,
                                   (ee_u16)0x6a79,
                                   (ee_u16)0xe714,
                                   (ee_u16)0xe3c1 };
static ee_u16 matrix_known_crc[] = { (ee_u16)0xbe52,
                                     (ee_u16)0x1199,
                                     (ee_u16)0x5608,
                                     (ee_u16)0x1fd7,
                                     (ee_u16)0x0747 };
static ee_u16 state_known_crc[]  = { (ee_u16)0x5e47,
                                    (ee_u16)0x39bf,
                                    (ee_u16)0xe5a4,
                                    (ee_u16)0x8e3a,
                                    (ee_u16)0x8d84 };
void *
iterate(void *pres)
{
    ee_u32        i;
    ee_u16        crc;
    core_results *res        = (core_results *)pres;
    ee_u32        iterations = res->iterations;
    res->crc                 = 0;
    res->crclist             = 0;
    res->crcmatrix           = 0;
    res->crcstate            = 0;

    for (i = 0; i < iterations; i++)
    {
        crc      = core_bench_list(res, 1);
        res->crc = crcu16(crc, res->crc);
        crc      = core_bench_list(res, -1);
        res->crc = crcu16(crc, res->crc);
        if (i == 0)
            res->crclist = res->crc;
    }
    return NULL;
}

#if (MEM_METHOD == MEM_STATIC)
ee_u8 static_memblk[TOTAL_DATA_SIZE];
#endif
char *mem_name[3] = { "Static", "Heap", "Stack" };


#if MAIN_HAS_NOARGC
MAIN_RETURN_TYPE
main(void)
{
    int   argc = 0;
    char *argv[1];
#else
MAIN_RETURN_TYPE
main(int argc, char *argv[])
{
#endif
    ee_u16       i, j = 0, num_algorithms = 0;
    ee_s16       known_id = -1, total_errors = 0;
    ee_u16       seedcrc = 0;
    CORE_TICKS   total_time;
    core_results results[MULTITHREAD];
#if (MEM_METHOD == MEM_STACK)
    ee_u8 stack_memblock[TOTAL_DATA_SIZE * MULTITHREAD];
#endif
    
    portable_init(&(results[0].port), &argc, argv);
    
    if (sizeof(struct list_head_s) > 128)
    {
        ee_printf("list_head structure too big for comparable data!\n");
        return MAIN_RETURN_VAL;
    }
    results[0].seed1      = get_seed(1);
    results[0].seed2      = get_seed(2);
    results[0].seed3      = get_seed(3);
    results[0].iterations = get_seed_32(4);
#if CORE_DEBUG
    results[0].iterations = 1;
#endif
    results[0].execs = get_seed_32(5);
    if (results[0].execs == 0)
    { 
        results[0].execs = ALL_ALGORITHMS_MASK;
    }
    
    if ((results[0].seed1 == 0) && (results[0].seed2 == 0)
        && (results[0].seed3 == 0))
    { 
        results[0].seed1 = 0;
        results[0].seed2 = 0;
        results[0].seed3 = 0x66;
    }
    if ((results[0].seed1 == 1) && (results[0].seed2 == 0)
        && (results[0].seed3 == 0))
    { 
        results[0].seed1 = 0x3415;
        results[0].seed2 = 0x3415;
        results[0].seed3 = 0x66;
    }
#if (MEM_METHOD == MEM_STATIC)
    results[0].memblock[0] = (void *)static_memblk;
    results[0].size        = TOTAL_DATA_SIZE;
    results[0].err         = 0;
#if (MULTITHREAD > 1)
#error "Cannot use a static data area with multiple contexts!"
#endif
#elif (MEM_METHOD == MEM_MALLOC)
    for (i = 0; i < MULTITHREAD; i++)
    {
        ee_s32 malloc_override = get_seed(7);
        if (malloc_override != 0)
            results[i].size = malloc_override;
        else
            results[i].size = TOTAL_DATA_SIZE;
        results[i].memblock[0] = portable_malloc(results[i].size);
        results[i].seed1       = results[0].seed1;
        results[i].seed2       = results[0].seed2;
        results[i].seed3       = results[0].seed3;
        results[i].err         = 0;
        results[i].execs       = results[0].execs;
    }
#elif (MEM_METHOD == MEM_STACK)
for (i = 0; i < MULTITHREAD; i++)
{
    results[i].memblock[0] = stack_memblock + i * TOTAL_DATA_SIZE;
    results[i].size        = TOTAL_DATA_SIZE;
    results[i].seed1       = results[0].seed1;
    results[i].seed2       = results[0].seed2;
    results[i].seed3       = results[0].seed3;
    results[i].err         = 0;
    results[i].execs       = results[0].execs;
}
#else
#error "Please define a way to initialize a memory block."
#endif
    
    
    for (i = 0; i < NUM_ALGORITHMS; i++)
    {
        if ((1 << (ee_u32)i) & results[0].execs)
            num_algorithms++;
    }
    for (i = 0; i < MULTITHREAD; i++)
        results[i].size = results[i].size / num_algorithms;
    
    for (i = 0; i < NUM_ALGORITHMS; i++)
    {
        ee_u32 ctx;
        if ((1 << (ee_u32)i) & results[0].execs)
        {
            for (ctx = 0; ctx < MULTITHREAD; ctx++)
                results[ctx].memblock[i + 1]
                    = (char *)(results[ctx].memblock[0]) + results[0].size * j;
            j++;
        }
    }
    
    for (i = 0; i < MULTITHREAD; i++)
    {
        if (results[i].execs & ID_LIST)
        {
            results[i].list = core_list_init(
                results[0].size, results[i].memblock[1], results[i].seed1);
        }
        if (results[i].execs & ID_MATRIX)
        {
            core_init_matrix(results[0].size,
                             results[i].memblock[2],
                             (ee_s32)results[i].seed1
                                 | (((ee_s32)results[i].seed2) << 16),
                             &(results[i].mat));
        }
        if (results[i].execs & ID_STATE)
        {
            core_init_state(
                results[0].size, results[i].seed1, results[i].memblock[3]);
        }
    }

    
    if (results[0].iterations == 0)
    {
        secs_ret secs_passed = 0;
        ee_u32   divisor;
        results[0].iterations = 1;
        while (secs_passed < (secs_ret)1)
        {
            results[0].iterations *= 10;
            start_time();
            iterate(&results[0]);
            stop_time();
            secs_passed = time_in_secs(get_time());
        }
        
        divisor = (ee_u32)secs_passed;
        if (divisor == 0) 
            divisor = 1;
        results[0].iterations *= 1 + 10 / divisor;
    }
    
    start_time();
#if (MULTITHREAD > 1)
    if (default_num_contexts > MULTITHREAD)
    {
        default_num_contexts = MULTITHREAD;
    }
    for (i = 0; i < default_num_contexts; i++)
    {
        results[i].iterations = results[0].iterations;
        results[i].execs      = results[0].execs;
        core_start_parallel(&results[i]);
    }
    for (i = 0; i < default_num_contexts; i++)
    {
        core_stop_parallel(&results[i]);
    }
#else
    iterate(&results[0]);
#endif
    stop_time();
    total_time = get_time();
    
    seedcrc = crc16(results[0].seed1, seedcrc);
    seedcrc = crc16(results[0].seed2, seedcrc);
    seedcrc = crc16(results[0].seed3, seedcrc);
    seedcrc = crc16(results[0].size, seedcrc);

    switch (seedcrc)
    {                
        case 0x8a02: 
            known_id = 0;
            ee_printf("6k performance run parameters for coremark.\n");
            break;
        case 0x7b05: 
            known_id = 1;
            ee_printf("6k validation run parameters for coremark.\n");
            break;
        case 0x4eaf: 
            known_id = 2;
            ee_printf("Profile generation run parameters for coremark.\n");
            break;
        case 0xe9f5: 
            known_id = 3;
            ee_printf("2K performance run parameters for coremark.\n");
            break;
        case 0x18f2: 
            known_id = 4;
            ee_printf("2K validation run parameters for coremark.\n");
            break;
        default:
            total_errors = -1;
            break;
    }
    if (known_id >= 0)
    {
        for (i = 0; i < default_num_contexts; i++)
        {
            results[i].err = 0;
            if ((results[i].execs & ID_LIST)
                && (results[i].crclist != list_known_crc[known_id]))
            {
                ee_printf("[%u]ERROR! list crc 0x%04x - should be 0x%04x\n",
                          i,
                          results[i].crclist,
                          list_known_crc[known_id]);
                results[i].err++;
            }
            if ((results[i].execs & ID_MATRIX)
                && (results[i].crcmatrix != matrix_known_crc[known_id]))
            {
                ee_printf("[%u]ERROR! matrix crc 0x%04x - should be 0x%04x\n",
                          i,
                          results[i].crcmatrix,
                          matrix_known_crc[known_id]);
                results[i].err++;
            }
            if ((results[i].execs & ID_STATE)
                && (results[i].crcstate != state_known_crc[known_id]))
            {
                ee_printf("[%u]ERROR! state crc 0x%04x - should be 0x%04x\n",
                          i,
                          results[i].crcstate,
                          state_known_crc[known_id]);
                results[i].err++;
            }
            total_errors += results[i].err;
        }
    }
    total_errors += check_data_types();
    
    ee_printf("CoreMark Size    : %lu\n", (long unsigned)results[0].size);
    ee_printf("Total ticks      : %lu\n", (long unsigned)total_time);
#if HAS_FLOAT
    ee_printf("Total time (secs): %f\n", time_in_secs(total_time));
    if (time_in_secs(total_time) > 0)
        ee_printf("Iterations/Sec   : %f\n",
                  default_num_contexts * results[0].iterations
                      / time_in_secs(total_time));
#else
    ee_printf("Total time (secs): %d\n", time_in_secs(total_time));
    if (time_in_secs(total_time) > 0)
        ee_printf("Iterations/Sec   : %d\n",
                  default_num_contexts * results[0].iterations
                      / time_in_secs(total_time));
#endif
    if (time_in_secs(total_time) < 10)
    {
        ee_printf(
            "ERROR! Must execute for at least 10 secs for a valid result!\n");
        total_errors++;
    }

    ee_printf("Iterations       : %lu\n",
              (long unsigned)default_num_contexts * results[0].iterations);
    ee_printf("Compiler version : %s\n", COMPILER_VERSION);
    ee_printf("Compiler flags   : %s\n", COMPILER_FLAGS);
#if (MULTITHREAD > 1)
    ee_printf("Parallel %s : %d\n", PARALLEL_METHOD, default_num_contexts);
#endif
    ee_printf("Memory location  : %s\n", MEM_LOCATION);
#ifdef PRINT_CRC
    
    ee_printf("seedcrc          : 0x%04x\n", seedcrc);
    if (results[0].execs & ID_LIST)
        for (i = 0; i < default_num_contexts; i++)
            ee_printf("[%d]crclist       : 0x%04x\n", i, results[i].crclist);
    if (results[0].execs & ID_MATRIX)
        for (i = 0; i < default_num_contexts; i++)
            ee_printf("[%d]crcmatrix     : 0x%04x\n", i, results[i].crcmatrix);
    if (results[0].execs & ID_STATE)
        for (i = 0; i < default_num_contexts; i++)
            ee_printf("[%d]crcstate      : 0x%04x\n", i, results[i].crcstate);
    for (i = 0; i < default_num_contexts; i++)
        ee_printf("[%d]crcfinal      : 0x%04x\n", i, results[i].crc);
#endif
    if (total_errors == 0)
    {
        ee_printf("Correct operation validated.\n");
#if HAS_FLOAT
        if (known_id == 3)
        {
            ee_printf("CoreMark 1.0 : %f / %s %s",
                      default_num_contexts * results[0].iterations
                          / time_in_secs(total_time),
                      COMPILER_VERSION,
                      COMPILER_FLAGS);
#if defined(MEM_LOCATION) && !defined(MEM_LOCATION_UNSPEC)
            ee_printf(" / %s", MEM_LOCATION);
#else
            ee_printf(" / %s", mem_name[MEM_METHOD]);
#endif

#if (MULTITHREAD > 1)
            ee_printf(" / %d:%s", default_num_contexts, PARALLEL_METHOD);
#endif
            ee_printf("\n");
        }
#endif
    }
    if (total_errors > 0)
        ee_printf("Errors detected\n");
    if (total_errors < 0)
        ee_printf(
            "Cannot validate operation for these seed values, please compare "
            "with results on a known platform.\n");

#if (MEM_METHOD == MEM_MALLOC)
    for (i = 0; i < MULTITHREAD; i++)
        portable_free(results[i].memblock[0]);
#endif
    
    portable_fini(&(results[0].port));

    return MAIN_RETURN_VAL;
}
