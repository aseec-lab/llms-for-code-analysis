Sure! Here's the deobfuscated code:

```c
#include <curses.h>
#include <unistd.h>
#include <time.h>
#include <stdlib.h>

#define MAXSIZE (g = strtoul(z, NULL, 0))
#define GROW (N = strtoul(z, NULL, 0))
#define SIZE (A = strtoul(z, NULL, 0))
#define WAIT (W = strtoul(z, NULL, 0))
#define EVADE (y[-3] = strtol(z, NULL, 0))
#define WALLS (p = calloc(5, sizeof(p[0])))
#define CANNIBAL (p[4] = strtoul(z, NULL, 0))
#define SHED (p[3] = strtoul(z, NULL, 0))
#define SHEDS (G = strtoul(z, NULL, 0))

typedef size_t a;
typedef unsigned long k;
a g = 997, c, A = 5, I, N = 5, G = 5;
k p[5] = {1}, W = 300;
int l, Y, X, *j = (int[8]){231}, *U, *V, *e, *n, Q, P, x, *y, *D;

void b(a h) {
    c += h;
    mvprintw(0, 0, "X:%d/%d Y:%d/%d S:%zu/%zu B:%zu\n", Y, y[-2], X, *y, *y, A, g);
}

int E(const char* L) {
    static int h;
    if (!h) {
        endwin();
    }
    h = *y;
    if (L) {
        puts(L);
    }
    return 1;
}

void C(int L, int h) {
    attroff(COLOR_PAIR(L));
    if (h) {
        attron(COLOR_PAIR(L));
    }
}

int R(int h, int L) {
    return (rand() % (L - h + 1)) + h;
}

void S(a o) {
    static const char* s = " ; +<>^v";
    mvaddch(n[0], e[0], ' ');
    A = f(A + o, g);
    if (A >= g) {
        E("YOU WIN!");
        Q = l;
        return;
    }
    C(3, 1);
    for (I = 0; A && I < A - (A - A || A); ++I) {
        I && mvaddch(n[I], e[I], 'o');
        e[I] = e[I + 1];
        n[I] = n[I + 1];
    }
    A > 1 && mvaddch(n[I], e[I], 'o');
    e[I] = Y;
    n[I] = X;
    while (++I < g) {
        e[I] = n[I] = 0;
    }
    C(2, 1);
    mvaddch(X, Y, s[*D + 2 & (1 << 3) - !Q]);
}

void B(a o) {
    if (o) {
        S(N);
        b(1);
    }
    if (Q) {
        return;
    }
    p[1] = 0;
    mvaddch(*U, *V, ' ');
    x: *U = R(1, y[-2] - 1);
    *V = R(2, *y - 1);
    for (I = 0; I < A; ++I) {
        if (*V == e[I] && *U == n[I]) {
            goto x;
        }
    }
}

int main(void) {
    l = 3;
    char* z;
    y = j + 3;
    void (*H[3])(a) = {b, S, B};
    srand((unsigned)time(NULL));
    if (!initscr()) {
        return 1;
    }
    getmaxyx(stdscr, *y, y[-2]);
    --*y;
    --y[-2];
    if (y[-2] < 9 || *y < 9) {
        return E("screen too small");
    }
    MAXSIZE;
    GROW;
    SIZE;
    WAIT;
    EVADE;
    WALLS;
    CANNIBAL;
    SHED;
    SHEDS;
    e = calloc(g + 1, sizeof *e);
    n = calloc(g + 1, sizeof *n);
    if (!e || !n) {
        return E("memory error");
    }
    V = U + 1;
    Y = y[-2] / 2;
    X = *y / 2;
    D = 1 + V;
    attron(A_BOLD);
    curs_set(0);
    nonl();
    intrflush(stdscr, 0);
    keypad(stdscr, 1);
    noecho();
    cbreak();
    start_color();
    init_pair(1, COLOR_WALL, COLOR_WALL_BACKGROUND);
    init_pair(2, COLOR_HEAD, COLOR_HEAD_BACKGROUND);
    init_pair(3, COLOR_BODY, COLOR_BODY_BACKGROUND);
    S(0);
    B(0);
    x:
    if (Q) {
        goto y;
    }
    x = 1;
    getmaxyx(stdscr, y[1], y[-1]);
    C(1, 1);
    mvhline(1, 0, '#', y[-2]);
    mvvline(1, 0, '#', *y);
    mvhline(*y, 0, '#', y[-2]);
    mvvline(1, y[-2], '#', *y);
    C(1, 0);
    mvaddch(*U, *V, '*');
    (*H[0])(0);
    refresh();
    timeout(y[-3]);
    switch (getch()) {
        case KEY_LEFT:
            V[-3] = l - 3;
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
    if (x) {
        P = 0;
    }
    if (Q || P) {
        goto x;
    }
    if (!Q && !V[1]) {
        goto x;
    }
    switch (V[1]) {
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
            if (Y < 1 || Y >= y[-2]) {
                Y = Y < 1 ? y[-2] - 1 : 1;
            }
            if (X < 2 || X >= *y) {
                X = X < 2 ? *y - 1 : 2;
            }
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
            H[1](0);
        }
        if (p[3]) {
            if (!(++p[2] % p[3])) {
                H[0](G);
                p[2] = 0;
            } else {
                S(0);
            }
        } else {
            S(0);
        }
    }
    goto x;
    y:
    E(NULL);
    printf("X:%d/%d Y:%d/%d S:%zu/%zu B:%zu\n", Y, y[-2], X, *y, *y, A, g);
    free(e);
    free(n);
    return 0;
}
```

The deobfuscated code is easier to read and understand. It removes unnecessary obfuscation techniques and clarifies the purpose of each section of code.