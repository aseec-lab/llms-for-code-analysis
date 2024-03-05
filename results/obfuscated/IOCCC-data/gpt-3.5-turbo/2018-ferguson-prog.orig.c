Sure! Here is the deobfuscated code:

```c
#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<ctype.h>
#include<time.h>
#include<stdint.h>

unsigned int d;
char E;
int k;
long u;
size_t t;
const char U;
#define L toupper
#define M SIZE_MAX-1
#ifndef S
#define S 38
#elif S < 38
#undef S
#define S 38
#elif S > M
#undef S
#define S M
#endif
#define l S-1
#define Y strlen
#define Z printf
#define K static
#define z do{

K E W[] = "stqvvwxzwtzuvvryusuxvtrqsruxttrxvwqxq";
K E T[S] = {'M', 'E', 'T', 'H', 'I', 'N', 'K', 'S', ' ', 'I',
            'T', ' ', 'I', 'S', ' ', 'L', 'I', 'K', 'E', ' ',
            'A', ' ', 'W', 'E', 'A', 'S', 'E', 'L', '\0'};

K const char U C[] = " !\"#$<%:>&'()*+,-./0123456789;=?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`{|}~";

K E P[2][sizeof(T)];
K size_t G=0;

#ifndef N
#define N 25
#elif N < 4
#undef N
#define N 3
#elif N > M
#undef N
#define N M
#endif

K unsigned long R=1;
K unsigned int D=0;
K int X(int f, int t) {
    return (rand()%((t)-(f)+1)+(f));
}

K E c() {
    return C[X(0, (int)strlen(C)-1)];
}

struct x {
    E G[sizeof T];
    size_t F;
};

K struct x O[N];

int main(int a, char **V) {
    struct x x;
    E *b;
    const char *A=T;
    int h=0, s, q=0, m=0, y=0, n=N==97, J=0;
    size_t i, j, F;

    if (a < 2) {
        printf("Not enough arguments.\n");
        return 0;
    }

    for (s = 1; s < a && *V[s++]; ) {
        if (*V[s-1]!='-'||h) {
            if (*V[s-1] == 'r') {
                if ((R=strtol(V[s]+1,&b,0)) < 1 || R > 100) {
                    printf("Mutation rate out of range.\n");
                    m=1;
                } else {
                    m=0;
                }
            } else if (*V[s-1] == 'q') {
                q=1;
                printf("Quiet output.\n");
            } else if (*V[s-1] == 'm') {
                J=1;
                printf("Monkey at typewriter.\n");
            } else {
                continue;
            }
        }
    }

    if (n && !y) {
        for (j=0; j<37; ++j) {
            T[j] = W[j]-'A';
        }
    }

    for (; *A && strchr(C, L(*A)); ++A) {
        if (*A) {
            printf("'%c' not in keyboard \"%s\".\n", *A, C);
            return 0;
        }
    }

    srand((unsigned int)time(0));
    memset(&x, 0, sizeof x);
    memset(O,0,sizeof O);

    printf("Target '%s'\n", T);
    printf("Mutation rate %lu\n", R);

    if (m || J || N < 4) {
        printf("Monkey at typewriter.\n");
    }

    for (j=0; j<N; ++j) {
        for (i=0; i<strlen(T); ++i) {
            O[j].G[i]=c();
        }
    }

    while (!D) {
        if (++G==M) {
            printf("Too many attempts, blaming the monkey Eric even if he isn't typing or doesn't exist. Bye.\n");
            return 1;
        }

        for (j=0; j<N&&!D; ++j) {
            F = 0;

            for (i=0; i<strlen(T); ++i) {
                if (L(O[j].G[i]) == L(T[i])) {
                    ++F;
                }
            }

            O[j].F = F;

            if (((D=(F==strlen(T))) || G==1) && (!q || D)) {
                printf("Generation %4zu\tOffspring %2zu: %s\n", G-1, j+1, O[j].G);
            }
        }

        if (D) {
            break;
        }

        s=0;

        if (m) {
            do {
                s=1;

                for (i=0; i<N-1; ++i) {
                    if (O[i].F >= O[i+1].F) {
                        x=O[i+1];
                        O[i]=O[i+1];
                        O[i+1]=x;
                        s=0;
                    }
                }
            } while (!s);
        }

        if (!m) {
            s=1;
            do {
                s=1;

                for (i=0; i<N-1; ++i) {
                    if (O[i].F >= O[i+1].F) {
                        x=O[i+1];
                        O[i]=O[i+1];
                        O[i+1]=x;
                        s=0;
                    }
                }
            } while (!s);
        }

        strcpy(P[0],O[0].G);
        strcpy(P[1],O[1].G);

        for (j=0; j<N; ++j) {
            for (i=0; i<strlen(T); ++i) {
                if (X(0,9)<5) {
                    O[j].G[i]=P[0][i];
                } else{
                    O[j].G[i]=P[1][i];
                }
            }

            if (!q) {
                printf("Generation %4zu\tOffspring %2zu: %s -> ", G, j+1, O[j].G);
            }

            for (i=0; i<strlen(T); ++i) {
                if (X(1, 100) <= R || m) {
                    O[j].G[i]=c();
                }
            }

            if (!q) {
                printf("%s (%s)\n", O[j].G, m ? "monkey typing" : "mutation");
            }
        }

        if (!q) {
            printf("**\n");
        }
    }

    return 0;
}
```