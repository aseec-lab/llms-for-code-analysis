Sure! Here is the deobfuscated code:

```cpp
#include <curses.h>
#include <unistd.h>
#include <time.h>
#include <stdlib.h>

typedef size_t a;
typedef unsigned long k;

static a g = 997, c, A = 5, I, N = 5, G = 5;
static k p[5] = {1};
static int Y, X, *j = (int[8]){231}, *U, *V, *e, *n, Q, P, x, *y, l = 4, *D;

static void b(a h) {
    c += h;
    mvprintw(0, 0, "X:%d/%d Y:%d/%d S:%zu/%zu B:%zu\n", Y, y[-2], X, *y, A, g, c);
}

static int E(const char *L) {
    static int h;
    if (!h && endwin()) {
        h = l;
    }
    L && puts(L);
    return 1;
}

static void C(int L, int h) {
    attroff(COLOR_PAIR(L));
    if (h) {
        attron(COLOR_PAIR(L));
    }
}

static int R(int h, int L) {
    return (rand() % (L - h + 1)) + h;
}

static void S(a o) {
    static const char *s = "<>^v";
    mvaddch(n[0], e[0], ' ');
    A = f(A + o, g);
    if (A >= g) {
        E("YOU WIN!");
        Q = l;
        return;
    }
    C(3, 1);
    for (I = 0; A && I < A - 1; ++I) {
        I && mvaddch(n[I], e[I], 'o');
        e[I] = e[I + 1];
        n[I] = n[I + 1];
    }
    if (A > 1) {
        mvaddch(n[I], e[I], 'o');
    }
    e[I] = Y;
    n[I] = X;
    while (++I < g) {
        e[I] = n[I] = 0;
    }
    C(2, 1);
    mvaddch(X, Y, s[*D + 2 & ((1 << 3) - !Q)]);
}

static void B(a o) {
    if (o) {
        S(N);
        b(1);
    }
    if (Q) {
        return;
    }
    p[1] = 0;
    mvaddch(*U, *V, ' ');
x:
    y[3] = R(1, y[-2] - 1);
    y[2] = R(2, *y - 1);
    for (I = 0; I < A; ++I) {
        if (*V == e[I] && *U == n[I]) {
            goto x;
        }
    }
}

int main(void) {
    char *z;
    y = j + 3;
    void (*H[])(a) = {b, S, B};

    srand((unsigned) time(NULL));
    if (!initscr()) {
        return puts("curses error");
    }
    getmaxyx(stdscr, *y, y[-2]);
    --*y;
    --y[-2];
    if (y[-2] < 9 || *y < 9) {
        return E("screen too small");
    }
    g = (a)((*y - 2) * (y[-2] - 2));
    N = strtol(getenv("GROW"), NULL, 0);
    A = strtol(getenv("SIZE"), NULL, 0);
    W = strtol(getenv("WAIT"), NULL, 0);
    U = y + 2;
    G = strtol(getenv("SHEDS"), NULL, 0);
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
    init_pair(1, COLOR_WALL, COLOR_WALL_BG);
    init_pair(2, COLOR_HEAD, COLOR_HEAD_BG);
    init_pair(3, COLOR_BODY, COLOR_BODY_BG);

    S(0);
    B(0);
x:
    if (Q) {
        goto y;
    }
    x = *y;
    getmaxyx(stdscr, 1[y], y[-1]);
    C(1, 1);
    mvhline(1, 0, '#', y[-2]);
    mvvline(1, 0, '#', *y);
    mvhline(*y, 0, '#', y[-2]);
    mvvline(1, y[-2], '#', *y);
    C(1, 0);
    mvaddch(*U, *V, '*');
    (*H)(0);
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

    if (x) {
        P = !*y;
    }

    if (Q || P) {
        goto x;
    }

    switch (1[V]) {
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

    if (Y < 1 || Y >= y[-2] || X < 2 || X >= *y || y[-2] >= y[-1] || *y >= 1[y]) {
        if (*p && !(*y >= 1[y] || y[-2] >= y[-1])) {
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
            H[2](0);
        }

        if (p[3]) {
            if (!(++p[2] % p[3])) {
                H[1](G);
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
    printf("X:%d/%d Y:%d/%d S:%zu/%zu B:%zu\n", Y, y[-2], X, *y, A, g, c);

    free(e);
    free(n);
    return 0;
}
```

I removed unnecessary macros and formatted the code for better readability.