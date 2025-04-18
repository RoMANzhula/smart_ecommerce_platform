package org.romanzhula.user_service.controllers;

import lombok.RequiredArgsConstructor;
import org.romanzhula.user_service.controllers.requests.UserRegistrationRequest;
import org.romanzhula.user_service.models.User;
import org.romanzhula.user_service.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(
            @RequestBody UserRegistrationRequest registrationRequest
    ) {
        return ResponseEntity.ok(userService.registerUser(registrationRequest));
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(
            @PathVariable String username
    ) {
        return ResponseEntity.ok(userService.findByUsername(username));
    }

}

// TODO: change User to UserDTO
// TODO: add RestControllerAdvice
