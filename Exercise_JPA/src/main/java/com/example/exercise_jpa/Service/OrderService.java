package com.example.exercise_jpa.Service;

import com.example.exercise_jpa.Model.MerchantStock;
import com.example.exercise_jpa.Model.Order;
import com.example.exercise_jpa.Model.Product;
import com.example.exercise_jpa.Model.User;
import com.example.exercise_jpa.Repository.MerchantStockRepository;
import com.example.exercise_jpa.Repository.OrdersRepository;
import com.example.exercise_jpa.Repository.ProductRepository;
import com.example.exercise_jpa.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrdersRepository ordersRepository ;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final MerchantStockRepository merchantStockRepository;
    public List<Order> getAllOrders(){
        return ordersRepository.findAll();
    }

    public String addOrder(Order order){
        for(User user: userRepository.findAll()){
            if(user.getId().equals(order.getUser_id())){
                for(Product product:productRepository.findAll()){
                    if(product.getId().equals(order.getProduct_id())){
                        ordersRepository.save(order);
                        return "True order added";
                    }
                }return "Product not found";

            }
        }return "User not found";
    }

    public String updateOrder(Integer id,Order order){
        Order oldOrder = ordersRepository.getById(id);
        if(oldOrder ==null){
            return "Order id not found";
        }
        for(User user: userRepository.findAll()){
            if(user.getId().equals(order.getUser_id())){
                for(Product product:productRepository.findAll()){
                    if(product.getId().equals(order.getProduct_id())){
                            oldOrder.setProduct_id(order.getProduct_id());
                            oldOrder.setUser_id(order.getUser_id());
                            ordersRepository.save(oldOrder);
                        return "True order updated";
                    }
                }return "Product not found";

            }
        }return "User not found";
    }


    public Boolean deleteOrder(Integer id){
        Order order = ordersRepository.getById(id);
        if(order ==null){
            return false;
        }
        ordersRepository.delete(order);
        return true;
    }

    public String returnProduct(Integer userId, Integer productId, Integer merchantStockId) {
        // Find the user
        User user = findUserById(userId);
        if (user == null) {
            return "A User not found";
        }

        // Check if the user has purchased the product
        boolean hasPurchased = ordersRepository.existsByUserIdAndProductId(userId, productId);
        if (!hasPurchased) {
            return "H User has not purchased this product";
        }

        // Find the product
        Product product = findProductById(productId);
        if (product == null) {
            return "B Product not found";
        }

        // Check if the product is refundable
        if (!"Refundable".equals(product.getStatus())) {
            return "C Product status does not allow for return";
        }

        // Find the merchant stock
        MerchantStock merchantStock = findMerchantStock(productId,merchantStockId);
        if (merchantStock == null) {
            return "D Merchant stock not found";
        }
        if (!merchantStock.getProductId().equals(productId)) {
            return "E Product does not belong to this merchant stock";
        }

        // Return the product to stock
        merchantStock.setStock(merchantStock.getStock() + 1);
        merchantStockRepository.save(merchantStock);

        // Refund the user's balance
        user.setBalance(user.getBalance() + product.getPrice());
        userRepository.save(user);

        // Remove the product from the user's orders
        ordersRepository.deleteByUserIdAndProductId(userId, productId);

        return "Good Product returned successfully";
    }



    private User findUserById(Integer userId) {
        for (User user : userRepository.findAll()) {
            if (user.getId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    private Product findProductById(Integer productId) {
        for (Product product : productRepository.findAll()) {
            if (product.getId().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    private MerchantStock findMerchantStock(Integer productId, Integer merchantId) {
        for (MerchantStock stock : merchantStockRepository.findAll()) {
            if (stock.getProductId().equals(productId) && stock.getMerchantId().equals(merchantId)) {
                return stock;
            }
        }
        return null;
    }



}
