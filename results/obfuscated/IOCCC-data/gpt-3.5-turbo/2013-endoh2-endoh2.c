Sure! Here's the deobfuscated version of the code:

```c
#include<stdio.h>

char*p, w[13997], *h = w, *q, *r;
int d, e, g;

void n(int d) {
    while (d > 8) {
        putchar(d);
        d--;
    }
}

void m(int z) {
    double f, B[3], i, j, k, l;
    for (int y = 1; y < 99; y += 2) {
        l *= z * z * .038553;
        k *= -y * (y + 1);
        j += l * k;
    }
    i *= j;
}

int c(int d) {
    return putchar(d);
}

int main() {
    for (; *p; p++) {
        if (*p < 33) {
            continue;
        }
        *h++ = *p - 32;
    }
    
    q = h;
    
    for (p = w; p < w + 823; p++) {
        for (d = (*p) - 56, e = -1; e < (d < 0 ? 5 : (d < 6 ? (d > 4 ? 160 : 30) : (d > 35 ? 27 << (d - 36) : d/9 * 6))); q++) {
            *q = d < 0 ? (*p >> e) & 1 : (d < 6 || q[d > 35 ? -108 : d % 9 * - 12 - 12]);
        }
    }
    
    for (q = h; g++ < 6156; q++) {
        if (*q) {
            *q = *p - 4 ? (e = *p + 2, e - 32 ? e : 0) : (r && *r ? g % 108 < 2 ? 2 : *r++ : (r = r ? p++ : w, 2));
        } else {
            *q = 0;
        }
    }
    
    n(8189);
    
    n(618);
    n(10000);
    n(7600);
    
    for (; s < 10; s++) {
        c(s < 3 ? s : 0);
        n(618);
        n(708);
    }
    
    n(602);
    
    n(4352);
    n(8);
    n(257);
    n(32);
    n(520);
    n(656);
    n(257);
    n(96);
    n(16512);
    
    for (; y++ < 207;) {
        c(y < 31 ? (y < 19 ? y > 6 : 30 - y) : (y < 47 ? (y < 32 || y > 36 ? 16 : 0) : (y < 48 ? 2 : y % 16 * 16 + 13 - y % 16)));
        n(90);
    }
    
    n(62208);
    
    for (; b < 40496; b++) {
        e = 32 < a ? 63 - a: a;
        for (f = d = t = s = 0; e > t;) {
            e -= ++t;
        }
        e = t % 2 ? e : t - e;
        
        for (d = a > 31 ? (e = 7 - e, 14 - t - e) : t - e; s < 64; t -= i * B[!d + !e]) {
            z = b % 82 * 8 + s * 8 - 4;
            y = b / 64 * 8 + s % 8 + 7;
            i = z < 0 || z > 647 || y % 11 > 9 ? 1 : q[h[z + y * 11 * 108]];
            m(s * 2 * d + d);
            m(s++ % 8 * 2 * e + e);
        }
        
        s = 2 + t * t;
        
        for (d = 2; s > 3; s /= 4) {
            d *= 2;
        }
        
        s = 8 << (a ? 12 : 9);
        s -= d * 2 << (!!a * 4);
        s |= t > 0 ? t : t - 1 + d;
        
        if (c(s) == 256) {
            c(0);
        }
        
        if (c(s)) {
            c(0);
        }
        
        if (a++ > 62) {
            n(612);
            a = 0;
        }
    }
    
    n(523);
    return 0;
}
```

Note: The deobfuscated code might not be completely correct as the original code was obfuscated. Some portions of the code may still be unclear or require further analysis.