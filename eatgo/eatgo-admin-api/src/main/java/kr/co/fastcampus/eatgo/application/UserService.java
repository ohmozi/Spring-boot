package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User addUser(String email, String name){

        User user = User.builder().email(email).name(name).build();

        return userRepository.save(user);
    }

    public User updateUser(Long id, String email, String name, Long level) {
        //TODO:restaruantService의 예외 처리 참고
        User user = userRepository.findById(id).orElse(null);

        user.setEmail(email);       //원래 이메일 확인하고 바꿔주기
        user.setName(name);
        user.setLevel(level);

        return user;
    }

    public User deactiveUser(Long id) {
        User user = userRepository.findById(id).orElse(null);

        return null;
    }

}
