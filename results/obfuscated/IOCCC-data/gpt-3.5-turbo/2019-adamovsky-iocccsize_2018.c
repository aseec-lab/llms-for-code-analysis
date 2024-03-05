Sure! Here's the deobfuscated version of the code:

```c
#include <ctype.h>
#include <stdio.h>
#include <string.h>
#include <limits.h>
#include <getopt.h>

#define FLAG_SILENCE    1
#define FLAG_KEEP       2
#define FLAG_RESERVED   4
#define FLAG_IOCCC      8

#define BUFFER_SIZE     521
#define MAX_SIZE        4096    /* IOCCC Rule 2 */
#define MAX_COUNT       2053    /* IOCCC Rule 2 */

static char usage[] =
"usage:  ioccc [-kirsch] < prog.c\n"
"\n"
"-k\t\tkeep block comments\n"
"-i\t\tprint official secondary IOCCC size to stdout\n"
"\t\tmax.size warnings to stderr, NOTE: implies -r -s\n"
"-r\t\tcount C reserved words as 1 byte\n"
"-s\t\tsuppress source output, write only the official size\n"
;

#define STRLEN(s)       (sizeof(s) - 1)

typedef struct {
    size_t length;
    const char *word;
} Word;

static Word cwords[] = {
    { STRLEN("#elif"), "#elif" },
    { STRLEN("#else"), "#else" },
    { STRLEN("#endif"), "#endif" },
    { STRLEN("#error"), "#error" },
    { STRLEN("#ident"), "#ident" },
    { STRLEN("#if"), "#if" },
    { STRLEN("#ifdef"), "#ifdef" },
    { STRLEN("#include"), "#include" },
    { STRLEN("#line"), "#line" },
    { STRLEN("#pragma"), "#pragma" },
    { STRLEN("#sccs"), "#sccs" },
    { STRLEN("#warning"), "#warning" },

    { STRLEN("_Alignas"), "_Alignas" },
    { STRLEN("_Alignof"), "_Alignof" },
    { STRLEN("_Atomic"), "_Atomic" },
    { STRLEN("_Bool"), "_Bool" },
    { STRLEN("_Complex"), "_Complex" },
    { STRLEN("_Generic"), "_Generic" },
    { STRLEN("_Imaginary"), "_Imaginary" },
    { STRLEN("_Noreturn"), "_Noreturn" },
    { STRLEN("_Pragma"), "_Pragma" },
    { STRLEN("_Static_assert"), "_Static_assert" },
    { STRLEN("_Thread_local"), "_Thread_local" },

    { STRLEN("alignas"), "alignas" },
    { STRLEN("alignof"), "alignof" },
    { STRLEN("and"), "and" },
    { STRLEN("and_eq"), "and_eq" },
    { STRLEN("auto"), "auto" },
    { STRLEN("bitand"), "bitand" },
    { STRLEN("bitor"), "bitor" },
    { STRLEN("bool"), "bool" },
    { STRLEN("break"), "break" },
    { STRLEN("case"), "case" },
    { STRLEN("char"), "char" },
    { STRLEN("compl"), "compl" },
    { STRLEN("const"), "const" },
    { STRLEN("continue"), "continue" },
    { STRLEN("default"), "default" },
    { STRLEN("do"), "do" },
    { STRLEN("double"), "double" },
    { STRLEN("else"), "else" },
    { STRLEN("enum"), "enum" },
    { STRLEN("extern"), "extern" },
    { STRLEN("false"), "false" },
    { STRLEN("float"), "float" },
    { STRLEN("for"), "for" },
    { STRLEN("goto"), "goto" },
    { STRLEN("I"), "I" },
    { STRLEN("if"), "if" },
    { STRLEN("inline"), "inline" },
    { STRLEN("int"), "int" },
    { STRLEN("long"), "long" },
    { STRLEN("noreturn"), "noreturn" },
    { STRLEN("not"), "not" },
    { STRLEN("not_eq"), "not_eq" },
    { STRLEN("or"), "or" },
    { STRLEN("or_eq"), "or_eq" },
    { STRLEN("register"), "register" },
    { STRLEN("restrict"), "restrict" },
    { STRLEN("return"), "return" },
    { STRLEN("short"), "short" },
    { STRLEN("signed"), "signed" },
    { STRLEN("sizeof"), "sizeof" },
    { STRLEN("static"), "static" },
    { STRLEN("static_assert"), "static_assert" },
    { STRLEN("struct"), "struct" },
    { STRLEN("switch"), "switch" },
    { STRLEN("thread_local"), "thread_local" },
    { STRLEN("true"), "true" },
    { STRLEN("typedef"), "typedef" },
    { STRLEN("union"), "union" },
    { STRLEN("unsigned"), "unsigned" },
    { STRLEN("void"), "void" },
    { STRLEN("volatile"), "volatile" },
    { STRLEN("while"), "while" },
    { STRLEN("xor"), "xor" },
    { STRLEN("xor_eq"), "xor_eq" },

    { 0, NULL }
};

static Word *
find_member(Word *table, const char *string)
{
    Word *w;
    for (w = table; w->length != 0; w++) {
        if (strncmp(string, w->word, w->length) == 0
            && !isalnum(string[w->length]) && string[w->length] != '_')
            return w;
    }
    return NULL;
}

static size_t
read_line(char *buf, size_t size)
{
    int ch;
    size_t length;

    if (buf == NULL || size == 0)
        return 0;

    for (size--, length = 0; length < size; ) {
        if ((ch = fgetc(stdin)) == EOF)
            break;
        if (ch == '\0')
            ch = ' ';
        if (ch == '\r') {
            xbcount++;
            continue;
        }
        if (2 <= length && buf[length-2] == '?' && buf[length-1] == '?') {
            char *tri;
            if ((tri = strchr(trigraph, ch)) != NULL) {
                ch = asciimap[tri - trigraph];
                xbcount += 2;
                length -= 2;
            }
        }
        if (ch == '\n' && 1 <= length && buf[length-1] == '\\') {
            xbcount += 2;
            xwcount++;
            xlcount++;
            length--;
            continue;
        }
        buf[length++] = (char) ch;
        if (ch == '\n') {
            break;
        }
    }

    buf[length] = '\0';

    return length;
}

static int
count(int flags)
{
    Word *w;
    unsigned long span;
    char *p, buf[BUFFER_SIZE];
    int lcount, wcount, bcount;
    int is_comment, is_word, dquote, escape;
    int count, keywords, saved, kw_saved;

    buf[0] = ' ';
    buf[BUFFER_SIZE - 1] = 0;

    count = saved = 0;
    keywords = kw_saved = 0;
    lcount = wcount = bcount = 0;
    is_comment = is_word = dquote = escape = 0;

    while (0 < read_line(buf+1, sizeof(buf)-1)) {
        if (!(flags & FLAG_KEEP)) {
            span = strspn(buf+1, "\t ");
            if (buf[1 + span] == '/' && buf[2 + span] == '\0') {
                (void) ungetc('/', stdin);
                continue;
            }
            if (buf[1 + span] == '/' && buf[2 + span] == '/') {
                continue;
            }
            if (buf[1 + span] == '/' && buf[2 + span] == '*') {
                is_comment = 1;
            }
            if (!(flags & FLAG_KEEP)) {
                continue;
            }
        }

        for (p = buf+1; *p != '\0'; p++) {
            if (dquote) {
                if (escape) {
                    escape = 0;
                }
                else if (*p == '\\') {
                    escape = 1;
                }
                else if (*p == '"') {
                    dquote = 0;
                }
            }
            else {
                if (is_comment) {
                    if (*p == '*' && p[1] == '\0') {
                        ungetc('*', stdin);
                        break;
                    }
                    if (*p == '*' && p[1] == '/') {
                        is_comment = 0;
                        if (!(flags & FLAG_KEEP)) {
                            p += 1 + strspn(p+2, " \t\r\n");
                        }
                    }
                    if (!(flags & FLAG_KEEP)) {
                        continue;
                    }
                }
                else if (*p == '/' && p[1] == '\0') {
                    ungetc('/', stdin);
                    break;
                }
                else if (*p == '/' && p[1] == '/') {
                    if (!(flags & FLAG_KEEP)) {
                        break;
                    }
                }
                else if (*p == '/' && p[1] == '*') {
                    is_comment = 1;
                    if (!(flags & FLAG_KEEP)) {
                        p++;
                        continue;
                    }
                }
                else if (!isalnum(p[-1]) && p[-1] != '_'
                         && (w = find_member(cwords, p)) != NULL) {
                    keywords++;
                    if (flags & FLAG_RESERVED) {
                        bcount += w->length;
                        if (!is_word) {
                            is_word = 1;
                            wcount++;
                        }
                        if (!(flags & FLAG_SILENCE)) {
                            fputs(w->word, stdout);
                        }
                        kw_saved += w->length - 1;
                        p += w->length - 1;
                        count++;
                        continue;
                    }
                }
                else if (*p == '"') {
                    dquote = 1;
                }
            }

            if (!(flags & FLAG_SILENCE)) {
                fputc(*p, stdout);
            }

            bcount++;
            if (*p == '\n') {
                lcount++;
            }

            if (isspace(*p)) {
                is_word = 0;
                saved++;
                continue;
            } else if (!is_word) {
                is_word = 1;
                wcount++;
            }

            if (strchr("{;}", *p) != NULL && (isspace(p[1]) || p[1] == '\0')) {
                saved++;
                continue;
            }

            count++;
        }
    }

    if (flags & FLAG_IOCCC) {
        printf("%d\n", count);
        if (MAX_SIZE < bcount) {
            fprintf(stderr, "WARNING: program size (%d) exceeds IOCCC Rule 2a max. size (%d)\n", bcount, MAX_SIZE);
        }
        if (MAX_COUNT < count) {
            fprintf(stderr, "WARNING: iocccsize count (%d) exceeds IOCCC Rule 2b max. count (%d)\n", count, MAX_COUNT);
        }
    } else {
        fprintf(stderr, "%d %d %d %d %d %d %d\n", lcount + xlcount, wcount + xwcount, bcount + xbcount, count,
            saved, keywords, kw_saved);
    }

    return count;
}

int
main(int argc, char **argv)
{
    int ch;
    int flags = 0;

    while ((ch = getopt(argc, argv, "kirsch")) != -1) {
        switch (ch) {
            case 'k':
                flags |= FLAG_KEEP;
                break;
            case 'i':
                flags |= FLAG_IOCCC | FLAG_RESERVED | FLAG_SILENCE;
                break;
            case 'r':
                flags |= FLAG_RESERVED;
                break;
            case 's':
                flags |= FLAG_SILENCE;
                break;
            default:
                fprintf(stderr, "%s\n", usage);
                return 2;
        }
    }

    (void) count(flags);

    return 0;
}
```

This version is easier to read and understand compared to the original obfuscated code.