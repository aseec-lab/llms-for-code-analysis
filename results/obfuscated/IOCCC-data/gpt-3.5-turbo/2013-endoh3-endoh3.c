Sure! Here's the deobfuscated version of the code:

```c
#include <stdio.h>

int main() {
    char a;
    float b = 0, c = 0;
    int d = 0;

    while (d > 2000 * c ? (c = 1, scanf(" %c%f", &a, &c), d = 55 - a % 32 * 9 / 5, d = d > 9, d = d % 13 - a / 32 * 12, 1) : (a = 2, 1)) {
        ++d;
        if (d < 24) {
            b *= 89 / 84.;
        } else {
            putchar(a = b * d);
        }
    }

    return 0;
}
```

In simplified terms, this code reads a character `a` and a float `c` from the user input. It then performs some calculations and loops until a specific condition is met. The loop iterates from `d` until it reaches 2000 times the value of `c`. Inside the loop, it calculates the value of `d` based on the input and checks certain conditions to determine the value of `a`. It then increments `d` and checks if it is less than 24. If so, it performs a calculation on `b`. Finally, if `d` is equal to or greater than 24, it prints the value of `a` (which is the result of the calculation `a = b * d`).