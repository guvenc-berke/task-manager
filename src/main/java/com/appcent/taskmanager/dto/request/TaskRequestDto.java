package com.appcent.taskmanager.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor

@ApiModel(value ="Task request DTO", description ="Task object for creation and updating of a task")
public class TaskRequestDto {

    @ApiModelProperty(value = "title of the task", required = true)
    private String title;

    @ApiModelProperty(value = "content of the task", required = true)
    private String content;
}
