package com.example.exercise_jpa.Service;

import com.example.exercise_jpa.Model.Category;
import com.example.exercise_jpa.Model.Product;
import com.example.exercise_jpa.Repository.CategoryRepository;
import com.example.exercise_jpa.Repository.ProductRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Boolean addProduct(Integer categoryId,Product product){
        for(Category category : categoryRepository.findAll()){
            if(category.getId().equals(categoryId)){
                productRepository.save(product);
                return true;
            }
        }
        return false;
    }

    public String updateProduct(Integer productId,Integer categoryId, Product product){
        Product oldProduct =productRepository.getById(productId);
        if(oldProduct==null){
            return "A";
        }
        for(Category category:categoryRepository.findAll()){
            if(category.getId().equals(categoryId)){
                oldProduct.setCategoryId(product.getCategoryId());
                oldProduct.setName(product.getName());
                oldProduct.setPrice(product.getPrice());
                oldProduct.setRating(product.getRating());
                oldProduct.setStatus(product.getStatus());
                productRepository.save(product);
                return "B";
            }
        }
        return "C";

    }


    public Boolean deleteProduct(Integer id){
        Product product =productRepository.getById(id);
        if(product==null){
            return false;
        }
        productRepository.delete(product);
        return true;
    }

    //5 Extra Method to get bestseller products based on number of sell
    public List<Product> bestSeller(Integer numberOfProduct) {
            List<Product> allProducts = productRepository.findAll();

        for (int i = 0; i < allProducts.size() - 1; i++) {
            for (int j = i + 1; j < allProducts.size(); j++) {
                if (allProducts.get(i).getCountOfSell() < allProducts.get(j).getCountOfSell()) {
                    // Swap the products
                    Product temp = allProducts.get(i);
                    allProducts.set(i, allProducts.get(j));
                    allProducts.set(j, temp);
                }
            }
        }

        List<Product> result =new ArrayList<>();

        for (int i = 0; i < Math.min(numberOfProduct, allProducts.size()); i++) {
            result.add(allProducts.get(i));
        }

        if(result.isEmpty()){
            return null;
        }
        return result;


    }

}
