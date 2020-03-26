package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.domain.MenuItem;
import kr.co.fastcampus.eatgo.domain.Restaurant;
import kr.co.fastcampus.eatgo.domain.RestaurantNotFoundException;
import kr.co.fastcampus.eatgo.domain.Review;
import kr.co.fastcampus.eatgo.application.RestaurantService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)  // junit4에서는 Runwith, 5에서는 extendwith
@WebMvcTest(RestaurantController.class)
class RestaurantControllerTests {

    @Autowired
    private MockMvc mvc;
    //MockBean 스프링에서의 사용
    @MockBean
    private RestaurantService restaurantService;  //가짜객체를 이용해 테스트 외의 객체를 대신한다

    //테스트에서는 의존성을 주입해줘야한다
//    @SpyBean(RestaurantRepositoryImpl.class)    //컨트롤러에 우리가 원하는 객체를 주입할수있다.
//    private RestaurantRepository restaurantRepository;
//    @SpyBean(MenuItemRepositoryImpl.class)
//    private MenuItemRepository menuItemRepository;
//    @SpyBean(RestaurantService.class)       //위의 의존성은 서비스로서 한 번에 해결가능하다.
//    private RestaurantService restaurantService;

    @Test
    public void list() throws Exception {
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(Restaurant.builder()
                .id(1004L)
                .categoryId(1L)
                .name("Bob zip")
                .address("seoul")
                .build());

        given(restaurantService.getRestaurants()).willReturn(restaurants);

        mvc.perform(get("/restaurants"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1004")
                ))
                .andExpect(content().string(
                        containsString("\"name\":\"Bob zip\"")
                ));
    }

    @Test
    public void detailWithExisted() throws Exception {
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .categoryId(1L)
                .name("Bob zip")
                .address("seoul")
                .build();

        given(restaurantService.getRestaurant(1004L)).willReturn(restaurant);

        mvc.perform(get("/restaurants/1004"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1004")
                ))
                .andExpect(content().string(
                        containsString("\"name\":\"Bob zip\"")
                ));
    }

    @Test
    public void detailWithNotExisted() throws Exception {
        given(restaurantService.getRestaurant(404L))
                .willThrow(new RestaurantNotFoundException(404L));

        mvc.perform(get("/restuarants/404"))
                .andExpect(status().isNotFound());
//                .andExpect(content().string("{}"));
//        org.springframework.web.servlet.resource.ResourceHttpRequestHandler  를 통해서 따로 처리없이 바로 되는데..?
//        Junit4 기준으로 RestaurantNotFoundException.java 파일 생성 및
//        @ControllerAdvice를 활용한 RestaurantErrorAdivce.java생성을 통해 에러처리
//        에러처리 없이 바로 돼서 < .andExpect(content().string("{}"));  >이 부분 처리가안됨  (강의내용대로라면 exception파일로 들어가야하는데..)
    }

    @Test
    public void createWithValidData() throws Exception {
        given(restaurantService.addRestaurant(any())).will(invocation -> {
                Restaurant restaurant = invocation.getArgument(0);
                return Restaurant.builder()
                        .id(1234L)
                        .categoryId(1L)
                        .name(restaurant.getName())
                        .address(restaurant.getAddress())
                        .build();
        });     // 여기 뭐지???? 강의내용에 포함 안되어있음  이부분 없으면 아래 테스트케이스에서 id값이 null로 읽힘
//        Restaurant restaurant = new Restaurant(1234L,"BeRyong","Busan");
        mvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)        //컨텐트가 json타입이란것을 알려주기
                .content("{\"categoryId\":1, \"name\":\"BeRyong\",\"address\":\"Busan\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/restaurants/1234"))
                .andExpect(content().string("{}"));

        verify(restaurantService).addRestaurant(any());
    }

    @Test
    public void createWithInvalidData() throws Exception {

        mvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)        //컨텐트가 json타입이란것을 알려주기
                .content("{\"categoryId\":1, \"name\":\"\",\"address\":\"\"}"))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void createWithoutNameData() throws Exception {

        mvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)        //컨텐트가 json타입이란것을 알려주기
                .content("{\"categoryId\":1, \"name\":\"\",\"address\":\"Busan\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateWithValidData() throws Exception {
        mvc.perform(patch("/restaurants/1004")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"categoryId\":1, \"name\":\"jihun bar\", \"address\":\"gunpo\"}"))
                .andExpect(status().isOk());

        verify(restaurantService).updateRestaurant(1004L,"jihun bar","gunpo");
    }

    @Test
    public void updateWithInvalidData() throws Exception {
        mvc.perform(patch("/restaurants/1004")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"categoryId\":1, \"name\":\"\", \"address\":\"\"}"))
                .andExpect(status().isBadRequest());

    }
}