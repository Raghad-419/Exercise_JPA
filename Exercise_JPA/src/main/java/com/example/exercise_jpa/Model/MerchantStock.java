package com.example.exercise_jpa.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Check(constraints = "stock >= 11")
public class MerchantStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "int not null")
    @NotEmpty(message = "Empty product ID")
    private Integer productId ;
    @Column(columnDefinition = "int not null")
    @NotEmpty(message = "Empty merchant ID")
    private Integer merchantId;
    @Column(columnDefinition = "int not null")
    @NotNull(message = "Empty stock")
    @Positive(message = "Stock should be more than 0")
    @Min(value = 11,message = "stock have to be more than 10 at start")
    private Integer stock;


}
