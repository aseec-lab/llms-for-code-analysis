






from .metadata import MetadataObject
from .sortabledict import SortableDict
from collections import MutableSequence
from .version import Version, VER_3_0, VER_2_0

class Grid(MutableSequence):
    


    def __init__(self, version=None, metadata=None, columns=None):
        

        
        version_given = version is not None
        if version_given:
            version = Version(version)
        else:
            version = VER_2_0
        self._version   = version
        self._version_given = version_given

        
        self.metadata   = MetadataObject(validate_fn=self._detect_or_validate)

        
        self.column     = SortableDict()

        
        self._row       = []

        if metadata is not None:
            self.metadata.update(metadata.items())

        if columns is not None:
            if isinstance(columns, dict) or isinstance(columns, SortableDict):
                columns = list(columns.items())

            for col_id, col_meta in columns:
                
                if isinstance(col_meta, dict) or \
                        isinstance(col_meta, SortableDict):
                    col_meta = list(col_meta.items())

                mo = MetadataObject(validate_fn=self._detect_or_validate)
                mo.extend(col_meta)
                self.column.add_item(col_id, mo)

    @property
    def version(self): 
        
        return self._version

    @property
    def nearest_version(self): 
        
        return Version.nearest(self._version)

    @property
    def ver_str(self): 
        
        return str(self.version)

    def __repr__(self): 
        
        

        parts = [u'\tVersion: %s' % self.ver_str]
        if bool(self.metadata):
            parts.append(u'\tMetadata: %s' % self.metadata)

        column_meta = []
        for col, col_meta in self.column.items():
            if bool(col_meta):
                column_meta.append(u'\t\t%s: %s' % (col, col_meta))
            else:
                column_meta.append(u'\t\t%s' % col)

        if bool(column_meta):
            parts.append(u'\tColumns:\n%s' % '\n'.join(column_meta))
        elif len(self.column):
            parts.append(u'\tColumns: %s' % ', '.join(self.column.keys()))
        else:
            parts.append(u'\tNo columns')

        if bool(self):
            parts.extend([
                u'\tRow %4d:\n\t%s' % (row, u'\n\t'.join([
                    ((u'%s=%r' % (col, data[col])) \
                            if col in data else \
                    (u'%s absent' % col)) for col \
                    in self.column.keys()]))
                for (row, data) in enumerate(self)
            ])
        else:
            parts.append(u'\tNo rows')
        
        class_name = self.__class__.__name__
        return u'<%s>\n%s\n</%s>' % (
                class_name, u'\n'.join(parts), class_name
        )

    def __getitem__(self, index):
        

        return self._row[index]

    def __len__(self):
        

        return len(self._row)

    def __setitem__(self, index, value):
        

        if not isinstance(value, dict):
            raise TypeError('value must be a dict')
        for val in value.values():
            self._detect_or_validate(val)
        self._row[index] = value

    def __delitem__(self, index):
        

        del self._row[index]

    def insert(self, index, value):
        

        if not isinstance(value, dict):
            raise TypeError('value must be a dict')
        for val in value.values():
            self._detect_or_validate(val)
        self._row.insert(index, value)

    def _detect_or_validate(self, val):
        

        if isinstance(val, list) \
                or isinstance(val, dict) \
                or isinstance(val, SortableDict) \
                or isinstance(val, Grid):
            
            self._assert_version(VER_3_0)

    def _assert_version(self, version):
        

        if self.nearest_version < version:
            if self._version_given:
                raise ValueError(
                        'Data type requires version %s' \
                        % version)
            else:
                self._version = version
