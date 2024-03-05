



import datetime

from arguspy.wmi_sh import Wmi


class FileNumber(Wmi):

    


    def __init__(self, *args, **kwargs):
        super(FileNumber, self).__init__(*args, **kwargs)
        self.logger.debug("Init FileNumber")

    def define_sub_options(self):
        super(FileNumber, self).define_sub_options()
        self.fn_parser = self.subparsers.add_parser('filenumber',
                                                    help='Count file number.',
                                                    description='Options\
                                                    for filenumber.')
        self.fn_parser.add_argument('-q', '--query',
                                    required=False,
                                    help='wql for wmi.',
                                    dest='query')
        self.fn_parser.add_argument('-d', '--drive',
                                    required=True,
                                    help='the windows driver, like C:',
                                    dest='drive')
        self.fn_parser.add_argument('-p', '--path',
                                    default="\\\\",
                                    required=False,
                                    help='the folder, default is %(default)s',
                                    dest='path')
        self.fn_parser.add_argument(
            '-f',
            '--filename',
            default="%%",
            required=False,
            help='the filename, default is %(default)s',
            dest='filename')
        self.fn_parser.add_argument(
            '-e', '--extension', default="%%", required=False,
            help='the file extension, default is %(default)s',
            dest='extension')
        self.fn_parser.add_argument('-R', '--recursion',
                                    action='store_true',
                                    help='Recursive count file under path.',
                                    dest='recursion')
        self.fn_parser.add_argument(
            '-w',
            '--warning',
            default=0,
            type=int,
            required=False,
            help='Warning number of file, default is %(default)s',
            dest='warning')
        self.fn_parser.add_argument(
            '-c',
            '--critical',
            default=0,
            type=int,
            required=False,
            help='Critical number of file, default is %(default)s',
            dest='critical')

    def __get_file(self, path):
        self.wql_file = "SELECT Name FROM CIM_DataFile WHERE Drive='{0}' \
            AND Path='{1}' AND FileName LIKE '{2}' AND Extension LIKE '{3}'".format(
            self.args.drive, path, self.args.filename, self.args.extension)
        self.file_data = self.query(self.wql_file)
        [self.file_list.append(file_data) for file_data in self.file_data]
        self.logger.debug("file_data: {}".format(self.file_data))
        return len(self.file_data), self.file_list

    def __get_folder(self, path):
        self.wql_folder = "SELECT FileName FROM CIM_Directory WHERE Drive='{0}' AND Path='{1}'".format(
            self.args.drive, path)
        self.number, self.file_list = self.__get_file(path)
        self.count += self.number
        self.folder_data = self.query(self.wql_folder)
        self.logger.debug("folder_data: {}".format(self.folder_data))
        if self.folder_data:
            for folder in self.folder_data:
                self.new_path = (
                    folder['Name'].split(":")[1] +
                    "\\").replace(
                    "\\",
                    "\\\\")
                self.__get_folder(self.new_path)
        return self.count, self.file_list

    def filenumber_handle(self):
        

        self.file_list = []
        self.count = 0
        status = self.ok

        if self.args.recursion:
            self.__result, self.__file_list = self.__get_folder(self.args.path)
        else:
            self.__result, self.__file_list = self.__get_file(self.args.path)

        
        if self.__result > self.args.critical:
            status = self.critical
        elif self.__result > self.args.warning:
            status = self.warning
        else:
            status = self.ok

        
        self.shortoutput = "Found {0} files in {1}.".format(self.__result,
                                                            self.args.path)
        self.logger.debug("file_list: {}".format(self.__file_list))
        [self.longoutput.append(file_data.get('Name'))
         for file_data in self.__file_list]
        self.perfdata.append("{path}={result};{warn};{crit};0;".format(
            crit=self.args.critical,
            warn=self.args.warning,
            result=self.__result,
            path=self.args.path))

        
        status(self.output(long_output_limit=None))
        self.logger.debug("Return status and exit to Nagios.")


class FileAge(Wmi):

    


    def __init__(self, *args, **kwargs):
        super(FileAge, self).__init__(*args, **kwargs)
        self.logger.debug("Init FileAge")

    def define_sub_options(self):
        super(FileAge, self).define_sub_options()
        self.fa_parser = self.subparsers.add_parser('fileage',
                                                    help='Get file age.',
                                                    description='Options\
                                                    for fileage.')
        self.fa_parser.add_argument('-q', '--query',
                                    required=False,
                                    help='wql for wmi.',
                                    dest='query')
        self.fa_parser.add_argument('-d', '--drive',
                                    required=True,
                                    help='the windows driver, like C:',
                                    dest='drive')
        self.fa_parser.add_argument('-p', '--path',
                                    default="\\\\",
                                    required=False,
                                    help='the folder, default is %(default)s',
                                    dest='path')
        self.fa_parser.add_argument(
            '-f',
            '--filename',
            default="%%",
            required=False,
            help='the filename, default is %(default)s',
            dest='filename')
        self.fa_parser.add_argument(
            '-e', '--extension', default="%%", required=False,
            help='the file extension, default is %(default)s',
            dest='extension')
        self.fa_parser.add_argument('-R', '--recursion',
                                    action='store_true',
                                    help='Recursive count file under path.',
                                    dest='recursion')
        self.fa_parser.add_argument(
            '-w',
            '--warning',
            default=30,
            type=int,
            required=False,
            help='Warning minute of file, default is %(default)s',
            dest='warning')
        self.fa_parser.add_argument(
            '-c',
            '--critical',
            default=60,
            type=int,
            required=False,
            help='Critical minute of file, default is %(default)s',
            dest='critical')

    def __get_file(self, path):
        self.wql_file = "SELECT LastModified FROM CIM_DataFile WHERE Drive='{0}' \
            AND Path='{1}' AND FileName LIKE '{2}' AND Extension LIKE '{3}'".format(
            self.args.drive, path, self.args.filename, self.args.extension)
        self.file_data = self.query(self.wql_file)
        [self.file_list.append(file_data) for file_data in self.file_data]
        self.logger.debug("file_data: {}".format(self.file_data))
        return self.file_list

    def __get_folder(self, path):
        self.wql_folder = "SELECT FileName FROM CIM_Directory WHERE Drive='{0}' AND Path='{1}'".format(
            self.args.drive, path)
        self.file_list = self.__get_file(path)
        self.folder_data = self.query(self.wql_folder)
        self.logger.debug("folder_data: {}".format(self.folder_data))
        if self.folder_data:
            for folder in self.folder_data:
                self.new_path = (
                    folder['Name'].split(":")[1] +
                    "\\").replace(
                    "\\",
                    "\\\\")
                self.__get_folder(self.new_path)
        return self.file_list

    def __get_current_datetime(self):
        

        self.wql_time = "SELECT LocalDateTime FROM Win32_OperatingSystem"
        self.current_time = self.query(self.wql_time)
        
        self.current_time_string = str(
            self.current_time[0].get('LocalDateTime').split('.')[0])
        
        self.current_time_format = datetime.datetime.strptime(
            self.current_time_string, '%Y%m%d%H%M%S')
        
        
        return self.current_time_format

    def fileage_handle(self):
        

        self.file_list = []
        self.ok_file = []
        self.warn_file = []
        self.crit_file = []
        status = self.ok

        if self.args.recursion:
            self.__file_list = self.__get_folder(self.args.path)
        else:
            self.__file_list = self.__get_file(self.args.path)
        self.logger.debug("file_list: {}".format(self.__file_list))
        
        
        

        for file_dict in self.__file_list:
            self.filename = file_dict.get('Name')
            if self.filename and self.filename != 'Name':
                self.logger.debug(
                    "===== start to compare {} =====".format(
                        self.filename))

                self.file_datetime_string = file_dict.get(
                    'LastModified').split('.')[0]
                self.file_datetime = datetime.datetime.strptime(
                    self.file_datetime_string, '%Y%m%d%H%M%S')
                self.logger.debug(
                    "file_datetime: {}".format(
                        self.file_datetime))

                self.current_datetime = self.__get_current_datetime()
                self.logger.debug(
                    "current_datetime: {}".format(
                        self.current_datetime))

                self.__delta_datetime = self.current_datetime - self.file_datetime
                self.logger.debug(
                    "delta_datetime: {}".format(
                        self.__delta_datetime))
                self.logger.debug(
                    "warn_datetime: {}".format(
                        datetime.timedelta(
                            minutes=self.args.warning)))
                self.logger.debug(
                    "crit_datetime: {}".format(
                        datetime.timedelta(
                            minutes=self.args.critical)))
                if self.__delta_datetime > datetime.timedelta(
                        minutes=self.args.critical):
                    self.crit_file.append(self.filename)
                elif self.__delta_datetime > datetime.timedelta(minutes=self.args.warning):
                    self.warn_file.append(self.filename)
                else:
                    self.ok_file.append(self.filename)

        
        if self.crit_file:
            status = self.critical
        elif self.warn_file:
            status = self.warning
        else:
            status = self.ok

        
        self.shortoutput = "Found {0} files out of date.".format(
            len(self.crit_file))
        if self.crit_file:
            self.longoutput.append("===== Critical File out of date ====")
        [self.longoutput.append(filename)
         for filename in self.crit_file if self.crit_file]
        if self.warn_file:
            self.longoutput.append("===== Warning File out of date ====")
        [self.longoutput.append(filename)
         for filename in self.warn_file if self.warn_file]
        if self.ok_file:
            self.longoutput.append("===== OK File out of date ====")
        [self.longoutput.append(filename)
         for filename in self.ok_file if self.ok_file]
        self.perfdata.append("{path}={result};{warn};{crit};0;".format(
            crit=self.args.critical,
            warn=self.args.warning,
            result=len(self.crit_file),
            path=self.args.drive + self.args.path))

        
        status(self.output(long_output_limit=None))
        self.logger.debug("Return status and exit to Nagios.")


class SqlserverLocks(Wmi):

    


    def __init__(self, *args, **kwargs):
        super(SqlserverLocks, self).__init__(*args, **kwargs)
        self.logger.debug("Init SqlserverLocks")

    def define_sub_options(self):
        super(SqlserverLocks, self).define_sub_options()
        self.sl_parser = self.subparsers.add_parser(
            'sqlserverlocks', help='Options for SqlserverLocks',
            description='All options for SqlserverLocks')
        self.sl_parser.add_argument('-q', '--query',
                                    required=False,
                                    help='wql for wmi.',
                                    dest='query')
        self.sl_parser.add_argument(
            '-m', '--mode', required=True,
            help='From ["LockTimeoutsPersec", "LockWaitsPersec", "NumberofDeadlocksPersec"]',
            dest='mode')
        self.sl_parser.add_argument('-w', '--warning',
                                    default=0,
                                    type=int,
                                    required=False,
                                    help='Default is %(default)s',
                                    dest='warning')
        self.sl_parser.add_argument('-c', '--critical',
                                    default=0,
                                    type=int,
                                    required=False,
                                    help='Default is %(default)s',
                                    dest='critical')

    def sqlserverlocks_handle(self):
        self.ok_list = []
        self.warn_list = []
        self.crit_list = []
        status = self.ok

        if self.args.mode == "LockTimeoutsPersec":
            self.wql = "select LockTimeoutsPersec from Win32_PerfFormattedData_MSSQLSERVER_SQLServerLocks"
        elif self.args.mode == "LockWaitsPersec":
            self.wql = "select LockWaitsPersec from Win32_PerfFormattedData_MSSQLSERVER_SQLServerLocks"
        elif self.args.mode == "NumberofDeadlocksPersec":
            self.wql = "select NumberofDeadlocksPersec from Win32_PerfFormattedData_MSSQLSERVER_SQLServerLocks"
        else:
            self.unknown("Unknown SqlServerLocks options")

        self.__results = self.query(self.wql)
        self.logger.debug("results: {}".format(self.__results))
        
        
        for lock_dict in self.__results:
            self.name = lock_dict.get('Name')
            self.logger.debug("name: {}".format(self.name))
            self.value = int(lock_dict.get(self.args.mode))
            self.logger.debug("value: {}".format(self.value))
            if self.value > self.args.critical:
                self.crit_list.append(self.name + " : " + self.value)
            elif self.value > self.args.warning:
                self.warn_list.append(self.name + " : " + self.value)
            else:
                self.ok_list.append(self.name + " : " + str(self.value))

        if self.crit_list:
            status = self.critical
        elif self.warn_list:
            status = self.warning
        else:
            status = self.ok

        self.shortoutput = "Found {0} {1} critical.".format(
            len(self.crit_list), self.args.mode)
        if self.crit_list:
            self.longoutput.append("===== Critical ====")
        [self.longoutput.append(filename)
         for filename in self.crit_list if self.crit_list]
        if self.warn_list:
            self.longoutput.append("===== Warning ====")
        [self.longoutput.append(filename)
         for filename in self.warn_list if self.warn_list]
        if self.ok_list:
            self.longoutput.append("===== OK ====")
        [self.longoutput.append(filename)
         for filename in self.ok_list if self.ok_list]
        self.perfdata.append("{mode}={result};{warn};{crit};0;".format(
            crit=self.args.critical,
            warn=self.args.warning,
            result=len(self.crit_list),
            mode=self.args.mode))

        
        status(self.output(long_output_limit=None))
        self.logger.debug("Return status and exit to Nagios.")


class Register(FileNumber, FileAge, SqlserverLocks):

    


    def __init__(self, *args, **kwargs):
        super(Register, self).__init__(*args, **kwargs)


def main():
    

    plugin = Register()
    if plugin.args.option == 'filenumber':
        plugin.filenumber_handle()
    elif plugin.args.option == 'fileage':
        plugin.fileage_handle()
    elif plugin.args.option == 'sqlserverlocks':
        plugin.sqlserverlocks_handle()
    else:
        plugin.unknown("Unknown actions.")

if __name__ == "__main__":
    main()
