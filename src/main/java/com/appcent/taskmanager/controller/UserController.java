package com.appcent.taskmanager.controller;


import com.appcent.taskmanager.dto.request.AuthorizationRequestDto;
import com.appcent.taskmanager.dto.security.JwtResponse;
import com.appcent.taskmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/authentication/login")
    public ResponseEntity<JwtResponse> login(@RequestBody AuthorizationRequestDto loginRequest) {

        return ResponseEntity.ok(userService.login(loginRequest));
    }

    @PostMapping("/authentication/register")
    public ResponseEntity<?> register(@RequestBody AuthorizationRequestDto registerRequest) {

        userService.register(registerRequest);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
