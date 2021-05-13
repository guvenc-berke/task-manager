package com.appcent.taskmanager.dto.response;

import com.appcent.taskmanager.dto.request.TaskRequestDto;
import com.appcent.taskmanager.model.Task;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@ApiModel(value ="Task response DTO", description ="Response DTO for task requests")
public class TaskResponseDto extends TaskRequestDto {

    @ApiModelProperty(value = "task id")
    private Long id;

    @ApiModelProperty(value = "creation date time of the task")
    private LocalDateTime createdAt;

    public TaskResponseDto(Task task) {
        super(task.getTitle(), task.getContent());
        this.id = task.getId();
        this.createdAt = task.getCreatedAt();
    }

    public TaskResponseDto(String title, String content, Long id) {
        super(title, content);
        this.createdAt = LocalDateTime.now();
        this.id = id;
    }
}
