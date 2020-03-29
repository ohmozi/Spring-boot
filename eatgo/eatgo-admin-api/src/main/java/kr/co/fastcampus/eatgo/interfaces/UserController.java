package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.UserService;
import kr.co.fastcampus.eatgo.domain.Region;
import kr.co.fastcampus.eatgo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> list(){
        List<User> users = userService.getUsers();

        return users;
    }

    @PostMapping("/users")
    public ResponseEntity<?> create(@Valid @RequestBody User resource
    ) throws URISyntaxException {
            User user = userService.addUser(resource.getEmail(), resource.getName());

            URI location = new URI("/users/" + resource.getId());
            return ResponseEntity.created(location).body("{}");
    }

    @PatchMapping("/users/{id}")
    public String update(
            @PathVariable("id") Long id,
            @RequestBody User resource){

        User user = userService.updateUser(id, resource.getEmail(), resource.getName(), resource.getLevel());

        return "";
    }

    @DeleteMapping("/users/{id}")
    public String deactivate(@PathVariable("id") Long id){

        User user =userService.deactiveUser(id);

        return "";
    }


    //1. user list
    //2. user create -> 회원가입
    //3. user update
    //4. user delete -> level:0 아무것도 못함 level1:normal customer 2:rest owner 3:admin
}
