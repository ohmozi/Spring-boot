package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.ReservationService;
import kr.co.fastcampus.eatgo.domain.Reservation;
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
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)  // junit4에서는 Runwith, 5에서는 extendwith
@WebMvcTest(ReservationController.class)
class ReservationControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ReservationService reservationService;

    @Test
    public void list() throws Exception {       //시큐리티 설정도 복사해오기
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjI5MywibmFtZSI6IuqwgOqyjOyjvOyduCIsInJlc3RhdXJhbnRJZCI6MX0.7unU7D0N2cUdxlhDgcF5NwVY985DNr9hrXbzqP6KzFU";      //가게 주인의 정보 토큰넣기

        mvc.perform(get("/reservations")      //1004번 레스토랑에  (292번 tester) 사용자가 예약하겠다.
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

        verify(reservationService).getReservations(1004L);
    }

}
// 가게주인 토큰
// eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjI5MywibmFtZSI6IuqwgOqyjOyjvOyduCIsInJlc3RhdXJhbnRJZCI6MX0.7unU7D0N2cUdxlhDgcF5NwVY985DNr9hrXbzqP6KzFU
// ceo@ceo.com  / 가게주인 / level:50 / restaurantId : 1