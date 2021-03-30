package org.cognizant.tms.tms.repository;

import org.cognizant.tms.tms.model.TmsTask;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 */
@Repository
public interface TaskRepository extends JpaRepository<TmsTask, Long>, JpaSpecificationExecutor<TmsTask> {
    List<TmsTask> findByTaskGroup(String name);
}
