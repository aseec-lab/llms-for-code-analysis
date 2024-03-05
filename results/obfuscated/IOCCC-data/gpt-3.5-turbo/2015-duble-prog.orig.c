The deobfuscated version of the code is as follows:

```c
#include <sys/ioctl.h>
#include <unistd.h>
#include <string.h>
#include <signal.h>
#include <stdlib.h>
#include <stdio.h>

#define O_o "sfX4.Fv8H!`uf\"|~0y'vWtA@:LcO9d}y.!uL!Gd+ml(<+Ds!Je.6!r!%l6G!n~^<i=%pEwL%P!'<!FQt%u 5toG57j/3!:E%;!ea!!!WqE0z!f/y}!%!!Qi6!uzt!n}?]!bl!ak!SetR<Zj$x!~V!n&g8!cK! KrgR'8@c]!%-q9V.3fa[E8X%dY'w!#H <P~6?guhljL!^P% ?\"8!@dP,!!o+fb\"!pv!;!Hm%Ro4n:}nkD!Q!kNe:| 'b5sc!e"

#define mu(a) a a a a a

#define O_(Q_) "\033[" #Q_
#define Q_(O) mu(mu(mu(O)))

#define main() main(){signal(13,1),_();}

char* O0 = O_o;
char OO;
char* Q1;
char O5[97];
int* Q5;
int _Q = 0;
int Q0 = 0;
int _O = 0;
int _0 = 0;
int O = 5;
int QQ;
int O6;
int Q6;
int O3;
int Q4;
int O4 = 41088;
int O1 = sizeof(O5);
int O7 = 234;
long long __;

_() {
    Q_({
        int* Q3;
        int Q2;
        int O2;
        int OQ;
        int QO;

        int O9 = O;
        int O8 = !!getenv("DRAFT");
        long long Q8;
        char* Q9 = O_(1A) O_(%dB) O_(%dC) O_(34m)"\xe2%c%c\r\n" O_(0m) O_(%dA);
        char* Q7;

        __ += (__ * 92 + *O0 - 35 - __) * (QO = Q_(!!) (*O0 - 33) * !O9--);
        O0 += O1 * QO;
        if (QO) {
            _();  
        }

        Q Q4 && (O--, _(), O0 += 194, O++, Q4--, _());

        Q O = 0;
        __ = 0;
        _();

        O = 3;
        _();

        Q __ && (_O += ((OQ = __ & 15) < 2) * 12 + !(QO = OQ & 14 ^ 2) * (4 - _O) + (OQ == 6) * (12 - 2 * _O) + (OQ > 6) * (9 - (OQ - 7) % 3), _Q += !QO * (_Q % QQ + (OQ & 1) * O3 - _Q), _0 += !_0 * !QO + (1 - 2 * _0) * !(OQ ^ 4), (OQ == 5) && (__ >>= 4, Q8 = __, Q7 = O0, Q4 = __ & 15, O = 1, _() | ++O & _(), O0 = Q7, __ = Q8));

        Q3 = (Q2 = (_Q += (lrand48() < O4 * O1 * 9) * O8 + (OQ > 5) * ((2 < (_O %= 8) % 6) - (_O % 7 < 2)) * QQ + ((_O + 7) % 8 < 3) - (_O > 4))) / O3) * O6 + Q5 + (O2 = _Q % QQ) / 2;
        *Q3 = *Q3 % O4 + O4 | (1 << Q_("@CADBE" "HI")[_Q % O3 / QQ * 2 + _Q % 2] - 64) * _0;
        sprintf(O5, Q9, Q2 += 1, O2 / 2, *Q3 >> 8, *Q3 & 85 * 3, Q2);

        if (_0 && (Q1 = O5, O = 8, _(), O = 7, Q2 = (lrand48() < O4 * _0 / (3 * O8 + 1)), !_Q && (OQ = OO - 10) | !(Q2 = OO - 32) * (Q0 + 58 > QQ) | (Q0 + 12 > QQ))) {
            Q1 = O_(3B);
            _();
            write(1, "> ", 2);
        }

        Q0 += !QO * !Q2 * 4 - QO * Q0;

        if (O2 * Q2) {
            if (!Q0) {
                memset(Q5, 0, 3 * O3);
                Q0 = 4;
                Q1 = "\n\n\n" O_(3A);
                _();
            }
            O = 7;
            _Q = 7 * QQ + Q0 + 2;
            _O = _0 = 0;

            OO += (OO > 64 && OO < 91) * 32;
            O0 = Q_(O_o) + O7;
            _();
            
            if ((*O0 - OO) || (*(O0 += O1) - 33)) {
                O = 0;
                _();
                O = 7;
                O0 += O1;
                _();
            }
        }

        write(0, Q1, strlen(Q1));
    })

    /*+++++ IOCCC 2015 +++++*/
}

int main() {
    puts("hello world!");
}
```

The code is still quite obfuscated and hard to understand. It seems to have various control flow paths and functions that perform calculations and operations based on the input. The main function simply prints "hello world!".