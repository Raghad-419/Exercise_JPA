package com.example.exercise_jpa.Controller;

import com.example.exercise_jpa.ApiResponse.ApiResponse;
import com.example.exercise_jpa.Model.Order;
import com.example.exercise_jpa.Service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/get")
    public ResponseEntity getAllOrders(){
        return ResponseEntity.status(200).body(orderService.getAllOrders());
    }

@PostMapping("/add")
    public ResponseEntity addOrder(@RequestBody @Valid Order order, Errors errors){
        if(errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        char result =orderService.addOrder(order).charAt(0);
        return switch (result){
            case 'P' -> ResponseEntity.status(400).body(new ApiResponse("Product not found"));
            case 'T' -> ResponseEntity.status(200).body(new ApiResponse("order added"));
            default -> ResponseEntity.status(400).body(new ApiResponse("User not found"));
        };
        }

        @PutMapping("/update/{id}")
public ResponseEntity updateOrder(@PathVariable Integer id ,@RequestBody @Valid Order order,Errors errors){
    if(errors.hasErrors()) {
        return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
    }
    char result =orderService.updateOrder(id,order).charAt(0);
    return switch (result){
        case 'P' -> ResponseEntity.status(400).body(new ApiResponse("Product not found"));
        case 'T' -> ResponseEntity.status(200).body(new ApiResponse("order added"));
        case 'O' -> ResponseEntity.status(400).body(new ApiResponse("Order id not found"));
        default -> ResponseEntity.status(400).body(new ApiResponse("User not found"));
    };

}


@DeleteMapping("/delete/{id}")
public ResponseEntity deleteOrder(@PathVariable Integer id){
        if(orderService.deleteOrder(id)){
            return ResponseEntity.status(200).body(new ApiResponse("Order deleted"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Order not found"));
}


    //2 Extra endpoint user can return product to stock
    @PutMapping("/returnProduct/{userId}/{productId}/{merchantStockId}")
    public ResponseEntity returnProduct(@PathVariable Integer userId,@PathVariable Integer productId,@PathVariable Integer merchantStockId){
        char result =orderService.returnProduct(userId,productId,merchantStockId).charAt(0);
        return switch (result){
            case 'A' -> ResponseEntity.status(400).body(new ApiResponse("user not found"));
            case 'B' ->ResponseEntity.status(400).body(new ApiResponse("Product not found"));
            case 'C' ->ResponseEntity.status(400).body(new ApiResponse("Product status does not allow for return."));
            case 'H' -> ResponseEntity.status(400).body(new ApiResponse("User has not purchased this product"));
            case 'D' ->ResponseEntity.status(400).body(new ApiResponse("Merchant stock not found"));
            case 'E' -> ResponseEntity.status(400).body(new ApiResponse("Product does not belong to this merchant stock"));
            default -> ResponseEntity.status(200).body(new ApiResponse("Returned successfully"));
        };
    }



}




