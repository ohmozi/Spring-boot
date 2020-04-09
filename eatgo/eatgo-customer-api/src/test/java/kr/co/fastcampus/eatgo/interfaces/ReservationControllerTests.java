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
    public void create() throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjI5MiwibmFtZSI6InRlc3RlciJ9.uoOEb8QouSZum_ZzT5iBgTycKUz5FwgcheSFjJrBy-c";

        Long userId = 292L;
        String name = "tester";
        String date = "2019-12-25";
        String time ="19:00";
        Integer partySize = 20;

        Reservation mockReservation = Reservation.builder()
                .userId(userId)
                .name(name)
                .date(date)
                .time(time)
                .partySize(partySize)
                .build();

        given(reservationService.addReservation(any(), any(), any(), any(), any(), any())).willReturn(mockReservation);

        mvc.perform(post("/restaurants/1004/reservations")      //1004번 레스토랑에  (292번 tester) 사용자가 예약하겠다.
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"date\":\"2019-12-25\",\"time\":\"19:00\",\"partySize\":20}"))
                .andExpect(status().isCreated());

        verify(reservationService).addReservation(
                eq(1004L), eq(userId), eq(name), eq(date), eq(time), eq(partySize));
    }

}