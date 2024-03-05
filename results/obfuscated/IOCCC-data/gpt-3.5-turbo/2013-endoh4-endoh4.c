int F, V, M, N, i;
#ifndef S
#define S 70,23
#endif
#define _POSIX_C_SOURCE 199309
#include <time.h>
#include <stdio.h>
#include <stdlib.h>

struct timespec R = {0, 1E6};
int j, k, m,
n, u, v, w, t, W, H;
double *P, *Q, I, J, K, L, x, y, z, X, Y, Z, a, b, c, d, e, f, g, h, p, q, r, s;
int *F[*], *V[*];

void o(double x) {
    for (p = q = i = 0, s = r = 1; i < 99; s = (s + x / s) / 2)
        i % 2 ? q += r, r = -r : p += r, r *= 3.14 * x / ++i;
}

int G(int p, int q, int s, int g, int f) {
    int *v = malloc(sizeof(int) * N);
    int *a, *b;
    int h = -1;
    int r = h;

    for (F[f] = V[f] = v; ++h < f;)
        if (V[h][p] == q) {
            if (s + 1 && E(p, q, V[h][q], s) < 1E-4) {
                for (a = F[g], b = F[h]; N > ++r; v[r] = q + 1 ? a[q] - r ? q : b[p] - r ? p : -1 : p)
                    p = a[r], q = b[r];
                
                for (r = 0; r < f; r++)
                    if (F[r] == a || F[r] == b)
                        F[r] = v;
            }
            return f;
        }

    for (h = 0; h < N; v[h++] = -1)
        r = h - p && h - q && (r < 0 || E(p, q, r, h) < 0) ? h : r;
    
    v[v[v[p] = q] = r] = p;
    return G(r, q, p, f, G(p, r, q, f, G(p, q, s, g, f + 1)));
}

char *T;

int main(void) {
    H = (W = S) * 2;
    T = malloc(sizeof(char) * (H * W + 4));
    
    for (srand(t = time(0)); i = scanf("%lf,", P = (Q = realloc(Q, (N + 1)) * sizeof(double))) >= 0;)
        i ? c += *P **P, 1 < N++%3 ? o(c), b = b < s ? b : s, c = 0 : 0 : scanf("%*s");
    
    for (P = malloc(sizeof(double) * N * 2); j < N;)
        Q[j++] /= b;
    
    o(U);
    I = p / 1E3;
    J = q / 1E3;
    K = U;
    L = U;
    N /= 3;
    F = malloc(sizeof(int *) * 4 * N);
    V = F + 2 * N;

    for (; u == v;)
        for (j = u = m = n = 0; j < 3 * N; j++)
            P[j] = Q[j] + (U) / 1E5;

    for (; m < N; m++)
        u = P[u * 3] > P[m * 3] ? m : u;

    for (v = !u; n < N; n++)
        v = B(u, v, n) > 0 ? n : v;

    for (puts("\x1b[2J"), M = G(u, v, -1, -1, 0);;) {
        for (i = 4; i < W * H / 2 + 3; T[++i] = j = 0)
            T[i] = i % W - 3 ? 32 : 10;
        
        for (j = 0; j < M; j++) {
            for (n = k = 0; F[j][m = n] < 0;)
                n++;
            
            for (p = n = 0; n < k; n += 2)
                p += P[n] * (P[(n + 3) % k] - P[(n + k - 1) % k]);
            
            for (q = time(0) < t + 3; (q || p >= 0) && n;) {
                a = P[n % k];
                b = P[n % k + 1];
                c = P[n -= 2];
                d = P[n + 1];
                e = d - b;
                
                if (e * e > (c - a) * D(b, c)) {
                    for (; c - a && u < c + 0.5; 0 <= w && w < W - 1 && 0 <= m && m < H / 2) {
                        v = m / 2 * W + w + 4;
                        T[v] = "',;;;,'"[T[v] % 8 ^ m % 2];
                        m = u < c + 0.5 ? c + 0.5 : u++;
                        w = i ? m = u++, v : u++;
                    }
                }
            }
        }

        puts(T);
        nanosleep(&R, 0);
        X = x;
        Y = y;
        Z = z;
    }
}