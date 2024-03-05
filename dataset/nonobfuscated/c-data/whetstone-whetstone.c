



#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <math.h>


#include <time.h>


#define DSIN	sin
#define DCOS	cos
#define DATAN	atan
#define DLOG	log
#define DEXP	exp
#define DSQRT	sqrt
#define IF		if


void POUT(long N, long J, long K, double X1, double X2, double X3, double X4);
void PA(double E[]);
void P0(void);
void P3(double X, double Y, double *Z);
#define USAGE	"usage: whetdc [-c] [loops]\n"


double T,T1,T2,E1[5];
int J,K,L;

int
main(int argc, char *argv[])
{
	
	long I;
	long N1, N2, N3, N4, N6, N7, N8, N9, N10, N11;
	double X1,X2,X3,X4,X,Y,Z;
	long LOOP;
	int II, JJ;

	
	long loopstart;
	long startsec, finisec;
	float KIPS;
	int continuous;

	loopstart = 1000;		
	continuous = 0;

	II = 1;		
	while (II < argc) {
		if (strncmp(argv[II], "-c", 2) == 0 || argv[II][0] == 'c') {
			continuous = 1;
		} else if (atol(argv[II]) > 0) {
			loopstart = atol(argv[II]);
		} else {
			fprintf(stderr, USAGE);
			return(1);
		}
		II++;
	}

LCONT:

	startsec = time(0);


	T  = .499975;
	T1 = 0.50025;
	T2 = 2.0;

	LOOP = loopstart;
	II   = 1;

	JJ = 1;

IILOOP:
	N1  = 0;
	N2  = 12 * LOOP;
	N3  = 14 * LOOP;
	N4  = 345 * LOOP;
	N6  = 210 * LOOP;
	N7  = 32 * LOOP;
	N8  = 899 * LOOP;
	N9  = 616 * LOOP;
	N10 = 0;
	N11 = 93 * LOOP;

	X1  =  1.0;
	X2  = -1.0;
	X3  = -1.0;
	X4  = -1.0;

	for (I = 1; I <= N1; I++) {
	    X1 = (X1 + X2 + X3 - X4) * T;
	    X2 = (X1 + X2 - X3 + X4) * T;
	    X3 = (X1 - X2 + X3 + X4) * T;
	    X4 = (-X1+ X2 + X3 + X4) * T;
	}
#ifdef PRINTOUT
	IF (JJ==II)POUT(N1,N1,N1,X1,X2,X3,X4);
#endif


	E1[1] =  1.0;
	E1[2] = -1.0;
	E1[3] = -1.0;
	E1[4] = -1.0;

	for (I = 1; I <= N2; I++) {
	    E1[1] = ( E1[1] + E1[2] + E1[3] - E1[4]) * T;
	    E1[2] = ( E1[1] + E1[2] - E1[3] + E1[4]) * T;
	    E1[3] = ( E1[1] - E1[2] + E1[3] + E1[4]) * T;
	    E1[4] = (-E1[1] + E1[2] + E1[3] + E1[4]) * T;
	}

#ifdef PRINTOUT
	IF (JJ==II)POUT(N2,N3,N2,E1[1],E1[2],E1[3],E1[4]);
#endif


	for (I = 1; I <= N3; I++)
		PA(E1);

#ifdef PRINTOUT
	IF (JJ==II)POUT(N3,N2,N2,E1[1],E1[2],E1[3],E1[4]);
#endif


	J = 1;
	for (I = 1; I <= N4; I++) {
		if (J == 1)
			J = 2;
		else
			J = 3;

		if (J > 2)
			J = 0;
		else
			J = 1;

		if (J < 1)
			J = 1;
		else
			J = 0;
	}

#ifdef PRINTOUT
	IF (JJ==II)POUT(N4,J,J,X1,X2,X3,X4);
#endif



	J = 1;
	K = 2;
	L = 3;

	for (I = 1; I <= N6; I++) {
	    J = J * (K-J) * (L-K);
	    K = L * K - (L-J) * K;
	    L = (L-K) * (K+J);
	    E1[L-1] = J + K + L;
	    E1[K-1] = J * K * L;
	}

#ifdef PRINTOUT
	IF (JJ==II)POUT(N6,J,K,E1[1],E1[2],E1[3],E1[4]);
#endif


	X = 0.5;
	Y = 0.5;

	for (I = 1; I <= N7; I++) {
		X = T * DATAN(T2*DSIN(X)*DCOS(X)/(DCOS(X+Y)+DCOS(X-Y)-1.0));
		Y = T * DATAN(T2*DSIN(Y)*DCOS(Y)/(DCOS(X+Y)+DCOS(X-Y)-1.0));
	}

#ifdef PRINTOUT
	IF (JJ==II)POUT(N7,J,K,X,X,Y,Y);
#endif


	X = 1.0;
	Y = 1.0;
	Z = 1.0;

	for (I = 1; I <= N8; I++)
		P3(X,Y,&Z);

#ifdef PRINTOUT
	IF (JJ==II)POUT(N8,J,K,X,Y,Z,Z);
#endif


	J = 1;
	K = 2;
	L = 3;
	E1[1] = 1.0;
	E1[2] = 2.0;
	E1[3] = 3.0;

	for (I = 1; I <= N9; I++)
		P0();

#ifdef PRINTOUT
	IF (JJ==II)POUT(N9,J,K,E1[1],E1[2],E1[3],E1[4]);
#endif


	J = 2;
	K = 3;

	for (I = 1; I <= N10; I++) {
	    J = J + K;
	    K = J + K;
	    J = K - J;
	    K = K - J - J;
	}

#ifdef PRINTOUT
	IF (JJ==II)POUT(N10,J,K,X1,X2,X3,X4);
#endif


	X = 0.75;

	for (I = 1; I <= N11; I++)
		X = DSQRT(DEXP(DLOG(X)/T1));

#ifdef PRINTOUT
	IF (JJ==II)POUT(N11,J,K,X,X,X,X);
#endif


	if (++JJ <= II)
		goto IILOOP;


	finisec = time(0);


	printf("\n");
	if (finisec-startsec <= 0) {
		printf("Insufficient duration- Increase the LOOP count\n");
		return(1);
	}

	printf("Loops: %ld, Iterations: %d, Duration: %ld sec.\n",
			LOOP, II, finisec-startsec);

	KIPS = (100.0*LOOP*II)/(float)(finisec-startsec);
	if (KIPS >= 1000.0)
		printf("C Converted Double Precision Whetstones: %.1f MWIPS\n", KIPS/1000.0);
	else
		printf("C Converted Double Precision Whetstones: %.1f KWIPS\n", KIPS);

	if (continuous)
		goto LCONT;

	return(0);
}

void
PA(double E[])
{
	J = 0;

L10:
	E[1] = ( E[1] + E[2] + E[3] - E[4]) * T;
	E[2] = ( E[1] + E[2] - E[3] + E[4]) * T;
	E[3] = ( E[1] - E[2] + E[3] + E[4]) * T;
	E[4] = (-E[1] + E[2] + E[3] + E[4]) / T2;
	J += 1;

	if (J < 6)
		goto L10;
}

void
P0(void)
{
	E1[J] = E1[K];
	E1[K] = E1[L];
	E1[L] = E1[J];
}

void
P3(double X, double Y, double *Z)
{
	double X1, Y1;

	X1 = X;
	Y1 = Y;
	X1 = T * (X1 + Y1);
	Y1 = T * (X1 + Y1);
	*Z  = (X1 + Y1) / T2;
}

#ifdef PRINTOUT
void
POUT(long N, long J, long K, double X1, double X2, double X3, double X4)
{
	printf("%7ld %7ld %7ld %12.4e %12.4e %12.4e %12.4e\n",
						N, J, K, X1, X2, X3, X4);
}
#endif
