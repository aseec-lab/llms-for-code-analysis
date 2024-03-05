

#include "coremark.h"

ee_s16 matrix_test(ee_u32 N, MATRES *C, MATDAT *A, MATDAT *B, MATDAT val);
ee_s16 matrix_sum(ee_u32 N, MATRES *C, MATDAT clipval);
void   matrix_mul_const(ee_u32 N, MATRES *C, MATDAT *A, MATDAT val);
void   matrix_mul_vect(ee_u32 N, MATRES *C, MATDAT *A, MATDAT *B);
void   matrix_mul_matrix(ee_u32 N, MATRES *C, MATDAT *A, MATDAT *B);
void   matrix_mul_matrix_bitextract(ee_u32 N, MATRES *C, MATDAT *A, MATDAT *B);
void   matrix_add_const(ee_u32 N, MATDAT *A, MATDAT val);

#define matrix_test_next(x)      (x + 1)
#define matrix_clip(x, y)        ((y) ? (x)&0x0ff : (x)&0x0ffff)
#define matrix_big(x)            (0xf000 | (x))
#define bit_extract(x, from, to) (((x) >> (from)) & (~(0xffffffff << (to))))

#if CORE_DEBUG
void
printmat(MATDAT *A, ee_u32 N, char *name)
{
    ee_u32 i, j;
    ee_printf("Matrix %s [%dx%d]:\n", name, N, N);
    for (i = 0; i < N; i++)
    {
        for (j = 0; j < N; j++)
        {
            if (j != 0)
                ee_printf(",");
            ee_printf("%d", A[i * N + j]);
        }
        ee_printf("\n");
    }
}
void
printmatC(MATRES *C, ee_u32 N, char *name)
{
    ee_u32 i, j;
    ee_printf("Matrix %s [%dx%d]:\n", name, N, N);
    for (i = 0; i < N; i++)
    {
        for (j = 0; j < N; j++)
        {
            if (j != 0)
                ee_printf(",");
            ee_printf("%d", C[i * N + j]);
        }
        ee_printf("\n");
    }
}
#endif

ee_u16
core_bench_matrix(mat_params *p, ee_s16 seed, ee_u16 crc)
{
    ee_u32  N   = p->N;
    MATRES *C   = p->C;
    MATDAT *A   = p->A;
    MATDAT *B   = p->B;
    MATDAT  val = (MATDAT)seed;

    crc = crc16(matrix_test(N, C, A, B, val), crc);

    return crc;
}


ee_s16
matrix_test(ee_u32 N, MATRES *C, MATDAT *A, MATDAT *B, MATDAT val)
{
    ee_u16 crc     = 0;
    MATDAT clipval = matrix_big(val);

    matrix_add_const(N, A, val); 
#if CORE_DEBUG
    printmat(A, N, "matrix_add_const");
#endif
    matrix_mul_const(N, C, A, val);
    crc = crc16(matrix_sum(N, C, clipval), crc);
#if CORE_DEBUG
    printmatC(C, N, "matrix_mul_const");
#endif
    matrix_mul_vect(N, C, A, B);
    crc = crc16(matrix_sum(N, C, clipval), crc);
#if CORE_DEBUG
    printmatC(C, N, "matrix_mul_vect");
#endif
    matrix_mul_matrix(N, C, A, B);
    crc = crc16(matrix_sum(N, C, clipval), crc);
#if CORE_DEBUG
    printmatC(C, N, "matrix_mul_matrix");
#endif
    matrix_mul_matrix_bitextract(N, C, A, B);
    crc = crc16(matrix_sum(N, C, clipval), crc);
#if CORE_DEBUG
    printmatC(C, N, "matrix_mul_matrix_bitextract");
#endif

    matrix_add_const(N, A, -val); 
    return crc;
}


ee_u32
core_init_matrix(ee_u32 blksize, void *memblk, ee_s32 seed, mat_params *p)
{
    ee_u32  N = 0;
    MATDAT *A;
    MATDAT *B;
    ee_s32  order = 1;
    MATDAT  val;
    ee_u32  i = 0, j = 0;
    if (seed == 0)
        seed = 1;
    while (j < blksize)
    {
        i++;
        j = i * i * 2 * 4;
    }
    N = i - 1;
    A = (MATDAT *)align_mem(memblk);
    B = A + N * N;

    for (i = 0; i < N; i++)
    {
        for (j = 0; j < N; j++)
        {
            seed         = ((order * seed) % 65536);
            val          = (seed + order);
            val          = matrix_clip(val, 0);
            B[i * N + j] = val;
            val          = (val + order);
            val          = matrix_clip(val, 1);
            A[i * N + j] = val;
            order++;
        }
    }

    p->A = A;
    p->B = B;
    p->C = (MATRES *)align_mem(B + N * N);
    p->N = N;
#if CORE_DEBUG
    printmat(A, N, "A");
    printmat(B, N, "B");
#endif
    return N;
}


ee_s16
matrix_sum(ee_u32 N, MATRES *C, MATDAT clipval)
{
    MATRES tmp = 0, prev = 0, cur = 0;
    ee_s16 ret = 0;
    ee_u32 i, j;
    for (i = 0; i < N; i++)
    {
        for (j = 0; j < N; j++)
        {
            cur = C[i * N + j];
            tmp += cur;
            if (tmp > clipval)
            {
                ret += 10;
                tmp = 0;
            }
            else
            {
                ret += (cur > prev) ? 1 : 0;
            }
            prev = cur;
        }
    }
    return ret;
}


void
matrix_mul_const(ee_u32 N, MATRES *C, MATDAT *A, MATDAT val)
{
    ee_u32 i, j;
    for (i = 0; i < N; i++)
    {
        for (j = 0; j < N; j++)
        {
            C[i * N + j] = (MATRES)A[i * N + j] * (MATRES)val;
        }
    }
}


void
matrix_add_const(ee_u32 N, MATDAT *A, MATDAT val)
{
    ee_u32 i, j;
    for (i = 0; i < N; i++)
    {
        for (j = 0; j < N; j++)
        {
            A[i * N + j] += val;
        }
    }
}


void
matrix_mul_vect(ee_u32 N, MATRES *C, MATDAT *A, MATDAT *B)
{
    ee_u32 i, j;
    for (i = 0; i < N; i++)
    {
        C[i] = 0;
        for (j = 0; j < N; j++)
        {
            C[i] += (MATRES)A[i * N + j] * (MATRES)B[j];
        }
    }
}


void
matrix_mul_matrix(ee_u32 N, MATRES *C, MATDAT *A, MATDAT *B)
{
    ee_u32 i, j, k;
    for (i = 0; i < N; i++)
    {
        for (j = 0; j < N; j++)
        {
            C[i * N + j] = 0;
            for (k = 0; k < N; k++)
            {
                C[i * N + j] += (MATRES)A[i * N + k] * (MATRES)B[k * N + j];
            }
        }
    }
}


void
matrix_mul_matrix_bitextract(ee_u32 N, MATRES *C, MATDAT *A, MATDAT *B)
{
    ee_u32 i, j, k;
    for (i = 0; i < N; i++)
    {
        for (j = 0; j < N; j++)
        {
            C[i * N + j] = 0;
            for (k = 0; k < N; k++)
            {
                MATRES tmp = (MATRES)A[i * N + k] * (MATRES)B[k * N + j];
                C[i * N + j] += bit_extract(tmp, 2, 4) * bit_extract(tmp, 5, 7);
            }
        }
    }
}
