The deobfuscated code is as follows:

```c
#include <stdio.h>

int main(int argc, char** argv){
    int num = *(int*)argv[1];
    int index = num % 4796 % 275 % argc;
    
    puts(argv[index]);
}
```

This code takes two command-line arguments: `argc` (the number of arguments) and `argv` (the arguments). It converts the second argument, `argv[1]`, into an integer and assigns it to the variable `num`. Then, it calculates the index by taking the modulo of `num` with 4796, then 275, and finally with `argc`. 

Finally, it uses `puts` to print the string at the calculated index in the `argv` array.