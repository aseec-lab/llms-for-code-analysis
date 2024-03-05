
































import random


__all__ = ['account_number', 'bik', 'inn', 'legal_inn', 'legal_ogrn',
           'ogrn', 'person_inn', 'person_ogrn']

TYPES = ['person', 'legal']


def account_number():
    

    account = [random.randint(1, 9) for _ in range(20)]
    return "".join(map(str, account))


def bik():
    

    return '04' + \
           ''.join([str(random.randint(1, 9)) for _ in range(5)]) + \
           str(random.randint(0, 49) + 50)


def inn(type="legal"):
    

    if (type in TYPES) and type == 'person':
        return person_inn()
    else:
        return legal_inn()


def legal_inn():
    

    mask = [2, 4, 10, 3, 5, 9, 4, 6, 8]
    inn = [random.randint(1, 9) for _ in range(10)]
    weighted = [v * mask[i] for i, v in enumerate(inn[:-1])]
    inn[9] = sum(weighted) % 11 % 10
    return "".join(map(str, inn))


def legal_ogrn():
    

    ogrn = "".join(map(str, [random.randint(1, 9) for _ in range(12)]))
    ogrn += str((int(ogrn) % 11 % 10))
    return ogrn


def ogrn(type="legal"):
    

    if (type in TYPES) and type == 'person':
        return person_ogrn()
    else:
        return legal_ogrn()


def person_inn():
    

    mask11 = [7, 2, 4, 10, 3, 5, 9, 4, 6, 8]
    mask12 = [3, 7, 2, 4, 10, 3, 5, 9, 4, 6, 8]
    inn = [random.randint(1, 9) for _ in range(12)]

    
    weighted11 = [v * mask11[i] for i, v in enumerate(inn[:-2])]
    inn[10] = sum(weighted11) % 11 % 10

    
    weighted12 = [v * mask12[i] for i, v in enumerate(inn[:-1])]
    inn[11] = sum(weighted12) % 11 % 10

    return "".join(map(str, inn))


def person_ogrn():
    

    ogrn = "".join(map(str, [random.randint(1, 9) for _ in range(14)]))
    ogrn += str((int(ogrn) % 13 % 10))
    return ogrn
