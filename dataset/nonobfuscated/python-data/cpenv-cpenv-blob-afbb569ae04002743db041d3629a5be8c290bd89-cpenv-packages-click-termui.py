import os
import sys
import struct

from ._compat import raw_input, text_type, string_types, \
     isatty, strip_ansi, get_winterm_size, DEFAULT_COLUMNS, WIN
from .utils import echo
from .exceptions import Abort, UsageError
from .types import convert_type
from .globals import resolve_color_default




visible_prompt_func = raw_input

_ansi_colors = ('black', 'red', 'green', 'yellow', 'blue', 'magenta',
                'cyan', 'white', 'reset')
_ansi_reset_all = '\033[0m'


def hidden_prompt_func(prompt):
    import getpass
    return getpass.getpass(prompt)


def _build_prompt(text, suffix, show_default=False, default=None):
    prompt = text
    if default is not None and show_default:
        prompt = '%s [%s]' % (prompt, default)
    return prompt + suffix


def prompt(text, default=None, hide_input=False,
           confirmation_prompt=False, type=None,
           value_proc=None, prompt_suffix=': ',
           show_default=True, err=False):
    

    result = None

    def prompt_func(text):
        f = hide_input and hidden_prompt_func or visible_prompt_func
        try:
            
            
            echo(text, nl=False, err=err)
            return f('')
        except (KeyboardInterrupt, EOFError):
            
            
            
            if hide_input:
                echo(None, err=err)
            raise Abort()

    if value_proc is None:
        value_proc = convert_type(type, default)

    prompt = _build_prompt(text, prompt_suffix, show_default, default)

    while 1:
        while 1:
            value = prompt_func(prompt)
            if value:
                break
            
            
            
            elif default is not None:
                return default
        try:
            result = value_proc(value)
        except UsageError as e:
            echo('Error: %s' % e.message, err=err)
            continue
        if not confirmation_prompt:
            return result
        while 1:
            value2 = prompt_func('Repeat for confirmation: ')
            if value2:
                break
        if value == value2:
            return result
        echo('Error: the two entered values do not match', err=err)


def confirm(text, default=False, abort=False, prompt_suffix=': ',
            show_default=True, err=False):
    

    prompt = _build_prompt(text, prompt_suffix, show_default,
                           default and 'Y/n' or 'y/N')
    while 1:
        try:
            
            
            echo(prompt, nl=False, err=err)
            value = visible_prompt_func('').lower().strip()
        except (KeyboardInterrupt, EOFError):
            raise Abort()
        if value in ('y', 'yes'):
            rv = True
        elif value in ('n', 'no'):
            rv = False
        elif value == '':
            rv = default
        else:
            echo('Error: invalid input', err=err)
            continue
        break
    if abort and not rv:
        raise Abort()
    return rv


def get_terminal_size():
    

    
    if sys.version_info >= (3, 3):
        import shutil
        shutil_get_terminal_size = getattr(shutil, 'get_terminal_size', None)
        if shutil_get_terminal_size:
            sz = shutil_get_terminal_size()
            return sz.columns, sz.lines

    if get_winterm_size is not None:
        return get_winterm_size()

    def ioctl_gwinsz(fd):
        try:
            import fcntl
            import termios
            cr = struct.unpack(
                'hh', fcntl.ioctl(fd, termios.TIOCGWINSZ, '1234'))
        except Exception:
            return
        return cr

    cr = ioctl_gwinsz(0) or ioctl_gwinsz(1) or ioctl_gwinsz(2)
    if not cr:
        try:
            fd = os.open(os.ctermid(), os.O_RDONLY)
            try:
                cr = ioctl_gwinsz(fd)
            finally:
                os.close(fd)
        except Exception:
            pass
    if not cr or not cr[0] or not cr[1]:
        cr = (os.environ.get('LINES', 25),
              os.environ.get('COLUMNS', DEFAULT_COLUMNS))
    return int(cr[1]), int(cr[0])


def echo_via_pager(text, color=None):
    

    color = resolve_color_default(color)
    if not isinstance(text, string_types):
        text = text_type(text)
    from ._termui_impl import pager
    return pager(text + '\n', color)


def progressbar(iterable=None, length=None, label=None, show_eta=True,
                show_percent=None, show_pos=False,
                item_show_func=None, fill_char='
                bar_template='%(label)s  [%(bar)s]  %(info)s',
                info_sep='  ', width=36, file=None, color=None):
    

    from ._termui_impl import ProgressBar
    color = resolve_color_default(color)
    return ProgressBar(iterable=iterable, length=length, show_eta=show_eta,
                       show_percent=show_percent, show_pos=show_pos,
                       item_show_func=item_show_func, fill_char=fill_char,
                       empty_char=empty_char, bar_template=bar_template,
                       info_sep=info_sep, file=file, label=label,
                       width=width, color=color)


def clear():
    

    if not isatty(sys.stdout):
        return
    
    
    
    if WIN:
        os.system('cls')
    else:
        sys.stdout.write('\033[2J\033[1;1H')


def style(text, fg=None, bg=None, bold=None, dim=None, underline=None,
          blink=None, reverse=None, reset=True):
    

    bits = []
    if fg:
        try:
            bits.append('\033[%dm' % (_ansi_colors.index(fg) + 30))
        except ValueError:
            raise TypeError('Unknown color %r' % fg)
    if bg:
        try:
            bits.append('\033[%dm' % (_ansi_colors.index(bg) + 40))
        except ValueError:
            raise TypeError('Unknown color %r' % bg)
    if bold is not None:
        bits.append('\033[%dm' % (1 if bold else 22))
    if dim is not None:
        bits.append('\033[%dm' % (2 if dim else 22))
    if underline is not None:
        bits.append('\033[%dm' % (4 if underline else 24))
    if blink is not None:
        bits.append('\033[%dm' % (5 if blink else 25))
    if reverse is not None:
        bits.append('\033[%dm' % (7 if reverse else 27))
    bits.append(text)
    if reset:
        bits.append(_ansi_reset_all)
    return ''.join(bits)


def unstyle(text):
    

    return strip_ansi(text)


def secho(text, file=None, nl=True, err=False, color=None, **styles):
    

    return echo(style(text, **styles), file=file, nl=nl, err=err, color=color)


def edit(text=None, editor=None, env=None, require_save=True,
         extension='.txt', filename=None):
    r

    from ._termui_impl import Editor
    editor = Editor(editor=editor, env=env, require_save=require_save,
                    extension=extension)
    if filename is None:
        return editor.edit(text)
    editor.edit_file(filename)


def launch(url, wait=False, locate=False):
    

    from ._termui_impl import open_url
    return open_url(url, wait=wait, locate=locate)




_getchar = None


def getchar(echo=False):
    

    f = _getchar
    if f is None:
        from ._termui_impl import getchar as f
    return f(echo)


def pause(info='Press any key to continue ...', err=False):
    

    if not isatty(sys.stdin) or not isatty(sys.stdout):
        return
    try:
        if info:
            echo(info, nl=False, err=err)
        try:
            getchar()
        except (KeyboardInterrupt, EOFError):
            pass
    finally:
        if info:
            echo(err=err)
