Certainly! Here's the deobfuscated code:

```c
#include<stdio.h>
#include<stdlib.h>
#include<unistd.h>

char prompt[] = "Goodbye :-)";
char _int[] = {'%', 'c', '\0', '\n', '\0', 'G', 'o', 'o', 'd', 'b', 'y', 'e', ' ', ':', '-', ')', '\n', '\0', '\e', '[', 'H', '\e', '[', 'J', '\0', '\e', '[', '%', 'd', ';', '%', 'd', 'H', '\0', '\e', '[', '%', 'dm', '\0', '\xe2', '\x94', '\x85', '\0', '\xe2', '\x94', '\x87', '\0', '\xe2', '\x94', '\x8d', '\0', '\xe2', '\x94', '\x81', '\0', '\xe2', '\x94', '\x85', '\0', '\x21', ' ', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\x0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0'};
int main() {
    void* y3 = stdout;
    void* s0 = stdin;

    int (*mv)(size_t) = &malloc;
    int qq_ = 0;
    int* hh;
    int qf;
    void (*g4)(void*) = &fflush;
    int (*z1)(const char*, ...) = &printf;
    int (*p0)(int) = &putchar;
    int c = 0;
    int i;
    int dd;
    int sz;
    int h;
    int s;
    int e;
    int pr;
    int* trundle;
    int* vp5;
    int* nqd;
    int oo[10];
    char* p5;
    
    goto cow;

pic:
    g4(y3);
    sz = dd * h;
    hh = mv(sizeof(int) * sz);
    z1(prompt + 18);
    
    goto china;
    
tokyo:
    if (qq_ == qf) {
        goto ten;
    }
    c = hh[qq_++];
    oo[0] = dd;
    oo[1] = h;
    oo[2] = c % dd;
    oo[3] = c / dd;
    oo[4] = 0;
    
    int x = oo[2];
    int y = oo[3];
    int i = x + y * dd;
    
    if (x + 1 < dd && (p5[i + 1] == ' ' || p5[i + 1] == '!')) {
        oo[5 + oo[4]++] = i + 1;
    }
    if (x - 1 > -1 && (p5[i - 1] == _int[66] || p5[i - 1] == _int[67])) {
        goto chew;
    }
    if (y + 1 < h && (p5[i + dd] == _int[67] || p5[i + dd] == _int[66])) {
        oo[5 + oo[4]++] = i + dd;
    }
    if (y - 1 > -1 && (p5[i - dd] == _int[67] || p5[i - dd] == _int[66])) {
        goto chun;
    }
    
    if (c & 1 ? nqd[c] : vp5[c]) {
        goto tokyo;
    }
    
wow:
    if (oo[4]-- <= 0) {
        goto chin;
    }
    i = oo[5 + oo[4]];
    
    if ((i & 1 ? vp5[i] : nqd[i]) == 0) {
        trundle[i] = c;
        usleep(25000);
        g4(y3);
        z1(_int + 34, _int[67]);
        z1(_int + 25, 1 + i / dd, 1 + i % dd);
        p0(88);
        
        if (i == e) {
            goto z;
        }
        
        i & 1 ? (vp5[i] = 1) : (nqd[i] = 1);
        hh[qf++] = i;
    }
    
    goto wow;

chin:
    c & 1 ? (nqd[c] = 1) : (vp5[c] = 1);
    goto tokyo;

z:
    z1(_int + 34, 36);
    z1(_int + 25, 1 + e / dd, 1 + e % dd);
    p0(33);
    
tww:
    if (trundle[i] == s) {
        goto ten;
    }
    i = trundle[i];
    usleep(50000);
    z1(_int + 34, 35);
    z1(_int + 25, 1 + i / dd, 1 + i % dd);
    p0(47);
    g4(y3);
    z1(_int + 34, 33);
    z1(_int + 25, 1 + s / dd, 1 + s % dd);
    p0(95);
    
    goto tww;

ten:
    z1(_int + 25, h + 3, 1);
    g4(y3);
    
    goto pio;

cow:
    int c = 0;
    s = e = -1;
    char** strs = mv(1000);
    
    h = 0;
    for (; h < 1000;) {
        char* s = mv(512);
        if (fgets(s, 512, s0) || feof(s0)) {
            dd = strlen(s) > dd ? strlen(s) : dd;
            strs[h] = s;
            h += 1;
            
            if (feof(s0) && (h-- | 1)) {
                goto tau;
            }
        } else {
            goto ten;
        }
    }
    
tau:
    p5 = mv(dd * h);
    for (int i = 0; i < dd * h; i += 1) {
        p5[i] = ' ';
    }
    
    for (int i = 0; i < h; i++) {
        memcpy(p5 + i * dd, strs[i], strlen(strs[i]));
        
        for (int q = 0; ((d = strs[i][q]) || 1) && q < dd && ((d == '@' ? s = i * dd + q : d == '!' ? e = i * dd + q : d == '\n' ? p5[i * dd + q] = ' ' : 13) || 1); q++);
    }
    
    if (s == -1 || e == -1) {
        goto ten;
    }
    
    goto pic;
    
china:
    for (int r = 0; r < sz; r++) {
        z1(_int + 25, 1 + (r / dd), 1 + (r % dd));
        p0(p5[r]);
    }
    
    trundle = mv(sizeof(int) * sz);
    vp5 = mv(sizeof(int) * sz);
    nqd = mv(sizeof(int) * sz);
    trundle[s] = 0;
    hh[0] = s;
    qf = 1;
    qq_ = 0;
    
    goto tokyo;

pio:
    (_int[0] = '\e') && (_int[1] = '[') && (_int[2] = '0') && (_int[3] = 'm') && (_int[4] = '\n') && (_int[5] = '\0') && z1(_int);
    
    return 0;
}
```