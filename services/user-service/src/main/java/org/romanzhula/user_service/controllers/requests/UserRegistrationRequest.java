package org.romanzhula.user_service.controllers.requests;

public record UserRegistrationRequest(
        String username,
        String password,
        String email
) { }
