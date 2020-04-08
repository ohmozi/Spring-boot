package kr.co.fastcampus.eatgo.utils;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class JwtUtilTests {

    private static final String SECRET = "12345678901234567890123456789012";
//        key를 32자리 이상써줘야한다
    private JwtUtil jwtUtil;

    @BeforeEach
    public void setUp(){
        jwtUtil = new JwtUtil(SECRET);
    }

    @Test
    public void createToken(){

        String token = jwtUtil.createToken(1004L, "John", null );       // 유저Id와 사용자 이름 두가지를 넣기로함

        assertThat(token, containsString("."));
    }

    @Test
    public void getClaims(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjI5MiwibmFtZSI6InRlc3RlciJ9.uoOEb8QouSZum_ZzT5iBgTycKUz5FwgcheSFjJrBy-c";

        Claims claims = jwtUtil.getClaims(token);

        assertThat(claims.get("name"), is("tester"));
        assertThat(claims.get("userId", Long.class), is(292L));        //숫자는 스트링이 아니므로 이렇게 확인 할 것임
//      이제 리뷰를 남길때 사용자가 이름을 직접남겨줘야했는데 인증된 사용자의 이름을 바로 넣는다.
    }

}