package com.aceprep.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userid;
    private String name;
    @Email
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    private String role;
    private LocalDateTime createdAt = LocalDateTime.now();
}
