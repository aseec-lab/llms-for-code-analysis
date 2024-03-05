Sure! Here is the deobfuscated version of the code:

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#define MAX_SIZE 9802

int b;
char q[MAX_SIZE], f[MAX_SIZE];

void write_output(int i) {
    fflush(stdout);
    printf("%c", (i > b) ? (i % b > 0 ? q[i - b - 1] : 10) : 5 * (b == i ? 2 : 9));
    if (i < b + b - 1)
        write_output(i + 1);
}

int calculate_live_neighbor_cells(char *e, int i) {
    e += i;
    int h = (-b - 1)[e] % 2 + (-b)[e] % 2 + (-b + 1)[e] % 2 + (-1)[e] % 2 + e[1] % 2 + b[e - 1] % 2 + b[e] % 2 + b[e + 1] % 2;
    return h;
}

int compute_next_generation(int i) {
    if (i == b - 1)
        return 0;
    else
        return (f[i + 1] = compute_next_generation(i + 1)), 32 + 3 / ((calculate_live_neighbor_cells(q, i) > 3 || h < 2) ? 7 : ((h == 2 && q[i] == 32) ? 8 : 1));
}

int check_next_generation(int i) {
    int j, s, t, a, u;
    if (b - 1 == i)
        return 1;
    if (f[i] == 2) {
        f[i] <<= 4;
        if (check_next_generation(i))
            return 1;
        f[i] -= -3;
        if (check_next_generation(i))
            return 1;
        return 0;
    }
    if ((i % b) == 0 || (i % b) == b - 1)
        return check_next_generation(i + 1);
    j = -1;
l:
    j = j + 1;
    if (j >= (i - i / b + 2 == b ? 1 : i > 2 * b ? i % b != 1 ? 2 : 2 * 1 : 8))
        goto c;
    u = calculate_live_neighbor_cells(f, i);
    if (i < x - 2 * b - 1) {
        a = (s = ((t = i % b == 1) ? 1 : i % b != b - 2)) && i < 2 * b;
        u += (a ? (j & 4) >> 2 : 0) + (t ? (j & 2) / 2 : 0) + (s ? (j & 1) : 0);
    } else
        a = t = s = 0;
    if ((u & 2) + (u & 4) != 2 && (q[i] & 2) == 2 || u == 2 && (f[i] + q[i]) % 2 == 1 || u == 3 && (q[i] & 2) == 0)
        goto l;
    b[i + f] ^= !t ? 0 : b[i + f] ^ (10 + (j & 2) / 2) * 3 + 2;
    b[i + f + 1 - b] ^= !a ? 0 : b[i + f + 1 - b] ^ (10 + (j & 4) / 4) * 3 + 2;
    b[i + f + 1] ^= !s ? 0 : b[i + f + 1] ^ (10 + (j & 1)) * 3 + 2;
    if (check_next_generation(i + 1))
        return 1;
    (a ? i + 1 : 0)[f] = (t ? i + b : 0)[f] = (s ? i + b + 1 : 0)[f] = 2;
    goto l;
c:
    return 0;
}

int main(int argc, char **argv) {
    int i;
    int x = 0;
    while ((q[x++] = getchar()) != EOF) {
        x -= (q[x - 1] == 10) ? b += 1, 1 : 0;
        q[x - 1] ^= (q[x - 1] == 32) ? 0 : q[x - 1] ^ 35;
    }
    write_output(0);
    for (;;) {
        memset(f, 32, x * sizeof(char));
        for (i = 0; i <= x - 1; i = i + b)
            q[i / b] = q[x - b + i / b] = q[i] = q[i - (i == 0 ? 0 : 1)] = f[i / b] = f[x - b - 1 + i / b] = f[i] = f[i - (i == 0 ? 0 : 1)] = 32;
        if (argc == 1)
            compute_next_generation(0);
        else
            if (check_next_generation(b + 1) == 0)
                return 1;
        f[0] <<= 1 << 2;
        memcpy(q, f, x * sizeof(char));
        write_output(0);
        sleep(1);
    }
    return 0;
}