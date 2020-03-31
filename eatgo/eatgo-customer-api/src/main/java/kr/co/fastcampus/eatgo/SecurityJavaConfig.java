package kr.co.fastcampus.eatgo;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


// gradle에서 starter-security 의존성 추가해주어서  port/login 으로 들어가면 로그인 페이지가나옴
// 우리가 만드는 api에서는 이 페이지가 나오지 않길 바라기 때문에 config를 해주는 것
@Configuration
@EnableWebSecurity
public class SecurityJavaConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().disable()       //cross origin 처리
                .csrf().disable()       //http post처리
                .formLogin().disable()     //login페이지가 안뜨도록 disable
                .headers().frameOptions().disable();        //iframe사용 허용
    }
}
