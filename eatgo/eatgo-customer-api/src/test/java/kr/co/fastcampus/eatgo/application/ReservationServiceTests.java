package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.Reservation;
import kr.co.fastcampus.eatgo.domain.ReservationRepository;
import kr.co.fastcampus.eatgo.domain.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class ReservationServiceTests {

    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);     //Mock 잡았으니 해줘야함.

        reservationService = new ReservationService(reservationRepository);
    }

    @Test
    public void addReservation(){
        Long restaurantId = 1004L;
        Long userId = 292L;
        String name = "tester";
        String date = "2019-12-25";
        String time ="19:00";
        Integer partySize = 20;
//
//        Reservation mockReservation = Reservation.builder().name(name).build();
//
//        given(reservationRepository.save(any())).willReturn(mockReservation);
//        위처럼 하거나

        given(reservationRepository.save(any())).will(invocation -> {       //아래 addReservation을 했을때 사용한 값들로 객체를 만들어서 사용해준다.
            Reservation reservation = invocation.getArgument(0);
            return reservation;
        });

        Reservation reservation = reservationService.addReservation(restaurantId, userId, name, date, time, partySize);

        assertThat(reservation.getName(), is("tester"));        //nullpointexception이 나온다면 given해주기

        verify(reservationRepository).save(any());
    }

}