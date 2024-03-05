















import os
import textwrap

from . import job_model

_LOCALIZE_COMMAND_MAP = {
    job_model.P_GCS: 'gsutil -m rsync -r',
    job_model.P_LOCAL: 'rsync -r',
}




























DATA_MOUNT_POINT = '/mnt/data'

SCRIPT_DIR = '%s/script' % DATA_MOUNT_POINT
TMP_DIR = '%s/tmp' % DATA_MOUNT_POINT
WORKING_DIR = '%s/workingdir' % DATA_MOUNT_POINT


def get_file_environment_variables(file_params):
  

  env = {}
  for param in file_params:
    
    
    
    
    env[param.name] = os.path.join(
        DATA_MOUNT_POINT, param.docker_path.rstrip('/')) if param.value else ''
  return env


def build_recursive_localize_env(destination, inputs):
  

  export_input_dirs = '\n'.join([
      'export {0}={1}/{2}'.format(var.name, destination.rstrip('/'),
                                  var.docker_path.rstrip('/'))
      for var in inputs
      if var.recursive and var.docker_path
  ])
  return export_input_dirs


def build_recursive_localize_command(destination, inputs, file_provider):
  

  command = _LOCALIZE_COMMAND_MAP[file_provider]
  filtered_inputs = [
      var for var in inputs
      if var.recursive and var.file_provider == file_provider
  ]

  copy_input_dirs = '\n'.join([
      textwrap.dedent(
).format(
          command=command,
          source_uri=var.uri,
          data_mount=destination.rstrip('/'),
          docker_path=var.docker_path) for var in filtered_inputs
  ])
  return copy_input_dirs


def build_recursive_gcs_delocalize_env(source, outputs):
  

  filtered_outs = [
      var for var in outputs
      if var.recursive and var.file_provider == job_model.P_GCS
  ]
  return '\n'.join([
      'export {0}={1}/{2}'.format(var.name,
                                  source.rstrip('/'),
                                  var.docker_path.rstrip('/'))
      for var in filtered_outs
  ])


def build_recursive_delocalize_command(source, outputs, file_provider):
  

  command = _LOCALIZE_COMMAND_MAP[file_provider]
  filtered_outputs = [
      var for var in outputs
      if var.recursive and var.file_provider == file_provider
  ]

  return '\n'.join([
      textwrap.dedent(
).format(
          command=command,
          data_mount=source.rstrip('/'),
          docker_path=var.docker_path,
          destination_uri=var.uri) for var in filtered_outputs
  ])


def get_task_metadata(job_metadata, task_id):
  

  task_metadata = job_metadata.copy()
  task_metadata['task-id'] = task_id

  return task_metadata


def build_mount_env(source, mounts):
  

  return '\n'.join([
      'export {0}={1}/{2}'.format(var.name, source.rstrip('/'),
                                  var.docker_path.rstrip('/')) for var in mounts
  ])


def get_job_and_task_param(job_params, task_params, field):
  

  return job_params.get(field, set()) | task_params.get(field, set())
