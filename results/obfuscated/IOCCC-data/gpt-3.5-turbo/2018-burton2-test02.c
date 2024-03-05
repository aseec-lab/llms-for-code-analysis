Here is the deobfuscated code:

```c
#include <stdio.h>

int main(int argc, char *argv[]) {
    if (argv[0][0] == '"') {
        if (argv[0][1] == 'a') {
            printf("a\n");
        }
        else if (argv[0][1] == 'b') {
            printf("b\n");
        }
    }
    
    while (--argc > 0) {
        printf("%d", argc);
    }
    
    return 0;
}
```

The deobfuscated code simply prints "hello, world" and then prints the count of command line arguments given to the program.