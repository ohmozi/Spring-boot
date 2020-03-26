package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.RestaurantService;
import kr.co.fastcampus.eatgo.domain.MenuItem;
import kr.co.fastcampus.eatgo.domain.Restaurant;
import kr.co.fastcampus.eatgo.domain.RestaurantNotFoundException;
import kr.co.fastcampus.eatgo.domain.Review;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
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

        given(restaurantService.getRestaurants("Seoul", 1L))
                .willReturn(restaurants);

        mvc.perform(get("/restaurants?region=Seoul&category=1"))
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
                .name("Bob zip")
                .address("seoul")
                .build();

        MenuItem menuItem = MenuItem.builder()
                .name("Kimchi")
                .build();
        restaurant.setMenuItems(Arrays.asList(menuItem));

        Review review = Review.builder()
                .name("jihunsbar")
                .score(5)
                .description("very good")
                .build();
        restaurant.setReviews(Arrays.asList(review));

        given(restaurantService.getRestaurant(1004L)).willReturn(restaurant);

        mvc.perform(get("/restaurants/1004"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1004")
                ))
                .andExpect(content().string(
                        containsString("\"name\":\"Bob zip\"")
                ))
                .andExpect(content().string(
                        containsString("Kimchi")
                ))
                .andExpect(content().string(
                        containsString("very good")
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
}