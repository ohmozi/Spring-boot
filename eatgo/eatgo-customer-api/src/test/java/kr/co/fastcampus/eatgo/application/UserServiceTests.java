package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class UserServiceTests {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    public void registerUser(){
        String email = "test@test.com";
        String name = "tester";
        String password = "test";

        userService.registerUser(email, name, password);

        verify(userRepository).save(any());
    }

    @Test
    public void registerUserWithExistedEmail(){
        Exception exception = assertThrows(EmailExistedException.class, () ->{
            String email = "test@test.com";
            String name = "tester";
            String password = "test";

            User user = User.builder()
                    .email(email)
                    .name(name)
                    .password(password)
                    .build();

            given(userRepository.findByEmail(email)).willReturn(Optional.of(user));

            userService.registerUser(email, name, password);

//            verify(userRepository, never()).save(any());
        });
    }

    @Test
    public void authenticateWithValidAttributes(){
        String email = "test@test.com";
        String password = "test";

        User mockUser = User.builder()
                .email(email)
                .build();

        given(userRepository.findByEmail(email)).willReturn(Optional.of(mockUser));

        given(passwordEncoder.matches(any(), any())).willReturn(true);

        User user = userService.authenticate(email, password);

        assertThat(user.getEmail(), is(email));
    }

    @Test
    public void authenticateWithNotExistedEmail(){
        Exception exception = assertThrows(EmailNotExistedException.class, () -> {

            String email = "x@test.com";
            String password = "test";

            given(userRepository.findByEmail(email)).willReturn(Optional.empty());

            userService.authenticate(email, password);
        });
    }

    @Test
    public void authenticateWithWrongPassword(){
        Exception exception = assertThrows(PasswordWrongException.class, () -> {

            String email = "test@test.com";
            String password = "x";

            User mockUser = User.builder()
                    .email(email)
                    .build();

            given(userRepository.findByEmail(email)).willReturn(Optional.of(mockUser));

            given(passwordEncoder.matches(any(), any())).willReturn(false);

            userService.authenticate(email, password);
        });
    }
}