Sure! Here's the deobfuscated version of the code:

```python
main(a, b)
char** b;
{
    int c = 1, d = c, e = a - d;
    for (; e; e--)
    {
        if (_(e) < _(c))
            c = e;
        else if (_(e) > _(d))
            d = e;
        else
            7;
    }

    while (++e < a)
    {
        printf("\xe2\x96%c", 129 + (**b = 8 * (_(e) - _(c)) / (_(d) - _(c))));
    }
}
```

Now the code is cleaner and easier to understand.