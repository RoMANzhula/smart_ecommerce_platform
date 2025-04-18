package org.romanzhula.user_service.controllers.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private String id;
    private String username;
    private String password;
    private String email;
    private Set<String> roles;

}
