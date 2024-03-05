Sure! Here's the deobfuscated code:

```c
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <getopt.h>

typedef struct Entry {
    int op;
    int x;
    int y;
    long a;
    long b;
    struct Entry *next;
} Entry;

typedef struct {
    int y;
    Entry *entry;
} List;

typedef struct {
    long size;
    unsigned long long hash;
} Chunk;

typedef struct {
    unsigned long long size;
    unsigned long long num_chunks;
    Chunk chunks[1];
} HashTable;

int invert;
int print_diff_only;
HashTable *fileA, *fileB;
FILE *file1, *file2;
List *p, *q;

HashTable* createHashTable(HashTable *old_table) {
    unsigned long long size;
    HashTable *new_table;

    size = old_table ? old_table->size << 1 : 65536;

    new_table = realloc(old_table, sizeof(HashTable) + size * sizeof(new_table->chunks));
    if (!new_table)
        exit(2);

    new_table->size = size;

    if (!old_table)
        new_table->num_chunks = 0;

    return new_table;
}

HashTable* readFromFile(FILE *file) {
    unsigned long long hash;
    HashTable *table;
    long file_pos, offset, num_bytes;
    unsigned char buffer[65536], *buf;

    table = createHashTable(NULL);
    file_pos = 0;
    offset = 0;
    hash = 14695981039346656037ULL;
    table->chunks[offset].size = file_pos;

    while (0 < (num_bytes = fread(buffer, 1, sizeof(buffer), file))) {
        buf = buffer;
        while (0 < num_bytes--) {
            hash ^= *buf;
            hash += (hash << 1) + (hash << 4) + (hash << 5) + (hash << 7) + (hash << 8) + (hash << 40);
            file_pos++;

            if (*buf++ == '\n') {
                offset++;
                table->chunks[offset].hash = hash;
                if (table->size <= offset)
                    table = createHashTable(table);
                table->chunks[offset].size = file_pos;
                hash = 14695981039346656037ULL;
            }
        }
    }

    table->num_chunks = offset;
    rewind(file);
    return table;
}

FILE *openFile(char *filename) {
    FILE *file;

    if (!(file = fopen(filename, "r")) && strcmp(filename, "-") == 0) {
        if ((file = tmpfile())) {
            int c;
            while ((c = getchar()) != EOF)
                fputc(c, file);
            rewind(file);
        }
    }

    if (!file)
        exit(2);

    return file;
}

void writeFile(FILE *file) {
    int c;
    while ((c = fgetc(file)) != EOF) {
        putchar(c);
        if (c == '\n')
            break;
    }
}

Entry *reverseEntries(Entry *list) {
    Entry *a, *b;
    b = NULL;
    while (list) {
        a = list->next;
        list->next = b;
        b = list;
        list = a;
    }
    return b;
}

void printDiff(Entry *list) {
    Entry *a, *b;
    for (list = reverseEntries(list); list; ) {
        b = list;
        if (list->op) {
            do {
                a = b;
                b = b->next;
            } while (b && b->op && a->y + 1 == b->y);
            if (list->y < a->y)
                printf("%da%d,%d\n", list->x, list->y, a->y);
            else
                printf("%da%d\n", list->x, list->y);
            fseek(file2, list->b, SEEK_SET);
            for (; list != b; list = list->next) {
                printf("> ");
                writeFile(file2);
            }
        } else {
            do {
                a = b;
                b = b->next;
            } while (b && !b->op && a->x + 1 == b->x);
            if (list->x < a->x)
                printf("%d,%dd%d\n", list->x, a->x, list->y);
            else
                printf("%dd%d\n", list->x, list->y);
            fseek(file1, list->a, SEEK_SET);
            for (; list != b; list = list->next) {
                printf("< ");
                writeFile(file1);
            }
        }
    }
}

void compareFiles(int k) {
    List h = p[k - 1];
    List v = p[k + 1];
    int x, y, op;
    Entry *entry;

    if (v.y < h.y + 1) {
        op = 1;
        y = h.y + 1;
        entry = h.entry;
    } else {
        op = 0;
        y = v.y;
        entry = v.entry;
    }

    x = y - k;

    if (0 < y || 0 < x) {
        Entry *new_entry = malloc(sizeof(Entry));
        new_entry->x = x;
        new_entry->y = y;
        new_entry->next = entry;
        new_entry->op = op ^ invert;
        new_entry->a = fileA->chunks[x].size;
        new_entry->b = fileB->chunks[y].size;

        if (invert) {
            long c = new_entry->a;
            new_entry->a = new_entry->b;
            new_entry->b = c;
            new_entry->x = y;
            new_entry->y = x;
        }

        p[k].entry = new_entry;
    }

    while (x < fileA->num_chunks && y < fileB->num_chunks && fileA->chunks[x + 1].hash == fileB->chunks[y + 1].hash) {
        x++;
        y++;
    }
    p[k].y = y;
}

int diffFiles(void) {
    int k, p, d;

    if (fileA->num_chunks > fileB->num_chunks) {
        HashTable *temp = fileA;
        fileA = fileB;
        fileB = temp;
        invert = 1;
    }

    d = fileB->num_chunks - fileA->num_chunks;

    if (!(q = calloc(fileA->num_chunks + fileB->num_chunks + 3, sizeof(List)))) {
        exit(2);
    }

    p = -1;

    do {
        p++;
        for (k = -p; k < d; k++) {
            compareFiles(k);
        }
        for (k = d + p; d <= k; k--) {
            compareFiles(k);
        }
    } while (p[k].y != fileB->num_chunks);

    if (!print_diff_only)
        printDiff(p[d].entry);

    free(q);

    return d + 2 * p;
}

int main(int argc, char **argv) {
    int i;

    while ((i = getopt(argc, argv, "d")) != -1) {
        switch (i) {
            case 'd':
                print_diff_only = 1;
                break;
            default:
                optind = argc;
                break;
        }
    }

    if (argc <= optind)
        exit(2);

    file1 = openFile(argv[optind]);
    file2 = openFile(argv[optind + 1]);

    fileA = readFromFile(file1);
    fileB = readFromFile(file2);

    i = diffFiles();

    if (print_diff_only) {
        printf("%d\n", i);
    }

    return i > 0;
}
```

Hope that helps! Let me know if you have any further questions.