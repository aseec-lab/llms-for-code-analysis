Sure! Here's the deobfuscated version of the code:

```cpp
#include <iostream>
#include <cstdlib>

int main(int argc, char** argv) {
    long long n = 11;
    long long a = n;
    long long b = atoi(argv[1]);

    long long r = (a / b & a) >> 4;
    long long y = atoi(argv[2]);
    long long _ = (((a ^ n) / b) * (y >> 8) | y >> 6) & r | (a ^ r);

    std::cout << reinterpret_cast<char*>(&_) << std::endl;

    return 0;
}
```

This code takes command-line arguments `argv[1]` and `argv[2]` as inputs and performs a series of bitwise and arithmetic operations to calculate the value of `_`. Then, it prints the first 8 characters of `_` as a string.