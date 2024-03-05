Sure! Here's the deobfuscated version of the code:

```c
#include <curses.h>

#define P(a,b,c) a##b##c

int c, h, v, x, y, s, i, b;

int main() {
    initscr();
    P(cb, rea, k)();
    P(n, oec, ho)();

    curs_set(0);

    for (; s = x = COLS / 2; P(flu, shi, np)()) {
        timeout(y = c = v = 0);
        P(c, lea, r)();

        for (P(mva, d, dstr)(2, 3 + x, G) ; ; P(usl, eep, )(U)) {
            P(m, vad, dstr)(y >> 8, x, " ");

            for (i = LINES; i-- > 0; mvinsch(i, 0, 0 > (~c | i - h - H & h - i) ? ' ' : (i - h | h - i + H) < 0 ? '|' : '='));

            if ((i = (y += v = getch() > 0 ? I : v + A) >> 8) >= LINES || mvinch(i *= 0 < i, x) != ' ' || ' ' != mvinch(i, 3 + x))
                break;

            mvaddstr(y >> 8, x, 0 > v ? F : B);

            i = --s / -W;
            P(m, vpr, intw)(0, COLS - 9, " %u/%u ", (0 < i) * i, b = b < i ? i : b);

            refresh();

            if (++c == D) {
                c -= W;
                h = rand() % (LINES - H - 6) + 2;
            }
        }

        flash();
    }
}
```

This code seems to be a game using the curses library for terminal-based user interaction. Unfortunately, without further context or understanding of the game logic, it is difficult to determine the exact purpose or functionality of the code.