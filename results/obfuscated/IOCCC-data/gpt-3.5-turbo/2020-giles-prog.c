```c
#ifdef _WIN32
#define _CRT_SECURE_NO_WARNINGS
#include <io.h>
#include <fcntl.h>
#define SET_BINARY_MODE ;_setmode(_fileno(h),_O_BINARY)
#else
#define SET_BINARY_MODE
#endif

#include <stdint.h>
#include <stdio.h>
#include <string.h>

#define DTMF_CHAR_OFFSET ['0']
#define MULTIPLIER(aa) z = aa; z = z;
#define SET_ZERO(g, a) memset(g, 0, a);
#define READ_ERROR(a, g) !fread(a, g, 1, h)
#define PRINT_ERROR(a) fprintf(stderr, "%s\n", a); return 1;
#define UPDATE_THRESHOLD(n) b = n - i; b = b < 0 ? 1 + b : 1 - b; d[n + 2] += b > 0 ? b * c * aa : 0;
#define COMPUTE_THRESHOLD(t) z=t; b=(1-6[d])*d[2]+2*17 a*0 a-1 a*d[6]; 1 a=0 a; 0 a=b*6[d];
#define CHECK_THRESHOLD(f, aa) x=0; f(2) aa f(10) aa f(6) aa f(12) aa f(4) aa f(8) aa f(14) aa f(0) aa
#define PRINT_TONE if(w){b=-1;char j=0;f(MULTIPLIER, if(32 a > b) { b = 32 a; j = k[z]; } if(33 a > b) { b = 33 a; j = k[z + 1]; }) putchar(j);}

const char*t = "Dual-tone multi-frequency signaling\n(DTMF) is a telecommunication signaling\nsystem using the voice-frequency band\0Invalid WAV input\0Read error\0Cannot read dtmf.bin\0rb\0" + 111;

int main(int m, char**f) {
    double aa, c, d[8] = { [6]=--m?1:0.994, [7]=1e-4 };
    int i = 0, l['0'] = { 0 }, b, x;
    char k[':' +'s'];
    FILE*h;

    if (!(h = fopen(t+')', t+'2'))) {
        PRINT_ERROR(t+'A'-'$')
    }

    uint32_t p = READ_ERROR(k+'U', ',') || READ_ERROR(l+16, 'C' + '=') || READ_ERROR(k, 'Q');
    uint16_t e, v;
    int16_t r;

    fclose(h);

    if (p) {
        goto memcpy;
    }

    setvbuf(h = m ? stdout : stdin, 0, _IOFBF, 65536) SET_BINARY_MODE;

    if (m) {
        MULTIPLIER(6400 * strlen(*++f))
        memcpy(k + '+' + 'R', &z, 4);
        z+='$';
        memcpy(k + 'Y', &z, 4);
        fwrite(k + 'U', ',', 1, h);

        while (**f) {
            char* c = strrchr(k, *(*f)++);
            SET_ZERO(l, 'F' + ':')

            if (c) {
                w = c - k;
                double* n = l + w % 4 * 2;
                *n = n[16];
                n = l + 8 + (w / 4) % 4 * 2;
                *n = n[16];
            }

            for (w = 3200; w; --w) {
                MULTIPLIER(x += b * e2 * 49e2;)
                r = x;
                fwrite(&r, 2, 1, h);
                e2 += (w > 1300 ? 1 : -1) * 3e-2;
                e2 = (e2 > 1 ? 1 : (e2 > 0 ? e2 : 0));
            }
        }
    }
    else {
        if (READ_ERROR(k + '+' + 'V', ',')) {
            PRINT_ERROR(t + 18)
        }

        if (memcmp(k + '+' + '^', k + ']', 14)) {
            PRINT_ERROR(t)
        }

        memcpy(&n, k + '+' + 'n', 4);
        c = 16e3 / n;
        n = c;

        if (n != c) {
            ++n;
        }

        c /= n;

        memcpy(&v, k + 43 + 'x', 2);
        v /= 8;
        memcpy(&e, k + '+' + 'v', 2);

        if (!e || !v || v > 4) {
            goto int_16t;
        }

        e -= v;

        while (!READ_ERROR(k + 'Q', v)) {
            MULTIPLIER(e)
            
            while (z-- && getc(h) >= 0) {
                // do nothing
            }

            if (v > 1) {
                memcpy(&r, k + v + 'O', 2);
                g = r / 512.0 / '@';
            } else {
                g = (unsigned char)k['Q'] / 128.0 - 1;
            }

            for (p = n; p; --p) {
                aa = (e2 - g) * p / n + g;
                UPDATE_THRESHOLD(0)
                UPDATE_THRESHOLD(1)
                UPDATE_THRESHOLD(2)
                i += c;

                if (i >= 1) {
                    SET_ZERO(d, 16)
                    SET_ZERO(o, 8)
                    CHECK_THRESHOLD(COMPUTE_THRESHOLD, b = 0 a - d[6] * 17 a * 1 a; aa = d[6] * 1 a * 16 a; aa = b * b + aa * aa; x += aa; if (aa >= d[z / 8]) { o[z / 8] = z / 2; d[z / 8] = aa; })

                    if ((r = w ? x > d[7] * 0.45 : x > d[7] * 0.6)) {
                        if (!w) {
                            SET_ZERO(l + 32, 128)
                        }

                        if (x > d[7]) {
                            d[7] = x;
                        }

                        ++l[16 + o[1] * 4 + *o];
                    } else {
                        PRINT_TONE
                    }

                    memmove(d + 2, d + 3, 24);
                    --i;
                }
            }

            e2 = g;
        }

        if (ferror(h)) {
            goto fread;
        }

        PRINT_TONE
        puts("0123456789" + 10);
    }

    return 0;

int_16t:
    PRINT_ERROR(t)
}
```