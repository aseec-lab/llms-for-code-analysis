

#include "coremark.h"

#if (SEED_METHOD == SEED_VOLATILE)
extern volatile ee_s32 seed1_volatile;
extern volatile ee_s32 seed2_volatile;
extern volatile ee_s32 seed3_volatile;
extern volatile ee_s32 seed4_volatile;
extern volatile ee_s32 seed5_volatile;
ee_s32
get_seed_32(int i)
{
    ee_s32 retval;
    switch (i)
    {
        case 1:
            retval = seed1_volatile;
            break;
        case 2:
            retval = seed2_volatile;
            break;
        case 3:
            retval = seed3_volatile;
            break;
        case 4:
            retval = seed4_volatile;
            break;
        case 5:
            retval = seed5_volatile;
            break;
        default:
            retval = 0;
            break;
    }
    return retval;
}
#elif (SEED_METHOD == SEED_ARG)
ee_s32
parseval(char *valstring)
{
    ee_s32 retval  = 0;
    ee_s32 neg     = 1;
    int    hexmode = 0;
    if (*valstring == '-')
    {
        neg = -1;
        valstring++;
    }
    if ((valstring[0] == '0') && (valstring[1] == 'x'))
    {
        hexmode = 1;
        valstring += 2;
    }
    
    if (hexmode)
    {
        while (((*valstring >= '0') && (*valstring <= '9'))
               || ((*valstring >= 'a') && (*valstring <= 'f')))
        {
            ee_s32 digit = *valstring - '0';
            if (digit > 9)
                digit = 10 + *valstring - 'a';
            retval *= 16;
            retval += digit;
            valstring++;
        }
    }
    else
    {
        while ((*valstring >= '0') && (*valstring <= '9'))
        {
            ee_s32 digit = *valstring - '0';
            retval *= 10;
            retval += digit;
            valstring++;
        }
    }
    
    if (*valstring == 'K')
        retval *= 1024;
    if (*valstring == 'M')
        retval *= 1024 * 1024;

    retval *= neg;
    return retval;
}

ee_s32
get_seed_args(int i, int argc, char *argv[])
{
    if (argc > i)
        return parseval(argv[i]);
    return 0;
}

#elif (SEED_METHOD == SEED_FUNC)

ee_s32
get_seed_32(int i)
{
    ee_s32 retval;
    switch (i)
    {
        case 1:
            retval = portme_sys1();
            break;
        case 2:
            retval = portme_sys2();
            break;
        case 3:
            retval = portme_sys3();
            break;
        case 4:
            retval = portme_sys4();
            break;
        case 5:
            retval = portme_sys5();
            break;
        default:
            retval = 0;
            break;
    }
    return retval;
}
#endif


ee_u16
crcu8(ee_u8 data, ee_u16 crc)
{
    ee_u8 i = 0, x16 = 0, carry = 0;

    for (i = 0; i < 8; i++)
    {
        x16 = (ee_u8)((data & 1) ^ ((ee_u8)crc & 1));
        data >>= 1;

        if (x16 == 1)
        {
            crc ^= 0x4002;
            carry = 1;
        }
        else
            carry = 0;
        crc >>= 1;
        if (carry)
            crc |= 0x8000;
        else
            crc &= 0x7fff;
    }
    return crc;
}
ee_u16
crcu16(ee_u16 newval, ee_u16 crc)
{
    crc = crcu8((ee_u8)(newval), crc);
    crc = crcu8((ee_u8)((newval) >> 8), crc);
    return crc;
}
ee_u16
crcu32(ee_u32 newval, ee_u16 crc)
{
    crc = crc16((ee_s16)newval, crc);
    crc = crc16((ee_s16)(newval >> 16), crc);
    return crc;
}
ee_u16
crc16(ee_s16 newval, ee_u16 crc)
{
    return crcu16((ee_u16)newval, crc);
}

ee_u8
check_data_types(void)
{
    ee_u8 retval = 0;
    if (sizeof(ee_u8) != 1)
    {
        ee_printf("ERROR: ee_u8 is not an 8b datatype!\n");
        retval++;
    }
    if (sizeof(ee_u16) != 2)
    {
        ee_printf("ERROR: ee_u16 is not a 16b datatype!\n");
        retval++;
    }
    if (sizeof(ee_s16) != 2)
    {
        ee_printf("ERROR: ee_s16 is not a 16b datatype!\n");
        retval++;
    }
    if (sizeof(ee_s32) != 4)
    {
        ee_printf("ERROR: ee_s32 is not a 32b datatype!\n");
        retval++;
    }
    if (sizeof(ee_u32) != 4)
    {
        ee_printf("ERROR: ee_u32 is not a 32b datatype!\n");
        retval++;
    }
    if (sizeof(ee_ptr_int) != sizeof(int *))
    {
        ee_printf(
            "ERROR: ee_ptr_int is not a datatype that holds an int pointer!\n");
        retval++;
    }
    if (retval > 0)
    {
        ee_printf("ERROR: Please modify the datatypes in core_portme.h!\n");
    }
    return retval;
}
