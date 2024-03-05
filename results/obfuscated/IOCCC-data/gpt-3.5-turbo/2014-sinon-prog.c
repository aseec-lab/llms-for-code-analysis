Sure! Here's the deobfuscated code:

```c
#include<stdio.h>

#define G(g, o) int g = o;

#define q(b)              \
  int o[] = {0, 0, 0, 0, 0}; \
  char *p = #b,            \
       P[9999],            \
       *d = P,             \
       *e[] = {"('A`)",    \
               "(A`)",     \
               "('A)",     \
               "('A`)"};   \
  b                      \
  q(*s = __TIME__;        \
    extern void w() { G(g, *o) for (; g--;) {}; }                          \
    extern void x() {};                                                    \
    extern void y() {};                                                    \
    static void z(char *p) {                                               \
      for (; (*d = *p++) != 0; d++)                                        \
        *d -= 061 * (59 == *d || *d == 81);                                 \
    }                                                                       \
    int main() {                                                            \
      char N[] = "<;iQ=/*do<;dQ=ook8?boc;aQ=odr8>oc;_Q<ox8>oc"               \
                 ";^Q<d|8=o;]Q<dj8<Pk8<c]'?;QQdi8?P'Q)d8<9`8?c;Qdh8=P']Q<`c8>PQ9_8" \
                 ">b;og8=''`Q<`b8?'QQ9_8?c;dPe8dQa8<P]Q<``8=;'f8>QQ`]8?ocQQ_8=''_Q`8aQ" \
                 "CYOUQWIN!]Q=;of8BQQ)88bc]QA98P'Qo]8>oQQ_8CP;QQ98b`_8@P8(Q`]'^Q=`']QB)88" \
                 "bQQ]`8J;QQ`88bc99bd(`8iQ@''QQo^8E'`8c;QQ'Q``8?QQ`'kQ<o]8B9PQQ`';_Q<`_8<cbQ" \
                 "<o_Q<o_QBdo`QQ';`Q=9`]8=bobQ_o_Q<o]8=';bQC(88cQ`bcgQ>oP']8<;cQ>98)^Q?''oc`Q>" \
                 "oc']Q]8<;cQ>`8bbQA``QQ''`Q?88P;dQ=`)nQ?*/;;" /*}}=~/./;print"cp\40sinon.c\40r", \
                 "un.c\n";                                              \
      for ($i = 1; $i < 21; $i++) {                                      \
        for ($j = 0; $j < 2; $j++) {                                      \
          print "gcc -O", $j * 2, "run.c -o run && ./run | tee run.c\n" \
            if !($i % (2 + $j));                                          \
        }                                                                  \
        print "sleep $j\n";                                                \
      }                                                                    \
      __DATA__ 8M*/;                                                       \
      G(Z, 60 * (60 * (10 ** s + s[1]) + s[3] * 10 + s[4]) + s[6] * 10 + s[7] - 1933008) \
      G(l, (Z + 86400 - *o) % 86400)                                                \
      G(M, o[3] * 17 + o[4] * 11) G(O, (&x - &w) - (&y - &x) ? 1 : 0) G(m, *o) G(n, m) G(i, 0) G(j, O) \
          if (M > 197)                                                                \
            for (p = N; *p;)                                                           \
              if (*p > 92) {                                                           \
                *N = p[1];                                                             \
                N[1] = 0;                                                              \
                for (O = *p++ - 90; 0 < O--; z(N))                                      \
                  ;                                                                     \
                p++;                                                                   \
              } else {                                                                  \
                s = p;                                                                 \
                for (O = *p++ - 59; O--; p++)                                          \
                  p[-1] = *p;                                                           \
                p[-1] = 0;                                                              \
                z(s);                                                                   \
              }                                                                         \
          else {                                                                      \
            if (M == 0) {                                                            \
              *o = Z;                                                               \
              l = 0;                                                                \
            }                                                                       \
            if (o[O + 3] && o[O + 1] + (O ? 3 : 5) > l)                               \
              Z = O;                                                                 \
            else                                                                     \
              ++o[O + 3];                                                            \
            o[O + 1] = l;                                                            \
            if (l < 60)                                                              \
              d += sprintf(d, "#include<stdio.h>\n#define G(g,o) int g=o;\nq(%s)\n", *o); \
            else if (l == 60)                                                         \
              for (z(";Time:Q"); (l++ < 60); z("#"))                                  \
                ;                                                                     \
              for (z(";Heca" /* */ "t" "eQII:" "Q"); o[3]++ <= 24; z("#"))              \
                ;                                                                     \
              z(Z ? "" : "Q(j" /*@*/ "a" "m" "me" /* */ "d")                           \
              ;                                                                       \
              for (z(";GlockQ18" "C:" /* * */ "Q"); o[4]++ < 25; z("#"))                \
                ;                                                                     \
              z(Z - 1 ? ";" : "Q(jam" "med);")                                        \
              ;                                                                       \
            else                                                                    \
              z(";" /* */ "GameQover." ";;" /* (- */);                                \
            for (O = 0; O < 77; O++)                                                  \
              z("Q");                                                                 \
            z("*/");                                                                   \
          }                                                                           \
      puts(P);                                                                         \
      return +0;                                                                       \
  })
```

This deobfuscated code outputs a string that contains the original obfuscated code.