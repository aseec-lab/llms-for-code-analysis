




from __future__ import print_function

from . import file_info
from . import core
from .log import logger
from .core import ENCODING_VALS
from .core import is_number
from .core import sox
from .core import play
from .core import SoxError
from .core import SoxiError
from .core import VALID_FORMATS

from .transform import Transformer


COMBINE_VALS = [
    'concatenate', 'merge', 'mix', 'mix-power', 'multiply'
]


class Combiner(Transformer):
    

    def __init__(self):
        super(Combiner, self).__init__()

    def build(self, input_filepath_list, output_filepath, combine_type,
              input_volumes=None):
        

        file_info.validate_input_file_list(input_filepath_list)
        file_info.validate_output_file(output_filepath)
        _validate_combine_type(combine_type)
        _validate_volumes(input_volumes)

        input_format_list = _build_input_format_list(
            input_filepath_list, input_volumes, self.input_format
        )

        try:
            _validate_file_formats(input_filepath_list, combine_type)
        except SoxiError:
            logger.warning("unable to validate file formats.")

        args = []
        args.extend(self.globals)
        args.extend(['--combine', combine_type])

        input_args = _build_input_args(input_filepath_list, input_format_list)
        args.extend(input_args)

        args.extend(self.output_format)
        args.append(output_filepath)
        args.extend(self.effects)

        status, out, err = sox(args)

        if status != 0:
            raise SoxError(
                "Stdout: {}\nStderr: {}".format(out, err)
            )
        else:
            logger.info(
                "Created %s with combiner %s and  effects: %s",
                output_filepath,
                combine_type,
                " ".join(self.effects_log)
            )
            if out is not None:
                logger.info("[SoX] {}".format(out))
            return True

    def preview(self, input_filepath_list, combine_type, input_volumes=None):
        

        args = ["play", "--no-show-progress"]
        args.extend(self.globals)
        args.extend(['--combine', combine_type])

        input_format_list = _build_input_format_list(
            input_filepath_list, input_volumes, self.input_format
        )
        input_args = _build_input_args(input_filepath_list, input_format_list)
        args.extend(input_args)
        args.extend(self.effects)

        play(args)

    def set_input_format(self, file_type=None, rate=None, bits=None,
                         channels=None, encoding=None, ignore_length=None):
        

        if file_type is not None and not isinstance(file_type, list):
            raise ValueError("file_type must be a list or None.")

        if file_type is not None:
            if not all([f in VALID_FORMATS for f in file_type]):
                raise ValueError(
                    'file_type elements '
                    'must be one of {}'.format(VALID_FORMATS)
                )
        else:
            file_type = []

        if rate is not None and not isinstance(rate, list):
            raise ValueError("rate must be a list or None.")

        if rate is not None:
            if not all([is_number(r) and r > 0 for r in rate]):
                raise ValueError('rate elements must be positive floats.')
        else:
            rate = []

        if bits is not None and not isinstance(bits, list):
            raise ValueError("bits must be a list or None.")

        if bits is not None:
            if not all([isinstance(b, int) and b > 0 for b in bits]):
                raise ValueError('bit elements must be positive ints.')
        else:
            bits = []

        if channels is not None and not isinstance(channels, list):
            raise ValueError("channels must be a list or None.")

        if channels is not None:
            if not all([isinstance(c, int) and c > 0 for c in channels]):
                raise ValueError('channel elements must be positive ints.')
        else:
            channels = []

        if encoding is not None and not isinstance(encoding, list):
            raise ValueError("encoding must be a list or None.")

        if encoding is not None:
            if not all([e in ENCODING_VALS for e in encoding]):
                raise ValueError(
                    'elements of encoding must '
                    'be one of {}'.format(ENCODING_VALS)
                )
        else:
            encoding = []

        if ignore_length is not None and not isinstance(ignore_length, list):
            raise ValueError("ignore_length must be a list or None.")

        if ignore_length is not None:
            if not all([isinstance(l, bool) for l in ignore_length]):
                raise ValueError("ignore_length elements must be booleans.")
        else:
            ignore_length = []

        max_input_arg_len = max([
            len(file_type), len(rate), len(bits), len(channels),
            len(encoding), len(ignore_length)
        ])

        input_format = []
        for _ in range(max_input_arg_len):
            input_format.append([])

        for i, f in enumerate(file_type):
            input_format[i].extend(['-t', '{}'.format(f)])

        for i, r in enumerate(rate):
            input_format[i].extend(['-r', '{}'.format(r)])

        for i, b in enumerate(bits):
            input_format[i].extend(['-b', '{}'.format(b)])

        for i, c in enumerate(channels):
            input_format[i].extend(['-c', '{}'.format(c)])

        for i, e in enumerate(encoding):
            input_format[i].extend(['-e', '{}'.format(e)])

        for i, l in enumerate(ignore_length):
            if l is True:
                input_format[i].append('--ignore-length')

        self.input_format = input_format
        return self


def _validate_file_formats(input_filepath_list, combine_type):
    

    _validate_sample_rates(input_filepath_list, combine_type)

    if combine_type == 'concatenate':
        _validate_num_channels(input_filepath_list, combine_type)


def _validate_sample_rates(input_filepath_list, combine_type):
    

    sample_rates = [
        file_info.sample_rate(f) for f in input_filepath_list
    ]
    if not core.all_equal(sample_rates):
        raise IOError(
            "Input files do not have the same sample rate. The {} combine "
            "type requires that all files have the same sample rate"
            .format(combine_type)
        )


def _validate_num_channels(input_filepath_list, combine_type):
    

    channels = [
        file_info.channels(f) for f in input_filepath_list
    ]
    if not core.all_equal(channels):
        raise IOError(
            "Input files do not have the same number of channels. The "
            "{} combine type requires that all files have the same "
            "number of channels"
            .format(combine_type)
        )


def _build_input_format_list(input_filepath_list, input_volumes=None,
                             input_format=None):
    

    n_inputs = len(input_filepath_list)
    input_format_list = []
    for _ in range(n_inputs):
        input_format_list.append([])

    
    if input_volumes is None:
        vols = [1] * n_inputs
    else:
        n_volumes = len(input_volumes)
        if n_volumes < n_inputs:
            logger.warning(
                'Volumes were only specified for %s out of %s files.'
                'The last %s files will remain at their original volumes.',
                n_volumes, n_inputs, n_inputs - n_volumes
            )
            vols = input_volumes + [1] * (n_inputs - n_volumes)
        elif n_volumes > n_inputs:
            logger.warning(
                '%s volumes were specified but only %s input files exist.'
                'The last %s volumes will be ignored.',
                n_volumes, n_inputs, n_volumes - n_inputs
            )
            vols = input_volumes[:n_inputs]
        else:
            vols = [v for v in input_volumes]

    
    if input_format is None:
        fmts = [[] for _ in range(n_inputs)]
    else:
        n_fmts = len(input_format)
        if n_fmts < n_inputs:
            logger.warning(
                'Input formats were only specified for %s out of %s files.'
                'The last %s files will remain unformatted.',
                n_fmts, n_inputs, n_inputs - n_fmts
            )
            fmts = [f for f in input_format]
            fmts.extend([[] for _ in range(n_inputs - n_fmts)])
        elif n_fmts > n_inputs:
            logger.warning(
                '%s Input formats were specified but only %s input files exist'
                '. The last %s formats will be ignored.',
                n_fmts, n_inputs, n_fmts - n_inputs
            )
            fmts = input_format[:n_inputs]
        else:
            fmts = [f for f in input_format]

    for i, (vol, fmt) in enumerate(zip(vols, fmts)):
        input_format_list[i].extend(['-v', '{}'.format(vol)])
        input_format_list[i].extend(fmt)

    return input_format_list


def _build_input_args(input_filepath_list, input_format_list):
    

    if len(input_format_list) != len(input_filepath_list):
        raise ValueError(
            "input_format_list & input_filepath_list are not the same size"
        )

    input_args = []
    zipped = zip(input_filepath_list, input_format_list)
    for input_file, input_fmt in zipped:
        input_args.extend(input_fmt)
        input_args.append(input_file)

    return input_args


def _validate_combine_type(combine_type):
    

    if combine_type not in COMBINE_VALS:
        raise ValueError(
            'Invalid value for combine_type. Must be one of {}'.format(
                COMBINE_VALS)
        )


def _validate_volumes(input_volumes):
    

    if not (input_volumes is None or isinstance(input_volumes, list)):
        raise TypeError("input_volumes must be None or a list.")

    if isinstance(input_volumes, list):
        for vol in input_volumes:
            if not core.is_number(vol):
                raise ValueError(
                    "Elements of input_volumes must be numbers: found {}"
                    .format(vol)
                )
