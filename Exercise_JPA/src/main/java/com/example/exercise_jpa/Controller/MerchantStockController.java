package com.example.exercise_jpa.Controller;

import com.example.exercise_jpa.ApiResponse.ApiResponse;
import com.example.exercise_jpa.Model.MerchantStock;
import com.example.exercise_jpa.Service.MerchantStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/merchantStock")
public class MerchantStockController {
    private final MerchantStockService merchantStockService;

    @GetMapping("/get")
    public ResponseEntity getAllMerchantStock(){
        return ResponseEntity.status(200).body(merchantStockService.getAllMerchantStock());
    }

    @PostMapping("/add/{productId}/{merchantId}")
    public ResponseEntity addMerchantStock(@PathVariable Integer productId , @PathVariable Integer merchantId , @RequestBody @Valid MerchantStock merchantStock, Errors errors){
    if(errors.hasErrors()){
    return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());}
        char result = merchantStockService.addMerchantStock(productId,merchantId,merchantStock).charAt(0);
        return switch (result){
            case 'A' -> ResponseEntity.status(400).body(new ApiResponse("Product not found"));
            case 'B' -> ResponseEntity.status(400).body(new ApiResponse("merchant not found"));
            default -> ResponseEntity.status(200).body(new ApiResponse("Merchant Stock added"));
        };

    }

    @PutMapping("/update/{productId}/{merchantId}/{merchantStockId}")
    public ResponseEntity updateMerchantStock(@PathVariable Integer productId ,@PathVariable Integer merchantId ,@PathVariable Integer merchantStockId ,@RequestBody @Valid MerchantStock merchantStock,Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());}
        char result =merchantStockService.updateMerchantStock(productId, merchantId, merchantStockId, merchantStock).charAt(0);
        return switch (result){
            case 'A' -> ResponseEntity.status(400).body(new ApiResponse("Product not found"));
            case 'B' -> ResponseEntity.status(400).body(new ApiResponse("merchant not found"));
            case 'T' -> ResponseEntity.status(200).body(new ApiResponse("Merchant Stock Updated"));
            default -> ResponseEntity.status(400).body(new ApiResponse("Merchant Stock not found"));
        };

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteMerchantStock(@PathVariable Integer id){
        if(merchantStockService.deleteMerchantStock(id)){
            return ResponseEntity.status(200).body(new ApiResponse("Merchant Stock deleted"));
        }

        return ResponseEntity.status(400).body(new ApiResponse("Merchant Stock not found"));
    }

}//end tha class
