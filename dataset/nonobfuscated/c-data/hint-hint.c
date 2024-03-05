


















#include       "hint.h"    


#pragma GCC push_options
#pragma GCC optimize ("O0")
int main(int argc, char *argv[])
{
    FILE    *curv;        
#ifndef DEBUG
    char    filnm[80];    
#endif
    Speed   qdata[NSAMP]; 

    ERROR   eflag;        

    ISIZE   imax,         
            itm,          
            n,            
            nscout;       

	volatile
	ISIZE   itm2;         
 
    volatile
    DSIZE   tm1;          

    DSIZE   dmax,         
            gamut,        
            scx,          
            scy,          
            tm, tm2;      

	double  memref,
			bandwt;

    double  delq,         
			quips,        
            qpnet,        
            qpeak,        
            qprat,        
			qptmp,        
            t,            
            t0,           
            t1,           
            tdelta,       
                   
            tscout;       

    int     dbits,        
            ibits,        
            i, j, k,      
            laps,         
            memuse;       

    printf("         _    _\n");
    printf("         |    |  _ _   _ _____ TM\n");
    printf("         |--  |  | |\\  | | | |\n");
    printf("         |  --|  | | \\ |   |\n");
    printf("         |    |  | |  \\|   |\n");
    printf("         ^    ^  ^ ^   ^   ^\n\n");
    printf("*** The  HINT  PERFORMANCE ANALYZER ***\n");
    printf("   Version 1.0.1  June 1994-July 2022\n");
    printf("   John L. Gustafson & Quinn O. Snell\n");
    printf("     Scalable Computing Laboratory\n");
    printf("   236 Wilhelm, Iowa State University\n");
    printf("        Ames, Iowa    50011-3020\n");
    printf("            (515) 294 - 9294\n\n");
    printf("Copyright (C) 1994");
    printf(" Iowa State University Research Foundation, Inc.\n");
    printf("Modified for strict ANSI C compatibility 07/2022\n");
    printf("Please send results and questions to: hint@scl.ameslab.gov\n");
    printf("When sending results please follow the form in README\n");
    printf("________________________________________________________\n");

  if (argc > 1) printf("NOTE: This program does not take arguments.\n"); 
	printf("RECT is %d bytes\n",sizeof(RECT));
#ifdef DEBUG
    curv = stdout;
#else
    sprintf(filnm,"data/%s",DFILE);
    if ((curv = fopen(filnm, "w")) == NULL)
    {
        printf("Could not open data file\n");
        exit(1);
    }
#endif

 
    for (t0 = When(); ((t1 = When()) == t0););
    tdelta = t1 - t0;

 
    dbits = 0;
    tm = (DSIZE)1;
    tm2 = tm + tm;
    tm1 = tm2 + 1;
 
    while (((tm1 - tm2) == (DSIZE)1) && (tm2 > (DSIZE)0)) 
    {
        dbits++;
        tm  += tm;
        tm2 += tm2;
        tm1  = tm2 + 1;
    }
    dbits++;
 
    dmax = tm + (tm - 1);
    printf("Apparent number of bits of accuracy: %d\n", dbits);   
    printf("Maximum associative whole number:    %.0f\n",(double)dmax);

 
    ibits = 0;
    itm = (ISIZE)1;
    itm2 = itm + itm;
 
    while (itm2 > (ISIZE)0) 
    {
        ibits++;
        itm  += itm;
        itm2 += itm2;
    }
    imax = itm;
    printf("Maximum number of bits of index:     %d\n", ibits);     
    printf("Maximum representable index:         %.0f\n\n", (double)imax); 

 
    if ((ibits + ibits) < dbits)
    {
        dmax = (DSIZE)imax * (DSIZE)imax - 1;
        dbits = ibits + ibits;
    }
    printf("Index-limited data accuracy:         %d bits\n", dbits); 
    printf("Maximum usable whole number:         %.0f\n",(double)dmax);

 
    j = (dbits)/2;         

 
    for (i = 0, scx = 1; i < j; scx += scx, i++);
    for (i = 0, scy = 1; i < dbits - j; scy += scy, i++);
    printf("Grid: %.0f wide by %.0f high.\n",(double)scx,(double)scy);

 
    tscout = 0; 

 
    for (nscout = NMIN, laps = 3; nscout < scx; )
    {
        t = Run(laps, &gamut, scx, scy, dmax, nscout, &eflag);

        if (eflag != NOERROR)
        {
            nscout /= 2;
            break;
        }
        else if ((t > RUNTM) && (eflag == NOERROR))
        {
            tscout = t;
            break;
        }
        else
        {
            tscout =  t;
            nscout *= 2;
            if (nscout > scx)
            {
                nscout /= 2;
                break;
            }
        }
    }
    if (tscout == 0)
    {
        printf( "Data type for %s is too small\n", argv[0]);
        exit(0);
    }
    if ((tscout < RUNTM) && (eflag == NOMEM))
       printf("Memory is not sufficient for > %3.1f second runs.\n",
                                                                         RUNTM);
    else if (tscout < RUNTM)
       printf("Precision is not sufficient for > %3.1f second runs.\n",
                                                                         RUNTM);

 
    for (t = 0, i = 0, n = NMIN, qpeak = 0, qprat = 1; 
        ((i < NSAMP) && (t < STOPTM) && (n < scx) && (qprat > STOPRT));
        i++, n = ((int)(n * ADVANCE) > n)? (n * ADVANCE) : n + 1)
    {     
        printf(".");
        fflush(stdout);

     
        laps = MAX(RUNTM * nscout / (tscout * n), 1);
        t = Run(laps, &gamut, scx, scy, dmax, n, &eflag);
        if (t == 0)
            t = tdelta;

        if (eflag != NOERROR)
            break;
            
     
     
		delq = (double)dmax / gamut - 1;
        quips = delq / t + 1.0 / gamut / t;
        qdata[i].t  = t;
        qdata[i].qp = quips;
        qdata[i].delq = delq;
        qdata[i].n  = n;
        qpeak = MAX(qpeak, quips);
        qprat = quips / qpeak;
    }
    memuse = (int)(qdata[i-1].n * (sizeof(RECT)+sizeof(DSIZE)+sizeof(ISIZE)));
    if ((qprat > STOPRT) && (eflag == NOMEM))
        #if defined(ILONG)
        printf("\nThis run was memory limited at %ld subintervals -> %d bytes\n",
        #else
        printf("\nThis run was memory limited at %d subintervals -> %d bytes\n",
        #endif
                                                 n, memuse);
    printf("\nDone with first pass. Now computing net QUIPS\n");

	memref = (DSREFS * sizeof(DSIZE) + ISREFS * sizeof(ISIZE)) * qdata[i-1].n;
	memref /= (1024 * 1024);
	bandwt = memref / qdata[i-1].t;
    fprintf(curv,"%12.10f %f %f %f %10d %f\n", 
            qdata[i-1].t, qdata[i-1].qp, qdata[i-1].delq,
            (double)qdata[i-1].n, memuse, bandwt);
    
 
    for (qpnet = qdata[i-1].qp, j = i - 2; j >= 0; j--)
    {
     
        for (k = 0; ((qdata[j+1].t < qdata[j].t) && (k < PATIENCE)); k++)
        {
            printf("#"); 
            laps  = MAX(RUNTM * nscout / (tscout * qdata[j].n), 1);
            t = Run(laps, &gamut, scx, scy, dmax, qdata[j].n, &eflag);
            if (t == 0)
                t = tdelta;

	    delq = (double)dmax / gamut - 1;
            quips = delq / t + 1.0 / gamut / t;
            qdata[j].t  = t;
            qdata[j].qp = quips;
        }
        if (qdata[j+1].t < qdata[j].t)
        {
            #if defined(ILONG)
            printf(" Forcing a time for %ld subintervals\n", qdata[j].n);
            #else
            printf(" Forcing a time for %d subintervals\n", qdata[j].n);
            #endif
            qdata[j].t  = qdata[j+1].t;
			delq = (double)dmax / gamut - 1;
            qdata[j].qp = delq / qdata[j].t + 1.0 / gamut / qdata[j].t;
        }
        memuse = (int)(qdata[j].n * (sizeof(RECT)+sizeof(DSIZE)+sizeof(ISIZE)));
		memref = (DSREFS * sizeof(DSIZE) + ISREFS * sizeof(ISIZE)) * qdata[j].n;
		memref /= (1024 * 1024);
		bandwt = memref / qdata[j].t;
        fprintf(curv,"%12.10f %f %f %f %10d %f\n", 
                qdata[j].t, qdata[j].qp, qdata[j].delq,
                (double)qdata[j].n, memuse, bandwt);

     
     
        qptmp = qdata[j].qp * qdata[j].t / qdata[j+1].t;
        qpnet += (qdata[j].qp - qptmp);
    }
    printf("\nFinished with %f net QUIPs\n", qpnet);
    fclose(curv);
    return 0;
}
#pragma GCC push_options

double
Run(int laps,
    DSIZE *gamut, DSIZE scx, DSIZE scy, DSIZE dmax, ISIZE memry, ERROR *eflag)
{
    RECT    *rect=NULL;   
    ISIZE   *ixes=NULL;   
    DSIZE   *errs=NULL;   
 
    int     i, j;         

    double  t0, t1, tm,   
            mint = 1e32;  

    *eflag = NOERROR;

 
    rect = (RECT  *)malloc((MSIZE)(memry * sizeof(RECT)));
    errs = (DSIZE *)malloc((MSIZE)(memry * sizeof(DSIZE) * 2));
    ixes = (ISIZE *)malloc((MSIZE)(memry * sizeof(ISIZE) * 2));

 
    if ((rect == NULL) || (errs == NULL) || (ixes == NULL))
    {
        if (rect != NULL)
            free(rect);
        if (errs != NULL)
            free(errs);
        if (ixes != NULL)
            free(ixes);
        *eflag = NOMEM;
        return (-NOMEM);
    }

    for (i = 0; i < NTRIAL; i++)
    {
        t0 = When();

     
        for (j = 0; j < laps; j++)
            *gamut = Hint(&scx, &scy, &dmax, &memry, rect, errs, ixes, eflag);

        t1 = When();

     
        tm = (t1 - t0) / laps;
        mint = MIN(mint, tm);
    }

 
    free(rect);
    free(errs);
    free(ixes);

    return (mint);
}
    

double
When(void)
{
    struct timeval tp;
    gettimeofday(&tp, NULL);
    return ((double) tp.tv_sec + (double) tp.tv_usec * 1e-6);
}
