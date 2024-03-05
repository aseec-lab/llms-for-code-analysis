


#include <stdio.h>
#include "misc.h"








n_int32 randwc(n_int32 num)
{
	return(randnum((n_int32)0)%num);
}



u32 abs_randwc(u32 num)
{
n_int32 temp;		 

temp=randwc(num);
if(temp<0) temp=(n_int32)0-temp;

return((u32)temp);
}



n_int32 randnum(n_int32 lngval)
{
	register n_int32 interm;
	static n_int32 randw[2] = { (n_int32)13 , (n_int32)117 };

	if (lngval!=(n_int32)0)
	{	randw[0]=(n_int32)13; randw[1]=(n_int32)117; }

	interm=(randw[0]*(n_int32)254754+randw[1]*(n_int32)529562)%(n_int32)999563;
	randw[1]=randw[0];
	randw[0]=interm;
	return(interm);
}

