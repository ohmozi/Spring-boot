package kr.co.fastcampus.eatgo.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {
    List<Restaurant> findAll();

    Optional<Restaurant> findById(Long id);
    //Optional은 자바8부터 추가된 타입 : 레스토랑 또는 Null일때 Null을처리하지않고 레스토랑이 있냐 없냐로 처리함. Null로 반환할때 처리가 가능

    Restaurant save(Restaurant restaurant);
}
