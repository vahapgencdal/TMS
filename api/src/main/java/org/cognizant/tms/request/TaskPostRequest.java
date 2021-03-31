package org.cognizant.tms.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cognizant.tms.model.TmsTask;
import org.cognizant.tms.model.TaskGroup;
import org.cognizant.tms.model.TaskStatus;
import org.cognizant.tms.validator.ValueOfEnum;

import javax.validation.constraints.NotNull;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 */

@ApiModel(description = "All details about the task. ")
@Data
@NoArgsConstructor
public class TaskPostRequest {

    @ApiModelProperty(notes = "Task name is mandatory attribute. have to be at least 3 and maximum 255 characters")
    @Size(min = 3,max = 255, message = "Size of Task Name must be between 3 and 255")
    private String name;

    @ApiModelProperty(notes = "Task status is mandatory attribute, have to be specified type. Task Status: OPEN,PROCESSING and DONE")
    @NotNull
    @ValueOfEnum(enumClass = TaskStatus.class, message = "Must be specified types. OPEN,PROCESSING,DONE")
    private String taskStatus;

    @ApiModelProperty(notes = "TimeSpent is mandatory attribute, have to be positive number")
    @NotNull
    @Positive(message = "TimeSpent have to be positive number")
    private int timeSpent;

    @ApiModelProperty(notes = "Assignee name is mandatory attribute. have to be at least 3 and maximum 255 characters")
    @Size(min = 3,max = 255, message = "Size of Assignee must be between 3 and 255")
    private String assignee;

    @ApiModelProperty(notes = "Task Group is mandatory attribute, have to be specified type. Task Group: TEST, DEVOPS, DEVELOPMENT,ANALYST")
    @NotNull
    @ValueOfEnum(enumClass = TaskGroup.class, message = "Must be specified types. TEST, DEVOPS, DEVELOPMENT,ANALYST")
    private String taskGroup;

    @ApiModelProperty(notes = "Parent ID is not mandatory attribute. if you send null that mean it is parent Task")
    private Long parentId;

    public static TaskPostRequest toRequest(TmsTask entity) {
        TaskPostRequest tpr = new TaskPostRequest();
        tpr.setName(entity.getName());
        tpr.setTaskStatus(entity.getTaskStatus().name());
        tpr.setTimeSpent(entity.getTimeSpent());
        tpr.setAssignee(entity.getAssignee());
        tpr.setTaskGroup(entity.getTaskGroup().name());
        return tpr;
    }

    public static TmsTask toEntity(TaskPostRequest tpr) {
        TmsTask entity = new TmsTask();
        entity.setTaskStatus(TaskStatus.valueOf(tpr.getTaskStatus()));
        entity.setTaskGroup(TaskGroup.valueOf(tpr.getTaskGroup()));
        entity.setName(tpr.getName());
        entity.setTimeSpent(tpr.getTimeSpent());
        entity.setAssignee(tpr.getAssignee());
        return entity;
    }
}
