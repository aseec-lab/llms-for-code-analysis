Certainly! Here is the deobfuscated version of the code:

```c
main(_, l) char** l;
{
    while (--_ % 20)
    {
        if ((_ + _ / 21 & 56) > _)
        {
            if (strchr(1[l], _ ^ "pt`u}rxf~c{wk~zyHHOJ]QULGQ[Z"[_ / 2]) != NULL)
                putchar(111);
            else
                putchar(46);
        }
        else
        {
            putchar(32);
        }
    }
    putchar(10);
    if (_) main(2 + _, l);
}
```

The deobfuscated code is as follows:

```c
#include <stdio.h>
#include <string.h>

int main(int _, char** l)
{
    while (--_ % 20)
    {
        if ((_ + _ / 21 & 56) > _)
        {
            char* str = "pt`u}rxf~c{wk~zyHHOJ]QULGQ[Z";
            char temp = str[_ / 2];
            
            if (strchr(l[1], _ ^ temp) != NULL)
                putchar(111); // 'o'
            else
                putchar(46); // '.'
        }
        else
        {
            putchar(32); // ' '
        }
    }
    
    putchar(10); // '\n'
    
    if (_)
        main(2 + _, l);
}
```

The deobfuscated code performs the following actions:
1. It includes the necessary headers (`stdio.h` and `string.h`).
2. It defines the `main` function taking two arguments (an int `_` and a char array `l`).
3. It enters a while loop that decrements `_` by 1 until `_ % 20` becomes 0.
4. Inside the loop, it checks a condition related to the calculation `(_ + _ / 21 & 56) > _`.
5. If the condition is true, it retrieves the respective index character from a hardcoded string `"pt`u}rxf~c{wk~zyHHOJ]QULGQ[Z"`.
6. It performs an XOR operation between the retrieved character and a character at index `_ / 2` in `l[1]`.
7. If the XOR result exists in `l[1]`, it prints the character `111` ('o') using `putchar(111)`.
8. Otherwise, it prints the character `46` ('.') using `putchar(46)`.
9. If the condition is false, it prints the character `32` (' ').
10. After the loop, it prints the character `10` ('\n') using `putchar(10)`.
11. Finally, if `_` is not zero, it calls `main` recursively with the incremented value of `2 + _` and `l`.