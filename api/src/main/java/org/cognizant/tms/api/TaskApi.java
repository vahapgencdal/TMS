package org.cognizant.tms.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.cognizant.tms.request.TaskPostRequest;
import org.cognizant.tms.response.TaskResponse;
import org.cognizant.tms.model.TaskGroup;
import org.cognizant.tms.model.TaskStatus;
import org.cognizant.tms.model.TmsTask;
import org.cognizant.tms.service.api.TaskService;
import org.cognizant.tms.service.exception.ExistingTaskNameException;
import org.cognizant.tms.service.exception.SubTasksNotFinishedException;
import org.cognizant.tms.service.exception.TaskNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 */

@RestController
@RequestMapping("/api/v1")
@Validated
@Api(value = "Task Management service api")
public class TaskApi {


    @Autowired
    private TaskService taskService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(value = "Retrieve all tasks",
            notes = "Retrieve all tasks")
    public ResponseEntity<List<TaskResponse>> allTasks() {

        List<TmsTask> tmsTaskList = taskService.findAll();

        List<TaskResponse> taskResponseList = tmsTaskList
                .stream()
                .map(TaskResponse::toResponse)
                .collect(Collectors.toList());

        return CollectionUtils.isEmpty(taskResponseList) ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(taskResponseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Retrieve Specified Task Id",
            notes = "Retrieve Specified Task By Id")
    public ResponseEntity<TaskResponse> findById(
            @PathVariable @Positive(message = "Task Id have to be positive number") long id) {

        Optional<TmsTask> task = taskService.findById(id);

        return task.map(value -> new ResponseEntity<>(TaskResponse.toResponse(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));

    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ApiOperation(value = "Add Single Record of Task",
            notes = "Add Single Record of Task with JSON Object; Task Status: OPEN,PROCESSING,DONE. TaskGroup:TEST, DEVOPS, DEVELOPMENT,ANALYST. DateFormat: yyyy-MM-dd")
    public ResponseEntity<Object> add(@RequestBody @Valid TaskPostRequest taskPostRequest) {

        try {
            TmsTask tmsTask = TaskPostRequest.toEntity(taskPostRequest);

            if (taskPostRequest.getParentId()!=null && taskPostRequest.getParentId() > 0){
                TmsTask parentTask = taskService.findById(taskPostRequest.getParentId())
                        .orElseThrow(() -> new TaskNotFoundException("Parent Task Not Found"));

                tmsTask.setParent(parentTask);
            }

            taskService.save(tmsTask);
            return new ResponseEntity<>("Task added", HttpStatus.OK);
        } catch (ExistingTaskNameException e) {
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>("Unable edit task", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "Update Task",
            notes = "Update Task with JSON Object; Task Status: OPEN,PROCESSING,DONE. TaskGroup:TEST, DEVOPS, DEVELOPMENT,ANALYST. DateFormat: yyyy-MM-dd")
    public ResponseEntity<Object> update(@RequestBody @Valid TaskPostRequest taskPostRequest,
                                         @PathVariable @Positive(message = "Task Id have to be positive number") long id) {
        try {
            TmsTask task = taskService.findById(id).orElseThrow(() -> new TaskNotFoundException("Task Not Found"));

            task.setAssignee(taskPostRequest.getAssignee());
            task.setName(taskPostRequest.getName());

            if (taskPostRequest.getParentId()!=null && taskPostRequest.getParentId() > 0) {
                TmsTask parentTask = taskService.findById(taskPostRequest.getParentId()).orElseThrow(() -> new TaskNotFoundException("Parent Task Not Found"));
                task.setParent(parentTask);
            }

            task.setTimeSpent(taskPostRequest.getTimeSpent());
            task.setTaskStatus(TaskStatus.valueOf(taskPostRequest.getTaskStatus()));
            task.setTaskGroup(TaskGroup.valueOf(taskPostRequest.getTaskGroup()));

            taskService.update(task);

            return new ResponseEntity<>("Task edited", HttpStatus.OK);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.NO_CONTENT);
        } catch (SubTasksNotFinishedException e) {
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e) {
            return new ResponseEntity<>("Unable edit task", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete Specified Task Id",
            notes = "Delete Specified Task By Id")
    public ResponseEntity<?> removeById(
            @PathVariable @Positive(message = "Task Id have to be positive number") long id) {

        try {
            taskService.delete(id);
            return new ResponseEntity<>("Task removed", HttpStatus.OK);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>("Unable delete task", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
