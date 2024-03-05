Based on the given code, here is the deobfuscated version:

```c
#include <stdio.h>

int main(int argc, char **argv) {
	if (argv[0][0] == '"') {
		switch (argv[0][1]) {
		case 'a':
			printf("a\n");
			break;
		case 'b':
			printf("b\n");
			break;
		}
	}

	while (--argc > 0) {
		printf("%d", argc);
	}

	return 0;
}
```

Explanation:

The given code seems to be a C program with the main function accepting command-line arguments. The code first checks if the first character of the first argument (`argv[0]`) is a double quotation mark (`"`). If it is, it enters a switch statement to check the second character of `argv[0]`.

If the second character is 'a', it prints "a" followed by a newline character. If the second character is 'b', it prints "b" followed by a newline character.

After that, the code enters a while loop and decrements the `argc` variable (number of command-line arguments) and prints its value until it becomes 0.

Finally, the program terminates and returns 0.