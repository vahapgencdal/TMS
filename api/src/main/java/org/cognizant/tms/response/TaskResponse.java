package org.cognizant.tms.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cognizant.tms.tms.model.TmsTask;


/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 */

@Data
@ApiModel(description = "Task")
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {

    private long id;

    @ApiModelProperty("Task Name")
    private String name;

    @ApiModelProperty("Time Spent on Task")
    private int timeSpent;

    @ApiModelProperty("Assignee")
    private String assignee;

    @ApiModelProperty("Task status")
    private String taskStatus;

    @ApiModelProperty("Task group")
    private String taskGroup;

    public static TaskResponse toResponse(TmsTask entity) {
        TaskResponse tr = new TaskResponse();
        tr.setName(entity.getName());
        tr.setTaskStatus(entity.getTaskStatus().name());
        tr.setTaskGroup(entity.getTaskGroup().name());
        tr.setTimeSpent(entity.getTimeSpent());
        tr.setAssignee(entity.getAssignee());
        return tr;
    }

}
