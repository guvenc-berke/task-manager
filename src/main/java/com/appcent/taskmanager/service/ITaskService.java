package com.appcent.taskmanager.service;

import com.appcent.taskmanager.dto.request.TaskRequestDto;
import com.appcent.taskmanager.dto.response.TaskResponseDto;

import java.util.List;

public interface ITaskService {
    TaskResponseDto createTask(String email, TaskRequestDto requestDto);

    List<TaskResponseDto> getUserTasks(String email, Boolean isCompleted);

    TaskResponseDto updateTask(Long taskId, TaskRequestDto requestDto);

    void changeTaskStatus(Long taskId, Boolean isCompleted);

    void deleteTask(Long taskId);
}
