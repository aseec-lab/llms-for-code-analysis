

#include "coremark.h"




void copy_info(list_data *to, list_data *from);
list_head *core_list_find(list_head *list, list_data *info);
list_head *core_list_reverse(list_head *list);
list_head *core_list_remove(list_head *item);
list_head *core_list_undo_remove(list_head *item_removed,
                                 list_head *item_modified);
list_head *core_list_insert_new(list_head * insert_point,
                                list_data * info,
                                list_head **memblock,
                                list_data **datablock,
                                list_head * memblock_end,
                                list_data * datablock_end);
typedef ee_s32 (*list_cmp)(list_data *a, list_data *b, core_results *res);
list_head *core_list_mergesort(list_head *   list,
                               list_cmp      cmp,
                               core_results *res);



ee_s16 calc_func(ee_s16 *pdata, core_results *res);
ee_s32 cmp_complex(list_data *a, list_data *b, core_results *res);
ee_s32 cmp_idx(list_data *a, list_data *b, core_results *res);

ee_s16
calc_func(ee_s16 *pdata, core_results *res)
{
    ee_s16 data = *pdata;
    ee_s16 retval;
    ee_u8  optype
        = (data >> 7)
          & 1;  
    if (optype) 
        return (data & 0x007f);
    else
    {                             
        ee_s16 flag = data & 0x7; 
        ee_s16 dtype
            = ((data >> 3)
               & 0xf);       
        dtype |= dtype << 4; 
        switch (flag)
        {
            case 0:
                if (dtype < 0x22) 
                    dtype = 0x22;
                retval = core_bench_state(res->size,
                                          res->memblock[3],
                                          res->seed1,
                                          res->seed2,
                                          dtype,
                                          res->crc);
                if (res->crcstate == 0)
                    res->crcstate = retval;
                break;
            case 1:
                retval = core_bench_matrix(&(res->mat), dtype, res->crc);
                if (res->crcmatrix == 0)
                    res->crcmatrix = retval;
                break;
            default:
                retval = data;
                break;
        }
        res->crc = crcu16(retval, res->crc);
        retval &= 0x007f;
        *pdata = (data & 0xff00) | 0x0080 | retval; 
        return retval;
    }
}

ee_s32
cmp_complex(list_data *a, list_data *b, core_results *res)
{
    ee_s16 val1 = calc_func(&(a->data16), res);
    ee_s16 val2 = calc_func(&(b->data16), res);
    return val1 - val2;
}


ee_s32
cmp_idx(list_data *a, list_data *b, core_results *res)
{
    if (res == NULL)
    {
        a->data16 = (a->data16 & 0xff00) | (0x00ff & (a->data16 >> 8));
        b->data16 = (b->data16 & 0xff00) | (0x00ff & (b->data16 >> 8));
    }
    return a->idx - b->idx;
}

void
copy_info(list_data *to, list_data *from)
{
    to->data16 = from->data16;
    to->idx    = from->idx;
}


ee_u16
core_bench_list(core_results *res, ee_s16 finder_idx)
{
    ee_u16     retval = 0;
    ee_u16     found = 0, missed = 0;
    list_head *list     = res->list;
    ee_s16     find_num = res->seed3;
    list_head *this_find;
    list_head *finder, *remover;
    list_data  info = {0};
    ee_s16     i;

    info.idx = finder_idx;
    
    for (i = 0; i < find_num; i++)
    {
        info.data16 = (i & 0xff);
        this_find   = core_list_find(list, &info);
        list        = core_list_reverse(list);
        if (this_find == NULL)
        {
            missed++;
            retval += (list->next->info->data16 >> 8) & 1;
        }
        else
        {
            found++;
            if (this_find->info->data16 & 0x1) 
                retval += (this_find->info->data16 >> 9) & 1;
            
            if (this_find->next != NULL)
            {
                finder          = this_find->next;
                this_find->next = finder->next;
                finder->next    = list->next;
                list->next      = finder;
            }
        }
        if (info.idx >= 0)
            info.idx++;
#if CORE_DEBUG
        ee_printf("List find %d: [%d,%d,%d]\n", i, retval, missed, found);
#endif
    }
    retval += found * 4 - missed;
    
    if (finder_idx > 0)
        list = core_list_mergesort(list, cmp_complex, res);
    remover = core_list_remove(list->next);
    
    finder = core_list_find(list, &info);
    if (!finder)
        finder = list->next;
    while (finder)
    {
        retval = crc16(list->info->data16, retval);
        finder = finder->next;
    }
#if CORE_DEBUG
    ee_printf("List sort 1: %04x\n", retval);
#endif
    remover = core_list_undo_remove(remover, list->next);
    
    list = core_list_mergesort(list, cmp_idx, NULL);
    
    finder = list->next;
    while (finder)
    {
        retval = crc16(list->info->data16, retval);
        finder = finder->next;
    }
#if CORE_DEBUG
    ee_printf("List sort 2: %04x\n", retval);
#endif
    return retval;
}

list_head *
core_list_init(ee_u32 blksize, list_head *memblock, ee_s16 seed)
{
    
    ee_u32 per_item = 16 + sizeof(struct list_data_s);
    ee_u32 size     = (blksize / per_item)
                  - 2; 
    list_head *memblock_end  = memblock + size;
    list_data *datablock     = (list_data *)(memblock_end);
    list_data *datablock_end = datablock + size;
    
    ee_u32     i;
    list_head *finder, *list = memblock;
    list_data  info;

    
    list->next         = NULL;
    list->info         = datablock;
    list->info->idx    = 0x0000;
    list->info->data16 = (ee_s16)0x8080;
    memblock++;
    datablock++;
    info.idx    = 0x7fff;
    info.data16 = (ee_s16)0xffff;
    core_list_insert_new(
        list, &info, &memblock, &datablock, memblock_end, datablock_end);

    
    for (i = 0; i < size; i++)
    {
        ee_u16 datpat = ((ee_u16)(seed ^ i) & 0xf);
        ee_u16 dat
            = (datpat << 3) | (i & 0x7); 
        info.data16 = (dat << 8) | dat;  
        core_list_insert_new(
            list, &info, &memblock, &datablock, memblock_end, datablock_end);
    }
    
    finder = list->next;
    i      = 1;
    while (finder->next != NULL)
    {
        if (i < size / 5) 
            finder->info->idx = i++;
        else
        {
            ee_u16 pat = (ee_u16)(i++ ^ seed); 
            finder->info->idx = 0x3fff
                                & (((i & 0x07) << 8)
                                   | pat); 
        }
        finder = finder->next;
    }
    list = core_list_mergesort(list, cmp_idx, NULL);
#if CORE_DEBUG
    ee_printf("Initialized list:\n");
    finder = list;
    while (finder)
    {
        ee_printf(
            "[%04x,%04x]", finder->info->idx, (ee_u16)finder->info->data16);
        finder = finder->next;
    }
    ee_printf("\n");
#endif
    return list;
}


list_head *
core_list_insert_new(list_head * insert_point,
                     list_data * info,
                     list_head **memblock,
                     list_data **datablock,
                     list_head * memblock_end,
                     list_data * datablock_end)
{
    list_head *newitem;

    if ((*memblock + 1) >= memblock_end)
        return NULL;
    if ((*datablock + 1) >= datablock_end)
        return NULL;

    newitem = *memblock;
    (*memblock)++;
    newitem->next      = insert_point->next;
    insert_point->next = newitem;

    newitem->info = *datablock;
    (*datablock)++;
    copy_info(newitem->info, info);

    return newitem;
}


list_head *
core_list_remove(list_head *item)
{
    list_data *tmp;
    list_head *ret = item->next;
    
    tmp        = item->info;
    item->info = ret->info;
    ret->info  = tmp;
    
    item->next = item->next->next;
    ret->next  = NULL;
    return ret;
}


list_head *
core_list_undo_remove(list_head *item_removed, list_head *item_modified)
{
    list_data *tmp;
    
    tmp                 = item_removed->info;
    item_removed->info  = item_modified->info;
    item_modified->info = tmp;
    
    item_removed->next  = item_modified->next;
    item_modified->next = item_removed;
    return item_removed;
}


list_head *
core_list_find(list_head *list, list_data *info)
{
    if (info->idx >= 0)
    {
        while (list && (list->info->idx != info->idx))
            list = list->next;
        return list;
    }
    else
    {
        while (list && ((list->info->data16 & 0xff) != info->data16))
            list = list->next;
        return list;
    }
}


list_head *
core_list_reverse(list_head *list)
{
    list_head *next = NULL, *tmp;
    while (list)
    {
        tmp        = list->next;
        list->next = next;
        next       = list;
        list       = tmp;
    }
    return next;
}

list_head *
core_list_mergesort(list_head *list, list_cmp cmp, core_results *res)
{
    list_head *p, *q, *e, *tail;
    ee_s32     insize, nmerges, psize, qsize, i;

    insize = 1;

    while (1)
    {
        p    = list;
        list = NULL;
        tail = NULL;

        nmerges = 0; 

        while (p)
        {
            nmerges++; 
            
            q     = p;
            psize = 0;
            for (i = 0; i < insize; i++)
            {
                psize++;
                q = q->next;
                if (!q)
                    break;
            }

            
            qsize = insize;

            
            while (psize > 0 || (qsize > 0 && q))
            {

                
                if (psize == 0)
                {
                    
                    e = q;
                    q = q->next;
                    qsize--;
                }
                else if (qsize == 0 || !q)
                {
                    
                    e = p;
                    p = p->next;
                    psize--;
                }
                else if (cmp(p->info, q->info, res) <= 0)
                {
                    
                    e = p;
                    p = p->next;
                    psize--;
                }
                else
                {
                    
                    e = q;
                    q = q->next;
                    qsize--;
                }

                
                if (tail)
                {
                    tail->next = e;
                }
                else
                {
                    list = e;
                }
                tail = e;
            }

            
            p = q;
        }

        tail->next = NULL;

        
        if (nmerges <= 1) 
            return list;

        
        insize *= 2;
    }
#if COMPILER_REQUIRES_SORT_RETURN
    return list;
#endif
}
