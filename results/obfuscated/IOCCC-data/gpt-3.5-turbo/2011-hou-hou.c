Sure! Here's the deobfuscated code:

```c
#include <stdio.h>
#include <math.h>

double r;

int main(int argc, char** argv) {
    if (argc < 2) {
        printf("usage: calculator 11/26+222/31\n");
        printf("--------------------------------\n");
        printf(" clear ! 0 || l   -x    l   tan  I (/)\n");
        printf("--------------------------------\n");
        printf("  1  |  2  |  3  || l  1/x   l   cos  I (*)\n");
        printf("--------------------------------\n");
        printf("  4  |  5  |  6  || l  exp   l  sqrt  I (+)\n");
        printf("--------------------------------\n");
        printf("  7  |  8  |  9  || l  sin   l   log  I (-)\n");
        printf("-------------------------------(0\n");
    }
    return 0;
}
```

The deobfuscated code simply checks if the program was called with at least one command-line argument. If no argument is provided, it prints a usage message with some instructions for using a calculator. Otherwise, it returns 0 as the exit code.