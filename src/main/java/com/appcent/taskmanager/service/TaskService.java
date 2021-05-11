package com.appcent.taskmanager.service;

import com.appcent.taskmanager.dto.request.TaskRequestDto;
import com.appcent.taskmanager.dto.response.TaskResponseDto;
import com.appcent.taskmanager.model.Task;
import com.appcent.taskmanager.model.User;
import com.appcent.taskmanager.repo.ITaskRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService implements ITaskService {

    private final ITaskRepository taskRepository;
    private final IUserService userService;

    @Override
    public TaskResponseDto createTask(String email, TaskRequestDto requestDto) {

        User user = userService.getUserByEmail(email);

        Optional<Task> existingTask = taskRepository.findByTitleAndIsCompletedAndUser(requestDto.getTitle(), false, user);
        if (existingTask.isEmpty()) {
            Task task = new Task(requestDto.getTitle(), requestDto.getContent(), user);

            return new TaskResponseDto(taskRepository.save(task));
        } else {
            throw new RestClientException("Task already exists!!");
        }
    }

    @Override
    public List<TaskResponseDto> getUserTasks(String email, Boolean isCompleted) {
        User user = userService.getUserByEmail(email);

        List<Task> tasks = taskRepository.findAllByUserAndIsCompleted(user, isCompleted);

        return tasks.stream().map(TaskResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public TaskResponseDto updateTask(Long taskId, TaskRequestDto requestDto) {
        Task task = fetchTask(taskId);

        if (StringUtils.isNotEmpty(requestDto.getTitle())) {
            task.setTitle(requestDto.getTitle());
        }
        if (StringUtils.isNotEmpty(requestDto.getContent())) {
            task.setContent(requestDto.getContent());
        }

        return new TaskResponseDto(taskRepository.save(task));
    }

    @Override
    public void changeTaskStatus(Long taskId, Boolean isCompleted) {
        Task task = fetchTask(taskId);

        task.setIsCompleted(isCompleted);

        taskRepository.save(task);
    }

    @Override
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    private Task fetchTask(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new RestClientException("Task not found!!!"));
    }
}
