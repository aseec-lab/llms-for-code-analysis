Here is the deobfuscated version of the code:

```c
#include <curses.h>
#include <term.h>
#include <stdlib.h>
#include <stdio.h>

#define f(a,b) ((a)<(b)?(a):(b))

static char *v;
static size_t Y = 997;
static size_t X = 5;
static size_t S = 5;
static size_t rows, N, K;
static int x, y;

struct caps
{
    char *caps;
    char *sup;
    char *desc;
    int fatal;
};

static size_t b(size_t n)
{
    size_t i = n / 10;
    size_t j = 1;
    while (i)
    {
        i /= 10;
        ++j;
    }
    return j;
}

static size_t a(int n)
{
    int i = n / 10;
    size_t j = 1;
    while (i)
    {
        i /= 10;
        ++j;
    }
    return j;
}

static struct caps caps[] =
{
    { "cup", NULL, "cursor movement", 1 },
    { "civis", NULL, "making cursor invisible", 0 },
    { "bold", NULL, "bold", 0 },
    { NULL, NULL, NULL, 0 }
};

static size_t c(size_t M, size_t V)
{
    size_t i;

    if (!S)
        S = 5;

    for (i = 0; V < M; ++i)
        V += S;

    return i;
}

int main(void)
{
    int i, j, k = 0, f = 0;
    if (!initscr())
    {
        fprintf(stderr, "FATAL: couldn't initialise curses\n");
        return -1;
    }
    j = has_colors();
    k += !j;
    getmaxyx(stdscr, y, x);
    endwin();

    if (y < 10 || x < 10)
    {
        fprintf(stderr, "FATAL: terminal size must be at least 10 lines and 10 columns\n");
        return -1;
    }

    for (i = 0; caps[i].caps; ++i)
    {
        caps[i].sup = tigetstr(caps[i].caps);

        if (caps[i].sup == (char *)-1)
            caps[i].sup = NULL;
        if (!caps[i].sup)
            ++k;
    }

    for (i = 0; caps[i].caps; ++i)
    {
        printf("terminal%s support%s %s", caps[i].sup ? "" : " does not", caps[i].sup ? "s" : "", caps[i].desc);
        if (caps[i].fatal && !caps[i].sup)
        {
            ++f;
            printf(" (fatal)");
        }
        puts("");
    }
    printf("terminal%s support%s colours\n\n", j ? "" : " does not", j ? "s" : "");
    printf("terminal rows %3d (%-3d playable)\n", y, y - 3);
    printf("terminal cols %3d (%-3d playable)\n\n", x, x - 2);

    K = (size_t)((y - 3) * (x - 3));;

    v = getenv("MAXSIZE");
    if (v)
        Y = strtoul(v, NULL, 0);
    Y = f(K, Y);
    v = getenv("GROW");
    if (v)
        S = strtoul(v, NULL, 0);
    v = getenv("SIZE");
    if (v)
        X = strtoul(v, NULL, 0);
    N = c(Y, X);
    printf("snake size: %5zu (max size: %zu)\n", Y, K);
    printf("      bugs: %5zu (max size: %zu)\n\n", N, c(K, X));

    rows = 14 + b(Y) * 2 + a(y) * 2 + a(x) * 2 + b(N);
    if (x < (int)rows + 1)
        ++k;
    printf("at least %zu columns recommended for snake size %-5zu (is %d)\n", rows + 1, Y, x);

    rows = 14 + b(K) * 2 + a(y) * 2 + a(x) * 2 + b(c(K, X));
    printf("at least %zu columns recommended for snake size %-5zu (is %d)\n\n", rows + 1, K, x);

    if (!k)
        printf("No problems detected.\n");
    else
        printf("%d problem%s detected (%d fatal).\n", k, k != 1 ? "s" : "", f);

    return f ? -1 : k;
}
```

The deobfuscated code is easier to read and understand.