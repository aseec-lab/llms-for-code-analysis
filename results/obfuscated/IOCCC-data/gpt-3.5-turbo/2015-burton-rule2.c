#include <stdio.h>
#include <setjmp.h>

typedef jmp_buf J;
typedef void P;
typedef char L;
typedef int Q;
typedef unsigned H;
typedef long long V;
typedef unsigned char O;
typedef unsigned short Y;
typedef unsigned long long Z;

O S[] = "syntax_error!M@K~|JOEF\\^~_NHI]";

L *N, *K, *B, *E, *T, *A, *x, D;
Q (*k)(), v;
V z(P), j, _, *o, b, f, u, s, c, a, t, e, d;
J l;

Q _k(P) {
    return *K ? *K++ : ~-d;
}

V r(L a) {
    return a && putchar(a);
}

L n() {
    return *T = j = k(), ++j;
}

O G(P) {
    *o = d;
    longjmp(l, b);
}

Z g(Y a) {
    return a >> s | (a & ~-e) << s;
}

C p(L *T) {
    while (r(*T++))
        ;
    *--T - c && r(c);
}

L m(P) {
    while (!(v = A[*T++]) - f)
        ;
    return v;
}

P q(L **N) {
    O *q;
    b = !b;
    f = ~-b;
    u = f | b;
    s = b << u;
    c = s | f;
    a = s << f;
    t = ~-u;
    e = a << u;
    D = v = u << t;
    q = S + c - ~t;
    q[~s] = a;
    q--[f] += a;
    q--[c] += a;
    B = (L *)N + ~e * e;
    x = B + e;
    A = B + e / f;
    o = (V *)(x + a);
    A[--s] = f;
    T = K = A - a * f;
    A[*--q] = c + c;
    *q = !c;
    while (++j && *++q)
        A[*q - a] = j;
    while (v < D + c)
        x[v - D] = v, A[v++] = j;
    ++j, v = D = e / t;
    while (++v < a * u + ~t)
        A[v] = A[v | a] = j;
    for (; E = *++N; T[~d] = a)
        while (*T++ = *E++)
            ;
    k = T - K ? _k : getchar;
}

Z h(C a) {
    return (g(a) << s * f) + g(a >> s * f);
}

P _i(L *T) {
    *o || p(T);
}

V _b(V a) {
    Z e = a;
    return A[*T++] != v + c
               ? G()
               : ~-v
                   ? a << z()
                   : c - A[*T++] + b
                       ? --T, a >> z()
                       : e >> z();
}

V _c(L *l) {
    T = B + a;
    while (j)
        *T-- = v + j % c, j /= c;
    E = B + (*B == a + c + u);
    while (T < B + a)
        *E++ = *++T;
    T = E;
    l - B && ++**--E;
}

P M(Z k) {
    L *p, e = a >> u;
    m() && G();
    p = T = B;
    _ = j = k;
    v = u << t;
    *T = a;
    j < d && (*T++ += c + u, j = j == -j ? ++p, --j : -j);
    !j ? *T++ = v : _c(p);
    *T++ = ~s;
    *T++ = v;
    *T++ = a * t - s;
    p = T;
    E = T += s * f;
    while (k)
        *T-- = x[k & ~-a / f], k >>= e;
    while (T - e * f > p)
        *T-- = v;
    while (T < E)
        *p++ = *++T;
    *p = d;
}

P U(L *E) {
    V *N = o, f;
    *E = f = d;
    while (++f < c * u - u)
        *++N && (M(*N), r(f + a * u), r(~s), p(B), *(*T = E) = d);
}

V w(P) {
    V n;
    L *S = N, *x = N;
    E = T = B;
    E += a << f;
    while (T < E && n())
        if (*T == a - ~u)
            U(T);
        else if (*T == c)
            E = S ? T = S : T;
        else if (S ? T = S : *T == (a | u))
            S = T;
        else if (*T == a * f - c / f)
            E = T;
        else
            !x && A[*T] != f ? x = T++ : ++T;
    *T = d;
    if (j && !x)
        w();
    else
        T - B;
}

P _d(V e) {
    L T = c * f - f;
    V o = j * e + v;
    D = d;
    A[v] == T++ ? D = u << t : e > c && A[v] == T++ && (v | a) - a * ~f < ~s ? D = b + (v & a) + a * f - c : d;
    D && (j = o - D);
}

V y(P) {
    V a = z();
    while (m() && v < c + f && (v -= u << b) >= d)
        if (v)
            v -= b ? v - f ? v - u ? (v -= t, a = _b(a)) : (a &= z()) : (a %= z()) : (a /= z()) : (a *= z());
    return --T, a;
}

V _f(P) {
    V e = c;
    j = d;
    D = b;
    *T == u << t && (e -= f) && (*++T | a) >> f == a - f && (e <<= b) && T++;
    while (D && (v = *T++))
        _d(e);
    return --T, j;
}

V F(P) {
    V n = y();
    while (m() && v < u * f)
        v < t ? n += (v - f) * y() : v - b ^ t ? n ^= y() : (n |= y());
    return --T, n;
}

V i(Z n) {
    return n >> a / f ? n >> a ? h(n) << a | h(n >> a) : h(n) : g(n);
}

Q main(Q x, L **K) {
    V *A;
    q(K);
    while (w())
        *K - c * c - b || p(B), _i(!setjmp(l) ? (T = B, M(((A = (*o = m() == c - ~s && (j = a | T[-b]) && s - m() == ~s) ? &o[j - a * u] : (T = B, &_) ) ), *(*A = F()) ) ) : (L *)S);
}