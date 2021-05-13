package com.appcent.taskmanager.dto.response;

import com.appcent.taskmanager.dto.request.TaskRequestDto;
import com.appcent.taskmanager.model.Task;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class TaskResponseDto extends TaskRequestDto {
    private Long id;
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
