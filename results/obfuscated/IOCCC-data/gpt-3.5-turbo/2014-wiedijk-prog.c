Sure! Here's the deobfuscated code:

```C
#include <stdarg.h>
#include <stdio.h>
#include <stdlib.h>

typedef union w w;

union w {
  int _;
  void* p;
  w (*c)(w, ...);
};

w _(w f);
w a(w f, w x);
w a_(w f, w fw);
w F(w x);
w F_(w x);
w W(w f);
w Y(w f);

w _(w f) {
  w* k = f.p;
  w r = a(*(k->c), *(k+1));
  free(k);
  return r;
}

w a(w f, w x) {
  w* d = f.p;
  return (*(d->c))(x, d+1);
}

w a_(w f, w fw) {
  w* k = malloc(sizeof(w) << 1);
  k[0] = f;
  k[1] = fw;
  return (w) {.p = k};
}

w F(w x) {
  if (x._) {
    return x._ * a(_(F), (w) {._ = x._ - 1})._;
  } else {
    return (w) {._ = 1};
  }
}

w F_(w x) {
  return (w) {};
}

w W(w f) {
  return a(f, a_(f, f));
}

w Y(w f) {
  return a(W(f), W(f));
}

int main() {
  printf("%f\n", a(a(Y, F_), (w) {._ = 10})._ / 60.0 / 60.0 / 24.0);
  return 0;
}
```

The deobfuscated code is functionally the same as the obfuscated code, but it is much easier to read and understand.