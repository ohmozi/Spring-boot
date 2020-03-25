package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service        //component와 비슷
public class RestaurantService {

    private RestaurantRepository restaurantRepository;

    //각 autowired를 세번안하고 생성자로 묶어줄수있음.
    public RestaurantService(
            RestaurantRepository restaurantRepository)
    {
        this.restaurantRepository = restaurantRepository;
    }

    public List<Restaurant> getRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurants;
    }

    public Restaurant getRestaurant(Long id){
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(()->new RestaurantNotFoundException(id));     //optional 처리를 null로 하겠다.
        // 실무에서는 이렇게하면 안됌. restuarant 객체가 null로 들어왔을때 에러처리가 안되어있음

        return restaurant;
    }

    public Restaurant addRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);       //db에 반영
        //생성할때는 꼭 save인가?
    }

    @Transactional  //이 안에서 벗어날때 db에 적용이된다.     위에서는 save를 해서 반영했음.
    // 실제로 save를 사용하지 않고 어노테이션을 통해  범위에서 처리가 벗어났을때 바로 적용이 되도록 한다.
    public Restaurant updateRestaurant(long id, String name, String address) {

        Restaurant restaurant = restaurantRepository.findById(id).orElse(null);

        restaurant.updateInformation(name, address);

        return restaurant;
    }
}
