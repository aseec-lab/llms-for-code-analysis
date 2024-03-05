

import os, sys, json
from ..plugins.common import plugins_get_mgr
from dgitcore import exceptions





__all__ = ['transform']




def instantiate(repo, name=None, filename=None):
    


    default_transformers = repo.options.get('transformer', {})

    
    
    
    transformers = {}
    if name is not None:
        
        if name in default_transformers:
            transformers = {
                name : default_transformers[name]
            }
        else:
            transformers = {
                name : {
                    'files': [],
                }
            }
    else:
        transformers = default_transformers

    
    
    
    
    input_matching_files = None
    if filename is not None:
        input_matching_files = repo.find_matching_files([filename])

    for t in transformers:
        for k in transformers[t]:
            if "files" not in k:
                continue
            if k == "files" and input_matching_files is not None:
                
                transformers[t][k] = input_matching_files
            else:
                
                if transformers[t][k] is None or len(transformers[t][k]) == 0:
                    transformers[t][k] = []
                else:
                    matching_files = repo.find_matching_files(transformers[t][k])
                    transformers[t][k] = matching_files

    return transformers

def transform(repo,
              name=None,
              filename=None,
              force=False,
              args=[]):
    

    mgr = plugins_get_mgr()

    
    specs = instantiate(repo, name, filename)

    
    allresults = []
    for s in specs:
        keys = mgr.search(what='transformer',name=s)['transformer']
        for k in keys:
            t = mgr.get_by_key('transformer', k)
            result = t.evaluate(repo,
                                specs[s],
                                force,
                                args)
            allresults.extend(result)

    return allresults
