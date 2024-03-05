Certainly! Here's the deobfuscated code:

```c
#include <stdio.h>

int main(int ac, char **av) {
    if (!(ac != 1)) {
        printf("hello, world\n");
    } else {
        printf("hello, %s!\n", av[1]);
    }
}
```

This code checks if the number of command-line arguments passed to the program is equal to 1. If it is, it prints "hello, world". Otherwise, it prints "hello" followed by the first argument passed to the program.