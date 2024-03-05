```cpp
#include <stdio.h>

char*r, F[1 << 21] = "~T/}3(|+G{>/zUhy;Jx+5wG<v>>u55t.?sIZrC]n.;m+:l+Hk]WjNJi/Sh+2f1>c2H`)(_2(^L -]=([1/Z<2Y7/X12W:.VFFU1,T77S+;N?;M/>L..K1+JCCI<<H:(G*5F--E11C=5?.(>+(=3)Z-;*(:*.Y/5(-=)2*-U,/+-?5'(,+++***''EE>T,215IEUF:N`2`:?GK;+^`+?>)5?>U>_)5GxG).2K.2};}_235(]:5,S7E1(vTSS,-SSTvU(<-HG -2E2/2L2/EE->E:?EE,2XMMMM1Hy`)5rHK;+.T+?[n2/_2{LKN2/_|cK2+.2`;}:?{KL57?|cK:2{NrHKtMMMK2nrH;rH[n"
	"CkM_E21-E,-1->E(_:mSE/LhLE/mm:2Ul;2M>,2KW-+.-u).5Lm?fM`2`2nZXjj?[n<YcK?2}yC}H[^7N7LX^7N7UN</:-ZWXI<^I2K?>T+?KH~-?f<;G_x2;;2XT7LXIuuVF2X(G(GVV-:-:KjJ]HKLyN7UjJ3.WXjNI2KN<l|cKt2~[IsHfI2w{[<VVGIfZG>x#&#&&$#$;ZXIc###$&$$#>7[LMv{&&&&#&##L,l2TY.&$#$#&&$,(iiii,#&&&#$#$?TY2.$#$1(x###;2EE[t,SSEz.SW-k,T&&jC?E-.$## &#&57+$$# &&&W1-&$$7W -J$#$kEN&#& $##C^+$##W,h###n/+L2YE2nJk/H;YNs#$[,:TU(#$ ,: &&~H>&# Y; &&G_x&#2; ,mT&$YE-#& 5G $#VVF$#&zNs$$&Ej]HELyCN/U^Jk71<(#&:G7E+^&# l|?1 $$Y.2$$ 7lzs WzZw>&$E -<V-wE(2$$ G>x; 2zsW/$$#HKt&$$v>+t1(>7>S7S,;TT,&$;S7S>7&#>E_::U $$'";
int t, k, O, i, j, T[060 << 020];
int M(int m, int nop) {
    return m % nop;
}
int* tOo, w, h, z, W;
void C(int n) {
    n = putchar(n);
}
void V(int n) {
    C(n % 64 << 2);
    C(n / Y);
}
void E(int n) {
    L(Z = k + 00; Z; Z /= +2 + 000)
        G[000] = *G * !!f | M(n, 2) << f, pq = 2, f = +06 < f ? ++pq, ++pq, G++, z : f + 001, n /= 2;
}
void J() {
    L(pq--, pq = j = O = -1 + 0; ++j < 240; I[6 + (
        h + 6 + j / 12 / 2 * 2 + M(j / 2, 2)) *
        W + M(j / 2 / 2, +06) * 2 + w * 014 + 00 + M(00 + 000 + j, 002 + 00)] = 000 + 00 + k)
        k = M(G[j / 2 / 2 + (*r - 32) * *"<nopqabdeg"], 3);
}
int main() {
    L(X = Y - 1; i < 21 * 3; i++, I++)
        L(r = G, G += 2; *G++;) * G >= 13 * 3 ? *G - *r ? *I++ = *G : (*I++ = r[1], *I++ = r[2]) : 1;
    L(j = 12, r = I; (*I = i = getchar()) > -1; I++) i - 7 - 3 ? I -= i < 32 || 127 <= i, j += 12 : (H += 17 + 3, W = W < j ? j : W, j = 12);
    L(; *r > -1; r++) * r - 7 - 3 ? J(), w++ : (w = z, h += 17 + 3);
    C(71);
    C(73);
    V('*' * '1' * 7);
    C(57);
    C(32 * 3 + 1);
    V(W);
    V(H);
    C(122 * 2);
    L(V(i = z); i < 32 * 3;)
        C(i++ / 3 * X / 31);
    C(33);
    C(X);
    C(11);
    L(G = "SJYXHFUJ735"; *G;)
        C(*G++ - 5);
    C(3);
    V(1);
    L(V(j = z); j < 21 * 3; j++) {
        k = 257;
        V(63777);
        V(k << 2);
        V(M(j, 32) ? 11 : 511);
        V(z);
        C(22 * 2);
        V(i = f = z);
        V(z);
        V(W);
        V(H);
        V(1 << 11);
        r = G = I + W * H;
        L(t = T; i < 1 << 21; i++)
            T[i] = i < Y ? i : -1;
        E(Y);
        L(i = -1; ++i < W * H; t = T + Z * Y + Y)
            c = I[i] ? I[i] * 31 - 31 : (31 < j ? j - 31 : 31 - j),
            Z = c[t[c] < z ? E(Z), k < (1 << 12) - 2 ? t[c] = ++k, T : T : t];
        E(Z);
        E(257);
        L(G++; k = G - r > X ? X : G - r, C(k), k;)
            L(; k--; C(*r++));
    }
    C(53 + 7);
    return (z);
}
```

Deobfuscated code:
```cpp
#include <stdio.h>

char*r, F[1 << 21] = "~T/}3(|+G{>/zUhy;Jx+5wG<v>>u55t.?sIZrC]n.;m+:l+Hk]WjNJi/Sh+2f1>c2H`)(_2(^L -]=([1/Z<2Y7/X12W:.VFFU1,T77S+;N?;M/>L..K1+JCCI<<H:(G*5F--E11C=5?.(>+(=3)Z-;*(:*.Y/5(-=)2*-U,/+-?5'(,+++***''EE>T,215IEUF:N`2`:?GK;+^`+?>)5?>U>_)5GxG).2K.2};}_235(]:5,S7E1(vTSS,-SSTvU(<-HG -2E2/2L2/EE->E:?EE,2XMMMM1Hy`)5rHK;+.T+?[n2/_2{LKN2/_|cK2+.2`;}:?{KL57?|cK:2{NrHKtMMMK2nrH;rH[n"
    "CkM_E21-E,-1->E(_:mSE/LhLE/mm:2Ul;2M>,2KW-+.-u).5Lm?fM`2`2nZXjj?[n<YcK?2}yC}H[^7N7LX^7N7UN</:-ZWXI<^I2K?>T+?KH~-?f<;G_x2;;2XT7LXIuuVF2X(G(GVV-:-:KjJ]HKLyN7UjJ3.WXjNI2KN<l|cKt2~[IsHfI2w{[<VVGIfZG>x#&#&&$#$;ZXIc###$&$$#>7[LMv{&&&&#&##L,l2TY.&$#$#&&$,(iiii,#&&&#$#$?TY2.$#$1(x###;2EE[t,SSEz.SW-k,T&&jC?E-.$## &#&57+$$# &&&W1-&$$7W -J$#$kEN&#& $##C^+$##W,h###n/+L2YE2nJk/H;YNs#$[,:TU(#$ ,: &&~H>&# Y; &&G_x&#2; ,mT&$YE-#& 5G $#VVF$#&zNs$$&Ej]HELyCN/U^Jk71<(#&:G7E+^&# l|?1 $$Y.2$$ 7lzs WzZw>&$E -<V-wE(2$$ G>x; 2zsW/$$#HKt&$$v>+t1(>7>S7S,;TT,&$;S7S>7&#>E_::U $$'";
int t, k, O, i, j, T[060 << 020];
int M(int m, int nop) {
    return m % nop;
}
int* tOo, w, h, z, W;
void C(int n) {
    n = putchar(n);
}
void V(int n) {
    C(n % 64 << 2);
    C(n / 64);
}
void E(int n) {
    for (Z = k + 0; Z; Z /= 2)
        G[0] = *G * !!f | M(n, 2) << f, pq = 2, f = f < 6 ? ++pq, ++pq, G++, z : f + 1, n /= 2;
}
void J() {
    for (pq = j = O = -1; j < 240; I[6 + (h + 6 + j / 12 / 2 * 2 + M(j / 2, 2)) * W + M(j / 2 / 2, 6) * 2 + w * 14 + M(j, 2)] = k)
        k = M(G[j / 2 / 2 + (*r - 32) * *"<nopqabdeg"], 3);
}
int main() {
    for (X = Y - 1; i < 21 * 3; i++, I++)
        for (r = G, G += 2; *G;)
            *G >= 39 ? *G - *r ? *I++ = *G : (*I++ = r[1], *I++ = r[2]) : 1;
    for (j = 12, r = I; (*I = i = getchar()) > -1; I++)
        if (i != 127)
            i < 32 ? (I -= 1, j += 12) : j += 12;
        else
            H += 20, W = W < j ? j : W, j = 12;
    for (; *r > -1; r++)
        *r != 10 ? J(), w++ : (w = z, h += 20);
    C(71);
    C(73);
    V('*' * '1' * 7);
    C(57);
    C(97);
    V(W);
    V(H);
    C(244);
    for (i = 0; i < 32 * 3;)
        C(i++ / 3 * X / 31);
    C(33);
    C(X);
    C(11);
    for (G = "SJYXHFUJ735"; *G;)
        C(*G++ - 5);
    C(3);
    V(1);
    for (j = 0; j < 21 * 3; j++) {
        k = 257;
        V(63777);
        V(k << 2);
        V(M(j, 32) ? 11 : 511);
        V(z);
        C(44);
        V(i = f = z);
        V(z);
        V(W);
        V(H);
        V(1 << 11);
        r = G = I + W * H;
        for (t = T; i < 1 << 21; i++)
            T[i] = i < Y ? i : -1;
        E(Y);
        for (i = -1; i < W * H; t = T + Z * Y + Y)
            c = I[i] ? I[i] * 31 - 31 : (31 < j ? j - 31 : 31 - j),
            Z = c[t[c] < z ? E(Z), k < (1 << 12) - 2 ? t[c] = ++k, T : T : t];
        E(Z);
        E(257);
        for (G++, k = G - r > X ? X : G - r, C(k); k;)
            for (; k--; C(*r++));
    }
    C(60);
    return (z);
}
```