package com.example.InstagramBackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId ;
    @NotBlank
    @Pattern(regexp = "^[A-Z][a-zA-Z]*$" , message = "First letter of name must be Capital")
    private String firstName;
    @NotBlank
    private String lastName;

    private Integer age;
    @Column(unique = true)
    @Email
    private String email;
    @NotBlank
    private String password ;
    @NotBlank
    private String phoneNumber;
}
