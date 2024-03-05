Below is the deobfuscated code:

```c
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>

char *keywords =
"  if door int for # if and not xor void char else goto case long enum auto bool true while break const float short union # else # elif # line _Bool false compl bit or_eq return sizeof struct double switch static extern signed # ifdef # endif inline # error bit and and_eq not_eq xor_eq typedef default # ifndef # pragma _Atomic _Pragma alignas alignof # include unsigned continue volatile register restrict # warning _Complex _Generic _Alignas _Alignof _Noreturn _Imaginary thread_local _Thread_local static_assert _Static_assert";

int cline, cword, cbyte, cioccc, ckey, cextra;
int tsaved, tkey;
int fkeep, fsuppress, freserved, fioccc, fcompat, ftoken;

unsigned char *buf, *cur, *end, *white, *start;

enum {
  CPP   = 256,
  CMT   = 257,
  KEYW  = 258,
  DIVEQ = 259,
  MULEQ = 260,
  MODEQ = 261,
  ADDEQ = 262,
  SUBEQ = 263,
  XOREQ = 264,
  OREQ  = 265,
  ANDEQ = 266,
  RSHEQ = 267,
  LSHEQ = 268,
  NOTEQ = 269,
  EQ    = 270,
  GE    = 271,
  LE    = 272,
  OR2   = 273,
  AND2  = 274,
  PP    = 275,
  MM    = 276,
  PTR   = 277,
  LSH   = 278,
  RSH   = 279,
  INT   = 300,
  FLOAT = 301,
  ID    = 302,
  DOT3  = 303
};

enum {
  O=0x01,
  H=0x02,
  D=0x04,
  I=0x08,
  W=0x10,
  B=0x20,
  OHD = O|H|D,
  HD  = H|D,
  HI  = H|I
};

char tab[256] = {
  W, 00, 00, 00, 00, 00, 00, 00, 00, W,  W,  W,  W,  W, 00, 00,
  00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00,
  W, 00, 00,  I, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00,
  OHD,OHD,OHD,OHD,OHD,OHD,OHD,OHD, HD, HD, 00,  B, 00, 00, 00, 00,

  00, HI, HI, HI, HI, HI, HI,  I,   I,  I,  I,  I,  I,  I,  I,  I,
  I,  I,  I,  I,  I,  I,  I,  I,   I,  I,  I, 00, 00, 00, 00,  I,
  00, HI, HI, HI, HI, HI, HI,  I,   I,  I,  I,  I,  I,  I,  I,  I,
  I,  I,  I,  I,  I,  I,  I,  I,   I,  I,  I,  B, 00,  B, 00, 00,
};

char tri[256] = {
  ['='] = '#',
  ['('] = '[',
  ['/'] = '\\',
  [')'] = ']',
  ['\'']= '^',
  ['<'] = '{',
  ['!'] = '|',
  ['>'] = '}',
  ['-'] = '~',
};

#define is(x, c) (tab[c]&(x))

void readsource() {
  int c, d, o = 2, len = 4096;
  buf = malloc(len);
  while ((c = getchar()) != EOF) {
    if (c == '\r') continue;
    if (buf[o-1] == '?' && buf[o-2] == '?' && tri[c]) {
      c = tri[c];
      o -= 2;
    }
    d = c;
    switch (buf[o-1]) {
      case '<': d = (c == ':' ? '[' : c == '%' ? '{' : c); break;
      case '%': d = (c == '>' ? '}' : c == ':' ? '#' : c); break;
      case ':': d = (c == '>' ? ']' : c); break;
    }
    if (c != d) {
      c = d;
      o--;
    }
    if (c == '\n' && buf[o-1] == '\\') {
      --o;
      cline++;
    } else {
      buf[o++] = c;
    }
    if (o == len) buf = realloc(buf, len += 4096);
  }
  buf[o] = 0;
  end = buf + o;
  cur = buf += 2;
}

void kwinit() {
  char *k = keywords;
  int nkwoff = 0;
  while (*k) {
    while (*k == ' ') {
      kwoff[++nkwoff] = ++k - keywords;
    }
    kwoff[++nkwoff] = ++k - keywords;
  }
}

int iskw(unsigned char *s, int len) {
  int found = 0;
  if (len < nkwoff) {
    char *off = keywords + kwoff[len];
    char *end = keywords + kwoff[len + 1] - 1;
    for (; !found && off < end; off += len) {
      found = *s == *off && !strncmp((char*)s, off, len);
    }
    if (found) {
      ckey++;
      tkey += len - 1;
    }
  }
  return found;
}

int want(char m) {
  while (cur < end) {
    if (*cur == '\\') {
      if (cur[1] == '\\' || cur[1] == '\'' || cur[1] == '"') {
        cur += 2;
        continue;
      }
    }
    if (*cur == '\n') {
      ++cline;
    }
    if (*cur++ == m) {
      return m;
    }
  }
  return 0;
}

int follow(char m, int y, int n) {
  if (*cur == m) {
    return ++cur, y;
  }
  return n;
}

int follow2(char m, int y, int n, int y2) {
  if (*cur == m) {
    return ++cur, y;
  }
  if (*cur == n) {
    return ++cur, y2;
  }
  return n;
}

int skipwhite() {
  int c;
  for (c = *cur++; cur <= end && is(W, c); c = *cur++) {
    ++cbyte;
    if (c == '\n') ++cline;
  }
  return c;
}

int lex() {
  int c, t;

  white = cur;
  c = skipwhite();
  start = cur - 1;

  if (cur >= end) return c;

  switch (c) {
    case '\'': want(c); return c;
    case '"': want(c); return c;
    case '/':
      t = follow2('=', DIVEQ, c, CMT);
      if (t == CMT) want('\n');
      else if (t == c && (t = follow('*', CMT, c)) == CMT) {
        do { want('*'); } while (cur < end && follow('/', 0, 1));
      }
      return t;
    case '*': return follow('=', MULEQ, c);
    case '%': return follow('=', MODEQ, c);
    case '=': return follow('=', EQ, c);
    case '!': return follow('=', NOTEQ, c);
    case '^': return follow('=', XOREQ, c);
    case '|': return follow2('=', OREQ, c, OR2);
    case '&': return follow2('=', ANDEQ, c, AND2);
    case '+': return follow2('=', ADDEQ, c, PP);
    case '-':
      t = follow2('=', SUBEQ, c, MM);
      return t == c ? follow('>', PTR, t) : t;
    case '>':
      t = follow2('=', GE, c, RSH);
      return t == RSH ? follow('=', RSHEQ, t) : t;
    case '<':
      t = follow2('=', LE, c, LSH);
      return t == LSH ? follow('=', LSHEQ, t) : t;
    case '#':
      if (ftoken || !fcompat) return want('\n'), CPP;
    case '.':
      if (cur[0] == c && cur[1] == c) return cur += 2, DOT3;
  }

  if (is(D, c)) {
    if (c == '0' && (*cur | 32) == 'x') c = *++cur;
    while (is(OHD, c)) c = *cur++;
    if (c == '.' || (c | 32) == 'e') {
      (void)strtod((char*)start, (char**)&cur);
      return FLOAT;
    }
    return --cur, INT;
  }

  if (is(I, c)) {
    while (is(I | D, c)) c = *cur++;
    --cur;
    return iskw(start, cur - start) ? KEYW : ID;
  }

  return c ? c : ' ';
}

void compat_words(int tlen, int inword) {
  int c;
  for (c = 0; c < tlen; ++c) {
    int isw = is(W, start[c]);
    if (isw || (is(B, start[c]) && is(W, start[c+1]))) {
      --cioccc;
    }
    if (inword) {
      inword = !isw;
    } else if (!isw) {
      inword = ++cword;
    }
  }
}

void compat_kw(int tlen, int inword) {
  int c, w = 0;
  for (w = c = 2; c < tlen; ++c) {
    int isw = !is(I | D, start[c]);
    if (inword) {
      inword = !isw;
      if (!inword && iskw(start + w, c - w)) {
        cioccc -= c - w - 1;
      }
    } else if (!isw) {
      inword = w = c;
    }
  }
}

void compat(int t, int tlen) {
  static int tprev;
  int c;
  if (tprev == CMT) {
    unsigned char *ow = white;
    while (ow < start && *ow++ != '\n') {}
    if (!fkeep && ow[-1] == '\n') {
      --cline;
    }
    cbyte -= ow -white;
    tprev = 0;
  }
  if (white == start && white[-1] == '\n') {
    --white;
  }
  if (t == CMT) {
    if (fkeep) {
      int inword = white == start && start > buf;
      cioccc += tlen;
      if (start[1] == '/') compat_kw(tlen, inword);
      compat_words(tlen, inword);
    } else {
      for (c = 0; c < tlen; ++c) {
        if (start[c] == '\n') {
          --cline;
        }
      }
    }
    unsigned char *os = start;
    while (os > white) {
      if (*--os == '\n') {
        break;
      }
    }
    if (*os == '\n' || os == buf || white[-1] == '\n') {
      cbyte -= start - os - (*os == '\n');
    }
    if (!fkeep && (white != start || start == buf) && !is(W, start[tlen]) && start[1] == '*') {
      ++cword;
    }
    if (start[1] == '*') {
      tprev = CMT;
    }
  } else if (t == '"' || t == '\'') {
    compat_words(tlen, 1);
  }
  if (t && t != CMT && white == start && start > buf) {
    --cword;
  }
  if (!fsuppress && (t != CMT || fkeep)) {
    if (!ftoken) {
      printf("%3d ", t);
    }
    printf("%.*s", tlen, start);
    if (cur[-1] != '\n') {
      printf("\n");
    }
  }
}

void count() {
  int t, tlen;
  kwinit();
  while ((t = lex())) {
    tlen = cur - start;
    if (t != CMT) {
      cbyte += tlen;
      ++cword;
      if (freserved && t == KEYW) {
        cioccc += 1;
      } else if (t < 256 && is(B, t) && (is(W, *cur) || !*cur)) {
      } else {
        cioccc += tlen;
      }
    }
    if (fcompat) {
      compat(t, tlen);
    } else if (!fsuppress && (t != CMT || fkeep)) {
      if (!ftoken) {
        printf("%3d ", t);
      }
      printf("%.*s", tlen, start);
      if (cur[-1] != '\n') {
        printf("\n");
      }
    }
  }

  if (fcompat) {
    compat(0, end - white);
  }

  if (fkeep) {
    cbyte = end - buf;
  }
  if (!freserved) {
    tkey = 0;
  }
  tsaved = cbyte - cioccc - tkey;
  cbyte += cextra;
}

int main(int ac, char **av) {
  static char usage[] = "[flags] [file]\n\
  -t  tokenize input\n\
  -c  compatible count and display token ids\n\
  -k  keep comments\n\
  -s  suppress source\n\
  -r  reserved words count as 1\n\
  -i  ioccc size (implies -rs)\n\
  -h  this help";

  fcompat = 1;

  int c;
  while ((c = getopt(ac, av, "?tcksrih")) != EOF) {
    switch (c) {
      case 't': ftoken    = 1;
      case 'c': fcompat   = 0; break;
      case 'k': fkeep     = 1; break;
      case 's': fsuppress = 1; break;
      case 'r': freserved = 1; break;
      case 'i': fioccc = freserved = fsuppress = fcompat = 1; break;
      case 'h':
      default:
        fprintf(stderr, "%s %s\n", av[0], usage);
        return 1;
    }
  }

  ac -= optind;
  av += optind;
  if (ac > 0 && !freopen(*av, "r", stdin)) {
    perror(*av);
    exit(1);
  }

  readsource();
  count();

  if (!ftoken) {
    if (fioccc) printf("%d\n", cioccc);
    else fprintf(stderr, "%d %d %d %d %d %d %d\n", cline, cword, cbyte, cioccc, tsaved, ckey, tkey);
  }
  return 0;
}
```