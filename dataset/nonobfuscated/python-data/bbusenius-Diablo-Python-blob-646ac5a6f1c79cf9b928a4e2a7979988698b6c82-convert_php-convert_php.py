

















import phpserialize
from collections import OrderedDict
import pprint
import json

class ConvertPHP():
    

    def __init__(self):
        

        self.data_structure = ''
        self.built_in = set(['python', 'json'])

    def __str__(self):
        

        return self.data_structure

    def is_built_in(self, language):
        

        return language in self.built_in

    
    lang_specific_values = {'php': { 
                                    'True'  : 'true',
                                    'False' : 'false',
                                    'None'  : 'null'},
                            'javascript' : {
                                    'True' : 'true',
                                    'False' : 'false',
                                    'None' : 'null'},
                            'ocaml' : {
                                    'True' : 'true',
                                    'False': 'false'}}

    
    outer_templates = {'php' : 'array (\n%s\n);',
                       'javascript' : 'var jsObject = {\n%s\n}',
                       'ocaml' : 'let map = [|\n%s\n|] ;;'}

    def get_built_in(self, language, level, data):
        

        
        pp = pprint.PrettyPrinter(indent=level)

        lookup = {'python' : pp.pformat(data),
                  'json' : str(json.dumps(data, sort_keys=True, indent=level, separators=(',', ': ')))}

        self.data_structure = lookup[language]

    def get_inner_template(self, language, template_type, indentation, key, val):
        

        
        inner_templates = {'php' : {
                                'iterable' : '%s%s => array \n%s( \n%s%s),\n' % (indentation, key, indentation, val, indentation),
                                'singular' : '%s%s => %s, \n' % (indentation, key, val) },
                           'javascript' : {
                                'iterable' : '%s%s : {\n%s\n%s},\n' % (indentation, key, val, indentation),
                                'singular' : '%s%s: %s,\n' % (indentation, key, val)},
                           'ocaml' : { 
                                'iterable' : '%s[| (%s, (\n%s\n%s))|] ;;\n' % (indentation, key, val, indentation),
                                'singular' : '%s(%s, %s);\n' % (indentation, key, val)}}

        return inner_templates[language][template_type]

    def translate_val(self, language, value):
        

        return self.lang_specific_values[language][value]


    def is_iterable(self, data):
        

        try:
            iterate = iter(data)
            return True
        except:
            return False 


    def translate_array(self, string, language, level=3, retdata=False):
        

        language = language.lower()
        assert self.is_built_in(language) or language in self.outer_templates, \
            "Sorry, " + language + " is not a supported language."

        
        data = phpserialize.loads(bytes(string, 'utf-8'), array_hook=list, decode_strings=True)

        
        
        if self.is_built_in(language):
            self.get_built_in(language, level, data) 
            print(self)
            return self.data_structure if retdata else None

        
        def loop_print(iterable, level=3):
            

            retval = ''
            indentation = ' ' * level

            
            if not self.is_iterable(iterable) or isinstance(iterable, str):
                non_iterable = str(iterable)
                return str(non_iterable)
             
            
            for item in iterable:
                
                if isinstance(item, tuple) and len(item) == 2:
                    
                    key = item[0]
                    val = loop_print(item[1], level=level+3)
            
                    
                    val = self.translate_val(language, val) if language in self.lang_specific_values \
                          and val in self.lang_specific_values[language] else val
     
                    
                    
                    key = str(key) if isinstance(key, int) else '\'' + str(key) + '\''

                    
                    needs_unpacking = hasattr(item[0],'__iter__') == False \
                                      and hasattr(item[1],'__iter__') == True 

                    
                    if needs_unpacking:
                        retval += self.get_inner_template(language, 'iterable', indentation, key, val)
                    
                    else:
                        
                        
                        val = str(val) if val.isdigit() or val in self.lang_specific_values[language].values() else '\'' + str(val) + '\''

                        retval += self.get_inner_template(language, 'singular', indentation, key, val) 

            return retval
    
        
        self.data_structure = self.outer_templates[language] % (loop_print(data))
        print(self)
        return self.data_structure if retdata else None

