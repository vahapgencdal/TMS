package org.cognizant.tms.service.impl;

import org.cognizant.tms.model.TaskStatus;
import org.cognizant.tms.model.TmsTask;
import org.cognizant.tms.service.exception.SubTasksNotFinishedException;
import org.cognizant.tms.service.exception.TaskNotFoundException;
import org.cognizant.tms.repository.TaskRepository;
import org.cognizant.tms.service.api.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl  implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Optional<TmsTask> findById(long id){
        return taskRepository.findById(id);
    }

    @Override
    public List<TmsTask> findAll() {
        return taskRepository.findAll();
    }

    public List<TmsTask> findsSubTmsTasksById(Long id){
        return taskRepository.findsSubTmsTasksById(id);
    }

    @Override
    public List<TmsTask> findByGroupName(String groupName) {
        return taskRepository.findByTaskGroup(groupName);
    }

    @Override
    public TmsTask findByName(String name) {
        return taskRepository.findByName(name);
    }

    @Override
    public void save(TmsTask tmsTask) {
        taskRepository.save(tmsTask);
    }

    @Override
    public void update(TmsTask tmsTask) throws SubTasksNotFinishedException {
        List<TmsTask> childList = taskRepository.findsSubTmsTasksById(tmsTask.getId());
        if(tmsTask.getTaskStatus().equals(TaskStatus.DONE) &&
            !childList.stream().allMatch(tsk->tsk.getTaskStatus().equals(TaskStatus.DONE))){
            throw new SubTasksNotFinishedException("Sub Tasks have to be in DONE status for change parent status to DONE");
        }
        taskRepository.save(tmsTask);
    }

    @Override
    @Transactional
    public void delete(long id) throws TaskNotFoundException {
        TmsTask tmsTask = findById(id).orElseThrow(() -> new TaskNotFoundException("Task Not Found, TaskId:" + id));

        List<TmsTask> childList = taskRepository.findByParentId(id);
        if (!childList.isEmpty()){
            childList.forEach(tmsTask1 -> tmsTask1.setParent(null));

            taskRepository.saveAll(childList);
        }

        taskRepository.delete(tmsTask);
    }
}
