Sure! Here is the deobfuscated code:

```c
#include <stdio.h>

int main() {
    char *p, s[999] = "int main(){ printf(\"%s\", \"Hello, world!\\n\"); return(0); }";

    for (p = s; *p; p++) {
        *p = *p == '~' ? ' ' : *p;
    }

    printf(s);

    return 0;
}
```

The deobfuscated code simply prints the string "Hello, world!" to the console.