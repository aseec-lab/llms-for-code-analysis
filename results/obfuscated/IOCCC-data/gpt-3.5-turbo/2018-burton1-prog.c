Certainly! Here's the deobfuscated version of the code:

```c
#include <stdio.h>

char O, o[256];

int main() {
    int l;
    while ((l = getchar()) != EOF) {
        if (l != '~') {
            if (4 < (4 ^ l >> 5)) {
                O = l;
            } else {
                O = 46;
            }
        } else {
            O = 0;
        }

        if (O != 0) {
            O[o] = O;
            printf("%02x ", O);
        } else {
            puts(o);
        }
    }
    return 0;
}
```

The deobfuscated code features a `main` function that reads characters from input until the end of file (EOF). For each character read, it checks if the character is not '~'. If it is not '~', it performs a bitwise shift of the character `l` to the right by 5 and XORs the result with 4. If the result of this operation is greater than 4, it assigns the character to `O`; otherwise, it assigns `46` (ASCII value of '.') to `O`. 

If the character is '~', it assigns `0` to `O`. 

After setting the value of `O`, the code enters a conditional branch. If `O` is not `0`, it assigns `O` to `O[o]` (effectively storing it in an array). It then prints the hexadecimal representation of `O` followed by a space. 

If `O` is `0`, it prints the string stored in `o` using the `puts` function.

The code continues this process until it reaches the end of file.