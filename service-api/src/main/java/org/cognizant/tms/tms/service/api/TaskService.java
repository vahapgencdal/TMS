package org.cognizant.tms.tms.service.api;


import javassist.NotFoundException;
import org.cognizant.tms.tms.model.TmsTask;

import java.util.List;
import java.util.Optional;


public interface TaskService {
    Optional<TmsTask> findById(long id);
    List<TmsTask> findAll();
    List<TmsTask> findByGroupName(String groupName);
    void save(TmsTask tmsTask);
    void delete(long id) throws NotFoundException;
}
