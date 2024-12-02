package com.example.exercise_jpa.Controller;

import com.example.exercise_jpa.ApiResponse.ApiResponse;
import com.example.exercise_jpa.Model.User;
import com.example.exercise_jpa.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/get")
    public ResponseEntity getAllUsers(){
        return ResponseEntity.status(200).body(userService.getAllUsers());
    }

    @PostMapping("/add")
    public ResponseEntity addUser(@RequestBody @Valid User user, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        userService.addUser(user);
        return ResponseEntity.status(200).body(new ApiResponse("User added"));

    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateUser(@PathVariable Integer id , @RequestBody @Valid User user, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if(userService.updateUser(id,user)){
            return ResponseEntity.status(200).body(new ApiResponse("User updated"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("User not found"));
    }
@DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable Integer id){
        if(userService.deleteUser(id)){
            return ResponseEntity.status(200).body(new ApiResponse("User deleted"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("User not found"));
    }


    @PutMapping("/buying/{user_id}/{product_id}/{merchant_id}")
    public ResponseEntity buying(@PathVariable Integer user_id,@PathVariable Integer product_id,@PathVariable Integer merchant_id){
        char result= userService.buying(user_id,product_id,merchant_id).charAt(0);
        return switch (result) {
            case 'A' -> ResponseEntity.status(400).body(new ApiResponse("User not found"));
            case 'B' -> ResponseEntity.status(400).body(new ApiResponse("Product not found"));
            case 'C' -> ResponseEntity.status(400).body(new ApiResponse("Merchant or product stock not found"));
            case 'D' -> ResponseEntity.status(400).body(new ApiResponse("Merchant stock is insufficient"));
            case 'E' -> ResponseEntity.status(400).body(new ApiResponse("User balance is insufficient"));
            default -> ResponseEntity.status(200).body(new ApiResponse("Purchase successful."));
        };

    }


    @GetMapping("/ApplyDiscount/{adminId}/{productId}/{percent}")
    public ResponseEntity applyDiscount(@PathVariable Integer adminId,@PathVariable Integer productId,@PathVariable double percent){
        char result = userService.applyDiscount(adminId,productId,percent).charAt(0);
        return switch (result){
            case 'A' -> ResponseEntity.status(400).body(new ApiResponse("You are not authorized to apply the discount."));
            case 'B' -> ResponseEntity.status(400).body(new ApiResponse("User not found"));
            case 'C' -> ResponseEntity.status(400).body(new ApiResponse("product not found"));
            case 'D' ->ResponseEntity.status(200).body(new ApiResponse(userService.applyDiscount(adminId,productId,percent)));
            default -> ResponseEntity.status(400).body(new ApiResponse("Invalid discount percentage. It must be between 0 and 100"));

        };

    }


}
