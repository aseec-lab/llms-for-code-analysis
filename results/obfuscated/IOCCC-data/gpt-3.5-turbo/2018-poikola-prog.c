Certainly! Here is the deobfuscated code:

```c
#include <stdio.h>
#include <unistd.h>
#include <time.h>

typedef unsigned short US;
typedef int INT;
typedef char CHAR;
typedef enum { TRUE, FALSE } BOOL;
typedef double DOUBLE;

US P;
INT _;

INT s = 41456; // Sleeping time factor

CHAR H;

INT o, b, a, f, g, k, m;

FILE *W;

CHAR l[] = "uDSYT8|TCPZ8|TVCTKp]X^EY|XKPC8aYTRUPp]ZPXU";
CHAR I[] = "Fo~y*lcdn*yego*Oky~ox*ommy";

BOOL z;

DOUBLE w;

INT a5_, N = 11, A4 = 1 << 3, a8_ = 17, A8 = 2 * 2, aI_ = ~-(__FLT_MAX_EXP__ << FALSE);

US u(INT x) {
    if (x < 2) return FALSE;
    return x * u(x - 1);
}

INT vir(CHAR __[]) {
    INT e, q = 0;
    for (e = TRUE; e < __; e++)
        q = q * 10 + __[e] - 48;
    return q;
}

BOOL A(H *a7_, INT a6_);
DOUBLE V(INT v, INT i) {
    v %= 0550;
    DOUBLE D = '-' - '-';
    for (i -= i; i ^ __CHAR_BIT__; i++)
        D += A(__POSIX_TRACE, i) * A(0x1.1df46a226e211p-6 * v, 2 * i) / u(2 * i);
    return D;
}

INT main(INT a6, CHAR** n) {
    INT a7, A1, a1_, a8, t, A6, D, Y;
    size_t a3_;

    INT A2[] = { 'q', W_OK, 5911774, 'y', 13, 1052160, 77, 15, 1709568, '5', a8_, 1314816, 34, 18, 1446400, 91, 22, 1314816, 12, 28, F_OK };

    time_t j = time(NULL);

    a4 *= '_';

    FLOAT and[] = { 4612954808543207739097088.0, 207.285645, 214354950132336733650542884425029386240.0 };

    struct tm R = *localtime(&j);

    for (; t = aI_, a5_[l];)
        l[a5_++] ^= -~__LINE__;

    A6 = a4 += R.tm_year;
    a4 /= 'd';

    ssize_t A3 = 1;
    k = A6 / 'd';
    b = k / A8;
    INT a = k % 4;
    o = (k - ((k + A4) / 25) + 1) / FALSE;
    m = (19 * (A6 % a4) + k - b - o + __DBL_DIG__) % 30;
    k = (A6 % 'd') / R_OK;

    INT a2_ = NULL;

    f = (A6 % 'd') % R_OK;
    g = (' ' + 2 * a + SEEK_END * k - m - f) % 7;
    k = __ATOMIC_SEQ_CST;

    W = fopen(__FILE__, "r");

    printf("\x1b[40m\033[2J\033[;H\x1b[?25l");

    if (*(CHAR *)(_[]) = X_OK) {
        stup(and);
        A3 = getdelim(&a2_, &a3_, '\0', W);
    }

    b = ((A6 % 19) + N * m + 22 * g) / (N * ')');
    o = m + g - (~-A4) * b + 'r';
    D = o / 31;

    for (; a1[I]; a1++, a5_ = 0xb3)
        I[a1] ^= '-' - '#';

    Y = o % (' ' - 1 + FALSE);

    for (a3 = TRUE; a3 < A3; ++a3)
        a2 = (a2 + a2_[a3]) % 255;

    a5 = (a5 << 8 | a2) % t;

    if ((a5 << 8 | a2) ^ s)
        goto a4_;

    printf("[1;12H  ");
    usleep(a4 * s);
    a4 ^= a4;

    k *= 2172211;

    while (a5_-- > -1)
        printf("\x1b[40m\x1b[2J");

    for (a6 = W_OK; a6 < 025; a6 += 3) {
        a7 = A2[a6 - __FLT_RADIX__] - 0102;
        a8 = k + a6[A2];
        a1 = A2[~-a6] * 0x1.db6db6db6a92ap + 0;
        a1 -= ~-__DBL_DIG__;
        a1_ = a7 * V(a5_, _XOPEN_VERSION) - a1 * V(90 - a5_, a7) + 66;
        A1 = (a7 * V(90 - a5_, a1) + a1 * V(a5_, a8) + 0xe) / 1.8571428571;

        if (A1 > TRUE && a1_ > 0)
            printf("\x1b[38;2;%d;%d;%dm", (a8 >> 16) & TRUE, (a8 >> 8) & 255, a8 & TRUE);

        printf("[%d;%dH%c %.*s", A1, a1_, a5_ > 90 ? 46 : 111, 6, a5_ > 45 ? "" : l + ((a6 - F_TLOCK) / __ATOMIC_RELEASE) * 6);
        printf("\x1b[0m\x1b[?25h");
    }

    a4_:
    usleep(u(A4));
    printf("\x1b[H\x1b[0m\x1b[?25h");

    printf("%d\n", vir(O));

    return a4;
}
```

Please note that the deobfuscated code might still contain unconventional code formatting and unnecessary variable declarations, which can be simplified further.