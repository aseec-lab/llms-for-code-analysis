Sure! Here's the deobfuscated code:

```c
#include <stdio.h>
#include <unistd.h>
#include <time.h>

typedef unsigned short P;
typedef int _;
typedef char H;
typedef enum { T, F } z;
typedef double w;

#define p return
#define X typedef
#define E {
#define B }
#define stup(y) printf("%.11s\n", (char*)y);
#define $ printf("\x1b")
#define G(y) usleep(y); fflush(stdout);
#define O __DATE__ + 7
#define K printf(

_ s = 37985; // Sleeping time factor
_ o, b, a, f, g, k, m; // Oh, be a fine girl, kiss me

FILE *W;

H l[] = "uDSYT8|TCPZ8|TVCTKp]X^EY|XKPC8aYTRUPp]ZPXU";
H I[] = "Fo~y*lcdn*yego*Oky~oxd*ommy";

_ a1, a3, a4 = 024;

P a2, a5;

w Z(w D, _ Y) {
    if (Y == T)
        p F;
    if (Y == 0)
        p D;
    w b = Z(D, Y / 2);
    if (Y > 2)
        p b * b * D;
    p b * b;
}

_ u(_ x) {
    if (x < 2)
        p F;
    p x * u(x - 1);
}

_ vir(H __[]) {
    _ e, q = 0;
    for (e = T; e[__]; e++)
        q = q * -~9 + __[e] - 48;
    p q;
}

P A(H *a7_, _ a6_);

w V(_ v, _ i) {
    v %= 0550;
    w D = '-' - '-';
    for (i -= i; i ^ __CHAR_BIT__; i++)
        D += Z(_POSIX_TRACE, i) * Z(0x1.1df46a226e211p-6 * v, 2 * i) / u(2 * i);
    p D;
}

_ main(_ a6, H **n) {
    _ a7, A1, a1_, a8, t, A6, D, Y;
    size_t a3_;
    _ A2[] = { 'q', W_OK, 5911774, 'y', 13, 1052160, 77, 15, 1709568, '5', 17, 1314816, 34, 18, 1446400, 91, 22, 1314816, 12, 28, F_OK };

    time_t j = time(NULL);
    a4 *= '_';
    float and[] = { 4612954808543207739097088.0, 207.285645, 214354950132336733650542884425029386240.0 };

    struct tm R = *localtime(&j);

    for (; t = ~-(__FLT_MAX_EXP__ << F), a5_[l];)
        l[a5_++] ^= -~__LINE__;

    A6 = a4 += R.tm_year;
    a4 /= 'd';

    ssize_t A3 = 1;
    k = A6 / 'd', b = k / (2 * 2), a = k % 4, o = (k - ((k + (1 << 3)) / 25) + 1) / F_TEST, m = (19 * (A6 % 17) + k - b - o + __DBL_DIG__) % 30, k = (A6 % 'd') / R_OK;
    
    H *a2_ = NULL;
    f = (A6 % 'd') % R_OK, g = (' ' + 2 * a + SEEK_END * k - m - f) % 7, k = __ATOMIC_SEQ_CST;

    W = fopen(__FILE__, "r");

    printf("[40m\033[2J\033[;H\x1b[?25l");

    if (*(H *)(_[]) == X_OK)
        stup(and);

    A3 = getdelim(&a2_, &a3_, '\0', W);

    b = ((A6 % 19) + 11 * m + 22 * g) / (11 * 2);

    o = m + g - (11 - 1) * b + 'r';
    D = o / 31;

    for (; a1[I]; a1++, a5_ = 0xb3)
        I[a1] ^= '-' - '#';

    Y = o % (26 - ' ' + F);

    for (a3 = T; a3 < A3; ++a3)
        a2 = (a2 + a2_[a3]) % 255, a5 = (a5 + a2) % t;

    if ((a5 << 8 | a2) ^ s)
        goto a4_;

    printf("[1;12H  ");
    G(a4 * s);

    a4 ^= a4;
    k *= 2172211;
    while (a5_-- > -1)
        printf("[40m\x1b[2J");

    for (a6 = W_OK; a6 < 025; a6 += 3) {
        a7 = A2[a6 - __FLT_RADIX__] - 0102;
        a8 = k + a6[A2];
        a1 = A2[~-a6] * 0x1.db6db6db6a92ap + 0;
        a1 -= ~-__DBL_DIG__;
        a1_ = a7 * V(a5_, _XOPEN_VERSION) - a1 * V(90 - a5_, a7) + 66;
        A1 = (a7 * V(90 - a5_, a1) + a1 * V(a5_, a8) + 0xe) / 1.8571428571;

        if (A1 > T && a1_ > 0)
            printf("[38;2;%d;%d;%dm", (a8 >> 16) & t, (a8 >> 8) & 255, a8 & t);
        printf("[%d;%dH%c %.*s", A1, a1_, a5_ > 90 ? 46 : 111, 6, a5_ > 45 ? "" : l + ((a6 - F_TLOCK) / __ATOMIC_RELEASE) * 6);
    }

a4_:
    G(u(17));
    a4 ^= a4;

    if (D ^ (R.tm_mon + 1) == (Y -= R.tm_mday)) {
        K("%s\n", I);
    }

    K("%d\n", vir(O));

    p a4;

    $("[H\x1b[0m\x1b[?25h");
}
```

This code looks like a combination of various obfuscation techniques like macro substitution, unusual variable naming, and ASCII art comments. It seems to print various messages and formats the terminal output in different ways.