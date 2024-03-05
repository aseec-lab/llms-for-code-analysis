




















from __future__ import (
    absolute_import,
    division,
    print_function,
    unicode_literals,
)

from unicodedata import normalize

from six import text_type
from six.moves import range

from ._stemmer import _Stemmer

__all__ = ['Schinke', 'schinke']


class Schinke(_Stemmer):
    


    _keep_que = {
        'at',
        'quo',
        'ne',
        'ita',
        'abs',
        'aps',
        'abus',
        'adae',
        'adus',
        'deni',
        'de',
        'sus',
        'obli',
        'perae',
        'plenis',
        'quando',
        'quis',
        'quae',
        'cuius',
        'cui',
        'quem',
        'quam',
        'qua',
        'qui',
        'quorum',
        'quarum',
        'quibus',
        'quos',
        'quas',
        'quotusquis',
        'quous',
        'ubi',
        'undi',
        'us',
        'uter',
        'uti',
        'utro',
        'utribi',
        'tor',
        'co',
        'conco',
        'contor',
        'detor',
        'deco',
        'exco',
        'extor',
        'obtor',
        'optor',
        'retor',
        'reco',
        'attor',
        'inco',
        'intor',
        'praetor',
    }

    _n_endings = {
        4: {'ibus'},
        3: {'ius'},
        2: {
            'is',
            'nt',
            'ae',
            'os',
            'am',
            'ud',
            'as',
            'um',
            'em',
            'us',
            'es',
            'ia',
        },
        1: {'a', 'e', 'i', 'o', 'u'},
    }

    _v_endings_strip = {
        6: {},
        5: {},
        4: {'mini', 'ntur', 'stis'},
        3: {'mur', 'mus', 'ris', 'sti', 'tis', 'tur'},
        2: {'ns', 'nt', 'ri'},
        1: {'m', 'r', 's', 't'},
    }
    _v_endings_alter = {
        6: {'iuntur'},
        5: {'beris', 'erunt', 'untur'},
        4: {'iunt'},
        3: {'bor', 'ero', 'unt'},
        2: {'bo'},
        1: {},
    }

    def stem(self, word):
        

        word = normalize('NFKD', text_type(word.lower()))
        word = ''.join(
            c
            for c in word
            if c
            in {
                'a',
                'b',
                'c',
                'd',
                'e',
                'f',
                'g',
                'h',
                'i',
                'j',
                'k',
                'l',
                'm',
                'n',
                'o',
                'p',
                'q',
                'r',
                's',
                't',
                'u',
                'v',
                'w',
                'x',
                'y',
                'z',
            }
        )

        
        word = word.replace('j', 'i').replace('v', 'u')

        
        if word[-3:] == 'que':
            
            
            if word[:-3] in self._keep_que or word == 'que':
                return {'n': word, 'v': word}
            else:
                word = word[:-3]

        
        noun = word
        verb = word

        
        for endlen in range(4, 0, -1):
            if word[-endlen:] in self._n_endings[endlen]:
                if len(word) - 2 >= endlen:
                    noun = word[:-endlen]
                else:
                    noun = word
                break

        for endlen in range(6, 0, -1):
            if word[-endlen:] in self._v_endings_strip[endlen]:
                if len(word) - 2 >= endlen:
                    verb = word[:-endlen]
                else:
                    verb = word
                break
            if word[-endlen:] in self._v_endings_alter[endlen]:
                if word[-endlen:] in {
                    'iuntur',
                    'erunt',
                    'untur',
                    'iunt',
                    'unt',
                }:
                    new_word = word[:-endlen] + 'i'
                    addlen = 1
                elif word[-endlen:] in {'beris', 'bor', 'bo'}:
                    new_word = word[:-endlen] + 'bi'
                    addlen = 2
                else:
                    new_word = word[:-endlen] + 'eri'
                    addlen = 3

                
                
                if len(new_word) >= 2 + addlen:
                    verb = new_word
                else:
                    verb = word
                break

        return {'n': noun, 'v': verb}


def schinke(word):
    

    return Schinke().stem(word)


if __name__ == '__main__':
    import doctest

    doctest.testmod()
