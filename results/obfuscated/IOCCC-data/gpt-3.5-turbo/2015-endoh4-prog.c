Here's the deobfuscated version of the code:

```
#include <stdio.h>

void f(int y, int x) {
    int m, z;
    
    for (m = z = 1; m*m <= y ? z = y % m ? z : m : x + 1 ? z < 2 ? y && f(x, 0) : f(z, x), putchar(x ? 10 : 32 << !y), y -= z * !!y : (f(z, y / z), 0);) {
        m++;
    }
}

int main(int y) {
    f(y - 1, -1);
    return 0;
}
```

The code defines a recursive function `f` that takes two integers `y` and `x`. It also includes the necessary header `stdio.h` for input/output operations.

The main function initializes `y` with the value of the program argument minus 1. It then calls the `f` function with arguments `y - 1` and -1.

The `f` function uses a loop with a variable `m` and `z`.
- Initially, `m` and `z` are set to 1.
- Inside the loop, if `m*m` is less than or equal to `y`, it checks if `y` is divisible by `m`. If not, `z` remains the same, otherwise, `z` is updated to `m`.
- If `m*m` is greater than `y`, the code checks if `x + 1` is true. If so, it checks if `z` is less than 2. If true, it checks if `y` is not zero and calls `f` recursively with arguments `x` and 0. Otherwise, it calls `f` recursively with arguments `z` and `x`.
- After that, it prints a newline character (`\n`) if `x` is non-zero, otherwise, it prints a space character (`' '`).
- It then updates `y` by subtracting the product of `z` and whether `y` is non-zero.
- If `y` is zero, it calls `f` recursively with arguments `z` and `y/z`.
- The loop continues until the condition `m*m <= y ? ... : ...` is false.

In summary, the main function calls the `f` function recursively to perform some calculations and prints the values or newline characters based on the conditions in the loop.