package com.example.exercise_jpa.Controller;

import com.example.exercise_jpa.ApiResponse.ApiResponse;
import com.example.exercise_jpa.Model.Review;
import com.example.exercise_jpa.Service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/review")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/get")
    public ResponseEntity getAllReview(){
        return ResponseEntity.status(200).body(reviewService.getAllReview());
    }

    @PostMapping("/add")
    public ResponseEntity addReview(@RequestBody @Valid Review review, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        char result =reviewService.addReview(review).charAt(0);
        return switch (result){
            case 'P' -> ResponseEntity.status(400).body(new ApiResponse("Product not found"));
            case 'A' -> ResponseEntity.status(200).body(new ApiResponse("Review Added"));
            default -> ResponseEntity.status(400).body(new ApiResponse("User not found"));
        };
    }


    @PutMapping("/update/{id}")
    public ResponseEntity updateReview(@PathVariable Integer id ,@RequestBody @Valid Review review,Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        char result =reviewService.updateReview(id,review).charAt(0);
        return switch (result){
            case 'P' -> ResponseEntity.status(400).body(new ApiResponse("Product not found"));
            case 'T' -> ResponseEntity.status(200).body(new ApiResponse("Review Updated"));
            case 'U' -> ResponseEntity.status(400).body(new ApiResponse("User not found"));
            default -> ResponseEntity.status(400).body(new ApiResponse("Review not found"));
        };

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteReview(@PathVariable Integer id){
        if(reviewService.deleteReview(id)){
            return ResponseEntity.status(200).body(new ApiResponse("Review deleted"));
        }

        return ResponseEntity.status(400).body(new ApiResponse("Review not found"));
    }



}
