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
@Check(constraints = "price>0 AND (status = 'Refundable' OR status = 'Non-refundable') AND (rating>=0 AND rating<=5)")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "varchar(10)")
    @NotEmpty(message = "Empty name")
    @Size(min = 4 ,message = "Name have to be more than 3 length long")
    private String name;
    @Column(columnDefinition ="DOUBLE not null" )
    @NotNull(message = "Empty price")
    @Positive(message = "Price must be positive number and more than 0")
    private Double price;
    @Column(columnDefinition = "int not null")
    @NotEmpty(message = "Empty category ID ")
    private Integer categoryId ;
    @Column(columnDefinition = "varchar(15) not null")
    @NotEmpty(message = "Empty status")
    @Pattern(regexp = "Refundable|Non-refundable")
    private String status ;
    @Column(columnDefinition = "DOUBLE not null")
    @Max(value = 5,message = "Rating should be less than 5")
    @PositiveOrZero(message = "Rating should be zero or positive")
    private Double rating=0.0;
    @Column(columnDefinition = "int not null")
    @PositiveOrZero
    private Integer countOfSell=0;
}
