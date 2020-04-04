package kr.co.fastcampus.eatgo.utils;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTests {

    @Test
    public void createToken(){
        JwtUtil jwtUtil = new JwtUtil();

        String token = jwtUtil.createToken(1004L, "John");       // 유저Id와 사용자 이름 두가지를 넣기로함

        assertThat(token, containsString("."));
    }

}