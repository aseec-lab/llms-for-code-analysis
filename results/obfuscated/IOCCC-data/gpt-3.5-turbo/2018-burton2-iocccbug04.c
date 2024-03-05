The deobfuscated code:

```c
#include <stdio.h>

int main(int ac, char** av) {
    char* dquote = "dquote";

    if (ac > 1 && av[1][0] == '\"')
        printf("%s", dquote);

    if (ac == 0)
        printf("int ac is zero\n");

    return 0;
}
```

Explanation:
The code checks if the first command-line argument (av[1]) starts with a double quote ("), and if it does, it prints the value of the variable "dquote". If the program is called with no command-line arguments (ac == 0), it prints "int ac is zero".