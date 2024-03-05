Certainly! Here's the deobfuscated version of the code:

```cpp
#include <stdio.h>

int main(int argc, char* argv[]) {
    if (argc > 1) {
        while (--argc) {
            puts(*++argv);
        }
    } else {
        puts("none");
    }
    return 0;
}
```

This program checks if any command-line arguments are provided. If there are, it prints each argument on a new line. If there are no arguments, it simply prints "none".