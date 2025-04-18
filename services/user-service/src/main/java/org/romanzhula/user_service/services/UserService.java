package org.romanzhula.user_service.services;

import lombok.RequiredArgsConstructor;
import org.romanzhula.user_service.controllers.requests.UserRegistrationRequest;
import org.romanzhula.user_service.models.User;
import org.romanzhula.user_service.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    @Transactional
    public User registerUser(UserRegistrationRequest registrationRequest) {
        User user = new User();

        user.setUsername(registrationRequest.username());
        user.setPassword(passwordEncoder.encode(registrationRequest.password()));
        user.setEmail(registrationRequest.email());

        return userRepository.save(user);
    }

}
