Sure! Here's the deobfuscated code:

```c
#include <stdio.h>
#include <stdlib.h>

#define M malloc(sizeof(
#define R fread(x,sizeof(float),Z*w,f)
#define E free(

char *G = "\0\0Usage\n"
"    %s [-h] [-d #c cid] file1 file2 ... (or stdin)";

int s, p, e, c, t, r, a, l;

int F(int o, int n, int t) {
    char *p = (o * (136 - o) >= 1260) * 7 * (o - 9) + G;
    int n = 0;
    for (; *p != '\0'; p++) {
        n = (*p - '@') + (64 * n);
        o += 3;
        if (((o / 4) < (5 - t))) {
            break;
        }
    }
    return (n >> (2 * o % 8)) & 0xFF;
}

float L[0x400];

int T(float a, float b, float l, float *e) {
    if (L[0x3FF] || !(e[1] = b * a + l * (*e)) || !T(l * a - b * (*e), b, l, e + 1)) {
        return 1;
    }
    return 0;
}

float f(int x) {
    int w = s << !!T(1, 6.135885e-3, -1.882472e-5 + 1, L);
    x = ((x % w) + w) % w << 9;
    return (L[x / s] * (s - x % s) + L[x / s + 1] * (x % s)) / s;
}

int C(float *h, float *i, int r, int P) {
    int x, y, c;
    for (y = 0; h < i; ++y, h += r) {
        c = P < 1;
        *h = 0;
        for (x = 0; x < 8; x++) {
            c += ((1 & P >> x) && P + (*h += f(e * (l + a * (2 * x + 1)) * y) * f(s / 2 - e * a * y + e * a * y * y * (t / p) / (s / p)))));
        }
        float z = f(y * t) * f(y * t);
        *h = z * (1 + 3 * z + 2 * z * z) / (6 * (*h) * c);
    }
    return h - i - r + 1;
}

void d(int w, int n, FILE *f) {
    int Z = s / t;
    int S[5];
    float **P = M float *) c);
    for (r = 0; r < c; ++r) {
        P[r] = M float) * Z);
        C(P[r], P[r] + Z, 1, r);
    }
    float *x = M float) * Z * w);
    for (;;) {
        for (r = 0; r < 5; ++r) {
            int o = 1;
            for (R, a = 256; o && --a;) {
                for (o = t = 0; t < Z;) {
                    o += (P[a][t] != x[w * t + n]);
                    ++t;
                }
            }
            S[r] = a;
        }
        if (R - Z * w) {
            break;
        }
        for (r = a = -12; r < 5 && (a += (a < 11 ? 22 : 1)) < 127;) {
            for (r = -1; ++r < 5 && F(a, 1, r) == S[r];);
        }
        if (a < 127 && r == 5) {
            putchar(a);
        }
    }
    for (; --c; E P[c]);
    E P;
    fclose(f);
}

int main(int q, char **v) {
    s = 44100;
    p = 25;
    e = 55;
    c = 256;
    t = 75;
    r = (q > 1) ? (v[1][0] * c + v[1][1]) : 0;
    a = 7;
    l = 16;

    if (r == 11620) {
        d(atoi(v[2]), atoi(v[3]), (q < 5) ? stdin : fopen(v[4], "r"));
        return 0;
    }

    char *H = malloc(c);
    char *h = H;
    sprintf(H, G, v[0]);

    if (*H) {
        q = 1;
    }

    int w = (q > 1) ? q - 1 : 1;
    FILE **f = M FILE *) *w);
    f[0] = stdin;

    if (q > 1) {
        for (r = 0; r < w; r++) {
            f[r] = fopen(v[r + 1], "r");
        }
    }

    int l = s / t * w;

    float *b = M float *) l;
    float *t = b + l;
    int *i = M int *) w;

    for (;;) {
        int x = (*h && 1 > ungetc(*H++, *f));

        for (r = w; r--; ) {
            i[r] = getc(f[r]);
            if (i[r] < 0) {
                i[r] = !++x;
            }
        }

        if (x == w) {
            break;
        }

        while (++r < 6) {
            while (C(b++, t, w, F(*i++, r)));
            i -= w;
            fwrite(b -= w, sizeof(float), l, stdout);
        }

        fflush(stdout);
    }

    while (fclose(f[--w]), w);
    E f;
    E b;
    E i;
    E h;
}
```