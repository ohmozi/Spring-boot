package kr.co.fastcampus.eatgo.interfaces;

import io.jsonwebtoken.Claims;
import kr.co.fastcampus.eatgo.application.ReviewService;
import kr.co.fastcampus.eatgo.domain.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;


@RestController
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("restaurants/{restaurantId}/reviews")
    public ResponseEntity<?> create(
            Authentication authentication,          // 바로 filter에서 잡은 auth를 쓸수있다.
            @PathVariable("restaurantId") Long restaurantId,
            @Valid @RequestBody Review resource
    ) throws URISyntaxException
    {
//        Review review = reviewService.addReview(
//                Review.builder()
//                        .name(resource.getName())
//                        .score(resource.getScore())
//                        .description(resource.getDescription())
//                        .build(), restaurantId);

        Claims claims = (Claims) authentication.getPrincipal();

        String name = claims.get("name", String.class);
        Integer score = resource.getScore();
        String description = resource.getDescription();

        Review review = reviewService.addReview(restaurantId, name, score, description);

        URI location = new URI("/restaurants/" + restaurantId + "/reviews/" + review.getId());
        return ResponseEntity.created(location).body("{}");
    }
}
