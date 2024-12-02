package com.example.exercise_jpa.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Check(constraints = "rating>=0 AND rating<=5")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;
    @Column(columnDefinition = "int not null")
    @Min(value = 0 ,message = "rating must be Positive ")
    @Max(value = 5 ,message = "rating must be less than or equal 5")
    @NotNull(message = "Empty rating")
    @PositiveOrZero(message = "rating must be positive")
    private Integer rating;
    @NotEmpty(message = "Empty comment")
    @Column(columnDefinition = "varchar(100) not null")
    private String comment ;
    @Column(columnDefinition = "int not null")
    @NotNull(message = "empty product id")
    private Integer product_id;
    @Column(columnDefinition = "int not null")
    @NotNull(message = "empty user id")
    private Integer user_id;

}
