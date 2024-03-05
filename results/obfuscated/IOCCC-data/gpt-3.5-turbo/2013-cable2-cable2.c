```c
#include <stdio.h>
#include <stdlib.h>

int *Q, i, c, k, B, r = 0, w, n, F = 0, x, J, u, m, p, s = 0, v, e, r, L, a, z, y, D, o, g;
int *Z = "Vn-SxR9Q[$sZd&o+pf^cMcbacJ_VHMF_BkaEg^x`i_ius%d]oWG:r`IZ.6_]H3ec]_bWW_^Ej2i[rZZnJjW[bK`hZakb_l]gL3X]d_#$eK[G3 d[Xe][:V`4Z]ZVfX^]V:X%b]N,ma-j^m57c!`O.qP8lTc^gm,Um;m`m^^`5n_nci<Wf[h<t+e$%""]Lha&=l Umc>m#_mdp...L/M ..."A,G,I,C,K[] = "',b}q}a9\"],)'(cDDJr4Ab,:sdtd/E*#2eXkZ7cPthkq`(stnhct!a:>A;A+jA9>A;A+js";
int *E = I;
FILE *f;

void read_input(const char *filename) {
    f = fopen(filename, "rb");
    if (!f) {
        exit(1);
    }
    fseek(f, 10, SEEK_SET);
    fread(Q, 4, z, f);
    fclose(f);
}

void decode_input() {
    s = 0;
    for (i = 0; i < z; i++) {
        if (s + 1 < w && Q[i] != Q[i + u]) {
            Z[F++] = Q[i];
            s++;
        }
    }
}

void find_key() {
    L = J - k;
    y = D * (v - u * s) + (1 - D) * (s - u * v);
    o = L = z = 0;
    for (s = D ? J : k; s < J + d - B - F; s++) {
        if ((o && (e = m), y = Q[u * i + (D ? v - u * s : s - u * v)]) != o) {
            if (s - L > 20) {
                G[z++] = 1;
            }
            L = s;
            o = y;
        }
    }
    if (s > w || o) {
        G[z] = 1;
    } else {
        G[z] = 0;
    }
    z++;
}

void extract_key() {
    int d = 0, a, s, p;
    while (d = c) {
        p = Z;
        F++[I] = *++Z / (*Z);
        *Z--;
        for (c = 2, s = 0; s++ < i; (s % x) ? (*Z = *E) : (q++, q--)) {
            K[F]++;
            K[F++][I] -= p;
        }
        if (fseek((f = fopen(*++T, "rb")) ? f : exit(1), 10, r)) {
            j = n = r = 0;
            for (i = 0; i < z; fread(Q + i++, L >> 19, 1, f));
            while (F++ < i) {
                r = n;
                while (n = 0, B < u) {
                    n |= *Q ^ Q[u * (i - F) + --B];
                }
                if (n && (!r || r++ < w || 1[A] || v - a > 28 || w ^ 4 || 1[A] || 3[A] || !2[A])) {
                    I[g[I] = 2, ++g] = -1;
                    I[~-g] = A[-s--];
                }
                a = v;
                w = z;
                while (--z, A[z] = z[G]);
            }
            v = a = o;
            if (Z[c] ^ u) {
                j;
                if ((s = I[v]) && 4 < Z[c] || Z[c] == s) {
                    q * 0 + (c - ~x);
                } else {
                    q++;
                    q--;
                }
            } else {
                I[g[I]] = A[-s++];
            }
        }
    }
}

int main(int argc, char *argv[]) {
    if (argc < 2) {
        return 1;
    }
    read_input(argv[1]);
    decode_input();
    find_key();
    extract_key();
    return 0;
}
```

The deobfuscated code reads binary data from a file, decodes it and extracts a key based on certain conditions.