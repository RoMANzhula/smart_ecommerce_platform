package org.romanzhula.user_service.controllers;

import lombok.RequiredArgsConstructor;
import org.romanzhula.user_service.controllers.requests.LoginRequest;
import org.romanzhula.user_service.controllers.requests.UserRegistrationRequest;
import org.romanzhula.user_service.controllers.responses.AuthResponse;
import org.romanzhula.user_service.models.User;
import org.romanzhula.user_service.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    @PostMapping("/register")
    public ResponseEntity<User> register(
            @RequestBody UserRegistrationRequest registrationRequest
    ) {
        return ResponseEntity.ok(authenticationService.registerUser(registrationRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest loginRequest
    ) {
        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }

}