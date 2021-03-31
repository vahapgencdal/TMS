package org.cognizant.tms.web;

import javafx.concurrent.Task;
import org.cognizant.tms.model.TaskGroup;
import org.cognizant.tms.model.TaskStatus;
import org.cognizant.tms.model.TmsTask;
import org.cognizant.tms.request.TaskPostRequest;
import org.cognizant.tms.service.api.TaskService;
import org.cognizant.tms.service.exception.ExistingTaskNameException;
import org.cognizant.tms.service.exception.TaskNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Arrays;

@Controller
public class TaskController {

    @Autowired
    private TaskService taskService;

    private static final String ADD_USER = "add";
    private static final String REDIRECT_SLASH = "redirect:/";

    @RequestMapping("/")
    public String taskList(Model model, @RequestParam(value = "error",required = false) String error) {
        model.addAttribute("error",error);
        model.addAttribute("tasks", taskService.findAll());
        return "index";
    }

    @RequestMapping("/{id}")
    public String subTaskList(Model model, @PathVariable @Positive(message = "Task Id") long id ) {
        model.addAttribute("tasks", taskService.findsSubTmsTasksById(id));
        return "index";
    }

    @GetMapping("/add")
    public String getUser(Model model) {

        TaskPostRequest taskPostRequest = new TaskPostRequest();

        model.addAttribute("taskPostRequest",taskPostRequest);
        model.addAttribute("taskGroupList", Arrays.asList(TaskGroup.values()));
        model.addAttribute("taskStatusList",Arrays.asList(TaskStatus.values()));
        model.addAttribute("taskList", taskService.findAll());

        return ADD_USER;

    }

    @PostMapping("/add")
    public String postUSer(@Valid @ModelAttribute TaskPostRequest taskPostRequest, BindingResult result, Model model) {
        String error="";
        if (result.hasErrors()) {
            return ADD_USER;
        }

        try {
            TmsTask tmsTask = TaskPostRequest.toEntity(taskPostRequest);

            if (taskPostRequest.getParentId()!=null && taskPostRequest.getParentId() > 0){
                TmsTask parentTask = taskService.findById(taskPostRequest.getParentId())
                        .orElseThrow(() -> new TaskNotFoundException("Parent Task Not Found"));

                tmsTask.setParent(parentTask);
            }

            taskService.save(tmsTask);
        } catch (ExistingTaskNameException | TaskNotFoundException e) {
            error=e.getMessage();
        } catch (Exception e) {
           error="Unable to add task";
        }

        return REDIRECT_SLASH+"?error="+error;
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable("id") long id, Model model) {

        TmsTask task=taskService.findById(id).orElse(new TmsTask());

        TaskPostRequest taskPostRequest = TaskPostRequest.toRequest(task);

        model.addAttribute("taskPostRequest",taskPostRequest);
        model.addAttribute("taskGroupList", Arrays.asList(TaskGroup.values()));
        model.addAttribute("taskStatusList",Arrays.asList(TaskStatus.values()));
        model.addAttribute("taskList", taskService.findAll());

        return ADD_USER;
    }

    @PostMapping("/edit/{id}")
    public String updateTask(@Valid @ModelAttribute TaskPostRequest taskPostRequest, BindingResult result, Model model,
                           @PathVariable @Positive(message = "Task Id have to be positive number") long id) {
        String error="";
        if (result.hasErrors()) {
            return ADD_USER;
        }

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
        } catch (ExistingTaskNameException | TaskNotFoundException e) {
            error=e.getMessage();
        } catch (Exception e) {
            error="Unable to edit task";
        }

        return REDIRECT_SLASH+"?error="+error;
    }

    @RequestMapping("/info")
    public String info() {
        return "info";
    }

    @RequestMapping("/delete/{id}")
    public String delete(Model model, @PathVariable @Positive(message = "Task Id have to be positive number") long id) {
        String error="";
        try {
            taskService.delete(id);
        } catch (TaskNotFoundException e) {
            error=e.getMessage();
        } catch (Exception e) {
            error="Unable delete task";
        }

        return REDIRECT_SLASH+"?error="+error;
    }

}