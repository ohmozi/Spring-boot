package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.RestaurantService;
import kr.co.fastcampus.eatgo.domain.MenuItem;
import kr.co.fastcampus.eatgo.domain.MenuItemRepository;
import kr.co.fastcampus.eatgo.domain.Restaurant;
import kr.co.fastcampus.eatgo.domain.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

//** UI layer는 사용자와 내부에 있는 로직및 도메인 모델들이 서로 상관없도록 디자인 **
//우리가 이 파일을 컨트롤러라고 따로 만들지 않았지만 동작하는 이유
@CrossOrigin    //CORS로 막힌 문제를 해결
@RestController
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/restaurants")
    public List<Restaurant> list(){     //가게 목록 리스트
        List<Restaurant> restaurants = restaurantService.getRestaurants();

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

    @PostMapping("/restaurants")
    // 응답을 할때 상태까지 함께 응답하기위해(200, 201같은) responseentity 사용
    public ResponseEntity<?> create(@Valid @RequestBody Restaurant resource)
            //@Valid 제약조건 확인       restaurant 클래스에서 @Notempty추가
            throws URISyntaxException {

        Restaurant restaurant = restaurantService.addRestaurant(
                Restaurant.builder()
                        .name(resource.getName())
                        .address(resource.getAddress())
                        .build());

        URI location = new URI("/restaurants/" + restaurant.getId());
        return ResponseEntity.created(location).body("{}");
    }

    @PatchMapping("/restaurants/{id}")
    public String update(
            @PathVariable("id") Long id, @Valid @RequestBody Restaurant resource){

        String name = resource.getName();
        String address= resource.getAddress();

        restaurantService.updateRestaurant(id, name, address);

        return "{}";
    }
}
