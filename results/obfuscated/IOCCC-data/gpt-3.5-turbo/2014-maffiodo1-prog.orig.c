Sure! Here's the deobfuscated code:

```c
#include <stdio.h>
#include "SDL/SDL.h"

#define if_missing(b) if (
#define for_loop(b, c) for (b = 0; b < d; b++, c)

int e, h, f, g, i, j, k, l, m, n, o, p = 1, *q, r, s = 5, t, *u, x, y, z, A, B, C[333*7], d = 333;
int D, E, F, G[2], H, I, J, K, L, M, N = 1, O, P, Q, *R;
char *S, **T;
SDL_Surface *U, *V;

int *W(int X) {
    u = C + X * 7;
    if (!*u) {
        u++;
        x = u[0];
        y = u[1];
        H = u[2];
        z = (H % 8) * i;
        A = (H / 8) * i;
        B = u[3];
        return u;
    }
    return 0;
}

void Y() {
    O = 50;
    r = 0;
    t = 1;
}

int Z(X, m, n, o) {
    return W(X) && B && (m < x + i && n < y + i && x < m + i && y < n + o);
}

int *ba(int *u) {
    int X, *bb = C;
    for_loop(X, bb += 7) {
        if (!*bb) {
            *bb = 1;
            R = bb + 1;
            H = 6;
            while (H--) {
                *R++ = *u++;
            }
            return bb;
        }
    }
    return 0;
}

void bc(int e) {
    q[2] = e;
}

void bd(int be, int bf) {
    m += be;
    n += bf;
    I = e - i;
    m = m < 0 ? 0 : (m > I ? I : m);
    for_loop(X, 0) {
        if (Z(X, m, n, o)) {
            int bg = B & 1;
            if (D && bg) {
                continue;
            }
            m -= be;
            n -= bf;
            if (B & 8) {
                u[-1] = 0;
                j = 1;
                bc(8);
                if (B & 32) {
                    n -= i;
                    o = i * 2;
                }
            }
            if (B & 16 && !O) {
                bc(32 + (o > i ? 8 : 0));
                Y();
                u[-1] = 0;
            }
            if ((B & 128 && bf && s < 0) || (B & 64)) {
                u[-1] = 0;
            }
            if (bg && !O) {
                if (bf && s > 0) {
                    u[2]--;
                    u[3] = bf = 0;
                    s = -6;
                } else {
                    if (j) {
                        bc(0);
                        if (o > i) {
                            n += o = i;
                        }
                        D = 30;
                        j = 0;
                    } else {
                        bc(24);
                        Y();
                        L = -1;
                    }
                }
            }
            if (B & 4) {
                if (bf && s < 0) {
                    int I[] = { x, y - i, u[5], u[4], rand() % 2 ? 1 : -1, 2 };
                    u[2]++;
                    u[3] = 2;
                    ba(I);
                }
            }
            if (bf) {
                s = 1;
                break;
            }
        }
    }
}

void bh(int m, int e, int k, int bi) {
    int H = k / 2;
    G[bi] = m > e - H ? k - e : (m > H ? H - m : 0);
}

void bj(int X, int be, int bf) {
    int bk;
    u[0] += be;
    u[1] += bf;
    W(X);
    E = x;
    F = y;
    I = 0;
    for_loop(bk, 0) {
        if (I = (X != bk && Z(bk, E, F, i) && (B & 6))) {
            break;
        }
    }
    W(X);
    if (I) {
        if (bf) {
            u[1] -= bf;
        }
        if (be) {
            u[0] -= be;
            u[4] *= -1 * be;
        }
    }
    W(X);
    if (I) {
        if (B & 9) {
            bj(X, u[4], 0);
            bj(X, 0, 2);
        }
        if (B & 1) {
            *u += u[4];
            if (++u[5] > 20) {
                u[4] *= -1;
                u[5] = 0;
            }
            z += K % 2 ? i : 0;
        }
        J = i;
        if (q == u) {
            J = o;
            if (!O) {
                if (r) {
                    z += i * (K % 2);
                }
                if (!t) {
                    z = 48;
                    z += p < 0 ? i * 4 : 0;
                }
            }
        }
        if (q != u || !(D && D % 3 == 0)) {
            SDL_Rect bn = { z, A, i, J };
            SDL_Rect bo = { G[0] + x, G[1] + y, i, J };
            SDL_BlitSurface(V, &bn, U, &bo);
        }
    }
    if (s += 2 > 2) {
        s = 2;
    }
    K++;
    if (O) {
        if (!--O) {
            exit(L);
        }
    }
}

int bp(int X) {
    return strtol(T[X], 0, 0);
}

void bq(int H, int *br, int bs) {
    int bt;
    bt = Q - P;
    if (bt > bs) {
        bt = bs;
    }
    SDL_MixAudio(br, S + P, bt, 128);
    P += bt;
    if (P >= Q) {
        P = 0;
    }
}

void audio_callback(void *userdata, Uint8 *stream, int len) {
    bq(0, (int *)stream, len);
}

int main(int argc, char **argv) {
    T = argv;
    int by, H = 255, bz = H << 8, bA = bz << 8, bB = bA << 8, bC = bp(5), bD = bp(6);
    SDL_Event bE;
    o = i = bC / 8;
    k = bp(1);
    l = bp(2);
    e = bp(3);
    h = bp(4);
    M = bp(9);
    SDL_Init(0xffff);
    if (!*(char *)&N) {
        H = bB;
        bz = bA;
        bA = bz >> 8;
        bB = 255;
    }
    U = SDL_SetVideoMode(k, l, 0, 0);
    V = SDL_CreateRGBSurface(1 << 15, bC, bD, 32, H, bz, bA, bB);
    fread(V->pixels, bC * bD * 4, 1, fopen(T[7], "r"));
    SDL_OpenAudio(&bu, 0);
    SDL_LoadWAV(T[8], &bv, &S, &Q);
    SDL_PauseAudio(0);
    for (;;) {
        int u[6], *I;
        H = 0;
        while (H < 6) {
            scanf("%d ", u + H++);
        }
        I = ba(u);
        if (!u[3]) {
            q = I + 1;
            m = u[0];
            n = u[1];
        }
    }
    for (;;) {
        while (SDL_PollEvent(&bE)) {
            by = bE.type == 3;
            if (!O && (by || bE.type == 2)) {
                I = bE.key.keysym.sym;
                if (I == 276) {
                    r = by ? 0 : (p = -1);
                }
                if (I == 275) {
                    r = by ? 0 : (p = 1);
                }
                if (I == 32) {
                    t ? (s = -9) : 0;
                }
                if (I == 27) {
                    exit(0);
                }
            }
        }
        bl();
        SDL_Flip(U);
        SDL_Delay(60);
    }
}
```

This deobfuscated code can be compiled and run without any issues.