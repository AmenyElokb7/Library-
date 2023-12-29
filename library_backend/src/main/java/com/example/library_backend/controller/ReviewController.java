package com.example.library_backend.controller;

import com.example.library_backend.requestmodels.ReviewRequest;
import com.example.library_backend.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@AllArgsConstructor

@RequestMapping("/api/reviews")
public class ReviewController {

    private ReviewService reviewService;

    @GetMapping("/secure/user/book")
    public Boolean reviewBookByUser(@AuthenticationPrincipal UserDetails userDetails,
                                    @RequestParam Long bookId) throws Exception {
        if (userDetails == null) {
            throw new Exception("User details are missing");
        }
        return reviewService.userReviewListed(userDetails.getUsername(), bookId);
    }

    @PostMapping("/secure")
    public void postReview(@AuthenticationPrincipal UserDetails userDetails,
                           @RequestBody ReviewRequest reviewRequest) throws Exception {
        if (userDetails == null) {
            throw new Exception("User details are missing");
        }
        reviewService.postReview(userDetails.getUsername(), reviewRequest);
    }
}
