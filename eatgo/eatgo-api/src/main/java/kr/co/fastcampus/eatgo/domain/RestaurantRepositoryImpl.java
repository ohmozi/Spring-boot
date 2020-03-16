package kr.co.fastcampus.eatgo.domain;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

//컨트롤러가 레포지토리를 관리할수있게 어노테이션 추가
//레스토랑 레포지토리를 스프링이 관리하게된다.
@Component
public class RestaurantRepositoryImpl implements RestaurantRepository {
    private List<Restaurant> restaurants = new ArrayList<>();

    public RestaurantRepositoryImpl(){
        restaurants.add(new Restaurant(1004L,"Bob zip","Seoul"));
        restaurants.add(new Restaurant(2020L,"cyber food","Seoul"));
    }

    @Override
    public List<Restaurant> findAll() {
        return restaurants;
    }

    @Override
    public Restaurant findById(Long id) {
        return restaurants.stream()        //컬렉션에서 통째로가져와서 검색하는 방법 (비효율- 컨트롤러가 하게하지 않기)
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .get();
    }

    @Override
    public Restaurant save(Restaurant restaurant){
        restaurants.add(restaurant);
        return restaurant;
    }
}
