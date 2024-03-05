

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <time.h>
#include <float.h>

#ifndef SP
#ifndef DP
#define DP
#endif
#endif

#ifdef SP
#define ZERO        0.0
#define ONE         1.0
#define PREC        "Single"
#define BASE10DIG   FLT_DIG

typedef float   REAL;
#endif

#ifdef DP
#define ZERO        0.0e0
#define ONE         1.0e0
#define PREC        "Double"
#define BASE10DIG   DBL_DIG

typedef double  REAL;
#endif

                                    
#define MEM_T long

static REAL linpack  (long nreps,int arsize);
static void matgen   (REAL *a,int lda,int n,REAL *b,REAL *norma);
static void dgefa    (REAL *a,int lda,int n,int *ipvt,int *info,int roll);
static void dgesl    (REAL *a,int lda,int n,int *ipvt,REAL *b,int job,int roll);
static void daxpy_r  (int n,REAL da,REAL *dx,int incx,REAL *dy,int incy);
static REAL ddot_r   (int n,REAL *dx,int incx,REAL *dy,int incy);
static void dscal_r  (int n,REAL da,REAL *dx,int incx);
static void daxpy_ur (int n,REAL da,REAL *dx,int incx,REAL *dy,int incy);
static REAL ddot_ur  (int n,REAL *dx,int incx,REAL *dy,int incy);
static void dscal_ur (int n,REAL da,REAL *dx,int incx);
static int  idamax   (int n,REAL *dx,int incx);
static REAL second   (void);

static void *mempool;


int main(int argc, char **argv)

    {
    char    buf[80];
    int     arsize;
    long    arsize2d,nreps;
    size_t  malloc_arg;
    MEM_T   memreq;

    while (1)
        {
	if (argc < 2)
       	    {
	    printf("Enter array size (q to quit) [100]:  ");
            fgets(buf,79,stdin);
            }
	if (buf[0]=='q' || buf[0]=='Q')
            break;
        if (buf[0]=='\0' || buf[0]=='\n')
	    arsize=100;
	else
            arsize=atoi(buf);
        if (argc > 1)
	    arsize=atoi(argv[1]);
        arsize/=2;
        arsize*=2;
        if (arsize<10)
            {
            printf("Too small.\n");
	    if (argc > 1) break;
            continue;
            }
        arsize2d = (long)arsize*(long)arsize;
        memreq=arsize2d*sizeof(REAL)+(long)arsize*sizeof(REAL)+(long)arsize*sizeof(int);
        malloc_arg=(size_t)memreq;
        if ((MEM_T)malloc_arg!=memreq || (mempool=malloc(malloc_arg))==NULL)
            {
            printf("Not enough memory available for given array size.\n");
	    if (argc > 1) break;
            continue;
            }
        printf("LINPACK benchmark, %s precision.\n",PREC);
        printf("Machine precision:  %d digits.\n",BASE10DIG);
        printf("Array size %d X %d.\n",arsize,arsize);
        printf("Memory required:  %ldK.\n",(memreq+512L)>>10);
        printf("Average rolled and unrolled performance:\n\n");
        printf("    Reps Time(s) DGEFA   DGESL  OVERHEAD    KFLOPS\n");
        printf("----------------------------------------------------\n");
        nreps=1;
        while (linpack(nreps,arsize)<10.)
            nreps*=2;
        free(mempool);
        printf("\n");
	if (argc > 1) break;
        }
	return 0;
    }


static REAL linpack(long nreps,int arsize)

    {
    REAL  *a,*b;
    REAL   norma,t1,kflops,tdgesl,tdgefa,totalt,toverhead,ops;
    int   *ipvt,n,info,lda;
    long   i,arsize2d;

    lda = arsize;
    n = arsize/2;
    arsize2d = (long)arsize*(long)arsize;
    ops=((2.0*n*n*n)/3.0+2.0*n*n);
    a=(REAL *)mempool;
    b=a+arsize2d;
    ipvt=(int *)&b[arsize];
    tdgesl=0;
    tdgefa=0;
    totalt=second();
    for (i=0;i<nreps;i++)
        {
        matgen(a,lda,n,b,&norma);
        t1 = second();
        dgefa(a,lda,n,ipvt,&info,1);
        tdgefa += second()-t1;
        t1 = second();
        dgesl(a,lda,n,ipvt,b,0,1);
        tdgesl += second()-t1;
        }
    for (i=0;i<nreps;i++)
        {
        matgen(a,lda,n,b,&norma);
        t1 = second();
        dgefa(a,lda,n,ipvt,&info,0);
        tdgefa += second()-t1;
        t1 = second();
        dgesl(a,lda,n,ipvt,b,0,0);
        tdgesl += second()-t1;
        }
    totalt=second()-totalt;
    if (totalt<0.5 || tdgefa+tdgesl<0.2)
        return(0.);
    kflops=2.*nreps*ops/(1000.*(tdgefa+tdgesl));
    toverhead=totalt-tdgefa-tdgesl;
    if (tdgefa<0.)
        tdgefa=0.;
    if (tdgesl<0.)
        tdgesl=0.;
    if (toverhead<0.)
        toverhead=0.;
    printf("%8ld %6.2f %6.2f%% %6.2f%% %6.2f%%  %9.3f\n",
            nreps,totalt,100.*tdgefa/totalt,
            100.*tdgesl/totalt,100.*toverhead/totalt,
            kflops);
    return(totalt);
    }



static void matgen(REAL *a,int lda,int n,REAL *b,REAL *norma)

    {
    int init,i,j;

    init = 1325;
    *norma = 0.0;
    for (j = 0; j < n; j++)
        for (i = 0; i < n; i++)
            {
            init = (int)((long)3125*(long)init % 65536L);
            a[lda*j+i] = (init - 32768.0)/16384.0;
            *norma = (a[lda*j+i] > *norma) ? a[lda*j+i] : *norma;
            }
    for (i = 0; i < n; i++)
        b[i] = 0.0;
    for (j = 0; j < n; j++)
        for (i = 0; i < n; i++)
            b[i] = b[i] + a[lda*j+i];
    }



static void dgefa(REAL *a,int lda,int n,int *ipvt,int *info,int roll)

    {
    REAL t;
    int j,k,kp1,l,nm1;

    

    if (roll)
        {
        *info = 0;
        nm1 = n - 1;
        if (nm1 >=  0)
            for (k = 0; k < nm1; k++)
                {
                kp1 = k + 1;

                

                l = idamax(n-k,&a[lda*k+k],1) + k;
                ipvt[k] = l;

                

                if (a[lda*k+l] != ZERO)
                    {

                    

                    if (l != k)
                        {
                        t = a[lda*k+l];
                        a[lda*k+l] = a[lda*k+k];
                        a[lda*k+k] = t;
                        }

                    

                    t = -ONE/a[lda*k+k];
                    dscal_r(n-(k+1),t,&a[lda*k+k+1],1);

                    

                    for (j = kp1; j < n; j++)
                        {
                        t = a[lda*j+l];
                        if (l != k)
                            {
                            a[lda*j+l] = a[lda*j+k];
                            a[lda*j+k] = t;
                            }
                        daxpy_r(n-(k+1),t,&a[lda*k+k+1],1,&a[lda*j+k+1],1);
                        }
                    }
                else
                    (*info) = k;
                }
        ipvt[n-1] = n-1;
        if (a[lda*(n-1)+(n-1)] == ZERO)
            (*info) = n-1;
        }
    else
        {
        *info = 0;
        nm1 = n - 1;
        if (nm1 >=  0)
            for (k = 0; k < nm1; k++)
                {
                kp1 = k + 1;

                

                l = idamax(n-k,&a[lda*k+k],1) + k;
                ipvt[k] = l;

                

                if (a[lda*k+l] != ZERO)
                    {

                    

                    if (l != k)
                        {
                        t = a[lda*k+l];
                        a[lda*k+l] = a[lda*k+k];
                        a[lda*k+k] = t;
                        }

                    

                    t = -ONE/a[lda*k+k];
                    dscal_ur(n-(k+1),t,&a[lda*k+k+1],1);

                    

                    for (j = kp1; j < n; j++)
                        {
                        t = a[lda*j+l];
                        if (l != k)
                            {
                            a[lda*j+l] = a[lda*j+k];
                            a[lda*j+k] = t;
                            }
                        daxpy_ur(n-(k+1),t,&a[lda*k+k+1],1,&a[lda*j+k+1],1);
                        }
                    }
                else
                    (*info) = k;
                }
        ipvt[n-1] = n-1;
        if (a[lda*(n-1)+(n-1)] == ZERO)
            (*info) = n-1;
        }
    }



static void dgesl(REAL *a,int lda,int n,int *ipvt,REAL *b,int job,int roll)

    {
    REAL    t;
    int     k,kb,l,nm1;

    if (roll)
        {
        nm1 = n - 1;
        if (job == 0)
            {

            
            

            if (nm1 >= 1)
                for (k = 0; k < nm1; k++)
                    {
                    l = ipvt[k];
                    t = b[l];
                    if (l != k)
                        {
                        b[l] = b[k];
                        b[k] = t;
                        }
                    daxpy_r(n-(k+1),t,&a[lda*k+k+1],1,&b[k+1],1);
                    }

            

            for (kb = 0; kb < n; kb++)
                {
                k = n - (kb + 1);
                b[k] = b[k]/a[lda*k+k];
                t = -b[k];
                daxpy_r(k,t,&a[lda*k+0],1,&b[0],1);
                }
            }
        else
            {

            
            

            for (k = 0; k < n; k++)
                {
                t = ddot_r(k,&a[lda*k+0],1,&b[0],1);
                b[k] = (b[k] - t)/a[lda*k+k];
                }

            

            if (nm1 >= 1)
                for (kb = 1; kb < nm1; kb++)
                    {
                    k = n - (kb+1);
                    b[k] = b[k] + ddot_r(n-(k+1),&a[lda*k+k+1],1,&b[k+1],1);
                    l = ipvt[k];
                    if (l != k)
                        {
                        t = b[l];
                        b[l] = b[k];
                        b[k] = t;
                        }
                    }
            }
        }
    else
        {
        nm1 = n - 1;
        if (job == 0)
            {

            
            

            if (nm1 >= 1)
                for (k = 0; k < nm1; k++)
                    {
                    l = ipvt[k];
                    t = b[l];
                    if (l != k)
                        {
                        b[l] = b[k];
                        b[k] = t;
                        }
                    daxpy_ur(n-(k+1),t,&a[lda*k+k+1],1,&b[k+1],1);
                    }

            

            for (kb = 0; kb < n; kb++)
                {
                k = n - (kb + 1);
                b[k] = b[k]/a[lda*k+k];
                t = -b[k];
                daxpy_ur(k,t,&a[lda*k+0],1,&b[0],1);
                }
            }
        else
            {

            
            

            for (k = 0; k < n; k++)
                {
                t = ddot_ur(k,&a[lda*k+0],1,&b[0],1);
                b[k] = (b[k] - t)/a[lda*k+k];
                }

            

            if (nm1 >= 1)
                for (kb = 1; kb < nm1; kb++)
                    {
                    k = n - (kb+1);
                    b[k] = b[k] + ddot_ur(n-(k+1),&a[lda*k+k+1],1,&b[k+1],1);
                    l = ipvt[k];
                    if (l != k)
                        {
                        t = b[l];
                        b[l] = b[k];
                        b[k] = t;
                        }
                    }
            }
        }
    }




static void daxpy_r(int n,REAL da,REAL *dx,int incx,REAL *dy,int incy)

    {
    int i,ix,iy;

    if (n <= 0)
        return;
    if (da == ZERO)
        return;

    if (incx != 1 || incy != 1)
        {

        

        ix = 1;
        iy = 1;
        if(incx < 0) ix = (-n+1)*incx + 1;
        if(incy < 0)iy = (-n+1)*incy + 1;
        for (i = 0;i < n; i++)
            {
            dy[iy] = dy[iy] + da*dx[ix];
            ix = ix + incx;
            iy = iy + incy;
            }
        return;
        }

    

    for (i = 0;i < n; i++)
        dy[i] = dy[i] + da*dx[i];
    }



static REAL ddot_r(int n,REAL *dx,int incx,REAL *dy,int incy)

    {
    REAL dtemp;
    int i,ix,iy;

    dtemp = ZERO;

    if (n <= 0)
        return(ZERO);

    if (incx != 1 || incy != 1)
        {

        

        ix = 0;
        iy = 0;
        if (incx < 0) ix = (-n+1)*incx;
        if (incy < 0) iy = (-n+1)*incy;
        for (i = 0;i < n; i++)
            {
            dtemp = dtemp + dx[ix]*dy[iy];
            ix = ix + incx;
            iy = iy + incy;
            }
        return(dtemp);
        }

    

    for (i=0;i < n; i++)
        dtemp = dtemp + dx[i]*dy[i];
    return(dtemp);
    }



static void dscal_r(int n,REAL da,REAL *dx,int incx)

    {
    int i,nincx;

    if (n <= 0)
        return;
    if (incx != 1)
        {

        

        nincx = n*incx;
        for (i = 0; i < nincx; i = i + incx)
            dx[i] = da*dx[i];
        return;
        }

    

    for (i = 0; i < n; i++)
        dx[i] = da*dx[i];
    }



static void daxpy_ur(int n,REAL da,REAL *dx,int incx,REAL *dy,int incy)

    {
    int i,ix,iy,m;

    if (n <= 0)
        return;
    if (da == ZERO)
        return;

    if (incx != 1 || incy != 1)
        {

        

        ix = 1;
        iy = 1;
        if(incx < 0) ix = (-n+1)*incx + 1;
        if(incy < 0)iy = (-n+1)*incy + 1;
        for (i = 0;i < n; i++)
            {
            dy[iy] = dy[iy] + da*dx[ix];
            ix = ix + incx;
            iy = iy + incy;
            }
        return;
        }

    

    m = n % 4;
    if ( m != 0)
        {
        for (i = 0; i < m; i++)
            dy[i] = dy[i] + da*dx[i];
        if (n < 4)
            return;
        }
    for (i = m; i < n; i = i + 4)
        {
        dy[i] = dy[i] + da*dx[i];
        dy[i+1] = dy[i+1] + da*dx[i+1];
        dy[i+2] = dy[i+2] + da*dx[i+2];
        dy[i+3] = dy[i+3] + da*dx[i+3];
        }
    }



static REAL ddot_ur(int n,REAL *dx,int incx,REAL *dy,int incy)

    {
    REAL dtemp;
    int i,ix,iy,m;

    dtemp = ZERO;

    if (n <= 0)
        return(ZERO);

    if (incx != 1 || incy != 1)
        {

        

        ix = 0;
        iy = 0;
        if (incx < 0) ix = (-n+1)*incx;
        if (incy < 0) iy = (-n+1)*incy;
        for (i = 0;i < n; i++)
            {
            dtemp = dtemp + dx[ix]*dy[iy];
            ix = ix + incx;
            iy = iy + incy;
            }
        return(dtemp);
        }

    

    m = n % 5;
    if (m != 0)
        {
        for (i = 0; i < m; i++)
            dtemp = dtemp + dx[i]*dy[i];
        if (n < 5)
            return(dtemp);
        }
    for (i = m; i < n; i = i + 5)
        {
        dtemp = dtemp + dx[i]*dy[i] +
        dx[i+1]*dy[i+1] + dx[i+2]*dy[i+2] +
        dx[i+3]*dy[i+3] + dx[i+4]*dy[i+4];
        }
    return(dtemp);
    }



static void dscal_ur(int n,REAL da,REAL *dx,int incx)

    {
    int i,m,nincx;

    if (n <= 0)
        return;
    if (incx != 1)
        {

        

        nincx = n*incx;
        for (i = 0; i < nincx; i = i + incx)
            dx[i] = da*dx[i];
        return;
        }

    

    m = n % 5;
    if (m != 0)
        {
        for (i = 0; i < m; i++)
            dx[i] = da*dx[i];
        if (n < 5)
            return;
        }
    for (i = m; i < n; i = i + 5)
        {
        dx[i] = da*dx[i];
        dx[i+1] = da*dx[i+1];
        dx[i+2] = da*dx[i+2];
        dx[i+3] = da*dx[i+3];
        dx[i+4] = da*dx[i+4];
        }
    }



static int idamax(int n,REAL *dx,int incx)

    {
    REAL dmax;
    int i, ix, itemp;

    if (n < 1)
        return(-1);
    if (n ==1 )
        return(0);
    if(incx != 1)
        {

        

        ix = 1;
        dmax = fabs((double)dx[0]);
        ix = ix + incx;
        for (i = 1; i < n; i++)
            {
            if(fabs((double)dx[ix]) > dmax)
                {
                itemp = i;
                dmax = fabs((double)dx[ix]);
                }
            ix = ix + incx;
            }
        }
    else
        {

        

        itemp = 0;
        dmax = fabs((double)dx[0]);
        for (i = 1; i < n; i++)
            if(fabs((double)dx[i]) > dmax)
                {
                itemp = i;
                dmax = fabs((double)dx[i]);
                }
        }
    return (itemp);
    }


static REAL second(void)

    {
    return ((REAL)((REAL)clock()/(REAL)CLOCKS_PER_SEC));
    }


