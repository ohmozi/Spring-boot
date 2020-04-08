package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.EmailNotExistedException;
import kr.co.fastcampus.eatgo.application.PasswordWrongException;
import kr.co.fastcampus.eatgo.application.UserService;
import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.core.StringContains.containsString;

@ExtendWith(SpringExtension.class)  // junit4에서는 Runwith, 5에서는 extendwith
@WebMvcTest(SessionController.class)
class SessionControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private UserService userService;

    //로그인정보가 적절할때
    @Test
    public void createWithValidAttributes()throws Exception {

        Long id = 1004L;
        String name = "John";
        String email = "test@test.com";
        String password = "test";

        User mockUser = User.builder()
                .id(id)
                .name(name)
                .level(1L)
                .build();

        BDDMockito.given(userService.authenticate(email,password)).willReturn(mockUser);

        BDDMockito.given(jwtUtil.createToken(id, name, null)).willReturn("header.payload.signature");

        mvc.perform(MockMvcRequestBuilders.post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"test@test.com\", \"password\":\"test\"}"))       //여기서 입력한 값은 저장되는데 id, restid값이 저장이안됨
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("location","/session"))
                .andExpect(MockMvcResultMatchers.content().string(containsString("{\"accessToken\":\"header.payload.signature\"}")));


        Mockito.verify(userService).authenticate(ArgumentMatchers.eq(email), ArgumentMatchers.eq(password));
    }

    @Test
    public void createRestaurantOwner()throws Exception {

        Long id = 1004L;
        String name = "John";
        String email = "test@test.com";
        String password = "test";

        User mockUser = User.builder()
                .id(id)
                .name(name)
                .level(50L)
                .restaurantId(369L)
                .build();

        BDDMockito.given(userService.authenticate(email,password)).willReturn(mockUser);

        BDDMockito.given(jwtUtil.createToken(id, name, 369L)).willReturn("header.payload.signature");

        mvc.perform(MockMvcRequestBuilders.post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"test@test.com\", \"password\":\"test\"}"))       //여기서 입력한 값은 저장되는데 id, restid값이 저장이안됨
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("location","/session"))
                .andExpect(MockMvcResultMatchers.content().string(containsString("{\"accessToken\":\"header.payload.signature\"}")));


        Mockito.verify(userService).authenticate(ArgumentMatchers.eq(email), ArgumentMatchers.eq(password));
    }

    // 입력에 오류가 있을경우 처리, 패스워드가 틀리거나 이메일이 존재하지 않을 때
    @Test
    public void createWithWrongPassword()throws Exception {

        BDDMockito.given(userService.authenticate("test@test.com","x")).willThrow(PasswordWrongException.class);

        mvc.perform(MockMvcRequestBuilders.post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"test@test.com\", \"password\":\"x\"}"))       //여기서 입력한 값은 저장되는데 id, restid값이 저장이안됨
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        Mockito.verify(userService).authenticate(ArgumentMatchers.eq("test@test.com"), ArgumentMatchers.eq("x"));
    }

    @Test
    public void createWithNotExistedEmail()throws Exception {

        BDDMockito.given(userService.authenticate("x@test.com","test")).willThrow(EmailNotExistedException.class);

        mvc.perform(MockMvcRequestBuilders.post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"x@test.com\", \"password\":\"test\"}"))       //여기서 입력한 값은 저장되는데 id, restid값이 저장이안됨
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        Mockito.verify(userService).authenticate(ArgumentMatchers.eq("x@test.com"), ArgumentMatchers.eq("test"));
    }
}