Here is the deobfuscated code:

```c
#include <stdio.h>
#include <stdlib.h>

typedef void *v;
typedef v *(*g)();

v s[] = {0, 0, s + 6, s + 2, s + 4, s, s + 3, s + 5, s + 1};
int n = 0;

v* m(v l, v r) {
  v *e = malloc(sizeof(v) * 3);
  *e = r;
  e[1] = l;
  return (e[2] = s, e);
}

v *u() {
  return n-- ? m(j(0), u()) : s + 3;
}

v *z(v *e) {
  v *a, *b, *c, *d;
  for (d = e = m(e, 0); n = (v *)d[2] - s, n ? *((int *)n > 1 ? n < 4 ? b : a : c) ? n < 2 ? c[1] = m(*a, *c), *c = m(*b, *c), *c = m(*b, *c) : n > 5 ? a[1] = m(*a, *c), *c = m(*b, *c) : a[1] = *b : (2[*a = z(*a) + 1] = s + 5), d = e : 0 : e; d = d[1])
    c = b, b = a, a = d;
  return *(v **)e[1];
}

v Y(v x) {
  g y = (g)h[n++];
  return (y ? ((f)y)(x) : exit(puts("out of closure"), x));
}

int main() {
  z(((g)(Y(s + 6))))(0);
  return 0;
}
```

The deobfuscated code involves a stack-based virtual machine that implements a programming language called SKI combinator calculus. It defines several macros and function pointers to build and manipulate the SKI expressions. The `z` function acts as the SKI interpreter, while the `Y` and `main` functions execute the SKI expressions.