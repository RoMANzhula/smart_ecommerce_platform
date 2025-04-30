package org.romanzhula.user_service.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.romanzhula.user_service.controllers.responses.UserResponse;
import org.romanzhula.user_service.models.User;
import org.romanzhula.user_service.repositories.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    @Transactional(readOnly = true)
    public UserResponse findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(user -> new UserResponse(
                        user.getId().toString(),
                        user.getUsername(),
                        user.getPassword(),
                        user.getEmail(),
                        Collections.singleton(user.getRole().toString())
                ))
                .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username))
        ;
    }

    @Transactional(readOnly = true)
    public String getEmailByUserId(String userId) {
        return userRepository.findById(Long.valueOf(userId))
                .map(User::getEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + userId))
        ;
    }

}
