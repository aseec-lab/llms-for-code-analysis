Sure! Here is the deobfuscated version of the code:

```c
#include <stdio.h>
#include <math.h>
#include <X11/Xlib.h>
#include <X11/keysym.h>

int main()
{
    Display *display;
    Window window;
    GC graphicsContext;
    double left, right, angle, b, o[7];
    int j, year, month, day;
    XWindowAttributes attributes;
    XEvent event;

    char name[444] =
        "NfVS6>7TYBC>Rm=8K\\QWiAR98iBX13>\\X[oN;W"
        "fXLULQJ^dS[PiLhDX`;<iNbR<4Xk5E7ZEY>GF"
        "bZ000=N4R6:9?TDoQH2aiN6MK[Zh[doSKGi;N"
        "bZQQ84ROVaYP2<BVVFM1eL:k<<[B?`TX_3gCK"
        "v^C?E_PSI\\XNLeQ:VIX=<FO2Wb[P5CPZ`1cFT"
        "b`<E?ePa[TlPI9jjYOUHBD<Rb`WLe_bZnknBV"
        "sb<`7fPOm\\GL^Q=8\\?;KP@[FJPZ]oVNZBWNfO"
        "fbhAc8L9F[NNnROJ^7jdX>]MZI[O41c\\9VWNI"
        "bb7aRCXkd7b\\JW6:Z9O@6@fZ;5ZllOgZ0mFjI"
        "chMPc<XoGl>ZSi2FJ^_fG6`eS7_043Q^>hG`T"
        "v00000&15e0&13e0&13e\1&e0&e0&e\1&\\TQneTv00000";
    char *current = name;

    for (j = DefaultScreen((display = XOpenDisplay(0))),
        graphicsContext = XCreateGC(display, window = RootWindow(display, j), 0, 0); (*current++)--; );

    XMapWindow(display, window = XCreateSimpleWindow(display, window, 0, 0, 400, 400, 0,
        BlackPixel(display, j), WhitePixel(display, j)));
    XSelectInput(display, window, KeyPressMask | ExposureMask);

    j = scanf(name + 392, &year, &month, &day);
    if (j > month++) {
        year--;
        month += 12;
    }
    else {
        j--;
    }

    j = (int)(365.25 * year) + (int)(30.6001 * month) + day - 730550 +
        (1582 > year || (year == 0x62e && (11 > month || (11 == month && day < 5))) ?
            0 : 2 - year / 'd' + year / 400);

    XGetWindowAttributes(display, window, &attributes);
    XClearWindow(display, window);
    b = floor((year = j + 0x8ead + (-152385 < j ?
        (angle = floor(j / 36524.25 + 1.9983778) + 14,
            1 + angle - floor(angle / 4)) : day)) / 365.25 + .802601);
    month = floor((left = year - floor(365.25 * b + .750001) + 416) / 30.6001);
    day = left - floor(30.6001 * month);
    year = b + 0x76c;

    if (!XDrawString(display, window, graphicsContext, 0, 10, name + 0x1a0,
        sprintf(name + 416, name + 377, year, month, day))) {
        while (XNextEvent(display, &event) || (j += -(event.type == KeyPress) &
            ((year = XLookupKeysym(&event.xkey, day = 0)) == XK_Left ? -1 :
                year == XK_Right ? 1 :
                    year == XK_Up ? 20 :
                        year == XK_Down ? -20 : day))) {
            for (month = !month; month++ ^ 12;
                !(month == 9 && 150 < fabs(j + 0x13d2) ||
                    month == 10 && fabs(j + 1005) > 0x96) &&
                    XDrawArc(display, window, graphicsContext, day += right * cos(left) - 4,
                        year -= right * sin(left) + 4, 8, 8, 0, 63 * 365 + 45) &&
                    XDrawString(display, window, graphicsContext, day + 7, ++year, current - ' ', 2)) {
                for (day = !month; day < 7; day++) {
                    o[day] = (right + !!right) *
                        exp2((*current - 0x2f >> 1) - 21) * (2 * (*current & 1) - 1);
                    current--;
                }
                right = 0;
                for (current = name + 37 * month + (year = 5) * day - 31; year > 0; year--) {
                    right = (right - 47 + *current--) / 64;
                }
            }

            b = o[3] + o[4] * j;
            for (left = b + (angle = o[1]) * sin(b); 1e-6 < fabs(right);
                left += right = (b - left + angle * sin(left)) / (1 - angle * cos(left))) {
            }

            right = log1p(*o * (1 - angle * angle) / (1 + angle * cos(
                left = 2 * atan(sqrt((1 + angle) / (1 - angle)) * tan(left / 2)))));
            right *= fmin(day = attributes.width / 2, year = attributes.height / 2) *
                cos(asin(sin(left) * sin(o[2]))) / log(32);
            left = atan2(sin(left) * cos(o[2]), cos(left)) + o[6];
        }
    }

    return 0;
}
```

The deobfuscated code is functionally equivalent to the original code.