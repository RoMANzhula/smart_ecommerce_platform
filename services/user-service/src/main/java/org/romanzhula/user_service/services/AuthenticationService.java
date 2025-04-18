package org.romanzhula.user_service.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.romanzhula.user_service.configurations.security.implementations.UserDetailsImpl;
import org.romanzhula.user_service.configurations.security.jwt.JwtService;
import org.romanzhula.user_service.controllers.requests.LoginRequest;
import org.romanzhula.user_service.controllers.requests.UserRegistrationRequest;
import org.romanzhula.user_service.controllers.responses.AuthResponse;
import org.romanzhula.user_service.models.User;
import org.romanzhula.user_service.models.enums.UserRole;
import org.romanzhula.user_service.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    @Transactional
    public AuthResponse registerUser(UserRegistrationRequest registrationRequest) {
        User user = new User();

        user.setUsername(registrationRequest.username());
        user.setPassword(passwordEncoder.encode(registrationRequest.password()));
        user.setEmail(registrationRequest.email());
        user.setRole(UserRole.USER);

        userRepository.save(user);

        var userDetails = new UserDetailsImpl(
                user.getId().toString(),
                user.getUsername(),
                user.getPassword(),
                List.of(user.getRole().name())
        );

        var jwtToken = jwtService.generateToken(userDetails);

        return AuthResponse.builder()
                .token(jwtToken)
                .build()
        ;
    }


    @Transactional
    public AuthResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        // or we can get data about User from DAO
        // User logUser = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow();

        String jwtToken = jwtService.generateToken(userDetails);

        return AuthResponse.builder()
                .token(jwtToken)
                .build()
        ;
    }

}
