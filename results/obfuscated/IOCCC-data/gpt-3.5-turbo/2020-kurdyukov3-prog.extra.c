Sure! Here's the deobfuscated code:

```c
#include <time.h>
#include <stdio.h>
#include <stdlib.h>

#ifndef IDX
#define IDX 1
#endif

#if IDX==1 // square

int main() {
    unsigned a, i = 1, j = 0, t[256];
    srand(time(0));
    for (; i;) {
        if (j) {
            if (--i || ~(a >> 8) ? putchar(i ? t[j] : a) : 0) {
                t[j] = t[--i];
                j = i > 2 ? rand() % (i - 2) + 2 : 1;
            } else {
                a = getchar();
                if ((1 == (a >> 7) || 26 > ((a | 32) - 97)) && 256 > i) {
                    t[i++] = a;
                    j++;
                }
            }
        } else {
            a = getchar();
            if ((1 == (a >> 7) || 26 > ((a | 32) - 97)) && 256 > i) {
                t[i++] = a;
                j++;
            }
        }
    }
}

#elif IDX==2

int main() {
    unsigned a = 0, i, j, t[256];
    srand(time(0));
    for (; 256 > a ;) {
        for (i = j = 0; (26 > ((a = getchar()) | 32) - 97 || 1 == a >> 7) && i < 256; ) {
            t[i++] = a;
        }
        for (; i || ~(a >> 8) ? putchar(i ? t[j] : a) : 0; j = i > 1 ? rand() % (i - 1) + 1 : 0) {
            t[j] = t[i];
            i--;
        }
    }
}

#elif IDX==3 // round

int main() {
    unsigned a, i = 1, j = 0, t[64];
    srand(time(0));
    for (; i;) {
        if (j) {
            if (--i || ~(a >> 8) ? putchar(i ? t[j] : a) : 0) {
                t[j] = t[--i];
                j = i > 2 ? rand() % (i - 2) + 2 : 1;
            } else {
                a = getchar();
                if ((26 > (a | 32) - 97 || 1 == a >> 7) && i < 64) {
                    t[i++] = a;
                    j++;
                }
            }
        } else {
            a = getchar();
            if ((26 > (a | 32) - 97 || 1 == a >> 7) && i < 64) {
                t[i++] = a;
                j++;
            }
        }
    }
}

#elif IDX==4

int main() {
    unsigned a, i, j = a = 0, t[64];
    srand(time(0));
    for (; ~(a >> 8); ) {
        for (; (26 > ((a = getchar()) | 32) - 97 || 1 == a >> 7) && i < 64; ) {
            t[i++] = a;
        }
        for (; !i && a >> 8 ? 0 : putchar(i ? t[j] : a); j = i > 1 ? rand() % (i - 1) + 1 : 0) {
            t[j] = t[--i];
        }
    }
}

#elif IDX==5 // profile

int main() {
    unsigned a = 0, i, j, t[64];
    srand(time(0));
    for (; a < 256; ~a >> 8 ? putchar(a): 0) {
        for (i = j = 0; (a = getchar(), (a | 32) - 97 < 26 || a >> 7 == 1) && i < 64; ) {
            t[i++] = a;
        }
        for (i -= 1; i + 1; putchar(t[j]), t[j] = t[i--], j = i ? rand() % i + 1 : 0) {
        }
    }
    return !a;
}

#endif
```

I have removed the unnecessary obfuscation techniques used in the code, making it easier to read and understand.