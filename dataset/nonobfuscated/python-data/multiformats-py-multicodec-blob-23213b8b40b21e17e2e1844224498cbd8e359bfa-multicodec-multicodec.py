import varint

from .constants import NAME_TABLE, CODE_TABLE


def extract_prefix(bytes_):
    

    try:
        return varint.decode_bytes(bytes_)
    except TypeError:
        raise ValueError('incorrect varint provided')


def get_prefix(multicodec):
    

    try:
        prefix = varint.encode(NAME_TABLE[multicodec])
    except KeyError:
        raise ValueError('{} multicodec is not supported.'.format(multicodec))
    return prefix


def add_prefix(multicodec, bytes_):
    

    prefix = get_prefix(multicodec)
    return b''.join([prefix, bytes_])


def remove_prefix(bytes_):
    

    prefix_int = extract_prefix(bytes_)
    prefix = varint.encode(prefix_int)
    return bytes_[len(prefix):]


def get_codec(bytes_):
    

    prefix = extract_prefix(bytes_)
    try:
        return CODE_TABLE[prefix]
    except KeyError:
        raise ValueError('Prefix {} not present in the lookup table'.format(prefix))


def is_codec(name):
    

    return name in NAME_TABLE
