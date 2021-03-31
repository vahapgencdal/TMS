package org.cognizant.tms.repository;

import org.cognizant.tms.model.TaskGroup;
import org.cognizant.tms.model.TmsTask;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 */
@Repository
public interface TaskRepository extends JpaRepository<TmsTask, Long>, JpaSpecificationExecutor<TmsTask> {
    List<TmsTask> findByTaskGroup(TaskGroup name);

    TmsTask findByName(String name);

    List<TmsTask> findByParentId(Long id);

    // @formatter:off
    @Query(value = "select tms.subTmsTasks from TmsTask tms " +
            "where tms.id=:theId")
    // @formatter:on
    List<TmsTask> findsSubTmsTasksById( @Param("theId") Long id);
}
