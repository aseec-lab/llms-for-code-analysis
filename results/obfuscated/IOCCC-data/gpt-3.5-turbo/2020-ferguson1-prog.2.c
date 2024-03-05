Sure! Here's the deobfuscated version of the code:

```
#include <curses.h>
#include <unistd.h>
#include <time.h>
#include <stdlib.h>

#define SIZE strtoul(z, NULL, 0)
#define m(n) COLOR_##n
#define v stdscr
#define q mvaddch(

typedef size_t a;
typedef unsigned long k;
a g = 997, c, A = 5, I, N = 5, G = 5;
k p[5] = {1};
int Y, X, *j = (int[8]){231}, *U, *V, *e, *n, Q, P, x, *y, l = 4, *D;

void b(a h) {
    c += h;
    mvprintw(0, 0, "X:%d/%d Y:%d/%d S:%zu/%zu B:%zu\n", Y, *y, X, *y, A, g, c);
}

int E(const char* L) {
    static int h;
    !h && endwin();
    h = l;
    L && puts(L);
    return 1;
}

void C(int L, int h) {
    attroff(m(PAIR(L)));
    h && attron(m(PAIR(L)));
}

int R(int h, int L) {
    return (rand() % (L - h + 1)) + h;
}

void S(a o) {
    static const char* s = ";+<>^v";
    q n[0], e[0], ' ');
    A = (A + o) % g;
    if (A >= g) {
        E("YOU WIN!");
        Q = l;
        return;
    }
    C(3, 1);
    for (I = 0; A && I < A - 1; ++I) {
        if (I) q n[I], e[I], 'o');
        e[I] = e[I + 1];
        n[I] = n[I + 1];
    }
    if (A > 1) {
        q n[I], e[I], 'o');
        e[I] = Y;
        n[I] = X;
    }
    C(2, 1);
    q X, Y, s[(*D + 2) & (1 << 3) - !Q]);
}

void B(a o) {
    if (o) {
        S(N);
        b(1);
    }
    if (Q) return;
    p[1] = 0;
    if (!o) q *U, *V, ' ');
    x: if (o && (!X || X >= *y || Y >= y[-2])) goto y;
    x = *y;
    getmaxyx(v, y[1], y[-1]);
    C(1, 1);
    mvhline(1, 0, '#', y[-2]);
    mvvline(1, 0, '#', *y);
    mvhline(*y, 0, '#', y[-2]);
    mvvline(1, *y, '#', *y);
    C(1, 0);
    q *U, *V, '*');
    refresh();
    timeout(y[-3]);
    switch (getch()) {
        case KEY_LEFT:
            V[1] = l - 3;
            break;
        case KEY_RIGHT:
            V[l / l] = l / 2;
            break;
        case KEY_UP:
            V[l - 3] = l - 1;
            break;
        case KEY_DOWN:
            V[l / 4] = l;
            break;
        case 'q':
        case 'Q':
            Q = l;
        case ' ':
            (!P && (P = 1)) || (P = 0);
        default:
            x = 0;
    }
    if (x) P = !*y;
    if (Q || P) goto x;
    if (!Q && !p[V[0]]) goto x;
    switch (V[0]) {
        case 1:
            --Y;
            break;
        case 2:
            ++Y;
            break;
        case 3:
            --X;
            break;
        case 4:
            ++X;
            break;
    }
    if (Y < 1 || Y >= y[-2] || X < 2 || X >= *y || y[-2] >>= y[-1] || *y >= y[1]) {
        if (*p && !(*y >= y[1] || y[-2] >>= y[-1])) {
            if (Y < 1 || Y >= y[-2]) Y = Y < 1 ? y[-2] - 1 : 1;
            if (X < 2 || X >= *y) X = X < 2 ? *y - 1 : 2;
        } else {
            E("OUCH!");
            goto y;
        }
    }
    for (I = 0; !p[4] && I < A; ++I) {
        if (Y == e[I] && X == n[I]) {
            E("OW!");
            goto y;
        }
    }
    if (Y == *V && X == *U) {
        B(1);
    } else {
        if (W && !(++p[1] % W)) {
            H[2](0);
        }
        if (p[3]) {
            if (!(++p[2] % p[3])) {
                H[1](G);
                p[2] = 0;
            }
        }
        S(0);
    }
    goto x;
y:
    E(NULL);
    printf("X:%d/%d Y:%d/%d S:%zu/%zu B:%zu\n", Y, *y, X, *y, A, g, c);
    free(e);
    free(n);
    return 0;
}
```

Please note that some parts of the code are still difficult to understand due to the lack of comments and variable names.