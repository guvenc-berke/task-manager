package com.appcent.taskmanager.controller;


import com.appcent.taskmanager.dto.request.TaskRequestDto;
import com.appcent.taskmanager.dto.response.TaskResponseDto;
import com.appcent.taskmanager.dto.security.UserDetailsImpl;
import com.appcent.taskmanager.model.User;
import com.appcent.taskmanager.repo.IUserRepository;
import com.appcent.taskmanager.security.JwtUtils;
import com.appcent.taskmanager.service.TaskService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;








































































































































import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtils jwtUtils;

    @MockBean
    private TaskService taskService;

    @MockBean
    private IUserRepository userRepository;

    @Test
    public void createTask() throws Exception {
        TaskRequestDto requestDto = new TaskRequestDto("title", "content");

        String jwtToken = setupJwtToken();

        when(taskService.createTask(eq("test@test"), any(TaskRequestDto.class)))
                .thenReturn(new TaskResponseDto("title", "content", 1L));

        mockMvc.perform(post("/task")
                .header("authorization", jwtToken)
                .content(new Gson().toJson(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("title")))
                .andExpect(jsonPath("$.content", is("content")))
                .andExpect(jsonPath("$.id", is(1)));
    }

    private String setupJwtToken() {

        User user = new User(1L, "test@test", "test");
        UserDetailsImpl userPrincipal = UserDetailsImpl.create(user);

        String jwtToken = jwtUtils.generateJwtToken(userPrincipal);

        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));

        return "Bearer " + jwtToken;
    }
}
