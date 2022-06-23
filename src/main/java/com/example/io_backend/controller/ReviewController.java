package com.example.io_backend.controller;

import com.example.io_backend.model.Review;
import com.example.io_backend.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("")
    public List<Review> getAll() {
        return reviewService.getReviews();
    }

    @GetMapping("/{id}")
    public Review getById(@PathVariable Integer id) {
        return reviewService.getReviewById(id);
    }

    @PostMapping("")
    public Review add(@RequestBody Review review) {
        return reviewService.addReview(review);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Review review, @PathVariable Integer id){
        reviewService.updateReview(review,id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        reviewService.deleteReview(id);
    }

}
