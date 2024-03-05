Sure! Here's the deobfuscated code:

```C
#include<stdio.h>
#include<stdlib.h>
#include<assert.h>
#include <X11/Xlib.h>
#include <strings.h>
#include <math.h>
#include <assert.h>

#define MAXLEV 1024
#define W (32*40l)
#define H (32*40l)
#define HBITS 29

#define HSIZE (1ll<<HBITS)
#define HMASK (HSIZE -1)
#define MAXMEM (HSIZE)
typedef unsigned long ulong;

int g_step;
const unsigned mh=0xf0f0f0f;

ulong q8x8(ulong in)
{
    const ulong m4=0x1111111111111111;
    for(int j=0;j<2-!g_step;j++){
        ulong res=0;
        for(int i=0;i<4;i++) {
            ulong o=in>>i & m4;
            o+= in*2>>i & m4;
            o+= in/2>>i & m4;
            o+=(o << 8)+(o >> 8);
            res |= (((o+m4 & m4*6 ^ 3*m4)+m4 >> 3) & (in >> i | o) & m4) << i;
        }
        in=res;
    }
    return in>>18 & mh;
}

ulong *g_lmem;
unsigned *g_hash;

unsigned getquad(unsigned cell,int i){
     return g_lmem[cell+1]&8192 ? g_lmem[cell+(i&2)] >> i%2*32 : g_lmem[cell] >> i%2*4+16*(i&2)&mh ;
}

void mq(unsigned cell,unsigned *q)  {
     for(int j=0;j<2-!g_step;j++){
         ulong res=0;
         for(int i=0;i<4;i++) {
             ulong o=in>>i & m4;
             o+= in*2>>i & m4;
             o+= in/2>>i & m4;
             o+=(o << 8)+(o >> 8);
             res |= (((o+m4 & m4*6 ^ 3*m4)+m4 >> 3) & (in >> i | o) & m4) << i;
         }
         in=res;
     }
  return in>>18 & mh;
}

unsigned GQ(int i, int level, int flags) {
    return hget(q+(i),(level)*4+flags);
}

unsigned calc_result(unsigned cell,ulong leaf,ulong lh,unsigned *q,int level,int res_step) {
    if(!cell) {
        g_lmem[cell=g_ptr]=leaf;
        g_lmem[g_ptr+=2]=lh;
        g_ptr+= level>0;
    }
    if(!level) {
        g_lmem[cell+1]=res_step|(q8x8(leaf)<<32);
    } else {
        int j;
        unsigned q[16];
        mq(cell,q);
        for(int i=0;i<11;i++) if((i+1)&3)  q[i]=GQ(i,level-1,0);
        QUADLOOP(q[j]=GQ(j, level-1, hr));
        ulong result= GQ(0,level-1,2);
        g_lmem[cell+1]=res_step|8192|(result<<32);
    }
    return cell;
}

unsigned hget(unsigned* qi, int level) {
    ulong leaf,lh,ll;
    int lv=level>>2;
    unsigned hind=0;
    unsigned cell;
    ulong *data=(ulong *)qi;
    if(lv>0) {
        if(level&1) {
            unsigned q[6];
            QUADLOOP(q[j]=getquad(qi[j],i^3));
            return GQ(0,lv-1,2);
        }
        QUADLOOP(hind=917*hind+qi[j]);
        leaf=data[0];
        lh=data[2];
    } else {
        hind= (ll=qi[0]|qi[1]<<4) + 719*(lh=qi[4]|qi[5]<<4);
        if(level&1) return (ll>>18)&0xf0f | (lh&0x3c3c)<<14;
        leaf= ll | lh <<32;
    }
    for(; cell=g_hash[hind&=HMASK]; hind++) {
        if (leaf==g_lmem[cell] && (!lv || g_lmem[cell+2]==lh) && !!lv == (g_lmem[cell+1]>>13&1)) {
            break;
        }
    }
    int res_step= lv+1;
    if(res_step > g_step) res_step=g_step;
    if (!cell || g_lmem[cell+1]&8191^res_step) {
        cell=g_hash[hind]=calc_result(cell,leaf,lh,qi,lv,res_step);
    }
    return (level&2) ? cell: g_lmem[cell+1]>>32;
}

void init_empty_space(int maxlev) {
    unsigned q[16];
    unsigned cell=0;
    for(unsigned i=0;i<maxlev;i++) {
        QUADLOOP(q[j]=cell);
        cell=GQ(0,i,2);
        g_ptr+=!i;
    }
}

void error(char *s) {
    printf("%s\n",s);
    exit(1);
}

int getin(FILE *f,int *lv) {
    char c,line[100];
    static int lev,skip;
    unsigned cell,q[16];
    unsigned iptr=1;
    while(fgets(line,100,f)) {
        if(skip++ && line[0]!='#') {
            if(sscanf(line,"%d%d%d%d%d",&lev,q,q+1,q+4,q+5)==5){
                lev-=3;
                QUADLOOP(if(q[j]>=iptr)error("Bad node input data"));
                QUADLOOP(q[j]=q[j]?g_cells[q[j]]:lev*3-1);
            } else {
                lev=g_lmem[0]=0;
                int l=0,j=0,i=0;
                while(c=line[i++]){
                    if(c=='*') {
                        if(l*8+j>64) error("Bad leaf input data");
                        g_lmem[0]|=1l<<l*8+j;
                    }
                    j++;
                    if(c=='$')l++,j=0;
                }
                mq(0,q);
            }
            cell=g_cells[iptr++]=GQ(0,lev,2);
            if(iptr==MAXMOVES) error ("Input too big! increase MAXMOVES please\n");
        }
    }
    g_cells[0]=cell; *lv=g_cells[1]=lev;
    return cell;
}

unsigned g_cells[MAXMOVES];
double g_gens[MAXMOVES];

int g_freeze=0;

unsigned adv(unsigned cell,int *ilevel) {
    unsigned q[16];
    int level=*ilevel;
    int target=level+2;
    if(target >= MAXLEV) {
        g_freeze=1;
        return cell;
    }
    if(target<g_step) target=g_step;
    do {
        for(int i=0;i<16;i++) q[i]=3*level-1;
        mq(cell,q+5);
        QUADLOOP(q[j]=GQ(2*j,level,2));
        cell= GQ(0,++level,2);
    } while (level < target);
    cell=g_lmem[cell+1]>>32;
    while(--level > 0) {
        mq(cell,q+10);
        QUADLOOP(mq(q[10+j],q+2*j));
        for(int i=0;i<16;i++)  if((i^i/2)&5^5 && q[i] != 3*level-4) {*ilevel=level; return cell;}
        cell=GQ(5,level-1,2);
    }
}

#define MAXMOVES 500000
unsigned g_cells[MAXMOVES];
double   g_gens[MAXMOVES];

unsigned main(int ac,char **av) {
    int lev;
    if(ac!=2) error("Usage: apholife input.mc");
    FILE *f=fopen(av[1],"r");
    if(!f) error("Could not open input file!");
    unsigned cell=getin(f,&lev);

    double gen=0;
    bzero(g_out,(W+8*16)*H*4);
    Display *d=XOpenDisplay(NULL);
    Window win=XCreateSimpleWindow(d, RootWindow(d, 0), 0, 0, W, H, 1, 0, 0);
    XImage *xi= XCreateImage(d, DefaultVisual(d,0),24,2,0,(char *)g_out,W,H,32,g_stride*4);
    XMapWindow(d, win);
    XSelectInput(d, win, KeyPressMask|ButtonPressMask|ExposureMask);
    int curr=1;
    while(1) {
        bzero(g_out,(W+8*16)*H*4);
        draw(cell,lev,ldexp(-4,lev),ldexp(-4,lev),0,0);
        XPutImage(d, win, DefaultGC(d, 0), xi, 0, 0, 0, 0, W, H);
        processEvent(d, win, xi);
        if(g_adv) {
            if(!g_dir) {
                if(!g_freeze) {
                    int prev=g_ptr;
                    cell=adv(cell,&lev);
                    gen+=ldexp(1,g_step);
                    g_cells[curr+1]=cell; g_cells[curr+=2]=lev; g_gens[curr/2]=gen;
                    if(3*(g_ptr-prev) >= MAXMEM-g_ptr || curr>=MAXMOVES-3) {
                        g_freeze=curr;
                    }
                } else if(curr<g_freeze) {
                    cell=g_cells[curr+1]; lev=g_cells[curr+=2]; gen=g_gens[curr/2];
                }
            } else {
                if(curr>2) {
                    lev=g_cells[curr-=2]; cell=g_cells[curr-1]; gen=g_gens[curr/2];
                }
            }
            if(curr>2) {
                printf("G: %g  M: %d L%d S%d%s\n",gen,g_ptr,lev,g_pscale,g_freeze?" (Frozen)":"");
            }
        }
        if(g_sstep)g_adv=0;
    }
}
```

I removed unnecessary comments to make the code easier to read. However, please note that some variable names were not changed to maintain readability.