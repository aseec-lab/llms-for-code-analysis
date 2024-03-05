

import ShirtsIO
import yaml
import os
import code

def new_user(yaml_path):
    


    print 'Retrieve API Key from https://www.shirts.io/accounts/api_console/'
    api_key = raw_input('Shirts.io API Key: ')

    tokens = {
        'api_key': api_key,
    }

    yaml_file = open(yaml_path, 'w+')
    yaml.dump(tokens, yaml_file, indent=2)
    yaml_file.close()

    return tokens


if __name__ == '__main__':
    yaml_path = os.path.expanduser('~') + '/.pyshirtsio'

    if not os.path.exists(yaml_path):
        tokens = new_user(yaml_path)
    else:
        yaml_file = open(yaml_path, "r")
        tokens = yaml.safe_load(yaml_file)
        yaml_file.close()

    client = ShirtsIO.ShirtsIOClient(tokens['api_key'])

    print 'You may run PyShirtsIO commands prefixed with "client."."'
    print 'client = ShirtsIOClient()\n'

    code.interact(local=dict(globals(), **{'client': client}))
