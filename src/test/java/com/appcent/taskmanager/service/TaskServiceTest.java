package com.appcent.taskmanager.service;


import com.appcent.taskmanager.dto.request.TaskRequestDto;
import com.appcent.taskmanager.dto.response.TaskResponseDto;
import com.appcent.taskmanager.model.Task;
import com.appcent.taskmanager.model.User;
import com.appcent.taskmanager.repo.ITaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestClientException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    private static final String EMAIL = "test@test.com";
    private static final User USER = new User(EMAIL, "pass");

    @Autowired
    private ITaskService taskService;

    @MockBean
    private UserService userService;

    @MockBean
    private ITaskRepository taskRepository;

    @Test
    public void createTask_success() {
        TaskRequestDto requestDto = new TaskRequestDto("title", "content");

        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);

        when(userService.getUserByEmail(EMAIL)).thenReturn(USER);
        when(taskRepository.findByTitleAndIsCompletedAndUser(eq(requestDto.getTitle()), eq(false),eq(USER)))
                .thenReturn(Optional.empty());

        when(taskRepository.save(any(Task.class))).thenReturn(new Task());

        taskService.createTask(EMAIL, requestDto);

        verify(taskRepository).save(taskCaptor.capture());

        assertThat(taskCaptor.getValue().getTitle()).isEqualTo(requestDto.getTitle());
        assertThat(taskCaptor.getValue().getContent()).isEqualTo(requestDto.getContent());
    }

    @Test
    public void createTask_duplicateTitle() {
        TaskRequestDto requestDto = new TaskRequestDto("title", "content");

        when(userService.getUserByEmail(EMAIL)).thenReturn(USER);

        when(taskRepository.findByTitleAndIsCompletedAndUser(eq(requestDto.getTitle()), eq(false), any(User.class)))
                .thenReturn(Optional.of(new Task()));

        assertThrows(RestClientException.class, () -> taskService.createTask(EMAIL, requestDto));
    }

    @Test
    public void getUserTasks_success() {
        when(userService.getUserByEmail(EMAIL)).thenReturn(USER);


        when(taskRepository.findAllByUserAndIsCompleted(eq(USER), eq(false)))
                .thenReturn(Collections.singletonList(new Task()));

        List<TaskResponseDto> result = taskService.getUserTasks(EMAIL, false);

        assertThat(result).hasSize(1);
    }

    @Test
    public void updateTask_success() {
    }

    @Test
    public void updateTask_taskNotFound() {
    }

    @Test
    public void changeTaskStatus_success() {
    }

    @Test
    public void deleteTask_success() {
    }
}
