package com.example.exercise_jpa.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Check(constraints = "LENGTH(password) >= 7 AND email LIKE '%_@__%.__%' AND role IN ('Admin', 'Customer') AND balance >= 0")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "varchar(10) not null")
    @NotEmpty(message = "Empty username")
    @Size(min = 6, message = "Username have to be more than 5 length long")
    private String username;
    @Column(columnDefinition = "VARCHAR(15) NOT NULL")
    @NotEmpty(message = "Empty password")
    @Size(min = 7, message = "Password must be more than 6 characters long")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{7,}$", message = "Password must contain at least one uppercase letter, one lowercase letter, and one number")
    private String password;
    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    @NotEmpty(message = "Empty email")
    @Email(message = "Enter a valid email")
    private String email;
    @Column(columnDefinition = "varchar(8) not null")
    @NotEmpty(message = "Empty role")
    @Pattern(regexp = "Admin|Customer", message = "Role must be Admin, or Customer")
    private String role;
    @Column(columnDefinition = "Double not null")
    @NotNull(message = "Empty balance")
    @PositiveOrZero(message = "Balance should be more than or equal 0")
    private Double balance;


}

