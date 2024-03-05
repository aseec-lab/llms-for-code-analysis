


#include <stdio.h>
#include <string.h>
#include "nmglobal.h"
#include "emfloat.h"




void SetupCPUEmFloatArrays(InternalFPF *abase,
                InternalFPF *bbase,
                InternalFPF *cbase,
                ulong arraysize)
{
ulong i;
InternalFPF locFPF1,locFPF2;

extern n_int32 randnum(n_int32 lngval);
randnum((n_int32)13);

for(i=0;i<arraysize;i++)
{
        n_int32ToInternalFPF(randwc((n_int32)50000),&locFPF1);
 
        n_int32ToInternalFPF(randwc((n_int32)50000)+(n_int32)1,&locFPF2);
        DivideInternalFPF(&locFPF1,&locFPF2,abase+i);
 
        n_int32ToInternalFPF(randwc((n_int32)50000)+(n_int32)1,&locFPF2);
        DivideInternalFPF(&locFPF1,&locFPF2,bbase+i);
}
return;
}


ulong DoEmFloatIteration(InternalFPF *abase,
                InternalFPF *bbase,
                InternalFPF *cbase,
                ulong arraysize, ulong loops)
{
ulong elapsed;          
static uchar jtable[16] = {0,0,0,0,1,1,1,1,2,2,2,2,2,3,3,3};
ulong i;
#ifdef DEBUG
int number_of_loops;
#endif

elapsed=StartStopwatch();
#ifdef DEBUG
number_of_loops=loops-1; 
#endif


while(loops--)
{
        for(i=0;i<arraysize;i++)
                switch(jtable[i % 16])
                {
                        case 0: 
                                AddSubInternalFPF(0,abase+i,
                                  bbase+i,
                                  cbase+i);
                                break;
                        case 1: 
                                AddSubInternalFPF(1,abase+i,
                                  bbase+i,
                                  cbase+i);
                                break;
                        case 2: 
                                MultiplyInternalFPF(abase+i,
                                  bbase+i,
                                  cbase+i);
                                break;
                        case 3: 
                                DivideInternalFPF(abase+i,
                                  bbase+i,
                                  cbase+i);
                                break;
                }
#ifdef DEBUG
{
  ulong j[8];   
  int k;
  ulong i;
  char buffer[1024];
  if (number_of_loops==loops) 
    {
      j[0]=(ulong)2;
      j[1]=(ulong)6;
      j[2]=(ulong)10;
      j[3]=(ulong)14;
      j[4]=(ulong)(arraysize-14);
      j[5]=(ulong)(arraysize-10);
      j[6]=(ulong)(arraysize-6);
      j[7]=(ulong)(arraysize-2);
      for(k=0;k<8;k++){
	i=j[k];
	InternalFPFToString(buffer,abase+i);
	printf("%6ld: (%s) ",i,buffer);
	switch(jtable[i % 16])
	  {
	  case 0: strcpy(buffer,"+"); break;
	  case 1: strcpy(buffer,"-"); break;
	  case 2: strcpy(buffer,"*"); break;
	  case 3: strcpy(buffer,"/"); break;
	  }
	printf("%s ",buffer);
	InternalFPFToString(buffer,bbase+i);
	printf("(%s) = ",buffer);
	InternalFPFToString(buffer,cbase+i);
	printf("%s\n",buffer);
      }
    }
}
#endif
}
return(StopStopwatch(elapsed));
}


static void SetInternalFPFZero(InternalFPF *dest,
                        uchar sign)
{
int i;          

dest->type=IFPF_IS_ZERO;
dest->sign=sign;
dest->exp=MIN_EXP;
for(i=0;i<INTERNAL_FPF_PRECISION;i++)
        dest->mantissa[i]=0;
return;
}


static void SetInternalFPFInfinity(InternalFPF *dest,
                        uchar sign)
{
int i;          

dest->type=IFPF_IS_INFINITY;
dest->sign=sign;
dest->exp=MIN_EXP;
for(i=0;i<INTERNAL_FPF_PRECISION;i++)
        dest->mantissa[i]=0;
return;
}


static void SetInternalFPFNaN(InternalFPF *dest)
{
int i;          

dest->type=IFPF_IS_NAN;
dest->exp=MAX_EXP;
dest->sign=1;
dest->mantissa[0]=0x4000;
for(i=1;i<INTERNAL_FPF_PRECISION;i++)
        dest->mantissa[i]=0;

return;
}


static int IsMantissaZero(u16 *mant)
{
int i;          
int n;          

n=0;
for(i=0;i<INTERNAL_FPF_PRECISION;i++)
        n|=mant[i];

return(!n);
}


static void Add16Bits(u16 *carry,
                u16 *a,
                u16 b,
                u16 c)
{
u32 accum;              


accum=(u32)b;
accum+=(u32)c;
accum+=(u32)*carry;
*carry=(u16)((accum & 0x00010000) ? 1 : 0);     
*a=(u16)(accum & 0xFFFF);       
return;
}


static void Sub16Bits(u16 *borrow,
                u16 *a,
                u16 b,
                u16 c)
{
u32 accum;              

accum=(u32)b;
accum-=(u32)c;
accum-=(u32)*borrow;
*borrow=(u32)((accum & 0x00010000) ? 1 : 0);    
*a=(u16)(accum & 0xFFFF);
return;
}


static void ShiftMantLeft1(u16 *carry,
                        u16 *mantissa)
{
int i;          
int new_carry;
u16 accum;      

for(i=INTERNAL_FPF_PRECISION-1;i>=0;i--)
{       accum=mantissa[i];
        new_carry=accum & 0x8000;       
        accum=accum<<1;                 
        if(*carry)
                accum|=1;               
        *carry=new_carry;
        mantissa[i]=accum;              
}
return;
}


static void ShiftMantRight1(u16 *carry,
                        u16 *mantissa)
{
int i;          
int new_carry;
u16 accum;

for(i=0;i<INTERNAL_FPF_PRECISION;i++)
{       accum=mantissa[i];
        new_carry=accum & 1;            
        accum=accum>>1;
        if(*carry)
                accum|=0x8000;
        *carry=new_carry;
        mantissa[i]=accum;
}
return;
}



static void StickyShiftRightMant(InternalFPF *ptr,
                        int amount)
{
int i;          
u16 carry;      
u16 *mantissa;

mantissa=ptr->mantissa;

if(ptr->type!=IFPF_IS_ZERO)     
{
        
        if(amount>=INTERNAL_FPF_PRECISION * 16)
        {
                for(i=0;i<INTERNAL_FPF_PRECISION-1;i++)
                        mantissa[i]=0;
                mantissa[INTERNAL_FPF_PRECISION-1]=1;
        }
        else
                for(i=0;i<amount;i++)
                {
                        carry=0;
                        ShiftMantRight1(&carry,mantissa);
                        if(carry)
                                mantissa[INTERNAL_FPF_PRECISION-1] |= 1;
                }
}
return;
}





static void normalize(InternalFPF *ptr)
{
u16     carry;


while ((ptr->mantissa[0] & 0x8000) == 0)
{
        carry = 0;
        ShiftMantLeft1(&carry, ptr->mantissa);
        ptr->exp--;
}
return;
}


static void denormalize(InternalFPF *ptr,
                int minimum_exponent)
{
long exponent_difference;

if (IsMantissaZero(ptr->mantissa))
{
        printf("Error:  zero significand in denormalize\n");
}

exponent_difference = ptr->exp-minimum_exponent;
if (exponent_difference < 0)
{
        
        exponent_difference = -exponent_difference;
        if (exponent_difference >= (INTERNAL_FPF_PRECISION * 16))
        {
                
                SetInternalFPFZero(ptr, ptr->sign);
        }
        else
        {
                ptr->exp+=exponent_difference;
                StickyShiftRightMant(ptr, exponent_difference);
        }
}
return;
}



void RoundInternalFPF(InternalFPF *ptr)
{


if (ptr->type == IFPF_IS_NORMAL ||
        ptr->type == IFPF_IS_SUBNORMAL)
{
        denormalize(ptr, MIN_EXP);
        if (ptr->type != IFPF_IS_ZERO)
        {

                
                ptr->mantissa[3] &= 0xfff8;

                

        }
}
return;
}




static void choose_nan(InternalFPF *x,
                InternalFPF *y,
                InternalFPF *z,
                int intel_flag)
{
int i;


for (i=0; i<INTERNAL_FPF_PRECISION; i++)
{
        if (x->mantissa[i] > y->mantissa[i])
        {
                memmove((void *)x,(void *)z,sizeof(InternalFPF));
                return;
        }
        if (x->mantissa[i] < y->mantissa[i])
        {
                memmove((void *)y,(void *)z,sizeof(InternalFPF));
                return;
        }
}


if (!intel_flag)
        
        memmove((void *)x,(void *)z,sizeof(InternalFPF));
else
        
        memmove((void *)y,(void *)z,sizeof(InternalFPF));
return;
}



static void AddSubInternalFPF(uchar operation,
                InternalFPF *x,
                InternalFPF *y,
                InternalFPF *z)
{
int exponent_difference;
u16 borrow;
u16 carry;
int i;
InternalFPF locx,locy;  


switch ((x->type * IFPF_TYPE_COUNT) + y->type)
{
case ZERO_ZERO:
        memmove((void *)x,(void *)z,sizeof(InternalFPF));
        if (x->sign ^ y->sign ^ operation)
        {
                z->sign = 0; 
        }
        break;

case NAN_ZERO:
case NAN_SUBNORMAL:
case NAN_NORMAL:
case NAN_INFINITY:
case SUBNORMAL_ZERO:
case NORMAL_ZERO:
case INFINITY_ZERO:
case INFINITY_SUBNORMAL:
case INFINITY_NORMAL:
        memmove((void *)x,(void *)z,sizeof(InternalFPF));
        break;


case ZERO_NAN:
case SUBNORMAL_NAN:
case NORMAL_NAN:
case INFINITY_NAN:
        memmove((void *)y,(void *)z,sizeof(InternalFPF));
        break;

case ZERO_SUBNORMAL:
case ZERO_NORMAL:
case ZERO_INFINITY:
case SUBNORMAL_INFINITY:
case NORMAL_INFINITY:
        memmove((void *)y,(void *)z,sizeof(InternalFPF));
        z->sign ^= operation;
        break;

case SUBNORMAL_SUBNORMAL:
case SUBNORMAL_NORMAL:
case NORMAL_SUBNORMAL:
case NORMAL_NORMAL:
        
        memmove((void *)&locx,(void *)x,sizeof(InternalFPF));
        memmove((void *)&locy,(void *)y,sizeof(InternalFPF));

        
        exponent_difference = locx.exp-locy.exp;
        if (exponent_difference == 0)
        {
                
                if (locx.type == IFPF_IS_SUBNORMAL ||
                  locy.type == IFPF_IS_SUBNORMAL)
                        z->type = IFPF_IS_SUBNORMAL;
                else
                        z->type = IFPF_IS_NORMAL;

                
                z->sign = locx.sign;
                z->exp= locx.exp;
        }
        else
                if (exponent_difference > 0)
                {
                        
                        StickyShiftRightMant(&locy,
                                 exponent_difference);
                        z->type = locx.type;
                        z->sign = locx.sign;
                        z->exp = locx.exp;
                }
                else    
                {
                        
                        StickyShiftRightMant(&locx,
                                -exponent_difference);
                        z->type = locy.type;
                        z->sign = locy.sign ^ operation;
                        z->exp = locy.exp;
                }

                if (locx.sign ^ locy.sign ^ operation)
                {
                        
                        borrow = 0;
                        for (i=(INTERNAL_FPF_PRECISION-1); i>=0; i--)
                                Sub16Bits(&borrow,
                                        &z->mantissa[i],
                                        locx.mantissa[i],
                                        locy.mantissa[i]);

                        if (borrow)
                        {
                                
                                z->sign = locy.sign ^ operation;
                                borrow = 0;
                                for (i=(INTERNAL_FPF_PRECISION-1); i>=0; i--)
                                {
                                        Sub16Bits(&borrow,
                                                &z->mantissa[i],
                                                0,
                                                z->mantissa[i]);
                                }
                        }
                        else
                        {
                                
                        }

                        if (IsMantissaZero(z->mantissa))
                        {
                                z->type = IFPF_IS_ZERO;
                                z->sign = 0; 
                        }
                        else
                                if (locx.type == IFPF_IS_NORMAL ||
                                         locy.type == IFPF_IS_NORMAL)
                                {
                                        normalize(z);
                                }
                }
                else
                {
                        
                        carry = 0;
                        for (i=(INTERNAL_FPF_PRECISION-1); i>=0; i--)
                        {
                                Add16Bits(&carry,
                                        &z->mantissa[i],
                                        locx.mantissa[i],
                                        locy.mantissa[i]);
                        }

                        if (carry)
                        {
                                z->exp++;
                                carry=0;
                                ShiftMantRight1(&carry,z->mantissa);
                                z->mantissa[0] |= 0x8000;
                                z->type = IFPF_IS_NORMAL;
                        }
                        else
                                if (z->mantissa[0] & 0x8000)
                                        z->type = IFPF_IS_NORMAL;
        }
        break;

case INFINITY_INFINITY:
        SetInternalFPFNaN(z);
        break;

case NAN_NAN:
        choose_nan(x, y, z, 1);
        break;
}


RoundInternalFPF(z);
return;
}



static void MultiplyInternalFPF(InternalFPF *x,
                        InternalFPF *y,
                        InternalFPF *z)
{
int i;
int j;
u16 carry;
u16 extra_bits[INTERNAL_FPF_PRECISION];
InternalFPF locy;       

switch ((x->type * IFPF_TYPE_COUNT) + y->type)
{
case INFINITY_SUBNORMAL:
case INFINITY_NORMAL:
case INFINITY_INFINITY:
case ZERO_ZERO:
case ZERO_SUBNORMAL:
case ZERO_NORMAL:
        memmove((void *)x,(void *)z,sizeof(InternalFPF));
        z->sign ^= y->sign;
        break;

case SUBNORMAL_INFINITY:
case NORMAL_INFINITY:
case SUBNORMAL_ZERO:
case NORMAL_ZERO:
        memmove((void *)y,(void *)z,sizeof(InternalFPF));
        z->sign ^= x->sign;
        break;

case ZERO_INFINITY:
case INFINITY_ZERO:
        SetInternalFPFNaN(z);
        break;

case NAN_ZERO:
case NAN_SUBNORMAL:
case NAN_NORMAL:
case NAN_INFINITY:
        memmove((void *)x,(void *)z,sizeof(InternalFPF));
        break;

case ZERO_NAN:
case SUBNORMAL_NAN:
case NORMAL_NAN:
case INFINITY_NAN:
        memmove((void *)y,(void *)z,sizeof(InternalFPF));
        break;


case SUBNORMAL_SUBNORMAL:
case SUBNORMAL_NORMAL:
case NORMAL_SUBNORMAL:
case NORMAL_NORMAL:
        
        memmove((void *)&locy,(void *)y,sizeof(InternalFPF));

        
        if (IsMantissaZero(x->mantissa) || IsMantissaZero(y->mantissa))
                SetInternalFPFInfinity(z, 0);

        
        if (x->type == IFPF_IS_SUBNORMAL ||
            y->type == IFPF_IS_SUBNORMAL)
                z->type = IFPF_IS_SUBNORMAL;
        else
                z->type = IFPF_IS_NORMAL;

        z->sign = x->sign ^ y->sign;
        z->exp = x->exp + y->exp ;
        for (i=0; i<INTERNAL_FPF_PRECISION; i++)
        {
                z->mantissa[i] = 0;
                extra_bits[i] = 0;
        }

        for (i=0; i<(INTERNAL_FPF_PRECISION*16); i++)
        {
                
                carry = 0;
                ShiftMantRight1(&carry, locy.mantissa);
                if (carry)
                {
                        
                        carry = 0;
                        for (j=(INTERNAL_FPF_PRECISION-1); j>=0; j--)
                                Add16Bits(&carry,
                                        &z->mantissa[j],
                                        z->mantissa[j],
                                        x->mantissa[j]);
                }
                else
                {
                        carry = 0;
                }

                
                ShiftMantRight1(&carry, z->mantissa);
                ShiftMantRight1(&carry, extra_bits);
        }

        
        while ((z->mantissa[0] & 0x8000) == 0)
        {
                carry = 0;
                ShiftMantLeft1(&carry, extra_bits);
                ShiftMantLeft1(&carry, z->mantissa);
                z->exp--;
        }

        
        if (IsMantissaZero(extra_bits))
        {
                z->mantissa[INTERNAL_FPF_PRECISION-1] |= 1;
        }
        break;

case NAN_NAN:
        choose_nan(x, y, z, 0);
        break;
}


RoundInternalFPF(z);
return;
}



static void DivideInternalFPF(InternalFPF *x,
                        InternalFPF *y,
                        InternalFPF *z)
{
int i;
int j;
u16 carry;
u16 extra_bits[INTERNAL_FPF_PRECISION];
InternalFPF locx;       


switch ((x->type * IFPF_TYPE_COUNT) + y->type)
{
case ZERO_ZERO:
case INFINITY_INFINITY:
        SetInternalFPFNaN(z);
        break;

case ZERO_SUBNORMAL:
case ZERO_NORMAL:
        if (IsMantissaZero(y->mantissa))
        {
                SetInternalFPFNaN(z);
                break;
        }

case ZERO_INFINITY:
case SUBNORMAL_INFINITY:
case NORMAL_INFINITY:
        SetInternalFPFZero(z, x->sign ^ y->sign);
        break;

case SUBNORMAL_ZERO:
case NORMAL_ZERO:
        if (IsMantissaZero(x->mantissa))
        {
                SetInternalFPFNaN(z);
                break;
        }

case INFINITY_ZERO:
case INFINITY_SUBNORMAL:
case INFINITY_NORMAL:
        SetInternalFPFInfinity(z, 0);
        z->sign = x->sign ^ y->sign;
        break;

case NAN_ZERO:
case NAN_SUBNORMAL:
case NAN_NORMAL:
case NAN_INFINITY:
        memmove((void *)x,(void *)z,sizeof(InternalFPF));
        break;

case ZERO_NAN:
case SUBNORMAL_NAN:
case NORMAL_NAN:
case INFINITY_NAN:
        memmove((void *)y,(void *)z,sizeof(InternalFPF));
        break;

case SUBNORMAL_SUBNORMAL:
case NORMAL_SUBNORMAL:
case SUBNORMAL_NORMAL:
case NORMAL_NORMAL:
        
        memmove((void *)&locx,(void *)x,sizeof(InternalFPF));

        
        if (IsMantissaZero(locx.mantissa))
        {
                if (IsMantissaZero(y->mantissa))
                        SetInternalFPFNaN(z);
                else
                        SetInternalFPFZero(z, 0);
                break;
        }
        if (IsMantissaZero(y->mantissa))
        {
                SetInternalFPFInfinity(z, 0);
                break;
        }

        
        z->type = x->type;
        z->sign = x->sign ^ y->sign;
        z->exp = x->exp - y->exp +
                        ((INTERNAL_FPF_PRECISION * 16 * 2));
        for (i=0; i<INTERNAL_FPF_PRECISION; i++)
        {
                z->mantissa[i] = 0;
                extra_bits[i] = 0;
        }

        while ((z->mantissa[0] & 0x8000) == 0)
        {
                carry = 0;
                ShiftMantLeft1(&carry, locx.mantissa);
                ShiftMantLeft1(&carry, extra_bits);

                
                if (carry == 0)
                        for (j=0; j<INTERNAL_FPF_PRECISION; j++)
                        {
                                if (y->mantissa[j] > extra_bits[j])
                                {
                                        carry = 0;
                                        goto no_subtract;
                                }
                                if (y->mantissa[j] < extra_bits[j])
                                        break;
                        }
                
                carry = 0;
                for (j=(INTERNAL_FPF_PRECISION-1); j>=0; j--)
                        Sub16Bits(&carry,
                                &extra_bits[j],
                                extra_bits[j],
                                y->mantissa[j]);
                carry = 1;      
        no_subtract:
                ShiftMantLeft1(&carry, z->mantissa);
                z->exp--;
        }
        break;

case NAN_NAN:
        choose_nan(x, y, z, 0);
        break;
}


RoundInternalFPF(z);
}



static void n_int32ToInternalFPF(n_int32 mylong,
                InternalFPF *dest)
{
int i;          
u16 myword;     


if(mylong<(n_int32)0)
{       dest->sign=1;
        mylong=(n_int32)0-mylong;
}
else
        dest->sign=0;

dest->type=IFPF_IS_NORMAL;
for(i=0;i<INTERNAL_FPF_PRECISION;i++)
        dest->mantissa[i]=0;


if(mylong==0)
{       dest->type=IFPF_IS_ZERO;
        dest->exp=0;
        return;
}


dest->exp=32;
myword=(u16)((mylong >> 16) & 0xFFFFL);
dest->mantissa[0]=myword;
myword=(u16)(mylong & 0xFFFFL);
dest->mantissa[1]=myword;
normalize(dest);
return;
}

#ifdef DEBUG

static int InternalFPFToString(char *dest,
                InternalFPF *src)
{
InternalFPF locFPFNum;          
InternalFPF IFPF10;             
InternalFPF IFPFComp;           
int msign;                      
int expcount;                   
int ccount;                     
int i,j,k;                      
u16 carryaccum;                 
u16 mycarry;                    


switch(src->type)
{
        case IFPF_IS_NAN:
                memcpy(dest,"NaN",3);
                return(3);

        case IFPF_IS_INFINITY:
                if(src->sign==0)
                        memcpy(dest,"+Inf",4);
                else
                        memcpy(dest,"-Inf",4);
                return(4);

        case IFPF_IS_ZERO:
                if(src->sign==0)
                        memcpy(dest,"+0",2);
                else
                        memcpy(dest,"-0",2);
                return(2);
}


memcpy((void *)&locFPFNum,(void *)src,sizeof(InternalFPF));



n_int32ToInternalFPF((n_int32)10,&IFPF10);


msign=src->sign;

 
(&locFPFNum)->sign=0;

expcount=0;             



while(1)
{       AddSubInternalFPF(1,&locFPFNum,&IFPF10,&IFPFComp);
        if(IFPFComp.sign==0) break;
        MultiplyInternalFPF(&locFPFNum,&IFPF10,&IFPFComp);
        expcount--;
        memcpy((void *)&locFPFNum,(void *)&IFPFComp,sizeof(InternalFPF));
}


while(1)
{
        AddSubInternalFPF(1,&locFPFNum,&IFPF10,&IFPFComp);
        if(IFPFComp.sign!=0) break;
        DivideInternalFPF(&locFPFNum,&IFPF10,&IFPFComp);
        expcount++;
        memcpy((void *)&locFPFNum,(void *)&IFPFComp,sizeof(InternalFPF));
}


ccount=1;               
if(msign==0)
        *dest++='+';
else
        *dest++='-';



carryaccum=0;
while(locFPFNum.exp>0)
{
        mycarry=0;
        ShiftMantLeft1(&mycarry,locFPFNum.mantissa);
        carryaccum=(carryaccum<<1);
        if(mycarry) carryaccum++;
        locFPFNum.exp--;
}

while(locFPFNum.exp<0)
{
        mycarry=0;
        ShiftMantRight1(&mycarry,locFPFNum.mantissa);
        locFPFNum.exp++;
}

for(i=0;i<6;i++)
        if(i==1)
        {       
                *dest++='.';
                ccount++;
        }
        else
        {       
                *dest++=('0'+carryaccum);
                ccount++;

                carryaccum=0;
                memcpy((void *)&IFPF10,
                        (void *)&locFPFNum,
                        sizeof(InternalFPF));

                
                for(j=0;j<9;j++)
                {
                        mycarry=0;
                        for(k=(INTERNAL_FPF_PRECISION-1);k>=0;k--)
                                Add16Bits(&mycarry,&(IFPFComp.mantissa[k]),
                                        locFPFNum.mantissa[k],
                                        IFPF10.mantissa[k]);
                        carryaccum+=mycarry ? 1 : 0;
                        memcpy((void *)&locFPFNum,
                                (void *)&IFPFComp,
                                sizeof(InternalFPF));
                }
        }


*dest++='E';


ccount+=(int)sprintf(dest,"%4d",expcount);


return(ccount);

}

#endif
