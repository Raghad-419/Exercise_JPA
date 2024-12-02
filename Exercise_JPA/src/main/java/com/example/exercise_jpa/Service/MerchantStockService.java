package com.example.exercise_jpa.Service;

import com.example.exercise_jpa.Model.Merchant;
import com.example.exercise_jpa.Model.MerchantStock;
import com.example.exercise_jpa.Model.Product;
import com.example.exercise_jpa.Repository.MerchantRepository;
import com.example.exercise_jpa.Repository.MerchantStockRepository;
import com.example.exercise_jpa.Repository.ProductRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantStockService {
    private final MerchantStockRepository merchantStockRepository;
    private final MerchantRepository merchantRepository;
    private final ProductRepository productRepository;

    public List<MerchantStock> getAllMerchantStock(){
        return merchantStockRepository.findAll();
    }

    public String addMerchantStock(Integer productId ,Integer merchantId ,MerchantStock merchantStock){
        for(Product product :productRepository.findAll()){
            if(product.getId().equals(productId)){
                for(Merchant merchant: merchantRepository.findAll()){
                    if(merchant.getId().equals(merchantId)){
                        merchantStockRepository.save(merchantStock);
                        return "C Merchant Stock added";
                    }
                } return "B merchant not found";
            }
        } return "A Product not found";
    }


    public String updateMerchantStock(Integer productId ,Integer merchantId ,Integer merchantStockId ,MerchantStock merchantStock){
        MerchantStock oldMerchantStock =merchantStockRepository.getById(merchantStockId);
        if(oldMerchantStock==null){
            return "F merchantStock not found";
        }
        for(Product product:productRepository.findAll()){
            if(product.getId().equals(productId)){
                for(Merchant merchant: merchantRepository.findAll()) {
                    if (merchant.getId().equals(merchantId)) {
                        oldMerchantStock.setMerchantId(merchantStock.getMerchantId());
                        oldMerchantStock.setStock(merchantStock.getStock());
                        oldMerchantStock.setProductId(merchantStock.getProductId());
                        merchantStockRepository.save(oldMerchantStock);
                        return "T updated";
                    }
                } return "B merchant not found";
            }
        }return "A Product not found";
    }



    public Boolean deleteMerchantStock(Integer id){
        MerchantStock merchantStock = merchantStockRepository.getById(id);
        if(merchantStock==null){
            return false;
        }
        merchantStockRepository.delete(merchantStock);
        return true;
    }


}
