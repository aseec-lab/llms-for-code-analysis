Sure! Here's the deobfuscated code:

```
#include <stdio.h>

int main(int ac, char **av) {
    if (av[0][0] == '"') {
        switch (av[0][1]) {
            case 'a':
                printf("a\n");
                break;
            case 'b':
                printf("b\n");
                break;
        }
    }
    
    while (--ac > 0) {
        printf("%d", ac);
    }
    
    return 0;
}
```

In this code, the `main` function takes in command line arguments `ac` (argument count) and `av` (argument vector). It first checks if the first character of the first command line argument (which is `av[0]`) is a double quotation mark (`"`). If it is, it enters a switch statement based on the second character of `av[0]`.

If `av[0][1]` is `'a'`, it prints "a" followed by a newline character. If `av[0][1]` is `'b'`, it prints "b" followed by a newline character.

After that, the code enters a while loop that decrements `ac` (argument count) and prints its value as a integer. The loop continues until `ac` becomes 0.

Finally, the `main` function returns 0 to indicate successful execution.