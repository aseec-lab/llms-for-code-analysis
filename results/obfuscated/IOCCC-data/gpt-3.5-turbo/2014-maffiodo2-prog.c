Certainly! Here's the deobfuscated version of the code:

```c
#include <stdio.h>
#include <string.h>

int d = 80, e, j;

void g() {
    j += getchar();
}

int main(int a, char **b) {
    for (;;) {
        j = 0;
        g();
        if (j < 0)
            break;
        g(g());
        putchar(b[1][j / 3 * strlen(b[1]) >> 8]);
        if (!(++e % d))
            puts("");
    }
}
```

In deobfuscated form, the code reads characters from standard input and maps them to characters from a given string (`b[1]`), based on the value of `j`. It then checks if the current count (`e`) is divisible by `d`, and if so, prints a new line.