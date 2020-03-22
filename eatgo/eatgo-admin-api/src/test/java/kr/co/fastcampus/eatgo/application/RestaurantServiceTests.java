package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class RestaurantServiceTests {
    private RestaurantService restaurantService;
    // 레포지토리에 대한 처리는 가짜로 만들고 우리가 만든 Service에 집중한다. <?>
    //Mock 자바에서의 사용
    @Mock           // 아래 두개의 레포지토리는 restaurantService로 대체될수있다.
    private RestaurantRepository restaurantRepository;
    @Mock
    private MenuItemRepository menuItemRepository;
    @Mock
    private ReviewRepository reviewRepository;

    @BeforeEach     //Junit4에서 Before과 동일
    //모든테스트가 실행되기전에 실행하고 가기
    public void setUp(){
        MockitoAnnotations.initMocks(this);  //Mock으로 선언된 객체들을 초기화해준다.
//        restaurantRepository = new RestaurantRepositoryImpl();
//        menuItemRepository = new MenuItemRepositoryImpl();
        mockRestuarantRepository();
        mockMenuItemRepository();
        mockReivewRepository();

        restaurantService = new RestaurantService(
                restaurantRepository, menuItemRepository, reviewRepository);        //서비스에서 사용할 레포를 넣어줘야한다
    }


    private void mockMenuItemRepository() {
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(MenuItem.builder()
                .name("Kimchi")
                .build());
        given(menuItemRepository.findAllByRestaurantId(1004L)).willReturn(menuItems);
    }

    private void mockRestuarantRepository() {
        List<Restaurant> restaurants = new ArrayList<>();
//        Restaurant restaurant1 = new Restaurant(1004L, "Bob zip", "seoul");
        Restaurant restaurant1 = Restaurant.builder()
                .id(1004L)
                .name("Bob zip")
                .address("seoul")
                .build();
//        Restaurant restaurant2 = new Restaurant(2020L, "cyber food", "seoul");
        Restaurant restaurant2 = Restaurant.builder()
                .id(2020L)
                .name("cyber food")
                .address("seoul")
                .build();

        restaurants.add(restaurant1);
        restaurants.add(restaurant2);

        given(restaurantRepository.findAll()).willReturn(restaurants);

        given(restaurantRepository.findById(1004L)).willReturn(Optional.of(restaurant1));
        given(restaurantRepository.findById(2020L)).willReturn(Optional.of(restaurant2));

    }

    private void mockReivewRepository() {
        List<Review> reviews = new ArrayList<>();
        reviews.add(Review.builder()
                        .name("jusnag")
                        .score(1)
                        .description("bad")
                        .build());

        given(reviewRepository.findAllByRestaurantId(1004L)).willReturn(reviews);
    }

    @Test
    public void getRestaurants(){
        List<Restaurant> restaurants = restaurantService.getRestaurants();

        Restaurant restaurant = restaurants.get(0);

        assertThat(restaurant.getId(), is(1004L));
    }

    @Test
    public void getRestaurantWithExisted(){
        Restaurant restaurant = restaurantService.getRestaurant(1004L);

        verify(menuItemRepository).findAllByRestaurantId(eq(1004L));
        verify(reviewRepository).findAllByRestaurantId(1004L);

        assertThat(restaurant.getId(), is(1004L));

        MenuItem menuItem = restaurant.getMenuItems().get(0);
        assertThat(menuItem.getName(), is("Kimchi"));

        Review review = restaurant.getReviews().get(0);
        assertThat(review.getDescription(), is("bad"));
    }

    @Test
    public void getRestauranNottWithExisted(){
        Exception exception = assertThrows(RestaurantNotFoundException.class, () ->{
            restaurantService.getRestaurant(404L);
        });
    }

//    Junit4 에서는 아래 방식으로 표현이 가능하다.
//    @Test(expected = RestaurantNotFoundException.class)
//    public void getRestauranNottWithExisted(){
//        restaurantService.getRestaurant(404L);
//    }

    @Test
    public void addRestaurant(){
        given(restaurantRepository.save(any())).will(invocation -> {
            Restaurant restaurant = invocation.getArgument(0);
            restaurant.setId(1234L);
            return restaurant;
        });

        Restaurant restaurant = Restaurant.builder()
                .name("BeRyong")
                .address("Busan")
                .build();
//        Restaurant saved = Restaurant.builder()
//                .id(1234L)
//                .name("BeRyong")
//                .address("Busan")
//                .build();

        Restaurant created = restaurantService.addRestaurant(restaurant);

        assertThat(created.getId(), is(1234L));
    }

    @Test
    public void updateRestuarant(){
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .name("Bob zip")
                .address("seoul")
                .build();

        given(restaurantRepository.findById(1004L)).willReturn(Optional.of(restaurant));

        restaurantService.updateRestaurant(1004L, "sool zip","busan");

        assertThat(restaurant.getName(), is("sool zip"));
        assertThat(restaurant.getAddress(), is("busan"));

    }
}