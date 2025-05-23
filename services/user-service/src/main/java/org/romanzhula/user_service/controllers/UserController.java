package org.romanzhula.user_service.controllers;

import lombok.RequiredArgsConstructor;
import org.romanzhula.user_service.controllers.responses.UserResponse;
import org.romanzhula.user_service.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;


    @GetMapping("/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(
            @PathVariable String username
    ) {
        return ResponseEntity.ok(userService.findByUsername(username));
    }

    @GetMapping("/{user-id}/email")
    public ResponseEntity<String> getUserEmail(
            @PathVariable("user-id") String userId
    ) {
        return ResponseEntity.ok(userService.getEmailByUserId(userId));
    }

}
