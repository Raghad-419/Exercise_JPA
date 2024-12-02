package com.example.exercise_jpa.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "User_orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "int not null")
    @NotNull(message = "Empty user id")
    @Positive(message = "ID must be more than 0")
    private Integer user_id;
    @Column(columnDefinition = "int not null")
    @NotNull(message = "Empty product id")
    @Positive(message = "ID must be more than 0")
    private Integer product_id;


}

