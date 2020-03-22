package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.ReviewService;
import kr.co.fastcampus.eatgo.domain.Review;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)  // junit4에서는 Runwith, 5에서는 extendwith
@WebMvcTest(ReviewController.class)
class ReviewControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ReviewService reviewService;

    @Test
    public void createWithValidAttributes() throws Exception {

//        given(reviewService.addReview(any(), eq(1L))).willReturn(
//                Review.builder().id(1004L).build());

        given(reviewService.addReview(any(), eq(1L))).will(invocation -> {
            Review review = invocation.getArgument(0);
            return  Review.builder().id(1004L).build();
        });
        // 여기 뭐지???? 강의내용에 포함 안되어있음  이부분 없으면 아래 테스트케이스에서 id값이 null로 읽음
        // invocation?

        mvc.perform(post("/restaurants/1/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"jihun\", \"score\":5,\"description\":\"good!!\"}"))       //여기서 입력한 값은 저장되는데 id, restid값이 저장이안됨
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/restaurants/1/reviews/1004"));

        verify(reviewService).addReview(any(),eq(1L));
    }


    @Test
    public void createWithInvalidAttributes() throws Exception {
        mvc.perform(post("/restaurants/1/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());

        verify(reviewService, never()).addReview(any(),eq(1L));
        //잘못된게 들어와서 addreivew가 실행이 안되어야함.
    }
}