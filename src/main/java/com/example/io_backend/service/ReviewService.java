package com.example.io_backend.service;

import com.example.io_backend.exception.NotFoundException;
import com.example.io_backend.model.Review;
import com.example.io_backend.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;


    public List<Review> getReviews() {return reviewRepository.findAll();}


    public Review getReviewById(Integer id) {
        return reviewRepository.findById(id).orElseThrow(() -> new NotFoundException("No record with that id"));
    }

    public Review addReview(Review review) {
        return reviewRepository.save(review);
    }

    public void updateReview(Review review, Integer id) {
        var r = reviewRepository.findById(id).orElseThrow(() -> new NotFoundException("Review not found"));
        r.setId(review.getId());
        r.setContent(review.getContent());
        r.setRating(review.getRating());
        reviewRepository.save(r);

    }

    public void deleteReview(Integer id) {
        var r = reviewRepository.findById(id).orElseThrow(() -> new NotFoundException("Person not found"));
        reviewRepository.delete(r);
    }
}
