

















#include "hint.h"

DSIZE
Hint(DSIZE *scx, DSIZE *scy, DSIZE *dmax, ISIZE *mcnt, RECT *rect, 
                           DSIZE *errs, ISIZE *ixes, ERROR *eflag)
{
    DSIZE   errio,       
            errjo,       
            sh,          
            sl,          
            tm,          
            tm2;         

    ISIZE   inc, jnc,    
            io,          
            iq,          
            it,          
            itmp,        
            jo,          
            ma;          


   
      rect[0].xl = (DSIZE)0;    
      rect[0].xr = *scx;      
      rect[0].dx = *scx;     
      rect[0].fll = *scy;   
      rect[0].flh = *scy;  
      rect[0].frl = (DSIZE)0;
      rect[0].frh = (DSIZE)0;
      rect[0].ahi = *dmax;
      rect[0].alo = (DSIZE)0;
      iq = 0;
      errs[iq] = rect[0].ahi - rect[0].alo;
      ixes[iq] = iq;      
      sh = rect[0].ahi;
      sl = rect[0].alo;

      for (it = 0; ((it < *mcnt - 1) && (it <= iq)); it++) 
      {
          io = ma = ixes[it];        
          jo = it + 1;               

          tm = rect[ma].dx; 
       
          rect[io].dx  = rect[jo].dx = tm / (DSIZE)2;

       
          rect[jo].xr  = rect[ma].xr;
       
          rect[io].xr  = rect[io].xl + 
                         rect[io].dx; 
          rect[jo].xl  = rect[io].xr;
       
          rect[jo].frl = rect[ma].frl;
          rect[jo].frh = rect[ma].frh;
       

       
          tm =  (*scx + rect[io].xr); 
          tm2 = (*scy * (*scx - rect[io].xr));
          itmp = tm2 / tm;
          rect[io].frl = itmp;

       
          rect[io].frh = rect[io].frl + ((tm * rect[io].frl) != tm2);
          rect[jo].fll = rect[io].frl;
          rect[jo].flh = rect[io].frh;

       
          tm = (rect[io].fll - rect[io].frh) * (rect[io].dx - (DSIZE)2);
          if (tm < (DSIZE)0)
              tm = (DSIZE)0;
          errio = (rect[io].flh - rect[io].frh  + 
                   rect[io].fll - rect[io].frl) * 
                  (rect[io].dx - (DSIZE)1) - tm;

       
          tm = (rect[jo].fll - rect[jo].frh) * (rect[jo].dx - (DSIZE)2);
          if (tm < (DSIZE)0)
              tm = (DSIZE)0;
          errjo = (rect[jo].flh - rect[jo].frh  + 
                   rect[jo].fll - rect[jo].frl) * 
                  (rect[jo].dx - (DSIZE)1) - tm;

       
       
          inc = (errio < errjo) + 1;
          jnc = 3 - inc;

       
          errs[iq + inc] = errio;
          ixes[iq + inc] = io;
          errs[iq + jnc] = errjo;
          ixes[iq + jnc] = jo;

       
          iq = iq + (errs[iq + 2] != 0) + 1;

       
          tm = rect[ma].alo;
          rect[io].alo = rect[io].frl * rect[io].dx;
          rect[jo].alo = rect[jo].frl * rect[jo].dx;
          sl -= tm;                    
          sl += rect[io].alo + rect[jo].alo; 
          tm = rect[ma].ahi;
          rect[io].ahi = rect[io].flh * rect[io].dx;
          rect[jo].ahi = rect[jo].flh * rect[jo].dx;
          sh -= tm;
          sh += rect[io].ahi + rect[jo].ahi;
      }
      if (it > iq) {
          *eflag = DISCRET;
	  }
      else {
#pragma mips_frequency_hint FREQUENT
          *eflag = NOERROR;
	  }
      return  (sh - sl);
}
