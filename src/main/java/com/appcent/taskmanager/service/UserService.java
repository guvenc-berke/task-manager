package com.appcent.taskmanager.service;

import com.appcent.taskmanager.dto.security.JwtResponse;
import com.appcent.taskmanager.dto.request.AuthorizationRequestDto;
import com.appcent.taskmanager.dto.security.UserDetailsImpl;
import com.appcent.taskmanager.model.User;
import com.appcent.taskmanager.repo.IUserRepository;
import com.appcent.taskmanager.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public JwtResponse login(AuthorizationRequestDto loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getPassword());
    }

    @Override
    public void register(AuthorizationRequestDto registerRequest) {

        String email = registerRequest.getEmail();

        Optional<User> userOptional = findByEmail(email);
        if (userOptional.isPresent()) {
            throw new RestClientException("User already exists!");
        }

        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());

        User user = new User(email, encodedPassword);
        userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with given email: " + email));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
