Sure! Here's the deobfuscated code:

```c
#include <curses.h>
#include <unistd.h>
#include <time.h>
#include <stdlib.h>

typedef size_t a;
typedef unsigned long k;
static a g = 997, c, A = 5, I, N = 5, G = 5;
static k p[5] = {1};
static const int Y_INIT = 231;
static int Y,X;
static int *j = (int[8]){ 231 };
static int *U, *V, *e, *n, Q, P, x, *y, l = 4, *D;

static void b(a h) {
    c += h;
    mvprintw(0, 0, "X:%d/%d Y:%d/%d S:%zu/%zu B:%zu\n", Y, y[-2], X, *y, A, g, c);
}

static int E(const char* L) {
    static int h;
    if (!h && endwin()) {
        h = l;
        L && puts(L);
        return 1;
    }
    return 0;
}

static void C(int L, int h) {
    attroff(COLOR_PAIR(L));
    if (h)
        attron(COLOR_PAIR(L));
}

static int R(int h, int L) {
    return (rand() % (L - h + 1)) + h;
}

static void S(a o) {
    static const char* s = "><^v";
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
    A > 1 && mvaddch(n[I], e[I], 'o');
    e[I] = Y;
    n[I] = X;
    while (++I < g) {
        e[I] = 0;
        n[I] = 0;
    }
    C(2, 1);
    mvaddch(X, Y, s[*D + 2 & (1 << 3) - !Q]);
}

static void B(a o) {
    if (o) {
        S(N);
        b(1);
    }
    if (Q)
        return;
    p[1] = 0;
    mvaddch(*U, *V, ' ');
    x:
    y[3] = R(1, y[-2] - 1);
    y[2] = R(2, *y - 1);
    for (I = 0; I < A; ++I) {
        if (*V == e[I] && *U == n[I])
            goto x;
    }
}

int main(void) {
    char* z;
    y = j + 3;
    void (*H[])(a) = { b, S, B };
    srand((unsigned)time(NULL));
    if (!initscr())
        return puts("curses error");

    erase();
    getmaxyx(stdscr, *y, y[-2]);
    --*y;
    --y[-2];
    if (y[-2] < 9 || *y < 9)
        return E("screen too small");

    if (MAXSIZE) g = strtoul(z, NULL, 0);
    if (GROW) N = strtoul(z, NULL, 0);
    if (SIZE) A = strtoul(z, NULL, 0);
    if (WAIT) y[-3] = (int)strtoul(z, NULL, 0);
    if (EVADE) W = strtoul(z, NULL, 0);
    if (WALLS) *p = strtoul(z, NULL, 0);
    if (CANNIBAL) p[4] = strtoul(z, NULL, 0);
    if (SHED) p[3] = strtoul(z, NULL, 0);
    if (SHEDS) G = strtoul(z, NULL, 0);

    e = calloc(g + 1, sizeof(int));
    n = calloc(g + 1, sizeof(int));
    if (!e || !n)
        return E("memory error");
    V = U + 1;
    Y = y[-2] / 2;
    X = *y / 2;
    D = p + 1;
    attron(A_BOLD);
    curs_set(0);
    nonl();
    intrflush(stdscr, 0);
    keypad(stdscr, 1);
    noecho();
    cbreak();
    start_color();
    init_pair(1, COLOR_WALL, COLOR_WHITE);
    init_pair(2, COLOR_HEAD, COLOR_BLUE);
    init_pair(3, COLOR_BODY, COLOR_CYAN);
    init_pair(4, COLOR_BG, COLOR_BLACK);
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
    C(4, 1);
    mvaddch(*U, *V, '*');
    C(4, 0);
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

    if (x)
        P = !*y;
    if (Q || P)
        goto x;
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
            if (Y < 1 || Y >= y[-2])
                Y = Y < 1 ? y[-2] - 1 : 1;
            if (X < 2 || X >= *y)
                X = X < 2 ? *y - 1 : 2;
        } else {
            E("OUCH!");
            goto y;
        }
    }

    for (I = 0; !4[p] && I < A; ++I) {
        if (Y == e[I] && X == n[I]) {
            E("OW!");
            goto y;
        }
    }

    if (Y == *V && X == *U)
        B(1);
    else {
        if (W && !(++1[p] % W))
            H[2](0);
        if (3[p]) {
            if (!(++p[2] % 3[p])) {
                H[1](G);
                2[p] = 0;
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
    free(e);
    free(n);
    return 0;
}
```

I hope this helps! Let me know if you have any further questions.