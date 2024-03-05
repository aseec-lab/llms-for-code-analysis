Certainly! Here's the deobfuscated version of the code:

```c
#include <stdio.h>
#include <complex.h>
#include <time.h>

typedef complex double c;
c X[3], P, O;

int main(int argc, char **argv) {
    int D[9999], N = 20, L = 4, n, m, k, a[3], i;
    char *p, *q, S[2000] =
        "L@X-SGD-HNBBB-AD-VHSG-XNT\x1b[2J\x1b[H";
    char *s = S;
    char *G = "r2zZX!.+@KBK^yh.!:%Bud!.+Jyh.!6.BHBhp!6.BHBh!:%Buv{VT!.hBJ6p!042ljn!284b}`!.hR6Dp!..
    
    clock_t c = clock();

    int *d = D;
    for (m = 0; m < 26; m++, s++) {
        if (*s > 63) {
            *d++ = m % 7 * 16 - 7 * 8;
            *d++ = m / 7 * 25;
            *d++ = *s - 64;
        }
    }

    if (argv[1]) {
        FILE *F = fopen(argv[1], "r");
        for (d = D, L = N = m = 0; (x = fgetc(F)) > 0 || fclose(F);) {
            if (x > 13) {
                if (64 < x && x < 91) {
                    *d++ = m * 16;
                    *d++ = L * 25;
                    *d++ = x % 26;
                }
                m++;
            } else {
                L++;
            }
            if (d - D > N * 3 || m == 0) {
                for (N++; d - D > N * 3;) {
                    D[N * 3] -= m * 8;
                }
            }
        }
    }
    
    for (i = 0; i < 200 + L * 25; i++) {
        char *p = S + 33;
        for (n = 0; n < 1920; n++, *p++ = (n % 80 > 78) ? 10 : 32) {}
        
        for (*p = x = 0, d = D; x < N; x++, d += 3) {
            O = (d[1] - i - 40) * I + *d;
            n = d[2];
            p = G;
            for (; n--;) {
                for (; *p++ > 33;) {}
                *a = a[1] = *p++;
                for (; *p > 33; p++) {
                    if (*p % 2) {
                        *a = *p;
                        break;
                    }
                    a[2] = *p;
                    for (m = 0; m < 3; m++) {
                        k = a[m] / 2 - 18;
                        q = "/&&&##%%##.+),A$$$$'&&'&&((%-((#'/#%%#&#&&#D&";
                        for (n = 2; k--;) {
                            n += *(q++) - 34;
                        }
                        X[m] = n % 13 + n / 13 * I;
                    }
                    b(0, 1);
                    *a = a[1] = *p;
                }
            }
        }
        for (puts(S), s=S+30; (clock() - c) * 10 < i * CLOCKS_PER_SEC;) {}
    }
    return 0;
}

void b(double t, double u) {
    double s = P = B(t) - B(u);
    if (s < 1) {
        m = P = B((t + u) / 2);
        k = -I * P;
        if (m > -41 && m < 39 && 9 < k && k < 48) {
            m += k / 2 * 80 + 73;
            if (S[m] != -73) {
                if (k % 2) {
                    if (S[m] - 94) {
                        S[m] = 95;
                    } else {
                        S[m] = 73;
                    }
                } else {
                    if (S[m] - 95) {
                        S[m] = 94;
                    } else {
                        S[m] = 73;
                    }
                }
            } else {
                S[m] = 73;
            }
        } else {
            S[m] = 1;
        }
    } else {
        b(t, (t + u) / 2);
        b((t + u) / 2, u);
    }
}

c B(double t) {
    double s = 1 - t;
    P = s * s * X[1] + 2 * s * t * X + t * t * X[2] + O;
    double u = I * P;
    return +48 * ((s = P) + 48 * I) / (1 < u ? u : 1);
}
```

Now the code is much more readable and easier to understand.