package org.cognizant.tms.service.api;


import org.cognizant.tms.service.exception.SubTasksNotFinishedException;
import org.cognizant.tms.service.exception.TaskNotFoundException;
import org.cognizant.tms.model.TmsTask;

import java.util.List;
import java.util.Optional;


public interface TaskService {
    Optional<TmsTask> findById(long id);
    List<TmsTask> findAll();
    List<TmsTask> findsSubTmsTasksById(Long id);
    List<TmsTask> findByGroupName(String groupName);
    TmsTask findByName(String name);
    TmsTask save(TmsTask tmsTask) throws SubTasksNotFinishedException;
    TmsTask update(TmsTask tmsTask) throws SubTasksNotFinishedException;
    void delete(long id) throws TaskNotFoundException;
}
