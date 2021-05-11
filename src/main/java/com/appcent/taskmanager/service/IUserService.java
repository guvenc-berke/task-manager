package com.appcent.taskmanager.service;

import com.appcent.taskmanager.dto.security.JwtResponse;
import com.appcent.taskmanager.dto.request.AuthorizationRequestDto;
import com.appcent.taskmanager.model.User;

import java.util.Optional;

public interface IUserService {
    JwtResponse login(AuthorizationRequestDto loginRequest);

    void register(AuthorizationRequestDto registerRequest);

    User getUserByEmail(String email);

    Optional<User> findByEmail(String email);
}
