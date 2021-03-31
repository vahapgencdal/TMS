
import lombok.extern.slf4j.Slf4j;
import org.cognizant.tms.TmsApiApp;
import org.cognizant.tms.model.TaskGroup;
import org.cognizant.tms.model.TaskStatus;
import org.cognizant.tms.model.TmsTask;
import org.cognizant.tms.request.TaskPostRequest;
import org.cognizant.tms.response.TaskResponse;
import org.cognizant.tms.service.api.TaskService;
import org.cognizant.tms.utils.Util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 */

@SpringBootTest(classes = {TmsApiApp.class})
@Slf4j
@AutoConfigureMockMvc
class TaskApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Test
    void save_success_case() throws Exception {
        String uri = "/api/v1/";

        TaskPostRequest taskPostRequest = new TaskPostRequest();
        taskPostRequest.setAssignee("test");
        taskPostRequest.setName("test");
        taskPostRequest.setTaskStatus(TaskStatus.OPEN.name());
        taskPostRequest.setTaskGroup(TaskGroup.TEST.name());
        taskPostRequest.setTimeSpent(1);


        TmsTask tmsTask = new TmsTask();
        tmsTask.setAssignee("test");
        tmsTask.setName("test");
        tmsTask.setTaskStatus(TaskStatus.OPEN);
        tmsTask.setTaskGroup(TaskGroup.TEST);
        tmsTask.setTimeSpent(1);

        when(taskService.save(any())).thenReturn(tmsTask);
        MvcResult mvcResult = mockMvc.perform(post(uri)
                .contentType(Util.APPLICATION_JSON_UTF8)
                .content(Util.convertObjectToJsonBytes(taskPostRequest))
        ).andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());

    }

    @Test
    void update_success_case() throws Exception {
        String uri = "/api/v1/1";

        TaskPostRequest taskPostRequest = new TaskPostRequest();
        taskPostRequest.setAssignee("test");
        taskPostRequest.setName("test");
        taskPostRequest.setTaskStatus(TaskStatus.OPEN.name());
        taskPostRequest.setTaskGroup(TaskGroup.TEST.name());
        taskPostRequest.setTimeSpent(1);


        TmsTask tmsTask = new TmsTask();
        tmsTask.setAssignee("test");
        tmsTask.setName("test");
        tmsTask.setTaskStatus(TaskStatus.OPEN);
        tmsTask.setTaskGroup(TaskGroup.TEST);
        tmsTask.setTimeSpent(1);


        when(taskService.findById(anyLong())).thenReturn(Optional.ofNullable(tmsTask));

        when(taskService.update(any())).thenReturn(tmsTask);
        MvcResult mvcResult = mockMvc.perform(put(uri)
                .contentType(Util.APPLICATION_JSON_UTF8)
                .content(Util.convertObjectToJsonBytes(taskPostRequest))
        ).andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
    }



    @Test
    void findAll_success_case() throws Exception {
        String uri = "/api/v1/";

        List<TmsTask> expectedList = new ArrayList<>();
        TmsTask tmsTask = new TmsTask();
        tmsTask.setAssignee("test");
        tmsTask.setName("test");
        tmsTask.setId(1L);
        tmsTask.setTaskStatus(TaskStatus.OPEN);
        tmsTask.setTaskGroup(TaskGroup.TEST);
        tmsTask.setTimeSpent(1);

        expectedList.add(tmsTask);

        when(taskService.findAll()).thenReturn(expectedList);


        MockHttpServletResponse mvcResponse = mockMvc
                .perform(MockMvcRequestBuilders.get(uri))
                .andReturn().getResponse();
        assertTrue(mvcResponse.getContentAsString().contains("test"));
        assertEquals(200, mvcResponse.getStatus());

    }

    @Test
    void findById_success_case() throws Exception {
        String uri = "/api/v1/1";

        TmsTask tmsTask = new TmsTask();
        tmsTask.setAssignee("test");
        tmsTask.setName("test");
        tmsTask.setId(1L);
        tmsTask.setTaskStatus(TaskStatus.OPEN);
        tmsTask.setTaskGroup(TaskGroup.TEST);
        tmsTask.setTimeSpent(1);

        when(taskService.findById(anyLong())).thenReturn(Optional.of(tmsTask));

        MockHttpServletResponse mvcResponse = mockMvc
                .perform(MockMvcRequestBuilders.get(uri))
                .andReturn().getResponse();
        assertTrue(mvcResponse.getContentAsString().contains("test"));
        assertEquals(200, mvcResponse.getStatus());

    }

    @Test
    void delete_success_case() throws Exception {
        String uri = "/api/v1/1";

        TmsTask t = new TmsTask();
        t.setName("test");

         MockHttpServletResponse mvcResponse = mockMvc
                .perform(MockMvcRequestBuilders.delete(uri))
                .andReturn().getResponse();
        assertEquals(200, mvcResponse.getStatus());

    }


}