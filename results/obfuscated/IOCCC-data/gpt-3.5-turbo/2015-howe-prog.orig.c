Sure! Here's the deobfuscated code:

```c
#include <err.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <getopt.h>

typedef struct elem {
    int op;
    int x;
    int y;
    long a;
    long b;
    struct elem *n;
} Elem;

typedef struct {
    int y;
    Elem *e;
} Block;

typedef struct {
    long q;
    unsigned long y;
} Hash;

typedef struct {
    unsigned long z;
    unsigned long j;
    Hash b[1];
} HashTable;

int ignore_indentation = 0;
int show_difference = 0;
HashTable *hashTableA, *hashTableB;
FILE *fileA, *fileB;
Block *blocksA, *blocksB;

HashTable *createHashTable(HashTable *oldHashTable) {
    unsigned long size;
    HashTable *newHashTable;
    size = oldHashTable ? oldHashTable->z << 1 : 65536;
    if (!(newHashTable = realloc(oldHashTable, sizeof(*oldHashTable) + size * sizeof(oldHashTable->b))))
        err(2, 0);
    newHashTable->z = size;
    if (!oldHashTable)
        newHashTable->j = 0;
    return newHashTable;
}

HashTable *createHash(FILE *file) {
    unsigned long lineNumber;
    HashTable *hashTable;
    long bytesRead, offset, newline;
    unsigned char buffer[65536], *b;

    hashTable = createHashTable(NULL);
    lineNumber = 1;
    offset = 0;
    hashTable->b[lineNumber].q = offset;
    while (0 < (bytesRead = fread(buffer, 1, sizeof(buffer), file))) {
        b = buffer;
        while (0 < bytesRead--) {
            hashTable->b[lineNumber].y ^= *b;
            hashTable->b[lineNumber].y += (hashTable->b[lineNumber].y << 1) + (hashTable->b[lineNumber].y << 4)
                                         + (hashTable->b[lineNumber].y << 7) + (hashTable->b[lineNumber].y << 8)
                                         + (hashTable->b[lineNumber].y << 24);
            offset++;
            if (*b++ == '\n') {
                lineNumber++;
                hashTable->b[lineNumber].q = offset;
                if (hashTable->z <= lineNumber)
                    hashTable = createHashTable(hashTable);
                hashTable->b[lineNumber].y = 0;
            }
        }
    }
    hashTable->j = lineNumber - 1;
    rewind(file);
    return hashTable;
}

FILE *openFile(char *filename) {
    FILE *file;
    int i;
    if (!(file = fopen(filename, "r")) && !strcmp("-", filename)) {
        if ((file = tmpfile())) {
            while ((i = getchar()) != EOF)
                fputc(i, file);
            rewind(file);
        }
    }
    if (!file)
        err(2, "%s", filename);
    return file;
}

void writeDifference(FILE *file) {
    int c;
    while ((c = fgetc(file)) != EOF) {
        putchar(c);
        if (c == '\n')
            break;
    }
}

Elem *reverseList(Elem *list) {
    Elem *curr, *prev, *next;
    prev = NULL;
    curr = list;
    while (curr) {
        next = curr->n;
        curr->n = prev;
        prev = curr;
        curr = next;
    }
    return prev;
}

void printDiff(Elem *diffList) {
    Elem *curr, *next;
    curr = reverseList(diffList);
    while (curr) {
        next = curr->n;
        if (curr->op) {
            Elem *temp = curr;
            while (next && next->op && temp->y + 1 == next->y) {
                temp = next;
                next = next->n;
            }
            if (curr->y < temp->y)
                printf("%da%d,%d\n", curr->x, curr->y, temp->y);
            else
                printf("%da%d\n", curr->x, curr->y);
            fseek(fileB, curr->b, SEEK_SET);
            for (; curr != next; curr = curr->n) {
                printf("> ");
                writeDifference(fileB);
            }
        } else {
            Elem *temp = curr;
            while (next && !next->op && temp->x + 1 == next->x) {
                temp = next;
                next = next->n;
            }
            if (curr->x < temp->x)
                printf("%d,%dd%d\n", curr->x, temp->x, curr->y);
            else
                printf("%dd%d\n", curr->x, curr->y);
            fseek(fileA, curr->a, SEEK_SET);
            for (; curr != next; curr = curr->n) {
                printf("< ");
                writeDifference(fileA);
            }
        }
    }
}

void calculateDifference(int k) {
    Block above = blocksA[k - 1];
    Block below = blocksA[k + 1];
    int x, y, op;
    Elem *prev;

    if (below.y < above.y + 1) {
        op = 1;
        y = above.y + 1;
        prev = above.e;
    } else {
        op = 0;
        y = below.y;
        prev = below.e;
    }

    x = y - k;

    if (0 < y || 0 < x) {
        Elem *elem = malloc(sizeof(*elem));
        elem->x = x;
        elem->y = y;
        elem->n = prev;
        elem->op = op ^ ignore_indentation;
        elem->a = hashTableA->b[x].q;
        elem->b = hashTableB->b[y].q;
        if (ignore_indentation) {
            long temp = elem->a;
            elem->a = elem->b;
            elem->b = temp;
            elem->x = y;
            elem->y = x;
        }
        blocksA[k].e = elem;
    }

    while (x < hashTableA->j && y < hashTableB->j && hashTableA->b[x + 1].y == hashTableB->b[y + 1].y) {
        x++;
        y++;
    }

    blocksA[k].y = y;
}

int diff() {
    int k, p, d;
    if (hashTableA->j > hashTableB->j) {
        void *temp = hashTableA;
        hashTableA = hashTableB;
        hashTableB = temp;
        ignore_indentation = 1;
    }
    d = hashTableB->j - hashTableA->j;
    
    if (!(blocksA = calloc(hashTableA->j + hashTableB->j + 3, sizeof(*blocksA))))
        err(2, 0);

    for (k = 0; k < hashTableA->j + hashTableB->j + 3; k++)
        blocksA[k].y = -1;

    blocksB = blocksA + hashTableA->j + 1;
    p = -1;
    
    do {
        p++;
        for (k = -p; k < d; k++) {
            calculateDifference(k);
        }
        for (k = d + p; d <= k; k--) {
            calculateDifference(k);
        }
    } while (blocksA[d].y != hashTableB->j);

    if (!show_difference)
        printDiff(blocksA[d].e);

    free(blocksA);
    return d + 2 * p;
}

int main(int argc, char **argv) {
    int i;

    while ((i = getopt(argc, argv, "d")) != -1) {
        switch (i) {
            case 'd':
                show_difference = 1;
                break;
            default:
                optind = argc;
                break;
        }
    }

    if (argc <= optind)
        errx(2, "usage: %s [-d] file1 file2", *argv);

    fileA = openFile(argv[optind]);
    fileB = openFile(argv[optind + 1]);

    if (!(hashTableA = createHash(fileA)))
        err(2, "%s", argv[optind]);

    if (!(hashTableB = createHash(fileB)))
        err(2, "%s", argv[optind + 1]);

    i = diff();

    if (show_difference)
        printf("%d\n", i);

    return i > 0;
}
```