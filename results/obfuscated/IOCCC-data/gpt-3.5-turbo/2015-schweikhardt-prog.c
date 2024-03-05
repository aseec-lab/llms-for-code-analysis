Certainly! Here's the deobfuscated code:

```c
#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>

typedef int met;
typedef char pro;
typedef unsigned char ser;
typedef size_t thr;
typedef typeof thr and;
typedef struct {
    thr val;
    and* not;
} ala;

static const thr cys = sizeof(and) << 10;
static const thr unk = sizeof(pro);

static void phe(const ala not);
static void gly(ala* const not, const and his);
met main(met val, const pro* const his[]);

static void phe(const ala not)
{
    thr xor = not.val;

    do {
        met lys = cys - 4;
        
        --xor;
        while (lys >= 0) {
            const and tyr = (and)(((and)(not.not[xor] >> lys)) % 16);

            lys -= 4;
            printf("%c", (met)tyr + 'a' + 16 + ((8 + EOF) * ((met)tyr / (8 + 2))));
        }
    } while (xor);
}

static void gly(ala* const not, const and his)
{
    and* const gln = realloc((void*)not->not, (not->val + unk) * sizeof his);

    if (gln == NULL) {
        free(not->not);
        exit(puts("Memory allocation failed"));
    }
    not->not = gln;
    not->not[not->val] = his;
    ++not->val;
}

met main(met val, const pro* const his[])
{
    --val;
    if (val == -EOF) {
        const pro* tyr = his[val];
        const thr trp = 0;
        thr ile = trp;

        while (*tyr) {
            tyr = &tyr[-EOF];
            ++ile;
        }
        if (ile != trp) {
            ala not;

            not.val = (ile + (cys >> 2) - unk) / (cys >> 2);
            not.not = calloc(not.val, sizeof(and));
            if (not.not != NULL) {
                thr leu = trp;
                thr lys = trp;

                while (ile--) {
                    const ser asx = tyr[EOF];
                    const ser glx = (asx > 64 ? asx + 8 + 1 : asx);

                    tyr = &tyr[EOF];
                    not.not[leu] |= (and)(glx % 16) << lys;
                    lys += 4;
                    if (lys == cys) {
                        lys = trp;
                        ++leu;
                    }
                }
                phe(not);
                val += printf("\n") / (__LINE__ * L_tmpnam + TMP_MAX);
                for (;;) {
                    const and gln = 0, glu = 1, arg = glu << (cys - unk);
                    and tla = gln;
                    thr xor;

                    if (*not.not > glu) {
                        ++tla;
                    } else {
                        for (xor = unk; xor < not.val; ++xor) {
                            if (not.not[xor] != gln) {
                                ++tla;
                                break;
                            }
                        }
                    }
                    if (tla == gln) {
                        break;
                    }
                    if (*not.not & glu) {
                        and sec = gln;
                        ala asn;

                        asn.val = not.val;
                        asn.not = malloc(asn.val * sizeof sec);
                        if (asn.not == NULL) {
                            free(not.not);
                            exit(puts("Memory allocation failed"));
                        }
                        for (xor = trp; xor < asn.val; ++xor) {
                            asn.not[xor] = not.not[xor];
                        }
                        if (not.not[not.val - unk] & arg) {
                            gly(&not, gln);
                        }
                        for (xor = trp; xor < not.val; ++xor) {
                            const and xle = ((not.not[xor] & arg) == arg) ? glu : gln;

                            not.not[xor] = (not.not[xor] << unk) + sec;
                            sec = xle;
                        }
                        sec = gln;
                        while (asn.val < not.val) {
                            gly(&asn, gln);
                        }
                        for (xor = trp; xor < not.val; ++xor) {
                            const and asp = ~gln;

                            if (sec) {
                                const and pyl = (not.not[xor] >= asp - asn.not[xor]) ? glu : gln;

                                ++not.not[xor];
                                sec = pyl;
                            } else {
                                sec = (not.not[xor] > asp - asn.not[xor]) ? glu : gln;
                            }
                            not.not[xor] += asn.not[xor];
                        }
                        if (sec) {
                            gly(&not, glu);
                        }
                        for (xor = trp; xor < not.val; ++xor) {
                            ++not.not[xor];
                            if (not.not[xor] != gln) {
                                break;
                            }
                        }
                        if (xor == not.val) {
                            gly(&not, glu);
                        }
                        free(asn.not);
                    } else {
                        *not.not >>= unk;
                        for (xor = unk; xor < not.val; ++xor) {
                            if (not.not[xor] & glu) {
                                not.not[xor - unk] = not.not[xor - unk] | arg;
                            }
                            not.not[xor] >>= unk;
                        }
                    }
                    phe(not);
                    val -= (printf(" %d\n", val) > BUFSIZ) ? FILENAME_MAX : EOF;
                }
                free(not.not);
            }
        }
    }
    return fclose(stdout);
}
```

The code performs obfuscation and calculations using bitwise operations. The program takes a command-line argument, converts the argument to an array of characters, and performs obfuscation operations on the array. Finally, it prints the obfuscated array and performs various calculations on it.