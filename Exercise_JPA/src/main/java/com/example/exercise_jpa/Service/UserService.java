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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final MerchantStockRepository merchantStockRepository;
    private final OrdersRepository ordersRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void addUser(User user){
        userRepository.save(user);
    }

    public Boolean updateUser(Integer id , User user){
        User oldUser =userRepository.getById(id);
        if(oldUser==null){
            return false;
        }

        oldUser.setBalance(user.getBalance());
        oldUser.setEmail(user.getEmail());
        oldUser.setRole(user.getRole());
        oldUser.setUsername(user.getUsername());
        oldUser.setPassword(user.getPassword());
        userRepository.save(oldUser);
        return true;


    }


    public Boolean deleteUser(Integer id){
        User user =userRepository.getById(id);
        if(user==null){
            return false;
        }
        userRepository.delete(user);
        return true;
    }


    public String buying(Integer userId , Integer productId, Integer merchantId) {
        User user = findUserById(userId);
        if (user == null) {
            return "A User not found";
        }

        Product product = findProductById(productId);
        if (product == null) {
            return "B Product not found";
        }

        MerchantStock merchantStock = findMerchantStock(productId, merchantId);
        if (merchantStock == null) {
            return "C Merchant or product stock not found";
        }
        if (merchantStock.getStock() <= 0) {
            return "D Merchant stock is insufficient";
        }

        double price = product.getPrice();
        if (user.getBalance() < price) {
            return "E User balance is insufficient";
        }

        // Deduct stock and balance
        merchantStock.setStock(merchantStock.getStock() - 1);
        user.setBalance(user.getBalance() - product.getPrice());
        product.setCountOfSell(product.getCountOfSell() + 1);

        // Save updated entities
        merchantStockRepository.save(merchantStock);
        userRepository.save(user);
        productRepository.save(product);

        // Create and save the order
        Order order = new Order();
        order.setUser_id(user.getId());
        order.setProduct_id(product.getId());
        ordersRepository.save(order);

        return "Purchase successful. Remaining balance: " + user.getBalance();
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
    public String applyDiscount(Integer adminId, Integer productId, double percent) {
        // Validate discount percentage
        if (percent < 0 || percent > 100) {
            return "Invalid discount percentage. It must be between 0 and 100.";
        }

        // Find the user
        User user = findUserById(adminId);
        if (user == null) {
            return "B User not found";
        }

        // Check if the user is an admin
        if (!"Admin".equals(user.getRole())) {
            return "A Not admin";
        }

        // Find the product
        Product product = findProductById(productId);
        if (product == null) {
            return "C Product not found";
        }

        // Apply the discount
        double newPrice = product.getPrice() * (1 - percent / 100);
        product.setPrice(newPrice);

        // Save the updated product
        productRepository.save(product);

        return "Discount applied successfully. New Price: " + newPrice;
    }




}
