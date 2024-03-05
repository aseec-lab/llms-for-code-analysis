





import logging


from yotta.lib import version

from yotta.lib import access_common

logger = logging.getLogger('access')





class GitCloneVersion(version.Version):
    def __init__(self, semver, tag, working_copy):
        self.working_copy = working_copy
        self.tag = tag
        super(GitCloneVersion, self).__init__(semver)

    def unpackInto(self, directory):
        
        from yotta.lib import vcs
        
        from yotta.lib import fsutils
        logger.debug('unpack version %s from git repo %s to %s' % (self.version, self.working_copy.directory, directory))
        tag = self.tag
        fsutils.rmRf(directory)
        vcs.Git.cloneToDirectory(self.working_copy.directory, directory, tag)

        
        self.working_copy.remove()

class GitWorkingCopy(object):
    def __init__(self, vcs):
        self.vcs = vcs
        self.directory = vcs.workingDirectory()

    def remove(self):
        self.vcs.remove()
        self.directory = None

    def availableVersions(self):
        

        r = []
        for t in self.vcs.tags():
            logger.debug("available version tag: %s", t)
            
            if not len(t.strip()):
                continue
            try:
                r.append(GitCloneVersion(t, t, self))
            except ValueError:
                logger.debug('invalid version tag: %s', t)
        return r

    def availableTags(self):
        

        return [GitCloneVersion('', t, self) for t in self.vcs.tags()]

    def availableBranches(self):
        

        return [GitCloneVersion('', b, self) for b in self.vcs.branches()]


    def tipVersion(self):
        return GitCloneVersion('', '', self)

    def commitVersion(self, spec):
        

        import re

        commit_match = re.match('^[a-f0-9]{7,40}$', spec, re.I)
        if commit_match:
            return GitCloneVersion('', spec, self)

        return None

class GitComponent(access_common.RemoteComponent):
    def __init__(self, url, tag_or_branch=None, semantic_spec=None):
        logging.debug('create git component for url:%s version spec:%s' % (url, semantic_spec or tag_or_branch))
        self.url = url
        
        self.spec = semantic_spec
        self.tag_or_branch = tag_or_branch

    @classmethod
    def createFromSource(cls, vs, name=None):
        

        return GitComponent(vs.location, vs.spec, vs.semantic_spec)

    def versionSpec(self):
        return self.spec

    def tagOrBranchSpec(self):
        return self.tag_or_branch

    
    
    
    
    
    
    def clone(self):
        
        from yotta.lib import vcs
        clone = vcs.Git.cloneToTemporaryDir(self.url)
        clone.fetchAllBranches()
        return GitWorkingCopy(clone)

    @classmethod
    def remoteType(cls):
        return 'git'
