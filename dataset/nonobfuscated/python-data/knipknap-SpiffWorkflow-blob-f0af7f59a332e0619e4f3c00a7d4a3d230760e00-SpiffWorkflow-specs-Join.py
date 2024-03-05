
from __future__ import division, absolute_import
















from ..task import Task
from ..exceptions import WorkflowException
from .base import TaskSpec
from ..operators import valueof


class Join(TaskSpec):

    


    def __init__(self,
                 wf_spec,
                 name,
                 split_task=None,
                 threshold=None,
                 cancel=False,
                 **kwargs):
        

        super(Join, self).__init__(wf_spec, name, **kwargs)
        self.split_task = split_task
        self.threshold = threshold
        self.cancel_remaining = cancel

    def _branch_is_complete(self, my_task):
        
        
        skip = None
        for task in Task.Iterator(my_task, my_task.NOT_FINISHED_MASK):
            
            if skip is not None and task._is_descendant_of(skip):
                continue
            if task.task_spec == self:
                skip = task
                continue
            return False
        return True

    def _branch_may_merge_at(self, task):
        for child in task:
            
            if child.triggered:
                continue
            
            if child.task_spec == self:
                return True
            
            
            
            if not child._is_definite() \
                    and len(child.task_spec.outputs) > len(child.children):
                return True
        return False

    def _check_threshold_unstructured(self, my_task, force=False):
        
        threshold = valueof(my_task, self.threshold)
        if threshold is None:
            threshold = len(self.inputs)

        
        tasks = []
        for input in self.inputs:
            for task in my_task.workflow.task_tree:
                if task.thread_id != my_task.thread_id:
                    continue
                if task.task_spec != input:
                    continue
                tasks.append(task)

        
        waiting_tasks = []
        completed = 0
        for task in tasks:
            if task.parent is None or task._has_state(Task.COMPLETED):
                completed += 1
            else:
                waiting_tasks.append(task)

        
        return force or completed >= threshold, waiting_tasks

    def _check_threshold_structured(self, my_task, force=False):
        
        
        split_task = my_task._find_ancestor_from_name(self.split_task)
        if split_task is None:
            msg = 'Join with %s, which was not reached' % self.split_task
            raise WorkflowException(self, msg)
        tasks = split_task.task_spec._get_activated_tasks(split_task, my_task)

        
        threshold = valueof(my_task, self.threshold)
        if threshold is None:
            threshold = len(tasks)

        
        waiting_tasks = []
        completed = 0
        for task in tasks:
            
            task.task_spec._predict(task)

            if not self._branch_may_merge_at(task):
                completed += 1
            elif self._branch_is_complete(task):
                completed += 1
            else:
                waiting_tasks.append(task)

        
        return force or completed >= threshold, waiting_tasks

    def _start(self, my_task, force=False):
        

        
        if my_task._has_state(Task.COMPLETED):
            return True, None
        if my_task._has_state(Task.READY):
            return True, None

        
        if self.split_task is None:
            return self._check_threshold_unstructured(my_task, force)
        return self._check_threshold_structured(my_task, force)

    def _update_hook(self, my_task):
        
        may_fire, waiting_tasks = self._start(my_task)
        if not may_fire:
            my_task._set_state(Task.WAITING)
            return

        
        
        if self.cancel_remaining:
            for task in waiting_tasks:
                task.cancel()

        
        
        
        
        my_task._ready()

        
        self._do_join(my_task)

    def _do_join(self, my_task):
        
        
        
        
        
        
        
        
        
        
        
        
        if self.split_task:
            split_task = my_task.workflow.get_task_spec_from_name(
                self.split_task)
            split_task = my_task._find_ancestor(split_task)
        else:
            split_task = my_task.workflow.task_tree

        
        
        
        
        
        last_changed = None
        thread_tasks = []
        for task in split_task._find_any(self):
            
            if task.thread_id != my_task.thread_id:
                continue
            
            if self.split_task and task._is_descendant_of(my_task):
                continue

            
            thread_tasks.append(task)

            
            
            changed = task.parent.last_state_change
            if last_changed is None \
                    or changed > last_changed.parent.last_state_change:
                last_changed = task

        
        
        
        
        
        for task in thread_tasks:
            if task == last_changed:
                self.entered_event.emit(my_task.workflow, my_task)
                task._ready()
            else:
                task.state = Task.COMPLETED
                task._drop_children()

    def _on_trigger(self, my_task):
        

        for task in my_task.workflow.task_tree._find_any(self):
            if task.thread_id != my_task.thread_id:
                continue
            self._do_join(task)

    def serialize(self, serializer):
        return serializer.serialize_join(self)

    @classmethod
    def deserialize(self, serializer, wf_spec, s_state):
        return serializer.deserialize_join(wf_spec, s_state)
