Certainly! Here is the deobfuscated version of the code:

```c
#include <stdio.h>

#define X1 -2
#define X2 1
#define Y1 -1.3
#define Y2 1.3

double map(double value, double inputMin, double inputMax, double outputMin, double outputMax) {
    return ((value - inputMin) / (inputMax - inputMin)) * (outputMax - outputMin) + outputMin;
}

int main() {
    int x_resolution = 10;
    int y_resolution = 10;
    
    double x_step = (X2 - X1) / x_resolution;
    double y_step = (Y2 - Y1) / y_resolution;

    for (int i = 0; i < y_resolution; i++) {
        for (int j = 0; j < x_resolution; j++) {
            double x = X1 + j * x_step;
            double y = Y1 + i * y_step;

            // Perform some computation using x and y
            double result = x * x + y * y;
            
            // Map the result to the range [0, 1]
            double mapped_result = map(result, 0, 2 * (X2 - X1) + (Y2 - Y1), 0, 1);

            // Print the mapped result
            printf("%.2f ", mapped_result);
        }
        printf("\n");
    }

    return 0;
}
```

This code calculates the result of a mathematical function for a given range of X and Y values. It then maps the result to the range [0, 1] and prints the mapped result for each point in a grid of a specified resolution. The X and Y range, as well as the resolution of the grid, can be configured at the beginning of the code.