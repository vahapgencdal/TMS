import lombok.extern.slf4j.Slf4j;
import org.cognizant.tms.TmsApiApp;
import org.cognizant.tms.model.TaskGroup;
import org.cognizant.tms.model.TaskStatus;
import org.cognizant.tms.model.TmsTask;
import org.cognizant.tms.repository.TaskRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.TestPropertySource;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 */

@SpringBootTest(classes = {TmsApiApp.class})
@TestPropertySource(locations = "classpath:test.properties")
@Slf4j
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    private TmsTask task1;
    private TmsTask task2;


    @Transactional
    private void add(){
        TmsTask tmsTask1 = new TmsTask();
        tmsTask1.setName("task1");
        tmsTask1.setAssignee("test1");
        tmsTask1.setTaskGroup(TaskGroup.TEST);
        tmsTask1.setTaskStatus(TaskStatus.OPEN);
        tmsTask1.setTimeSpent(12);

        taskRepository.save(tmsTask1);
        task1 = taskRepository.findByName("task1");

        TmsTask tmsTask2 = new TmsTask();
        tmsTask2.setName("task2");
        tmsTask2.setParent(task1);
        tmsTask2.setAssignee("test2");
        tmsTask2.setTaskGroup(TaskGroup.DEVELOPMENT);
        tmsTask2.setTaskStatus(TaskStatus.OPEN);
        tmsTask2.setTimeSpent(12);
        taskRepository.save(tmsTask2);
        task2 = taskRepository.findByName("task2");
    }

    @AfterEach
    @Transactional
    public void afterEach() {
        taskRepository.delete(task2);
        taskRepository.delete(task1);
    }

    @Test
    public void findByName_Success_Case() {
        add();
        TmsTask tmsTask = taskRepository.findByName("task1");
        Assertions.assertEquals("task1", tmsTask.getName());
    }

    @Test
    public void findByParentId_Success_Case() {
        add();
        List<TmsTask> tmsTask = taskRepository.findByParentId(1L);
        Assertions.assertEquals("task2", tmsTask.get(0).getName());
    }

    @Test
    public void findByTaskGroup_Success_Case() {
        add();
        List<TmsTask> taskList = taskRepository.findByTaskGroup(TaskGroup.TEST);

        Assertions.assertEquals(1, taskList.size());
        Assertions.assertEquals("task1", taskList.get(0).getName());
    }


    @Test
    public void findsSubTmsTasksById_Success_case() {
        add();
        List<TmsTask> taskList = taskRepository.findsSubTmsTasksById(task1.getId());

        Assertions.assertEquals(1, taskList.size());
        Assertions.assertEquals("task2", taskList.get(0).getName());
    }


}
