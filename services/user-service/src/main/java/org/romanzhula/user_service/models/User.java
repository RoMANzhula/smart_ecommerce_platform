package org.romanzhula.user_service.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.romanzhula.user_service.models.enums.UserRole;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(nullable = false) // if the user will has multiple accounts for the same email
    private String email;

    @Enumerated(EnumType.STRING) //EnumType.ORDINAL - for numerated user roles ADMIN=0, USER=1 etc.
    @Column(name = "role", nullable = false)
    private UserRole role;

}
