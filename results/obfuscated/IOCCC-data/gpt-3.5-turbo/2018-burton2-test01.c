The code can be deobfuscated as follows:

```c
int main(int ac, char **av) {
    if (av[0][0] == '"') {
        switch (av[0][1]) {
            case 'a': printf("a\n"); break;
            case 'b': printf("b\n"); break;
        }
    }
    
    while (--ac > 0)
        printf("%d", ac);
    
    return 0;
}
```

The deobfuscated code simply checks if the first character of the first argument (`av[0]`) is a double quote. If it is, it then checks the second character (`av[0][1]`) and prints 'a' if it is 'a' or prints 'b' if it is 'b'. 

After that, it enters a loop and prints the number of arguments (`ac`) starting from the count of arguments minus one and decrementing `ac` in each iteration until `ac` becomes zero. Finally, it returns 0.