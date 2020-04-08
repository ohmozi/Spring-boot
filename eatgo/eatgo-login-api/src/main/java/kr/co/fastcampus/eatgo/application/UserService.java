package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {

    UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public User authenticate(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(()->new EmailNotExistedException(email));;

        // 패스워드의 인코딩
        if(!passwordEncoder.matches(password, user.getPassword())){         //인코딩된 비밀번호와 사용자가 입력한 비밀번호가 다를경우
            throw new PasswordWrongException();
        }

        return user;
    }
}
