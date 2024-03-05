Sure! Here's the deobfuscated version of the code:

```c
#include <curses.h>
#include <unistd.h>
#include <time.h>
#include <stdlib.h>

typedef size_t a;
typedef unsigned long k;
a g = 997, c, A = 5, I, N = 5, G = 5;
k p[5] = { 1 }, W = 300;
int l, Y, X, *j = (int[8]){ 231 }, *U, *V, *e, *n, Q, P, x, *y, *D;

void b(a h) {
	c += h;
	mvprintw(0, 0, "X:%d/%d Y:%d/%d S:%zu/%zu B:%zu\n", X, *y, Y, *y-2, c, A, g);
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
	return rand() % (L - h + 1) + h;
}

void S(a o) {
	static const char* s = "<>^v";
	mvaddch(n[0], e[0], ' ');
	A = fmin(A + o, g);
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
	if (A > 1) {
		mvaddch(n[I], e[I], 'o');
	}
	e[I] = Y;
	n[I] = X;
	while (++I<g) {
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
	*y = R(2, *y - 1);
	y[2] = R(2, *y - 1);
	for (I = 0; I < A; ++I) {
		if (*V == e[I] && *U == n[I]) {
			return;
		}
	}
	x:
	if (++I < g) {
		if (*V == e[I] || *U == n[I]) {
			goto x;
		}
	}
}

int main(void) {
	l = 3;
	char* z;
	y = j + 3;
	void(*H[3])(a) = { b, S, B };
	srand((unsigned)time(NULL));

	if (!initscr()) {
		return 1;
	}
	getmaxyx(stdscr, *y, y[1]);
	--*y;
	--y[1];
	if (y[1] < 9 || *y < 9) {
		return E("screen too small");
	}

#define F(x) z = getenv(#x); z && (x = strtol(z, NULL, 0))
	F(MAXSIZE);
	g = fmin((a)((*y - 2) * (*y - 2)), g);
	F(GROW);
	N = fmin(N, g);
	F(SIZE);
	A = fmin(A, g);
	F(WAIT);
	y[2] = (int)strtol(z, NULL, 0);
	F(EVADE);
	W = fmax(W, 1);
	F(WALLS);
	*p = fmin(*p, g);
	F(CANNIBAL);
	p[4] = fmin(p[4], g);
	F(SHED);
	p[3] = fmin(p[3], g);
	F(SHEDS);
	G = fmin(G, g);

	e = calloc(g + 1, sizeof * e);
	n = calloc(g + 1, sizeof * n);
	if (!e || !n) {
		return E("memory error");
	}

	V = U = y + 2;
	Y = y[1] / 2;
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
	x = 1;
	getmaxyx(stdscr, y[1], y[-1]);
	C(1, 1);
	mvhline(1, 0, '#', y[-1]);
	mvvline(1, 0, '#', *y);
	mvhline(*y, 0, '#', y[-1]);
	mvvline(1, y[-1], '#', *y);
	C(1, 0);

	mvaddch(*U, *V, '*');
	(*H[0])(0);
	refresh();

	timeout(y[2]);
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
			if ((!P && (P = 1)) || (P = 0));
		default:
			x = 0;
	}
	if (x) {
		P = 0;
	}
	if (Q || P) {
		goto x;
	}
	if (!Q && !V[0]) {
		goto x;
	}

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

	if (Y < 1 || Y >= y[-1] || X < 2 || X >= *y || *y< *y - 1) {
		if (*p && !(*y>= *y - 1)) {
			if (Y < 1 || Y >= y[-1]) {
				Y = Y < 1 ? y[-1] - 1 : 1;
			}
			if (X < 2 || X >= *y) {
				X = X < 2 ? *y - 1 : 2;
			}
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

	if (Y == *V && X == *U) {
		B(1);
	} else {
		if (W && !(++*p % W)) {
			(*H[2])(0);
		}
		if (*p > 3) {
			if (!(++p[2] % 3)) {
				(*H[1])(G);
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
	free(e);
	free(n);
	return 0;
}
```

I hope the deobfuscated code helps! Let me know if you have any further questions.