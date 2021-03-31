package org.cognizant.tms.service.api;


import org.cognizant.tms.service.exception.SubTasksNotFinishedException;
import org.cognizant.tms.service.exception.TaskNotFoundException;
import org.cognizant.tms.model.TmsTask;

import java.util.List;
import java.util.Optional;


public interface TaskService {
    Optional<TmsTask> findById(long id);
    List<TmsTask> findAll();
    List<TmsTask> findByGroupName(String groupName);
    TmsTask findByName(String name);
    void save(TmsTask tmsTask) throws SubTasksNotFinishedException;
    void update(TmsTask tmsTask) throws SubTasksNotFinishedException;
    void delete(long id) throws TaskNotFoundException;
}
