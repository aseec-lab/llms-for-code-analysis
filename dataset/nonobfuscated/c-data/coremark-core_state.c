

#include "coremark.h"

enum CORE_STATE core_state_transition(ee_u8 **instr, ee_u32 *transition_count);




ee_u16
core_bench_state(ee_u32 blksize,
                 ee_u8 *memblock,
                 ee_s16 seed1,
                 ee_s16 seed2,
                 ee_s16 step,
                 ee_u16 crc)
{
    ee_u32 final_counts[NUM_CORE_STATES];
    ee_u32 track_counts[NUM_CORE_STATES];
    ee_u8 *p = memblock;
    ee_u32 i;

#if CORE_DEBUG
    ee_printf("State Bench: %d,%d,%d,%04x\n", seed1, seed2, step, crc);
#endif
    for (i = 0; i < NUM_CORE_STATES; i++)
    {
        final_counts[i] = track_counts[i] = 0;
    }
    
    while (*p != 0)
    {
        enum CORE_STATE fstate = core_state_transition(&p, track_counts);
        final_counts[fstate]++;
#if CORE_DEBUG
        ee_printf("%d,", fstate);
    }
    ee_printf("\n");
#else
    }
#endif
    p = memblock;
    while (p < (memblock + blksize))
    { 
        if (*p != ',')
            *p ^= (ee_u8)seed1;
        p += step;
    }
    p = memblock;
    
    while (*p != 0)
    {
        enum CORE_STATE fstate = core_state_transition(&p, track_counts);
        final_counts[fstate]++;
#if CORE_DEBUG
        ee_printf("%d,", fstate);
    }
    ee_printf("\n");
#else
    }
#endif
    p = memblock;
    while (p < (memblock + blksize))
    { 
        if (*p != ',')
            *p ^= (ee_u8)seed2;
        p += step;
    }
    
    for (i = 0; i < NUM_CORE_STATES; i++)
    {
        crc = crcu32(final_counts[i], crc);
        crc = crcu32(track_counts[i], crc);
    }
    return crc;
}


static ee_u8 *intpat[4]
    = { (ee_u8 *)"5012", (ee_u8 *)"1234", (ee_u8 *)"-874", (ee_u8 *)"+122" };
static ee_u8 *floatpat[4] = { (ee_u8 *)"35.54400",
                              (ee_u8 *)".1234500",
                              (ee_u8 *)"-110.700",
                              (ee_u8 *)"+0.64400" };
static ee_u8 *scipat[4]   = { (ee_u8 *)"5.500e+3",
                            (ee_u8 *)"-.123e-2",
                            (ee_u8 *)"-87e+832",
                            (ee_u8 *)"+0.6e-12" };
static ee_u8 *errpat[4]   = { (ee_u8 *)"T0.3e-1F",
                            (ee_u8 *)"-T.T++Tq",
                            (ee_u8 *)"1T3.4e4z",
                            (ee_u8 *)"34.0e-T^" };


void
core_init_state(ee_u32 size, ee_s16 seed, ee_u8 *p)
{
    ee_u32 total = 0, next = 0, i;
    ee_u8 *buf = 0;
#if CORE_DEBUG
    ee_u8 *start = p;
    ee_printf("State: %d,%d\n", size, seed);
#endif
    size--;
    next = 0;
    while ((total + next + 1) < size)
    {
        if (next > 0)
        {
            for (i = 0; i < next; i++)
                *(p + total + i) = buf[i];
            *(p + total + i) = ',';
            total += next + 1;
        }
        seed++;
        switch (seed & 0x7)
        {
            case 0: 
            case 1: 
            case 2: 
                buf  = intpat[(seed >> 3) & 0x3];
                next = 4;
                break;
            case 3: 
            case 4: 
                buf  = floatpat[(seed >> 3) & 0x3];
                next = 8;
                break;
            case 5: 
            case 6: 
                buf  = scipat[(seed >> 3) & 0x3];
                next = 8;
                break;
            case 7: 
                buf  = errpat[(seed >> 3) & 0x3];
                next = 8;
                break;
            default: 
                break;
        }
    }
    size++;
    while (total < size)
    { 
        *(p + total) = 0;
        total++;
    }
#if CORE_DEBUG
    ee_printf("State Input: %s\n", start);
#endif
}

static ee_u8
ee_isdigit(ee_u8 c)
{
    ee_u8 retval;
    retval = ((c >= '0') & (c <= '9')) ? 1 : 0;
    return retval;
}



enum CORE_STATE
core_state_transition(ee_u8 **instr, ee_u32 *transition_count)
{
    ee_u8 *         str = *instr;
    ee_u8           NEXT_SYMBOL;
    enum CORE_STATE state = CORE_START;
    for (; *str && state != CORE_INVALID; str++)
    {
        NEXT_SYMBOL = *str;
        if (NEXT_SYMBOL == ',') 
        {
            str++;
            break;
        }
        switch (state)
        {
            case CORE_START:
                if (ee_isdigit(NEXT_SYMBOL))
                {
                    state = CORE_INT;
                }
                else if (NEXT_SYMBOL == '+' || NEXT_SYMBOL == '-')
                {
                    state = CORE_S1;
                }
                else if (NEXT_SYMBOL == '.')
                {
                    state = CORE_FLOAT;
                }
                else
                {
                    state = CORE_INVALID;
                    transition_count[CORE_INVALID]++;
                }
                transition_count[CORE_START]++;
                break;
            case CORE_S1:
                if (ee_isdigit(NEXT_SYMBOL))
                {
                    state = CORE_INT;
                    transition_count[CORE_S1]++;
                }
                else if (NEXT_SYMBOL == '.')
                {
                    state = CORE_FLOAT;
                    transition_count[CORE_S1]++;
                }
                else
                {
                    state = CORE_INVALID;
                    transition_count[CORE_S1]++;
                }
                break;
            case CORE_INT:
                if (NEXT_SYMBOL == '.')
                {
                    state = CORE_FLOAT;
                    transition_count[CORE_INT]++;
                }
                else if (!ee_isdigit(NEXT_SYMBOL))
                {
                    state = CORE_INVALID;
                    transition_count[CORE_INT]++;
                }
                break;
            case CORE_FLOAT:
                if (NEXT_SYMBOL == 'E' || NEXT_SYMBOL == 'e')
                {
                    state = CORE_S2;
                    transition_count[CORE_FLOAT]++;
                }
                else if (!ee_isdigit(NEXT_SYMBOL))
                {
                    state = CORE_INVALID;
                    transition_count[CORE_FLOAT]++;
                }
                break;
            case CORE_S2:
                if (NEXT_SYMBOL == '+' || NEXT_SYMBOL == '-')
                {
                    state = CORE_EXPONENT;
                    transition_count[CORE_S2]++;
                }
                else
                {
                    state = CORE_INVALID;
                    transition_count[CORE_S2]++;
                }
                break;
            case CORE_EXPONENT:
                if (ee_isdigit(NEXT_SYMBOL))
                {
                    state = CORE_SCIENTIFIC;
                    transition_count[CORE_EXPONENT]++;
                }
                else
                {
                    state = CORE_INVALID;
                    transition_count[CORE_EXPONENT]++;
                }
                break;
            case CORE_SCIENTIFIC:
                if (!ee_isdigit(NEXT_SYMBOL))
                {
                    state = CORE_INVALID;
                    transition_count[CORE_INVALID]++;
                }
                break;
            default:
                break;
        }
    }
    *instr = str;
    return state;
}
