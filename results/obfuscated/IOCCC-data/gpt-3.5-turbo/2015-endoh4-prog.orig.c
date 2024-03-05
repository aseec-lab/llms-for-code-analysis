The deobfuscated code is as follows:

```c
void f(int y, int x) {
    int m, z;
    for (m = z = 1; (m * m <= y ? z = (y % m ? z : m) : (x + 1 ? z < 2 ? (x ? f(x, 0) : putchar(64)) : f(z, x), putchar(x ? 10 : 32), y -= z : (f(z, y / z), 0));) {
        m++;
    }
}

int main(int y) {
    f(y - 1, -1);
}
```

Explanation:
This code is a recursive function `f(y, x)` that calculates and prints prime factors of `y`. It calls itself recursively to find factors, and it terminates when `y` becomes 1.
The `main` function simply calls `f()` with `y-1` and `-1` as arguments.

Deobfuscated `f(y, x)` function explanation:
- `m` and `z` are integers representing auxiliary variables.
- The loop starts with `m` and `z` both initialized to 1.
- The loop condition `m * m <= y` checks if the square of `m` is less than or equal to `y`.
- If the condition is `true`, it checks whether `y` is divisible by `m` using the modulo operator (`%`). If it is divisible (`y % m == 0`), it updates `z` with the value of `m`. If it is not divisible, `z` remains the same.
- If the condition `m * m <= y` is `false`, it checks if `x + 1` is `true`. If it is, it proceeds to the next step; otherwise, it directly jumps to printing a newline if `x` is `0`.
- Next, it checks if `z` is less than 2. If it is, it checks if `x` is `0`. If it is, it prints the ASCII character represented by `64` (which is `@`) using the `putchar()` function.
- If `z` is not less than 2, it recursively calls `f(z, x)`.
- After printing either `@` or `z`, it prints a newline character (`10`) if `x` is not `0`, otherwise a space character (`32`).
- If `y` is divisible by `z`, it updates `y` by dividing it by `z` and calls `f(z, y/z)`.
- The loop then repeats, incrementing `m` by 1.

The program starts by calling `main(y)` with an input number `y`, and the `f()` function handles the rest of the computation.