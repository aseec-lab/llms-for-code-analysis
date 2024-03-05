Here is the deobfuscated code:

```c
#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>

struct Rect {
    uint16_t x, y, w, h;
    int64_t *c;
    int64_t d;
};

void processImage(uint8_t *image, int stride, int width, int height, struct Rect *rects, int numRects) {
    int x, y, j;
    for (int i = 0; i < numRects; ++i) {
        struct Rect *rect = &rects[i];
        int u = 0;
        for (y = 0; y < rect->h; ++y) {
            for (x = rect->x; x < rect->x + rect->w; ++x) {
                for (j = 0; j < 3; ++j) {
                    int64_t z = image[y * stride + x * 3 + j];
                    rect->d += z * z;
                    rect->c[j] = (image[(y+rect->y) * stride + (x+rect->x) * 3 + j] 
                                  - image[(y+rect->y) * stride + x * 3 + j] 
                                  - image[y * stride + (x+rect->x) * 3 + j] 
                                  + image[y * stride + x * 3 + j] 
                                  + rect->w * rect->h / 2) / (rect->w * rect->h);
                }
            }
        }
    }
}

void readBinaryFile(FILE *file, uint8_t **data, int *size) {
    fseek(file, 0, SEEK_END);
    *size = ftell(file);
    rewind(file);
    *data = (uint8_t*)malloc(*size);
    fread(*data, 1, *size, file);
}

int main(int argc, char **argv) {
    int numRects = atoi(argv[1]);
    int width, height, stride;
    FILE *inputFile;

    if (!(inputFile = fopen(argv[2], "rb"))) {
        printf("Error: Cannot open input file.\n");
        return 1;
    }

    fread(&width, sizeof(int), 1, inputFile);
    fread(&height, sizeof(int), 1, inputFile);
    fread(&stride, sizeof(int), 1, inputFile);

    int dataSize = width * height * 3;
    uint8_t *imageData = (uint8_t*)malloc(dataSize);
    fread(imageData, 1, dataSize, inputFile);
    fclose(inputFile);

    struct Rect **rects;
    if (!(inputFile = fopen(argv[3], "rb"))) {
        printf("Error: Cannot open rects file.\n");
        return 1;
    }

    fread(&numRects, sizeof(int), 1, inputFile);
    rects = (struct Rect**)malloc(sizeof(struct Rect*) * numRects);

    for (int i = 0; i < numRects; ++i) {
        struct Rect *rect = (struct Rect*)malloc(sizeof(struct Rect));
        fread(&rect->x, sizeof(uint16_t), 1, inputFile);
        fread(&rect->y, sizeof(uint16_t), 1, inputFile);
        fread(&rect->w, sizeof(uint16_t), 1, inputFile);
        fread(&rect->h, sizeof(uint16_t), 1, inputFile);
        rects[i] = rect;
    }

    fclose(inputFile);

    processImage(imageData, stride, width, height, rects, numRects);

    FILE *outputFile;
    if (!(outputFile = fopen(argv[4], "wb"))) {
        printf("Error: Cannot open output file.\n");
        return 1;
    }

    fwrite(&width, sizeof(int), 1, outputFile);
    fwrite(&height, sizeof(int), 1, outputFile);
    fwrite(&numRects, sizeof(int), 1, outputFile);
    fwrite(imageData, 1, dataSize, outputFile);

    fclose(outputFile);

    free(imageData);
    for (int i = 0; i < numRects; ++i) {
        free(rects[i]);
    }
    free(rects);

    return 0;
}
```

The deobfuscated code is essentially an image processing program that takes input images and a list of rectangles, and performs some calculations on those rectangles within the images.