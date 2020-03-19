package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.ReviewService;
import kr.co.fastcampus.eatgo.domain.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;


@RestController
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("restaurants/{restaurantId}/reviews")
    public ResponseEntity<?> create(
            @PathVariable("restaurantId") Long restaurantId,
            @Valid @RequestBody Review review
    ) throws URISyntaxException
    {
        reviewService.addReview(review, restaurantId);
        URI location = new URI("/restaurants/" + restaurantId + "/reviews/" + review.getId()
                + review.getName()+ review.getRestaurantId() + review.getDescription() + review.getScore());
        return ResponseEntity.created(location).body("{}");
    }
}
