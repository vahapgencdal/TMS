package org.cognizant.tms.tms.service.impl;

import javassist.NotFoundException;
import org.cognizant.tms.tms.model.TmsTask;
import org.cognizant.tms.tms.repository.TaskRepository;
import org.cognizant.tms.tms.service.api.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public List<TmsTask> findByGroupName(String groupName) {
        return taskRepository.findByTaskGroup(groupName);
    }

    @Override
    public void save(TmsTask tmsTask) {
        taskRepository.save(tmsTask);
    }

    @Override
    public void delete(long id) throws NotFoundException {
        TmsTask tmsTask = findById(id).orElseThrow(() -> new NotFoundException("Task Not Found, TaskId:" + id));
        taskRepository.delete(tmsTask);
    }
}
