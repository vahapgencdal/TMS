
import lombok.extern.slf4j.Slf4j;
import org.cognizant.tms.TmsApiApp;
import org.cognizant.tms.model.TaskGroup;
import org.cognizant.tms.model.TaskStatus;
import org.cognizant.tms.model.TmsTask;
import org.cognizant.tms.repository.TaskRepository;
import org.cognizant.tms.service.api.TaskService;
import org.hibernate.mapping.Collection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 */

@SpringBootTest(classes = {TmsApiApp.class})
@Slf4j
class TaskServiceTest {

    @MockBean
    private TaskRepository taskRepository;

    @Autowired
    private TaskService taskService;

    @Test
    void findById_success_case() {
        TmsTask tmsTask = new TmsTask();
        tmsTask.setAssignee("test");
        tmsTask.setName("test");
        tmsTask.setId(1L);
        tmsTask.setTaskStatus(TaskStatus.OPEN);
        tmsTask.setTaskGroup(TaskGroup.TEST);
        tmsTask.setTimeSpent(1);

        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(tmsTask));

        Optional<TmsTask> actualResult = taskService.findById(anyLong());

        Assertions.assertTrue(actualResult.isPresent());
        Assertions.assertEquals(tmsTask.getName(), actualResult.get().getName());
    }

    @Test
    void findAll_success_case() {
        TmsTask tmsTask = new TmsTask();
        tmsTask.setAssignee("test");
        tmsTask.setName("test");
        tmsTask.setId(1L);
        tmsTask.setTaskStatus(TaskStatus.OPEN);
        tmsTask.setTaskGroup(TaskGroup.TEST);
        tmsTask.setTimeSpent(1);

        List<TmsTask> expected = new ArrayList<>();
        expected.add(tmsTask);

        when(taskRepository.findAll()).thenReturn(expected);

        List<TmsTask> actualResult = taskService.findAll();

        Assertions.assertEquals(expected.size(), actualResult.size());
        Assertions.assertEquals(expected.get(0).getName(), actualResult.get(0).getName());
    }

    @Test
    void findsSubTmsTasksById_success_case() {
        TmsTask tmsTask = new TmsTask();
        tmsTask.setAssignee("test");
        tmsTask.setName("test");
        tmsTask.setId(1L);
        tmsTask.setTaskStatus(TaskStatus.OPEN);
        tmsTask.setTaskGroup(TaskGroup.TEST);
        tmsTask.setTimeSpent(1);

        List<TmsTask> expected = new ArrayList<>();
        expected.add(tmsTask);

        when(taskRepository.findsSubTmsTasksById(anyLong())).thenReturn(expected);

        List<TmsTask> actualResult = taskService.findsSubTmsTasksById(anyLong());

        Assertions.assertEquals(expected.size(), actualResult.size());
        Assertions.assertEquals(expected.get(0).getName(), actualResult.get(0).getName());
    }

    @Test
    void save_success_case() {
        TmsTask tmsTask = new TmsTask();
        tmsTask.setAssignee("test");
        tmsTask.setName("test");
        tmsTask.setId(1L);
        tmsTask.setTaskStatus(TaskStatus.OPEN);
        tmsTask.setTaskGroup(TaskGroup.TEST);
        tmsTask.setTimeSpent(1);

        when(taskRepository.save(any())).thenReturn(tmsTask);

        TmsTask actualResult = taskService.save(tmsTask);

        Assertions.assertEquals(tmsTask.getName(), actualResult.getName());
    }

    @Test
    void update_success_case() {
        TmsTask tmsTask = new TmsTask();
        tmsTask.setAssignee("test");
        tmsTask.setName("test");
        tmsTask.setId(1L);
        tmsTask.setTaskStatus(TaskStatus.OPEN);
        tmsTask.setTaskGroup(TaskGroup.TEST);
        tmsTask.setTimeSpent(1);

        when(taskRepository.save(any())).thenReturn(tmsTask);
        when(taskRepository.findsSubTmsTasksById(anyLong())).thenReturn(Collections.EMPTY_LIST);

        TmsTask actualResult = taskService.update(tmsTask);

        Assertions.assertEquals(tmsTask.getName(), actualResult.getName());
    }

}