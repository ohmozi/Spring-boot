package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.Review;
import kr.co.fastcampus.eatgo.domain.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class ReviewServiceTests {

    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        reviewService = new ReviewService(reviewRepository);
    }

    @Test
    public void addReivew(){
        given(reviewRepository.save(any())).will(invocation -> {
            Review review = invocation.getArgument(0);
            review.setId(123L);
            return review;
        });

        Review review = Review.builder()
                .name("jihun")
                .score(3)
                .description("good enough")
                .build();

//        Review saved = Review.builder()
//                .id(123L)
//                .name("jihun")
//                .score(3)
//                .description("good enough")
//                .build();

        Review created = reviewService.addReview(review, 1004L  );

        assertThat(created.getId(), is(123L));
        assertThat(created.getName(), is("jihun"));

//        reviewService.addReview(review, 1004L);
        verify(reviewRepository).save(any());   //리뷰레포에 무엇인가 저장되어있는가?

    }
}