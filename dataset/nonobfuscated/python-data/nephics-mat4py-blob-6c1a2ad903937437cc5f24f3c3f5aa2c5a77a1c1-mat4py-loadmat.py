


__all__ = ['loadmat']


import struct
import sys
import zlib

from collections import Sequence
from itertools import tee
try:
    from itertools import izip
    ispy2 = True
except ImportError:
    izip = zip
    basestring = str
    ispy2 = False
from io import BytesIO



asbytes = lambda s: s.encode('latin1')
asstr = lambda b: b.decode('latin1')


etypes = {
    'miINT8': {'n': 1, 'fmt': 'b'},
    'miUINT8': {'n': 2, 'fmt': 'B'},
    'miINT16': {'n': 3, 'fmt': 'h'},
    'miUINT16': {'n': 4, 'fmt': 'H'},
    'miINT32': {'n': 5, 'fmt': 'i'},
    'miUINT32': {'n': 6, 'fmt': 'I'},
    'miSINGLE': {'n': 7, 'fmt': 'f'},
    'miDOUBLE': {'n': 9, 'fmt': 'd'},
    'miINT64': {'n': 12, 'fmt': 'q'},
    'miUINT64': {'n': 13, 'fmt': 'Q'},
    'miMATRIX': {'n': 14},
    'miCOMPRESSED': {'n': 15},
    'miUTF8': {'n': 16, 'fmt': 's'},
    'miUTF16': {'n': 17, 'fmt': 's'},
    'miUTF32': {'n': 18, 'fmt': 's'}
}


inv_etypes = dict((v['n'], k) for k, v in etypes.items())


mclasses = {
    'mxCELL_CLASS': 1,
    'mxSTRUCT_CLASS': 2,
    'mxOBJECT_CLASS': 3,
    'mxCHAR_CLASS': 4,
    'mxSPARSE_CLASS': 5,
    'mxDOUBLE_CLASS': 6,
    'mxSINGLE_CLASS': 7,
    'mxINT8_CLASS': 8,
    'mxUINT8_CLASS': 9,
    'mxINT16_CLASS': 10,
    'mxUINT16_CLASS': 11,
    'mxINT32_CLASS': 12,
    'mxUINT32_CLASS': 13,
    'mxINT64_CLASS': 14,
    'mxUINT64_CLASS': 15,
    'mxFUNCTION_CLASS': 16,
    'mxOPAQUE_CLASS': 17,
    'mxOBJECT_CLASS_FROM_MATRIX_H': 18
}


numeric_class_etypes = {
    'mxDOUBLE_CLASS': 'miDOUBLE',
    'mxSINGLE_CLASS': 'miSINGLE',
    'mxINT8_CLASS': 'miINT8',
    'mxUINT8_CLASS': 'miUINT8',
    'mxINT16_CLASS': 'miINT16',
    'mxUINT16_CLASS': 'miUINT16',
    'mxINT32_CLASS': 'miINT32',
    'mxUINT32_CLASS': 'miUINT32',
    'mxINT64_CLASS': 'miINT64',
    'mxUINT64_CLASS': 'miUINT64'
}

inv_mclasses = dict((v, k) for k, v in mclasses.items())


compressed_numeric = ['miINT32', 'miUINT16', 'miINT16', 'miUINT8']


def diff(iterable):
    

    a, b = tee(iterable)
    next(b, None)
    return (i - j for i, j in izip(a, b))






def unpack(endian, fmt, data):
    

    if fmt == 's':
        
        val = struct.unpack(''.join([endian, str(len(data)), 's']),
                            data)[0]
    else:
        
        num = len(data) // struct.calcsize(fmt)
        val = struct.unpack(''.join([endian, str(num), fmt]), data)
        if len(val) == 1:
            val = val[0]
    return val


def read_file_header(fd, endian):
    

    fields = [
        ('description', 's', 116),
        ('subsystem_offset', 's', 8),
        ('version', 'H', 2),
        ('endian_test', 's', 2)
    ]
    hdict = {}
    for name, fmt, num_bytes in fields:
        data = fd.read(num_bytes)
        hdict[name] = unpack(endian, fmt, data)
    hdict['description'] = hdict['description'].strip()
    v_major = hdict['version'] >> 8
    v_minor = hdict['version'] & 0xFF
    hdict['__version__'] = '%d.%d' % (v_major, v_minor)
    return hdict


def read_element_tag(fd, endian):
    

    data = fd.read(8)
    mtpn = unpack(endian, 'I', data[:4])
    
    
    num_bytes = mtpn >> 16
    if num_bytes > 0:
        
        mtpn = mtpn & 0xFFFF
        if num_bytes > 4:
            raise ParseError('Error parsing Small Data Element (SDE) '
                             'formatted data')
        data = data[4:4 + num_bytes]
    else:
        
        num_bytes = unpack(endian, 'I', data[4:])
        data = None
    return (mtpn, num_bytes, data)


def read_elements(fd, endian, mtps, is_name=False):
    

    mtpn, num_bytes, data = read_element_tag(fd, endian)
    if mtps and mtpn not in [etypes[mtp]['n'] for mtp in mtps]:
        raise ParseError('Got type {}, expected {}'.format(
            mtpn, ' / '.join('{} ({})'.format(
                etypes[mtp]['n'], mtp) for mtp in mtps)))
    if not data:
        
        data = fd.read(num_bytes)
        
        mod8 = num_bytes % 8
        if mod8:
            fd.seek(8 - mod8, 1)

    
    if is_name:
        
        fmt = 's'
        val = [unpack(endian, fmt, s)
               for s in data.split(b'\0') if s]
        if len(val) == 0:
            val = ''
        elif len(val) == 1:
            val = asstr(val[0])
        else:
            val = [asstr(s) for s in val]
    else:
        fmt = etypes[inv_etypes[mtpn]]['fmt']
        val = unpack(endian, fmt, data)
    return val


def read_header(fd, endian):
    

    flag_class, nzmax = read_elements(fd, endian, ['miUINT32'])
    header = {
        'mclass': flag_class & 0x0FF,
        'is_logical': (flag_class >> 9 & 1) == 1,
        'is_global': (flag_class >> 10 & 1) == 1,
        'is_complex': (flag_class >> 11 & 1) == 1,
        'nzmax': nzmax
    }
    header['dims'] = read_elements(fd, endian, ['miINT32'])
    header['n_dims'] = len(header['dims'])
    if header['n_dims'] != 2:
        raise ParseError('Only matrices with dimension 2 are supported.')
    header['name'] = read_elements(fd, endian, ['miINT8'], is_name=True)
    return header


def read_var_header(fd, endian):
    

    mtpn, num_bytes = unpack(endian, 'II', fd.read(8))
    next_pos = fd.tell() + num_bytes

    if mtpn == etypes['miCOMPRESSED']['n']:
        
        data = fd.read(num_bytes)
        dcor = zlib.decompressobj()
        
        fd_var = BytesIO(dcor.decompress(data))
        del data
        fd = fd_var
        
        if dcor.flush() != b'':
            raise ParseError('Error in compressed data.')
        
        mtpn, num_bytes = unpack(endian, 'II', fd.read(8))

    if mtpn != etypes['miMATRIX']['n']:
        raise ParseError('Expecting miMATRIX type number {}, '
                         'got {}'.format(etypes['miMATRIX']['n'], mtpn))
    
    header = read_header(fd, endian)
    return header, next_pos, fd


def squeeze(array):
    

    if len(array) == 1:
        array = array[0]
    return array


def read_numeric_array(fd, endian, header, data_etypes):
    

    if header['is_complex']:
        raise ParseError('Complex arrays are not supported')
    
    data = read_elements(fd, endian, data_etypes)
    if not isinstance(data, Sequence):
        
        return data
    
    
    rowcount = header['dims'][0]
    colcount = header['dims'][1]
    array = [list(data[c * rowcount + r] for c in range(colcount))
             for r in range(rowcount)]
    
    return squeeze(array)


def read_cell_array(fd, endian, header):
    

    array = [list() for i in range(header['dims'][0])]
    for row in range(header['dims'][0]):
        for col in range(header['dims'][1]):
            
            vheader, next_pos, fd_var = read_var_header(fd, endian)
            varray = read_var_array(fd_var, endian, vheader)
            array[row].append(varray)
            
            fd.seek(next_pos)
    
    if header['dims'][0] == 1:
        return squeeze(array[0])
    return squeeze(array)


def read_struct_array(fd, endian, header):
    

    
    field_name_length = read_elements(fd, endian, ['miINT32'])
    if field_name_length > 32:
        raise ParseError('Unexpected field name length: {}'.format(
                         field_name_length))

    
    fields = read_elements(fd, endian, ['miINT8'], is_name=True)
    if isinstance(fields, basestring):
        fields = [fields]

    
    empty = lambda: [list() for i in range(header['dims'][0])]
    array = {}
    for row in range(header['dims'][0]):
        for col in range(header['dims'][1]):
            for field in fields:
                
                vheader, next_pos, fd_var = read_var_header(fd, endian)
                data = read_var_array(fd_var, endian, vheader)
                if field not in array:
                    array[field] = empty()
                array[field][row].append(data)
                
                fd.seek(next_pos)
    
    for field in fields:
        rows = array[field]
        for i in range(header['dims'][0]):
            rows[i] = squeeze(rows[i])
        array[field] = squeeze(array[field])
    return array


def read_char_array(fd, endian, header):
    array = read_numeric_array(fd, endian, header, ['miUTF8'])
    if header['dims'][0] > 1:
        
        array = [asstr(bytearray(i)) for i in array]
    else:
        
        array = asstr(bytearray(array))
    return array


def read_var_array(fd, endian, header):
    

    mc = inv_mclasses[header['mclass']]

    if mc in numeric_class_etypes:
        return read_numeric_array(
            fd, endian, header,
            set(compressed_numeric).union([numeric_class_etypes[mc]])
        )
    elif mc == 'mxSPARSE_CLASS':
        raise ParseError('Sparse matrices not supported')
    elif mc == 'mxCHAR_CLASS':
        return read_char_array(fd, endian, header)
    elif mc == 'mxCELL_CLASS':
        return read_cell_array(fd, endian, header)
    elif mc == 'mxSTRUCT_CLASS':
        return read_struct_array(fd, endian, header)
    elif mc == 'mxOBJECT_CLASS':
        raise ParseError('Object classes not supported')
    elif mc == 'mxFUNCTION_CLASS':
        raise ParseError('Function classes not supported')
    elif mc == 'mxOPAQUE_CLASS':
        raise ParseError('Anonymous function classes not supported')


def eof(fd):
    

    b = fd.read(1)
    end = len(b) == 0
    if not end:
        curpos = fd.tell()
        fd.seek(curpos - 1)
    return end


class ParseError(Exception):
    pass







def loadmat(filename, meta=False):
    


    if isinstance(filename, basestring):
        fd = open(filename, 'rb')
    else:
        fd = filename

    
    
    
    
    fd.seek(124)
    tst_str = fd.read(4)
    little_endian = (tst_str[2:4] == b'IM')
    endian = ''
    if (sys.byteorder == 'little' and little_endian) or \
       (sys.byteorder == 'big' and not little_endian):
        
        pass
    elif sys.byteorder == 'little':
        
        endian = '>'
    else:
        
        endian = '<'
    maj_ind = int(little_endian)
    
    maj_val = ord(tst_str[maj_ind]) if ispy2 else tst_str[maj_ind]
    if maj_val != 1:
        raise ParseError('Can only read from Matlab level 5 MAT-files')
    
    

    mdict = {}
    if meta:
        
        fd.seek(0)
        mdict['__header__'] = read_file_header(fd, endian)
        mdict['__globals__'] = []

    
    while not eof(fd):
        hdr, next_position, fd_var = read_var_header(fd, endian)
        name = hdr['name']
        if name in mdict:
            raise ParseError('Duplicate variable name "{}" in mat file.'
                             .format(name))

        
        mdict[name] = read_var_array(fd_var, endian, hdr)
        if meta and hdr['is_global']:
            mdict['__globals__'].append(name)

        
        fd.seek(next_position)

    fd.close()
    return mdict
