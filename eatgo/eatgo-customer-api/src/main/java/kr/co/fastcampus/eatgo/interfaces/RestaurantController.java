package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.RestaurantService;
import kr.co.fastcampus.eatgo.domain.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//** UI layer는 사용자와 내부에 있는 로직및 도메인 모델들이 서로 상관없도록 디자인 **
//우리가 이 파일을 컨트롤러라고 따로 만들지 않았지만 동작하는 이유
@CrossOrigin    //CORS로 막힌 문제를 해결
@RestController
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/restaurants")
    public List<Restaurant> list(@RequestParam("region") String region,
                                 @RequestParam("category") Long categoryId){     //가게 목록 리스트

        List<Restaurant> restaurants = restaurantService.getRestaurants(region, categoryId);

        return restaurants;
    }

    @GetMapping("/restaurants/{id}")        //가게상세 api가 가게리스트 api와 겹치는 부분이 많다는 것을 볼수있다. repository로 분리한다(domain)
    public Restaurant detail(@PathVariable("id") Long id){  //특정 가게 정보, 주소로 받아진 id를 변수로 사용하겠다.
        //datailWithNotExisted 테스트에서 notfound결과를 처리해주는 try catch문을 사용해도되지만
        //@ControllerAdvice 어노테이션 사용해도 된다.
        Restaurant restaurant = restaurantService.getRestaurant(id);
        // 가게정보 + 메뉴정보          각 레포지토리에서 따로 가져와야하는 걸 서비스 모듈로 한번에 제공

//        Restaurant restaurant = restaurantRepository.findById(id);
//        List<MenuItem> menuItems = menuItemRepository.findAllByRestaurantId(id);
//        restaurant.setMenuItem(menuItems);

        return restaurant;
    }
}
