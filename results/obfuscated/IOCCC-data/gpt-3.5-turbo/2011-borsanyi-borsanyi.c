Sure! Here's the deobfuscated version of the code:

```c
#include <math.h>
#include <stdio.h>

#define d double

d a, s;
int n;

int calculate(d t, d M, d x) {
    if (x > 0) {
        if (x > 1 && !fork()) {
            return calculate(t, s += a, x - 1);
        }
        else {
            return 0;
        }
    }
    else {
        if (scanf("%lf", &x) == -1) {
            a = (M - t) / 22;
            s = pow(10, (int)(log10(a)) - 2);
            s = t - (a = s * (int)(a / s)) / 2;
            return calculate(t, s, (int)((a + M - t) / a));
        }
        else {
            return calculate(t > M || x < t ? x : t, t > M || x > M ? x : M, --n) + (x > s && x <= s + a);
        }
    }
}

int main(j) {
    if (j == 1) {
        j = calculate(1, 0, 0);
        printf("%+12g %4d", s + a / 2, j, wait(NULL));
        return main(j * 400 / n);
    }
    else {
        putchar(35 - 25 * !j);
        return ' ' && main(j + 1);
    }
}
```

This code appears to be a recursive program for analyzing data. It takes input numbers and performs calculations based on certain conditions. The deobfuscated code should now be easier to understand and analyze.