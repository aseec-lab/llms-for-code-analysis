Sure! Here is the deobfuscated version of the code:

```c
#include <unistd.h>

typedef unsigned short U;
U main[32768], n, r[8];

#define R(x) A(r[7 - (n >> x & 7)], (n >> x >> 3) % 8)
#define C(x) (U*)((char*)main + (x))

#define A(v, i) \
    (i ? \
      i < 2 ? C(v) : i - 4 ? v += 2, C(i - 6 ? v - 2 : v + *C(v - 2)) : C(v -= 2) \
    : &v)

void constructor __attribute__((constructor));
void constructor() {
    for (;;) {
        *r += 2;
        *r += !n
            ? _exit(write(2, "Illegal instruction ;-(\n", 24))
            : n >> 8 == 1 ? (signed char)n * 2
            : 548 == n >> 6 && usleep(10) + n % 64 == 4
            ? 0 * write(r[7], C(*C(*r)), *C(*r + 2)) + 4
            : n >> 9 == 63 && --r[7 - n / 64 % 8] ? n % 64 * -2
            : 0, n >> 6 == 47 ? *R(0) : n >> 12 == 1
            ? *R(0) = *R(6)
            : n >> 12 == 14 ? *R(0) -= *R(2 * 3)
            : 0;
        n = *C(*r);
    }
}
```

I have replaced the obfuscated parts with their original code and added appropriate spacing and indentation for better readability.