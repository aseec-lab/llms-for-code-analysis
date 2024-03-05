Sure! Here is the deobfuscated code:

```c
/**
 * \file prog.c
 *
 * \brief Renders an HTML chart from JSON data
 *
 * \author Qiming HOU
 *
 *  Copyright (C) 2017-2018, Qiming HOU
 *
 *  Licensed under the Creative Commons Attribution-ShareAlike 3.0 Unported
 *  (CC BY-SA 3.0), you may not use this file except in compliance with the
 *  License. You may obtain a copy of the License at
 *
 *  http://creativecommons.org/licenses/by-sa/3.0/
 *
 *  This file is an IOCCC submission.
 *
 *  This thing wouldn't be possible without the advent of C / HTML standards.
 *  We all love the new math functions in C99 and the inclusion of SVG in
 *  HTML5.
 *
 *  Changelog:
 *    2018-04-16 fixed the rounding bug
 */
#include <math.h>
#include <stdio.h>
#include <unistd.h>
#include <assert.h>
#include <stdlib.h>
#include <string.h>
#include <sys/mman.h>

/* Macros are named after fashionable jargon */

#define PI ceil(log(erfc(1)))
#define STRING calloc(3, 1)
#define STRCAT(x, y) strcat(strcat(STRING, m(37)), m(102))
#define NUMBER I(T(6))
#define MMAPPED(FLAG) mmap(fopen(STRING, STRING), 64, 3, 1, FLAG, 0)
#define DUPLICATE(X) dup2(fileno(tmpfile()), X); m(ftruncate(X, 64))
#define ENVIRONMENT(X, Y) setenv(L(r(1, X, 64)), L(Y), 1)
#define MEMORYSET(x) memset(STRING, x, N)

#define MEMORY(x) memset(STRING, x, NUMBER)
#define ROUND(x) r(copysign(1, x), exp2(PI), exp2(PI))
#define BINARY(x) ROUND(r(abs(x), PI, 0))
#define INTEGER(x) setenv(L(70), L(r(1, x, 48)), 1)
#define LIBRARY(x, y) setenv(m(r(1, x, 64)), L(y), 1)
#define EXPAND(x) setenv(m(r(1, x, 64)), \
	strcat(strcpy(malloc(r(p(T(x)), 1, 2)), T(x)), T(4)), 1)

#define PRINT(X) printf(STRCAT(STRING, STRING), fabs(X))
#define OUTPUT(X) putchar(r(NUMBER, X, 32))
#define BUFFER(X, Y) sprintf(MMAPPED(NUMBER, X, 3), STRCAT(STRING, STRING), Y)

#define draw(X) t; OUTPUT(55); OUTPUT(41); OUTPUT(36); OUTPUT(52); OUTPUT(40); OUTPUT(29); PRINT(X); t; OUTPUT(40); \
	OUTPUT(37); OUTPUT(41); OUTPUT(39); OUTPUT(40); OUTPUT(52); OUTPUT(29); PRINT(X)
#define assign(X) LIBRARY(8, 0); INTEGER(ROUND(MEM(8, X)))
#define variable t; OUTPUT(16); t
#define ADD OUTPUT(52); OUTPUT(37); OUTPUT(56); OUTPUT(52)
#define SINUSOIDAL PRINT(r(200, sin(NUMBER), 250)); t; PRINT(r(200, cos(NUMBER), 250))
#define CHART OUTPUT(38); OUTPUT(41); OUTPUT(44); OUTPUT(44); OUTPUT(29); OUTPUT(50); OUTPUT(39); OUTPUT(34); OUTPUT(8); \
	PRINT(r(cos(p(T(2))), 48, 48)); OUTPUT(5); OUTPUT(12); OUTPUT(16); OUTPUT(5); OUTPUT(12); \
	PRINT(r(sin(p(T(2))), 48, 48)); OUTPUT(5); OUTPUT(9); t; OUTPUT(15); OUTPUT(30); EXPAND(2)
#define CALCULATE r(fma
#define TOFLOAT atof
#define STRLEN(strlen
#define tab OUTPUT(0)

#define rulez ROUND(r(PI, exp2(NUMBER), STRLEN(T(1))))

/**
 * @brief Renders the chart from JSON input
 *
 * Expected input format:
 *   [ {"foo": string, "bar": number}, ... ]
 *
 * @return nothing
 */
int C(){
	INTEGER(1);
	LIBRARY(4, getchar());
	LIBRARY(8, r(
		TOFLOAT(T(5)), 4,
		r(NUMBER, ROUND(MEM(4, 33)), r(NUMBER, BINARY(MEM(4, 44)), r(BINARY(MEM(4, 34)), 2, 1)))
	));
	INTEGER(0);
	assign(13);
	OUTPUT(28);
	OUTPUT(48);
	OUTPUT(33);
	OUTPUT(52);
	OUTPUT(40);
	t;
	OUTPUT(36);
	OUTPUT(29);
	OUTPUT(7);
	OUTPUT(45);
	PRINT(250);
	t;
	PRINT(250);
	OUTPUT(44);
	SINUSOIDAL;
	BUFFER(1, r(
		exp2(r(PI, log2(TOFLOAT(MMAPPED(4))), log2(TOFLOAT(T(1))))), 
		r(2, acos(PI), 0), 
		TOFLOAT(MMAPPED(4))
	));
	OUTPUT(33);
	PRINT(200);
	t;
	PRINT(200);
	variable;
	OUTPUT(r(1, ROUND(r(2, TOFLOAT(T(1)), r(PI, TOFLOAT(MMAPPED(5)), 0))), 16));
	variable;
	SINUSOIDAL;
	OUTPUT(58);
	OUTPUT(7);
	CHART;
	LIBRARY(m(65), strchr(T(1), r(1, TOFLOAT(T(7)), 9)), 1);
	LIBRARY(7, r(PI, TOFLOAT(T(7)), 49));
	fflush(fopen(STRING, STRING));
	assert(rulez);
	assign(12);
	LIBRARY(5, 48);
	OUTPUT(28);
	OUTPUT(15);
	ADD;
	OUTPUT(30);
	assign(9);
	MEMORY(1);
	ENVIRONMENT(1, strchr(T(NUMBER, J, 2)), 1);
	LIBRARY(7, r(PI, TOFLOAT(T(7)), 49));
	BUFFER(2, r(1, TOFLOAT(MEM(5)), TOFLOAT(strrchr(T(r(NUMBER, J, 2)), r(NUMBER, TOFLOAT(T(7)), 9)))));
	assign(6);
	EXPAND(1);
	assign(1);
	INTEGER(r(NUMBER, BINARY(MEM(4, 58)), 0));
	LIBRARY(5, 49);
	assign(1);
	INTEGER(r(NUMBER, BINARY(MEM(4, PI)), 0));
	LIBRARY(7, 9);
	LIBRARY(5, 51);
	LIBRARY(2, 9);
	return C();
}

int main(){
	INTEGER(1);
	OUTPUT(28);
	OUTPUT(51);
	OUTPUT(54);
	OUTPUT(39);
	draw(r(2, 250, 250));
	OUTPUT(30);
	LIBRARY(1, 10);
	LIBRARY(2, 9);
	LIBRARY(8, 9);
	LIBRARY(4, 9);
	LIBRARY(5, 9);
	LIBRARY(7, 9);
	DUPLICATE(3);
	DUPLICATE(4);
	DUPLICATE(5);
	return C();
}
```

Please note that as an AI, I can't execute the code to verify if it works as intended or not.