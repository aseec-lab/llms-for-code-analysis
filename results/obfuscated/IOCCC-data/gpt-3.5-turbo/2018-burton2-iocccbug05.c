Certainly! Here's the deobfuscated code:

```cpp
#include <stdio.h>

enum Foo {
	I = 1,
	O = 2
};

int main(int ac, char** av) {
	for (--ac; *++av; ) {
		switch (*av[0] - '0') {
			case I:
				puts("input");
				break;
			case O:
				puts("output");
				break;
			default:
				puts("whatever");
				break;
		}
	}
	return 0;
}
```

This code declares an enumeration called `Foo` with two members: `I` and `O`, each with assigned values 1 and 2 respectively.

The `main` function takes command-line arguments `ac` (argument count) and `av` (argument vector). It loops through the command-line arguments using the `for` loop.

Inside the loop, it checks the first character of each argument (`*av[0]`) and converts it to an integer value by subtracting the ASCII value of '0'. Then, it uses a `switch` statement to perform different actions based on the obtained integer value.

If the integer value matches the value of `I` in the `Foo` enumeration, it prints "input". If it matches the value of `O`, it prints "output". Otherwise, it prints "whatever".

The code is missing a return type for the `main` function, so I added `int` to fix it. I also added the necessary header file `<stdio.h>` for the `puts` function and added a `return 0` statement at the end of the `main` function.