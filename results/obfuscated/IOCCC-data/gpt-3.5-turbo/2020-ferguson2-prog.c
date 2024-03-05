Here's the deobfuscated code:

#include <stdio.h>
#include <ctype.h>
#include <string.h>
#include <stdlib.h>

#define ALPHABET_SIZE 26
#define ARRAY_SIZE(arr) sizeof(arr)/sizeof(arr[0])

char *M, *R[3], Y[20][27] = {
    "EKMFLGDQVZNTOWYHXUSPAIBRCJ",
    "AJDKSIRUXBLHWTMCQGZNPYFVOE",
    "BDFHJLCPRTXVZNYEIWGAKMUSQO",
    "ESOVPZJAYQUIRHXLNFTGKDCMWB",
    "VZBRGITYUPSDNHLXAWMJQOFECK",
    "YRUHQSLDPXNGOKMIEBFZCWVJAT",
    "ABBBCCQEVJZ",
    "ABCDEFGHIJKLMNOPQRSTUVWXYZ",
    "Mjqq",
    "Lcxvago",
    "Huwfnbgt",
    "Hdoqhmket fbvy",
    "Mcromlhxo"
};
char *S, *r;
int *z = (int[13]){0}, *e, *g, *P;

char Z(char x) {
    for (*g = 0; *g < 10; ++*g) {
        r[-2] = Y[14][*(e+1)];
        r[-3] = Y[15][1+*e];
        if (r[-3] && r[-2] == r[-1])
            return r[-3];
        if (r[-2] && r[-3] == r[-1])
            return r[-2];
    }
    return x;
}

void w(char *s, int k, int v) {
    for (e[2] = 0; s[*P]; ++e[2]) {
        v = s[e[2]];
        if (v > 64 && v < 91)
            s[e[2]] = (((v - 65 + k) % ALPHABET_SIZE) + 65);
    }
}

void Q(void) {
    for (*g = 0; *g < 3; ++*g) {
        Y[16+e[1]] = S[e[e[1]-3]];
        R[e[1]] = Y[e[e[1]-3]+1];
        w(R[*g], (e[e[1]-6] = (strstr(Y[8], *Y[7]) - Y[8])), *g);
    }
}

void m(char *x) {
    char *p = x, a = Y[7][1], b = Y[7][3], c = Y[7][5];
    int f, E;

    for (; *p; ++p) {
        int u = islower(*p);
        if (isalpha(toupper(*p))) {
            char k = *p, v;
            f = 0;
            c == Y[18] && (f = 1);
            c = (c + 1) % ALPHABET_SIZE;
            if (f) {
                f = 0;
                b == Y[17] && (f = 1);
                b = (b + 1) % ALPHABET_SIZE;
                f && (f = 0 && (a = (a + 1) % ALPHABET_SIZE));
            } else if (b == Y[17]) {
                b = (b + 1) % ALPHABET_SIZE;
                a = (a + 1) % ALPHABET_SIZE;
            }
            k = Z(r[-1] = k);
            e[-6] = (strstr(Y[8], a) - Y[8]);
            e[-5] = (strstr(Y[8], b) - Y[8]);
            e[-4] = (strstr(Y[8], c) - Y[8]);
            V(k);
            d(R[2], e[-4]);
            V(v);
            O(e[-4]);
            V(k);
            d(R[1], e[-5]);
            V(v);
            O(e[-5]);
            V(k);
            d(*R, e[-6]);
            V(v);
            O(e[-6]);
            (Y[e[-10]+5][k-'A']) && (k = Y[e[-10]+5][k-'A']);
            V(k);
            d(Y[8], e[-6]);
            E = (strstr(*R, v) - R[0]);
            O(e[-6]);
            V(k);
            d(Y[8], e[-5]);
            E = (strstr(R[1], v) - R[1]);
            O(e[-5]);
            V(k);
            d(Y[8], e[-4]);
            E = (strstr(R[2], v) - R[2]);
            O(e[-4]);
            k = Z(r[-1] = k);
            *p = u ? tolower(k) : k;
        }
    }
}

void E(char *x, int s, int i) {
    if (isalpha(s))
        x[i] = s;
}

int p(char x) {
    return (strstr(Y[14], x) || strstr(Y[15], x));
}

int main(int argc, char **argv) {
    int j;
    e = z + 10;
    S = Y[7] + 6;
    r = S + 10;
    g = e + 1;
    P = g + 1;

    for (j = -3; j < 0; ++j)
        e[j] = j + 3;

    Q();

    for (j += 9; j < 14; ++j)
        m(Y[j]);

    if (!(argv[1] && *argv[1] == '-'))
        goto k;

    for (int i = 0; i < 3; ++i) {
        printf("%s %d: ", Y[9], i+1);
        char ch;
        scanf("%c", &ch);
        getchar();
        int j = ch - '0';
        e[i-3] = (j < 1 ? 1 : (j > 5 ? 5 : j)) - 1;

        printf("%s %d: ", Y[10], i+1);
        scanf("%c", &ch);
        getchar();
        E(Y[7], ch, i+(i%3));

        printf("%s %d: ", Y[11], i+1);
        scanf("%c", &ch);
        getchar();
        E(Y[7], ch, i+(i+1));
    }

    printf("%s: ", Y[13]);
    char ch;
    scanf("%c", &ch);
    getchar();
    e[-10] = (ch < 1 ? 1 : (ch > 1 ? 2 : ch)) - 1;

k:
    Q();

    if (getdelim(&M, &L, EOF, stdin) > 0) {
        m(M);
        printf("%s", M);
        free(M);
    }

    return 0;
}