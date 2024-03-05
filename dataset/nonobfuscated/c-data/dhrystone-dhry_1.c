

#include "dhry.h"



Rec_Pointer     Ptr_Glob,
                Next_Ptr_Glob;
int             Int_Glob;
Boolean         Bool_Glob;
char            Ch_1_Glob,
                Ch_2_Glob;
int             Arr_1_Glob [50];
int             Arr_2_Glob [50] [50];

extern void     *malloc ();

  

#ifndef REG
        Boolean Reg = false;
#define REG
        
        
#else
        Boolean Reg = true;
#endif



#ifdef TIMES
struct tms      time_info;
extern  int     times ();
                
#define Too_Small_Time (2*HZ)
                
#endif
#ifdef TIME
extern long     time();
                
#define Too_Small_Time 2
                
#endif
#ifdef MSC_CLOCK
extern clock_t	clock();
#define Too_Small_Time (2*HZ)
#endif

#ifndef FLOAT
	Boolean Float_Rating = false;
#else
	Boolean Float_Rating = true;
#endif

long            Begin_Time,
                End_Time,
                User_Time;
measure         Microseconds,
                Dhrystones_Per_Second;




Enumeration Func_1 (Capital_Letter Ch_1_Par_Val, Capital_Letter Ch_2_Par_Val);
void Proc_1 (REG Rec_Pointer Ptr_Val_Par);

int main (int argc, char **argv)


  
  
{
        One_Fifty       Int_1_Loc;
  REG   One_Fifty       Int_2_Loc;
        One_Fifty       Int_3_Loc;
  REG   char            Ch_Index;
        Enumeration     Enum_Loc;
        Str_30          Str_1_Loc;
        Str_30          Str_2_Loc;
  REG   int             Run_Index;
  REG   int             Number_Of_Runs;

  

  Next_Ptr_Glob = (Rec_Pointer) (char *) malloc (sizeof (Rec_Type));
  Ptr_Glob = (Rec_Pointer) (char *) malloc (sizeof (Rec_Type));

  Ptr_Glob->Ptr_Comp                    = Next_Ptr_Glob;
  Ptr_Glob->Discr                       = Ident_1;
  Ptr_Glob->variant.var_1.Enum_Comp     = Ident_3;
  Ptr_Glob->variant.var_1.Int_Comp      = 40;
  strcpy (Ptr_Glob->variant.var_1.Str_Comp, 
          "DHRYSTONE PROGRAM, SOME STRING");
  strcpy (Str_1_Loc, "DHRYSTONE PROGRAM, 1'ST STRING");

  Arr_2_Glob [8][7] = 10;
        
        
        
        

  printf ("\n");
  printf ("Dhrystone Benchmark, Version 2.1 (Language: C)\n");
  if (Reg)
  {
    printf ("Program compiled with 'register' attribute\n");
  }
  else
  {
    printf ("Program compiled without 'register' attribute\n");
  }
  if (Float_Rating)
  {
    printf ("Ratings using 'float' datatype (%d bytes)\n",sizeof(float));
  }
  else
  {
    printf ("Ratings using 'double' datatype (%d bytes)\n",sizeof(double));
  }
  printf ("HZ = %d\n", HZ);
  printf ("\n");
  if (argc < 2) {
  	printf ("Please give the number of runs through the benchmark: ");
  	{
    	int n;
   	scanf ("%d", &n);
    	Number_Of_Runs = n;
  	}
  	printf ("\n");
  } else {
	Number_Of_Runs = atoi(argv[1]);
  }
  printf ("Execution starts, %d runs through Dhrystone\n", Number_Of_Runs);

  
  
  
 
#ifdef TIMES
  times (&time_info);
  Begin_Time = (long) time_info.tms_utime;
#endif
#ifdef TIME
  Begin_Time = time ( (long *) 0);
#endif
#ifdef MSC_CLOCK
  Begin_Time = clock();
#endif

  for (Run_Index = 1; Run_Index <= Number_Of_Runs; ++Run_Index)
  {

    Proc_5();
    Proc_4();
      
    Int_1_Loc = 2;
    Int_2_Loc = 3;
    strcpy (Str_2_Loc, "DHRYSTONE PROGRAM, 2'ND STRING");
    Enum_Loc = Ident_2;
    Bool_Glob = ! Func_2 (Str_1_Loc, Str_2_Loc);
      
    while (Int_1_Loc < Int_2_Loc)  
    {
      Int_3_Loc = 5 * Int_1_Loc - Int_2_Loc;
        
      Proc_7 (Int_1_Loc, Int_2_Loc, &Int_3_Loc);
        
      Int_1_Loc += 1;
    } 
      
    Proc_8 (Arr_1_Glob, Arr_2_Glob, Int_1_Loc, Int_3_Loc);
      
    Proc_1 (Ptr_Glob);
    for (Ch_Index = 'A'; Ch_Index <= Ch_2_Glob; ++Ch_Index)
                             
    {
      if (Enum_Loc == Func_1 (Ch_Index, 'C'))
          
        {
        Proc_6 (Ident_1, &Enum_Loc);
        strcpy (Str_2_Loc, "DHRYSTONE PROGRAM, 3'RD STRING");
        Int_2_Loc = Run_Index;
        Int_Glob = Run_Index;
        }
    }
      
    Int_2_Loc = Int_2_Loc * Int_1_Loc;
    Int_1_Loc = Int_2_Loc / Int_3_Loc;
    Int_2_Loc = 7 * (Int_2_Loc - Int_3_Loc) - Int_1_Loc;
      
    Proc_2 (&Int_1_Loc);
      

  } 

  
  
  
  
#ifdef TIMES
  times (&time_info);
  End_Time = (long) time_info.tms_utime;
#endif
#ifdef TIME
  End_Time = time ( (long *) 0);
#endif
#ifdef MSC_CLOCK
  End_Time = clock();
#endif

  printf ("Execution ends\n");
  printf ("\n");
  printf ("Final values of the variables used in the benchmark:\n");
  printf ("\n");
  printf ("Int_Glob:            %d\n", Int_Glob);
  printf ("        should be:   %d\n", 5);
  printf ("Bool_Glob:           %d\n", Bool_Glob);
  printf ("        should be:   %d\n", 1);
  printf ("Ch_1_Glob:           %c\n", Ch_1_Glob);
  printf ("        should be:   %c\n", 'A');
  printf ("Ch_2_Glob:           %c\n", Ch_2_Glob);
  printf ("        should be:   %c\n", 'B');
  printf ("Arr_1_Glob[8]:       %d\n", Arr_1_Glob[8]);
  printf ("        should be:   %d\n", 7);
  printf ("Arr_2_Glob[8][7]:    %d\n", Arr_2_Glob[8][7]);
  printf ("        should be:   Number_Of_Runs + 10\n");
  printf ("Ptr_Glob->\n");
  printf ("  Discr:             %d\n", Ptr_Glob->Discr);
  printf ("        should be:   %d\n", 0);
  printf ("  Enum_Comp:         %d\n", Ptr_Glob->variant.var_1.Enum_Comp);
  printf ("        should be:   %d\n", 2);
  printf ("  Int_Comp:          %d\n", Ptr_Glob->variant.var_1.Int_Comp);
  printf ("        should be:   %d\n", 17);
  printf ("  Str_Comp:          %s\n", Ptr_Glob->variant.var_1.Str_Comp);
  printf ("        should be:   DHRYSTONE PROGRAM, SOME STRING\n");
  printf ("Next_Ptr_Glob->\n");
  printf ("  Discr:             %d\n", Next_Ptr_Glob->Discr);
  printf ("        should be:   %d\n", 0);
  printf ("  Enum_Comp:         %d\n", Next_Ptr_Glob->variant.var_1.Enum_Comp);
  printf ("        should be:   %d\n", 1);
  printf ("  Int_Comp:          %d\n", Next_Ptr_Glob->variant.var_1.Int_Comp);
  printf ("        should be:   %d\n", 18);
  printf ("  Str_Comp:          %s\n",
                                Next_Ptr_Glob->variant.var_1.Str_Comp);
  printf ("        should be:   DHRYSTONE PROGRAM, SOME STRING\n");
  printf ("Int_1_Loc:           %d\n", Int_1_Loc);
  printf ("        should be:   %d\n", 5);
  printf ("Int_2_Loc:           %d\n", Int_2_Loc);
  printf ("        should be:   %d\n", 13);
  printf ("Int_3_Loc:           %d\n", Int_3_Loc);
  printf ("        should be:   %d\n", 7);
  printf ("Enum_Loc:            %d\n", Enum_Loc);
  printf ("        should be:   %d\n", 1);
  printf ("Str_1_Loc:           %s\n", Str_1_Loc);
  printf ("        should be:   DHRYSTONE PROGRAM, 1'ST STRING\n");
  printf ("Str_2_Loc:           %s\n", Str_2_Loc);
  printf ("        should be:   DHRYSTONE PROGRAM, 2'ND STRING\n");
  printf ("\n");

  User_Time = End_Time - Begin_Time;

  if (User_Time < Too_Small_Time)
  {
    printf ("Measured time too small to obtain meaningful results\n");
    printf ("Please increase number of runs\n");
    printf ("\n");
  }
  else
  {
#ifdef TIME
    Microseconds = (measure) User_Time * Mic_secs_Per_Second 
                        / (measure) Number_Of_Runs;
    Dhrystones_Per_Second = (measure) Number_Of_Runs / (measure) User_Time;
#else
    Microseconds = (measure) User_Time * Mic_secs_Per_Second 
                        / ((measure) HZ * ((measure) Number_Of_Runs));
    Dhrystones_Per_Second = ((measure) HZ * (measure) Number_Of_Runs)
                        / (measure) User_Time;
#endif
    printf ("Microseconds for one run through Dhrystone: ");
    printf ("%12.1f \n", Microseconds);
    printf ("Dhrystones per Second:                      ");
    printf ("%12.1f \n", Dhrystones_Per_Second);
    printf ("VAX MIPS:                                   ");
    printf ("%12.1f \n", Dhrystones_Per_Second/1757);
    printf ("\n");
  }
  return 0;
}


void Proc_1 (Ptr_Val_Par)


REG Rec_Pointer Ptr_Val_Par;
    
{
  REG Rec_Pointer Next_Record = Ptr_Val_Par->Ptr_Comp;  
                                        
  
  
  
  structassign (*Ptr_Val_Par->Ptr_Comp, *Ptr_Glob); 
  Ptr_Val_Par->variant.var_1.Int_Comp = 5;
  Next_Record->variant.var_1.Int_Comp 
        = Ptr_Val_Par->variant.var_1.Int_Comp;
  Next_Record->Ptr_Comp = Ptr_Val_Par->Ptr_Comp;
  Proc_3 (&Next_Record->Ptr_Comp);
    
  if (Next_Record->Discr == Ident_1)
    
  {
    Next_Record->variant.var_1.Int_Comp = 6;
    Proc_6 (Ptr_Val_Par->variant.var_1.Enum_Comp, 
           &Next_Record->variant.var_1.Enum_Comp);
    Next_Record->Ptr_Comp = Ptr_Glob->Ptr_Comp;
    Proc_7 (Next_Record->variant.var_1.Int_Comp, 10, 
           &Next_Record->variant.var_1.Int_Comp);
  }
  else 
    structassign (*Ptr_Val_Par, *Ptr_Val_Par->Ptr_Comp);
} 


void Proc_2 (Int_Par_Ref)

    
    

One_Fifty   *Int_Par_Ref;
{
  One_Fifty  Int_Loc;  
  Enumeration   Enum_Loc;

  Int_Loc = *Int_Par_Ref + 10;
  do 
    if (Ch_1_Glob == 'A')
      
    {
      Int_Loc -= 1;
      *Int_Par_Ref = Int_Loc - Int_Glob;
      Enum_Loc = Ident_1;
    } 
  while (Enum_Loc != Ident_1); 
} 


void Proc_3 (Ptr_Ref_Par)

    
    

Rec_Pointer *Ptr_Ref_Par;

{
  if (Ptr_Glob != Null)
    
    *Ptr_Ref_Par = Ptr_Glob->Ptr_Comp;
  Proc_7 (10, Int_Glob, &Ptr_Glob->variant.var_1.Int_Comp);
} 


void Proc_4 () 

    
{
  Boolean Bool_Loc;

  Bool_Loc = Ch_1_Glob == 'A';
  Bool_Glob = Bool_Loc | Bool_Glob;
  Ch_2_Glob = 'B';
} 


void Proc_5 () 

    
{
  Ch_1_Glob = 'A';
  Bool_Glob = false;
} 


        
        
#ifdef  NOSTRUCTASSIGN
memcpy (d, s, l)
register char   *d;
register char   *s;
register int    l;
{
        while (l--) *d++ = *s++;
}
#endif


