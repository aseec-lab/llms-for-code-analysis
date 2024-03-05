The deobfuscated code is as follows:

```c
#include <stdio.h>

int main(int ac, char **av) {
    char foo[] = "line one\n\
    two\n\
    three\n";
    
    if (av[0][0] == '"') {
        switch (av[0][1]) {
            case 'a': printf("a\n"); break;
            case 'b': printf("b\n"); break;
        }
    }
    
    while (--ac > 0) {
        printf("%d", ac);
    }
    
    printf("%s", foo);
    
    return 0;
}
```

Explanation:
- The code includes the `<stdio.h>` library, which is necessary for the `printf` function.
- The `main` function is defined with the usual arguments `(int ac, char **av)`.
- The `foo` variable is an array of characters that stores the string "line one\n\two\n\tthree\n".
- Inside an `if` statement, it checks if the first character of the first argument (`av[0][0]`) is a double quote ("). If it is, it enters a `switch` statement based on the second character (`av[0][1]`).
- If the second character is 'a', it prints "a\n". If it is 'b', it prints "b\n".
- After that, a `while` loop is executed. It decrements the `ac` variable and prints its value until `ac` becomes 0.
- Finally, the `foo` variable is printed using `printf("%s", foo)`.
- The function returns 0 to indicate successful execution.