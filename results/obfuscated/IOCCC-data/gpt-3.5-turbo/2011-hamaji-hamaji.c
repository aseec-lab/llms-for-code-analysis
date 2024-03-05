#include <stdint.h>
#include <stdio.h>

char C[120][60]; 
int R[120][60], W, H, J, K, B[61][61], r, i, j, c, *q = &H, t = 7200, x, y, k;
int64_t *U, T[28800], *S = T, O[120], Z[120], v, z;

void D() {
    for (k = -1; k < 7200; k++) {
        v = U[k];
        r = !~v && *R[k / 60] ? 2 : (S[k] != v ? 1 : r);
    }
}

void L() {
    for (r = 1; r == 1; ) {
        r = 3;
        for (i = 0; i < 120; i++) {
            for (k = z = 1, j = 0; v = R[i][j]; j++) {
                O[i] |= ((1LL << v) - 1) << k;
                k += v;
                Z[i] = z |= 1LL << k++;
            }
            v = ~(3LL << k - 2);
            for (j = -61; ++j < 60; ) {
                if (j < 0) {
                    v = v >> 1;
                } else {
                    v = (v << 1) | 1;
                }
                v = (v | ~z) & (j < 0 ? v >> 1 : v << 1 | 1);
                S[60 * i + (j < 0 ? ~j : j)] |= (j ? v : ~3);
            }
        }
        for (z = 0; z < 7200; z++) {
            i = z / 60;
            j = z % 60;
            if (B[i < 60 ? i : j][i < 60 ? j : i - 60] = ~S[z] & O[i] ? ~S[z] & Z[i] ? r = 0 : (U = O, 1) : (U = Z, 2)) {
                k = i < 60 ? j + 60 : j;
                S[i % 60 + 60 * k] |= ~U[k];
            }
        }
        U = S;
        S -= t *= -1;
        D();
        z;
    }
}

int main() {
    for (; K = scanf("%d", R[*q + c * 60] + j) < 1 ? (q = &W, j--, 2 > ++c) : '\n'; j = -K + getchar()? j + 1 : ++*q * 0);
    L();
    if (!r) {
        for (K = 0; K < W * 60; K++) {
            if (K % 60 < W && !B[K / 60][K % 60]) {
                for (c = 64; c--; ) {
                    if (!(1 & S[K] >> c)) {
                        U = S;
                        S += J = 14400;
                        D();
                        S -= J;
                        S[K] |= r == 2 ? 1LL << c : 0;
                    }
                }
                L();
            }
        }
    }
    q = &K;
    for (J = K = i = 0; i < 120; Z[i] = k--, (i > 59 ? (q = &J) : 0), (*q < k ? *q = k : 0), (C[i++][k] = ' ')) {
        j = k = 0;
        for (; x = R[i][j++]; k += sprintf((C[i] + k), "%d.", x));
    }
    i = ~J;
    r & 1 || puts(r ? "invalid" : "failed");
    for (; i < H; i++, puts("")) {
        for (j = ~K; j < W; ) {
            putchar(i < 0 ? (j < 0 || (k = i + Z[j + 60]) < 0) ? ' ' : C[j + 60][k] : (j < 0 ? (k = j + Z[i]) < 0 ? ' ' : C[i][k] : "?X "[B[i][j]]));
            j++;
        }
    }
    return 0;
}