```c
#include <stdio.h>
#include <stdlib.h>

#define N 5

typedef union A
{
    union A* (*t) (union A* x);
    union A* (*l) (union A* x, union A* y);
    union A* a;
    long long p;
} A;

A R(A* x)
{
    x[N-1].p++;
    return x;
}

A* C(A* x)
{
    A* r = malloc(sizeof(A[N]));
    if(!r)
    {
        fputs("memory error\n", stderr);
        exit(-2);
    }
    r[0] = *x;
    for(int i = 1; i < N; i++)
        r[i] = (A){0};
    return R(r);
}

void D(A* f)
{
    if(f && --f[N-1].p == 0)
    {
        if(f[0].l != fW && f[0].l != fCh)
            D(f[1].a);
        if(f[0].l != fT)
            D(f[2].a);
        if(f[0].l != rE && f[0].l != rT && f[0].l != rC)
            D(f[3].a);
        free(f);
    }
}

void X(char* f, int r)
{
    fputs(f, stderr);
    exit(r);
}

A* T[256][2] = {
    [46] = {{.l = Ch}, {1}},
    [63] = {{.l = Ch}, {2}},
    [32] = {{.l = Ch}, {}},
    [64] = {{.t = C}, {0}},
    [9] = {{.l = Ch}, {}},
    [99] = {{.t = C}, {7}},
    [10] = {{.l = Ch}, {}},
    [100] = {{.t = C}, {}},
    [101] = {{.t = C}, {4}},
    [35] = {{.t = Ch}, {}},
    [105] = {{.t = C}, {}},
    [107] = {{.t = C}, {1}},
    [11] = {{.l = Ch}, {}},
    [13] = {{.l = Ch}, {}},
    [114] = {{.l = NL}, {}},
    [115] = {{.t = C}, {1}},
    [118] = {{.t = C}, {}},
    [12] = {{.l = Ch}, {}},
    [124] = {{.t = C}, {}}
};

int ch = EOF;
int c;
FILE* p;

void U(void)
{
    do
    {
        if(c == EOF)
            return;
        if(T[c][0].t)
            return;
        if(!T[c][1].p)
        {
            fputs("syntax error\n", stderr);
            exit(1);
        }
        if(T[c][1].p == 4)
        {
            do
                c = getc(p);
            while(c != '\n' && c != EOF);
        }
        c = getc(p);
    } while(1);
}

A* I(void)
{
    if(c == EOF)
    {
        fputs("syntax error\n", stderr);
        exit(1);
    }
    A* f = T[c];
    if(f[0].t != Ch)
    {
        c = getc(p);
        U();
    }
    return f[0].t(&f[1]);
}

A* Ch(void)
{
    A* r = C(&(A){.l = c});
    c = getc(p);
    U();
    return r;
}

A* NL(void)
{
    A* r = C(&(A){.l = '\n'});
    return r;
}

A* fW(A* x, A* y)
{
    putc(x[1].p, stdout);
    return y;
}

A* fR(A* x, A* y)
{
    ch = getc(stdin);
    A* r = C(&(A){.l = ch != EOF ? fI : fV});
    return u(y)(y, r);
}

A* rC(A* x, A* y)
{
    return y;
}

A* fC(A* x, A* y)
{
    A* r = C(&(A){.l = ch == x[1].p ? fI : fV});
    return u(y)(y, r);
}

A* fP(A* x, A* y)
{
    A* r = C(&(A){.l = ch != EOF ? fW : fV});
    if(ch != EOF)
        r[1].p = ch;
    return u(y)(y, r);
}

A* fE(A* x, A* y)
{
    if(y[3].a == y)
        return u(y)(y, x);
    A* r = C(&(A){});
    return u(r)(x, u(r)(r, y));
}

A* rE(A* x, A* y)
{
    if(y[3].a)
    {
        if(u(y) == rT)
            return y;
        if(u(y) == rC)
        {
            return u(I())(I(), y);
        }
        A* r = C(&(A){.l = rY2});
        r[3].a = y;
        return r;
    }
    if(u(y) != fD0)
        return u(x)(x, y);
    D(y);
    T[96][0].t = Y;
    return g(fD)I();
}

A* fD(A* x, A* y)
{
    A* a = fA(x[1].a);
    return u(x)(x, y);
}

A* fA2(A* x, A* y)
{
    A* a = R(x[1].a);
    A* b = R(x[3].a);
    return fA1(a, u(b)(b, y));
}

A* fA1(A* x, A* y)
{
    if(y[3].a)
    {
        if(u(y) == rT)
            return y;
        if(u(y) == rC)
        {
            k(I(), x);
        }
        A* r = C(&(A){.l = rA2});
        r[3].a = x;
        return r;
    }
    if(u(y) == fD0)
    {
        return g(fD)R(x[2].a);
    }
    A* r = fA(x[2].a);
    return k(x, fA1(y, r));
}

A* fA(A* x)
{
    R(x);
    if(x[0].l != fA)
        return x;
    return rA1(x, fA(x[1].a));
}

A* fD1(A* x, A* y)
{
    return u(x)(x, y);
}

A* fD(A* x, A* y)
{
    A* a = fA(x[1].a);
    return fD1(a, y);
}

A* rD(A* x, A* y)
{
    A* a = u(x)(x, y);
    return fD1(a, y);
}

A* Y2(A* x, A* y)
{
    A* a = R(x[1].a);
    A* b = R(x[3].a);
    return Y1(a, u(b)(b, y));
}

A* Y1(A* x, A* y)
{
    if(y[3].a)
    {
        if(u(y) == rT)
            return y;
        if(u(y) == rC)
        {
            k(I(), y);
        }
        A* r = C(&(A){.l = rY2});
        r[3].a = y;
        return r;
    }
    return Y2(x, u(y)(y, x));
}

A* Y(A* x)
{
    return rY1(I());
}

A* rY2(A* x, A* y)
{
    A* a = R(x[3].a);
    return rY1(u(a)(a, y));
}

A* rY1(A* x, A* y)
{
    if(x[3].a)
    {
        if(u(x) == rT)
            return x;
        if(u(x) == rC)
        {
            k(I(), x);
        }
        A* r = C(&(A){.l = rY2});
        r[3].a = x;
        return r;
    }
    if(u(x) != fD0)
    {
        return Y1(x, I());
    }
    D(x);
    T[96][0].t = YT;
    return g(fD)I();
}

A* rE2(A* x, A* y)
{
    return u(rE)(rE(x, y), I());
}

A* rE(A* x, A* y)
{
    A* a = R(x[1].a);
    return u(a)(a, I());
}

A* fK1(A* x, A* y)
{
    return u(y)(y);
}

A* fK0(A* x, A* y)
{
    return g(fK1)(y);
}

A* fS22(A* x, A* y)
{
    return u(x)(x, y);
}

A* fS21(A* x, A* y)
{
    A* a = R(x[1].a);
    return fS22(a, u(y)(y, x));
}

A* fS2(A* x, A* y)
{
    A* a = R(x[1].a);
    return rS1(x, u(a)(a, y));
}

A* rS2(A* x, A* y)
{
    A* a = R(x[3].a);
    A* b = R(x[2].a);
    A* f = R(x[1].a);
    return rS1(f, u(a)(a, y), b);
}

A* rS1(A* x, A* y, A* z)
{
    if(y[3].a)
    {
        if(u(y) == rT)
        {
            if(z)
                D(z);
            return y;
        }
        if(u(y) == rC)
        {
            k(I(), y);
        }
        A* r = C(&(A){.l = rS2});
        r[2].a = z;
        return r;
    }
    if(u(y) != fD0)
    {
        return rS1(x, y, I());
    }
    j(r, x, 2);
    return k(x, rS21(y, u(r)(r, z)));
}

A* fS1(A* x, A* y)
{
    A* f = R(x[1].a);
    return rS1(f, R(x[2].a), y);
}

A* fS0(A* x, A* y)
{
    return g(fS1)(y);
}

A* fI(A* x, A* y)
{
    return k(x, y);
}

A* fV(A* x, A* y)
{
    return k(y, x);
}

A* fT(A* x, A* y)
{
    A* f = R(x[1].a);
    A* y = R(x[2].a);
    A* r = R(x[3].a);
    if(fseek(p, x[2].p - 1, SEEK_SET))
    {
        fputs("fail\n", stderr);
        exit(-1);
    }
    c = getc(p);
    U();
    return k(x, r);
}

A* rT(A* x, A* y)
{
    (void)x;
    return y;
}

int main(int argc, const char** argv)
{
    if(argc != 2)
    {
        fputs("fail\n", stderr);
        exit(-1);
    }
    
    p = fopen(argv[1], "rb");
    if(!p)
    {
        fputs("fail\n", stderr);
        exit(-1);
    }
    
    c = getc(p);
    U();
    
    while(c != EOF)
    {
        A* f = I();
        
        while(f[3].a)
        {
            if(u(f) == rT)
            {
                f = u(f)(R(f[1].a), f);
            }
            else if(u(f) == rC)
            {
                T[96][0].t = Y;
                j(r, f, 1);
                D(f);
                f = r;
            }
            else
            {
                g(fT) f = u(f)(R(f[1].a), f);
            }
        }
        
        D(f);
    }
    
    return 0;
}
```