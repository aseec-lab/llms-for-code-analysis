Sure! Here is the deobfuscated version of the code:

```c
#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <png.h>

struct Rect {
    uint16_t x, y, w, h;
    int64_t *c;
    int64_t d;
};

void extract_colors(struct Rect *rect, uint8_t *data, int stride) {
    int x, y;

    for (y = rect->y; y < rect->y + rect->h; y++) {
        for (x = rect->x; x < rect->x + rect->w; x++) {
            int i;

            for (i = 0; i < stride; i++) {
                rect->c[i] = data[y * stride + x * stride + i];
            }
        }
    }
}

void calculate_distance(struct Rect *rect, int block_size, int image_size) {
    int x, y, j;
    int64_t u = 0;
    int64_t *p;

    for (y = rect->y; y < rect->y + rect->h; y++) {
        for (x = rect->x; x < rect->x + rect->w; x++) {
            for (j = 0; j < block_size; j++) {
                p = rect->c[j];
                u += (p[rect->w * block_size + rect->h * stride] - p[rect->w * block_size] - p[rect->h * stride] + *p + image_size/2) / image_size;
            }
        }
    }

    rect->d = u;
}

int main(int argc, char **argv) {
    if (argc != 4) {
        return 1;
    }
    
    int n = atoi(argv[1]);
    
    FILE *input_file = fopen(argv[2], "rb");
    FILE *output_file = fopen(argv[3], "wb");
    
    if (!input_file || !output_file) {
        return 1;
    }
    
    png_byte header[8];
    
    fread(header, 1, 8, input_file);
    
    if (png_sig_cmp(header, 0, 8)) {
        fclose(input_file);
        fclose(output_file);
        
        return 1;
    }
    
    png_structp png_struct = png_create_read_struct(PNG_LIBPNG_VER_STRING, NULL, NULL, NULL);
    
    if (!png_struct) {
        fclose(input_file);
        fclose(output_file);
        
        return 1;
    }
    
    png_infop png_info = png_create_info_struct(png_struct);
    
    if (!png_info) {
        png_destroy_read_struct(&png_struct, NULL, NULL);
        fclose(input_file);
        fclose(output_file);
        
        return 1;
    }
    
    png_init_io(png_struct, input_file);
    png_set_sig_bytes(png_struct, 8);
    png_read_info(png_struct, png_info);
    
    int width = png_get_image_width(png_struct, png_info);
    int height = png_get_image_height(png_struct, png_info);
    int bit_depth = png_get_bit_depth(png_struct, png_info);
    int channels = png_get_channels(png_struct, png_info);
    int color_type = png_get_color_type(png_struct, png_info);

    int64_t *data = malloc(width * height * bit_depth * channels / 8);
    
    if (!data) {
        png_destroy_read_struct(&png_struct, &png_info, NULL);
        fclose(input_file);
        fclose(output_file);
        
        return 1;
    }
    
    png_bytep *rows = malloc(sizeof(png_bytep) * height);
    
    for (int y = 0; y < height; y++) {
        rows[y] = (png_bytep)(data + y * width * bit_depth * channels / 8);
    }
    
    png_read_image(png_struct, rows);
    
    struct Rect *rects = malloc(sizeof(struct Rect) * n);
    
    if (!rects) {
        free(data);
        png_destroy_read_struct(&png_struct, &png_info, NULL);
        fclose(input_file);
        fclose(output_file);
        
        return 1;
    }
    
    for (int i = 0; i < n; i++) {
        rects[i].c = malloc(sizeof(int64_t) * channels);
    }
    
    for (int i = 0; i < n; i++) {
        rects[i].x = rects[i].y = rects[i].w = rects[i].h = 0;
        rects[i].d = 0;
    }
    
    for (int i = 0; i < n; i++) {
        struct Rect *rect = &rects[i];
        
        for (int j = 0; j < channels; j++) {
            rect->c[j] = data[j];
        }
        
        rect->w = width;
        rect->h = height;
        
        calculate_distance(rect, bit_depth, width * height);
    }
    
    for (int i = 0; i < n; i++) {
        struct Rect *rect = &rects[i];
        int x, y, j;
        
        for (int j = 0; j < channels; j++) {
            data[j] = rect->c[j];
        }
        
        int v = rect->w;
        int q = rect->h;
        
        for (int z = 0; z < v; z++) {
            int u, k, e, f, l;
            
            for (int z = 1; z < v; z++) {
                u = 0;
                l = 0;
                
                for (y = 1; y < q; y++) {
                    for (x = 0; x < z; x++) {
                        k = data[x + y * width * bit_depth * channels / 8];
                        e = k - data[x];
                        f = rect->c[j] * bit_depth;
                        l = data[(x + width * bit_depth) * channels + y * width * bit_depth * channels];
                        k -= data[y];
                        u += (l < 0) ? -l : l;
                        u += (k < 0) ? -k : k;
                        l = (e < f) ? rect->y += l, q -= rect->h = l : rect->x += k, z -= rect->w = k;
                    }
                }
            }
        }
    }
    
    for (int i = 0; i < n; i++) {
        struct Rect *rect = &rects[i];
        extract_colors(rect, data, width * bit_depth * channels / 8);
    }
    
    png_structp png_output_struct = png_create_write_struct(PNG_LIBPNG_VER_STRING, NULL, NULL, NULL);
    
    if (!png_output_struct) {
        free(rows);
        free(data);
        
        for (int i = 0; i < n; i++) {
            free(rects[i].c);
        }
        
        free(rects);
        png_destroy_read_struct(&png_struct, &png_info, NULL);
        fclose(input_file);
        fclose(output_file);
        
        return 1;
    }
    
    png_infop png_output_info = png_create_info_struct(png_output_struct);
    
    if (!png_output_info) {
        free(rows);
        free(data);
        
        for (int i = 0; i < n; i++) {
            free(rects[i].c);
        }
        
        free(rects);
        png_destroy_write_struct(&png_output_struct, NULL);
        png_destroy_read_struct(&png_struct, &png_info, NULL);
        fclose(input_file);
        fclose(output_file);
        
        return 1;
    }
    
    png_init_io(png_output_struct, output_file);
    png_set_IHDR(png_output_struct, png_output_info, width, height, 8, color_type, 0, 0, 0);
    png_write_info(png_output_struct, png_output_info);
    png_write_image(png_output_struct, rows);
    png_write_end(png_output_struct, NULL);
    
    free(rows);
    free(data);
    
    for (int i = 0; i < n; i++) {
        free(rects[i].c);
    }
    
    free(rects);
    
    png_destroy_write_struct(&png_output_struct, NULL);
    png_destroy_read_struct(&png_struct, &png_info, NULL);
    fclose(input_file);
    fclose(output_file);

    return 0;
}
```

This deobfuscated version of the code should be easier to understand and analyze. Let me know if you have any further questions!