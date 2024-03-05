


from __future__ import absolute_import, division, print_function, unicode_literals
import itertools as it
import six
import math
import operator
from six.moves import zip_longest
from six import next
if six.PY2:  
    import collections as collections_abc
else:
    from collections import abc as collections_abc


class chunks(object):
    

    def __init__(self, items, chunksize=None, nchunks=None, total=None,
                 bordermode='none'):
        if nchunks is not None and chunksize is not None:  
            raise ValueError('Cannot specify both chunksize and nchunks')
        if nchunks is None and chunksize is None:  
            raise ValueError('Must specify either chunksize or nchunks')
        if nchunks is not None:
            if total is None:
                total = len(items)
            chunksize = int(math.ceil(total / nchunks))

        self.bordermode = bordermode
        self.items = items
        self.chunksize = chunksize
        self.total = total

    def __len__(self):
        if self.total is None:
            self.total = len(self.items)
        nchunks = int(math.ceil(self.total / self.chunksize))
        return nchunks

    def __iter__(self):
        bordermode = self.bordermode
        items = self.items
        chunksize = self.chunksize
        if bordermode is None or bordermode == 'none':
            return self.noborder(items, chunksize)
        elif bordermode == 'cycle':
            return self.cycle(items, chunksize)
        elif bordermode == 'replicate':
            return self.replicate(items, chunksize)
        else:
            raise ValueError('unknown bordermode=%r' % (bordermode,))

    @staticmethod
    def noborder(items, chunksize):
        
        
        sentinal = object()
        copied_iters = [iter(items)] * chunksize
        chunks_with_sentinals = zip_longest(*copied_iters, fillvalue=sentinal)
        
        for chunk in chunks_with_sentinals:
            yield [item for item in chunk if item is not sentinal]

    @staticmethod
    def cycle(items, chunksize):
        sentinal = object()
        copied_iters = [iter(items)] * chunksize
        chunks_with_sentinals = zip_longest(*copied_iters, fillvalue=sentinal)
        
        bordervalues = it.cycle(iter(items))
        for chunk in chunks_with_sentinals:
            yield [item if item is not sentinal else next(bordervalues)
                   for item in chunk]

    @staticmethod
    def replicate(items, chunksize):
        sentinal = object()
        copied_iters = [iter(items)] * chunksize
        
        chunks_with_sentinals = zip_longest(*copied_iters, fillvalue=sentinal)
        for chunk in chunks_with_sentinals:
            filt_chunk = [item for item in chunk if item is not sentinal]
            if len(filt_chunk) == chunksize:
                yield filt_chunk
            else:
                sizediff = (chunksize - len(filt_chunk))
                padded_chunk = filt_chunk + [filt_chunk[-1]] * sizediff
                yield padded_chunk


def iterable(obj, strok=False):
    

    try:
        iter(obj)
    except Exception:
        return False
    else:
        return strok or not isinstance(obj, six.string_types)


def take(items, indices):
    

    return (items[index] for index in indices)


def compress(items, flags):
    

    return it.compress(items, flags)


def flatten(nested_list):
    

    return it.chain.from_iterable(nested_list)


def unique(items, key=None):
    

    seen = set()
    if key is None:
        for item in items:
            if item not in seen:
                seen.add(item)
                yield item
    else:
        for item in items:
            norm = key(item)
            if norm not in seen:
                seen.add(norm)
                yield item


def argunique(items, key=None):
    

    
    if key is None:
        return unique(range(len(items)), key=lambda i: items[i])
    else:
        return unique(range(len(items)), key=lambda i: key(items[i]))


def unique_flags(items, key=None):
    

    len_ = len(items)
    if key is None:
        item_to_index = dict(zip(reversed(items), reversed(range(len_))))
        indices = item_to_index.values()
    else:
        indices = argunique(items, key=key)
    flags = boolmask(indices, len_)
    return flags


def boolmask(indices, maxval=None):
    

    if maxval is None:
        indices = list(indices)
        maxval = max(indices) + 1
    mask = [False] * maxval
    for index in indices:
        mask[index] = True
    return mask


def iter_window(iterable, size=2, step=1, wrap=False):
    

    
    iter_list = it.tee(iterable, size)
    if wrap:
        
        iter_list = [iter_list[0]] + list(map(it.cycle, iter_list[1:]))
    
    try:
        for count, iter_ in enumerate(iter_list[1:], start=1):
            for _ in range(count):
                next(iter_)
    except StopIteration:
        return iter(())
    else:
        _window_iter = zip(*iter_list)
        
        window_iter = it.islice(_window_iter, 0, None, step)
        return window_iter


def allsame(iterable, eq=operator.eq):
    

    iter_ = iter(iterable)
    try:
        first = next(iter_)
    except StopIteration:
        return True
    return all(eq(first, item) for item in iter_)


def argsort(indexable, key=None, reverse=False):
    

    
    if isinstance(indexable, collections_abc.Mapping):
        vk_iter = ((v, k) for k, v in indexable.items())
    else:
        vk_iter = ((v, k) for k, v in enumerate(indexable))
    
    if key is None:
        indices = [k for v, k in sorted(vk_iter, reverse=reverse)]
    else:
        
        indices = [k for v, k in sorted(vk_iter, key=lambda vk: key(vk[0]),
                                        reverse=reverse)]
    return indices


def argmax(indexable, key=None):
    

    if key is None and isinstance(indexable, collections_abc.Mapping):
        return max(indexable.items(), key=operator.itemgetter(1))[0]
    elif hasattr(indexable, 'index'):
        if key is None:
            return indexable.index(max(indexable))
        else:
            return indexable.index(max(indexable, key=key))
    else:
        
        return argsort(indexable, key=key)[-1]


def argmin(indexable, key=None):
    

    if key is None and isinstance(indexable, collections_abc.Mapping):
        return min(indexable.items(), key=operator.itemgetter(1))[0]
    elif hasattr(indexable, 'index'):
        if key is None:
            return indexable.index(min(indexable))
        else:
            return indexable.index(min(indexable, key=key))
    else:
        
        return argsort(indexable, key=key)[0]


def peek(iterable):
    

    return next(iter(iterable))
