package com.example.exercise_jpa.Service;

import com.example.exercise_jpa.Model.Product;
import com.example.exercise_jpa.Model.Review;
import com.example.exercise_jpa.Model.User;
import com.example.exercise_jpa.Repository.ProductRepository;
import com.example.exercise_jpa.Repository.ReviewRepository;
import com.example.exercise_jpa.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public List<Review> getAllReview(){
        return reviewRepository.findAll();
    }
    //اضيف ميزة انه اتأكد انه اليوزر اشترا المنتج
    public String addReview(Review review){

        for(Product product:productRepository.findAll()){
            if(product.getId().equals(review.getProduct_id())){
                for (User user: userRepository.findAll()){
                    if(user.getId().equals(review.getUser_id())){
                        reviewRepository.save(review);
                        return "Added";
                    }
                }return "User not found";
            }
        }return "Product not found";


    }


    public String updateReview(Integer reviewId ,Review review){
        Review oldReview =reviewRepository.getById(reviewId);
        if(oldReview ==null){
            return "Review not found";
        }

        for(Product product: productRepository.findAll()){
            if(product.getId().equals(review.getProduct_id())){
                for(User user:userRepository.findAll()){
                    if(user.getId().equals(review.getUser_id())){
                        oldReview.setComment(review.getComment());
                        oldReview.setRating(review.getRating());
                        oldReview.setUser_id(review.getUser_id());
                        oldReview.setProduct_id(review.getProduct_id());
                        reviewRepository.save(oldReview);

                        return "T updated";

                    }
                }return "User not found";
            }
        }return "Product not found";




    }

    public Boolean deleteReview(Integer id){
        Review review=reviewRepository.getById(id);
        if(review==null){
            return false;
        }
        reviewRepository.delete(review);
        return true;
    }


}
