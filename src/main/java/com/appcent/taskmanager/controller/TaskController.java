package com.appcent.taskmanager.controller;


import com.appcent.taskmanager.dto.request.TaskRequestDto;
import com.appcent.taskmanager.dto.response.TaskResponseDto;
import com.appcent.taskmanager.service.ITaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/task", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TaskController {

    private final ITaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponseDto> createTask(@RequestBody TaskRequestDto taskRequestDto) {
        String currentUserEmail = getCurrentUserEmail();

        return ResponseEntity.ok(taskService.createTask(currentUserEmail, taskRequestDto));
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> getUserTasks(@RequestParam(name = "completed") Boolean isCompleted) {
        String currentUserEmail = getCurrentUserEmail();

        return ResponseEntity.ok(taskService.getUserTasks(currentUserEmail, isCompleted));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponseDto> updateTask(@PathVariable Long taskId, @RequestBody TaskRequestDto requestDto) {
        return ResponseEntity.ok(taskService.updateTask(taskId, requestDto));
    }

    @PutMapping("/{taskId}/status")
    public ResponseEntity<?> changeTaskStatus(@PathVariable Long taskId, @RequestParam(name = "completed") Boolean isCompleted) {

        taskService.changeTaskStatus(taskId, isCompleted);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private String getCurrentUserEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        return userDetails.getUsername();
    }
}
