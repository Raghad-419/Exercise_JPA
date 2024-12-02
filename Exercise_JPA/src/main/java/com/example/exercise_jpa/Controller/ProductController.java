package com.example.exercise_jpa.Controller;

import com.example.exercise_jpa.ApiResponse.ApiResponse;
import com.example.exercise_jpa.Model.Product;
import com.example.exercise_jpa.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/get")
    public ResponseEntity getAllProducts(){
        return ResponseEntity.status(200).body(productService.getAllProducts());
    }

    @PostMapping("/add/{categoryId}")
    public ResponseEntity addProduct(@PathVariable Integer categoryId ,@RequestBody @Valid Product product, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        if(productService.addProduct(categoryId,product)){
            return ResponseEntity.status(200).body(new ApiResponse("Product added"));

        }
        return ResponseEntity.status(400).body(new ApiResponse("Id not found "));
    }

    @PutMapping("/update/{productId}/{categoryId}")
    public ResponseEntity updateProduct(@PathVariable Integer productId ,@PathVariable Integer categoryId , @RequestBody @Valid Product product ,Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        char result = productService.updateProduct(productId,categoryId,product).charAt(0);
        return switch (result){
            case 'A' -> ResponseEntity.status(400).body(new ApiResponse("Product Id not found"));
            case 'B' -> ResponseEntity.status(200).body(new ApiResponse("Product updated"));
            default -> ResponseEntity.status(400).body(new ApiResponse("Category Id not found"));
        };
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteProduct(@PathVariable Integer id){
        if(productService.deleteProduct(id)){
            return ResponseEntity.status(200).body(new ApiResponse("Product deleted"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Product not found"));
    }

    @GetMapping("/bestSeller/{numberOfProduct}")
    public ResponseEntity bestSeller(@PathVariable Integer numberOfProduct){
        if(productService.bestSeller(numberOfProduct)==null){
            return ResponseEntity.status(400).body(new ApiResponse("Empty products"));
        }
        return ResponseEntity.status(200).body(productService.bestSeller(numberOfProduct));
    }

}
