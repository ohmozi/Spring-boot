package kr.co.fastcampus.eatgo;

import kr.co.fastcampus.eatgo.filters.JwtAuthenticationFilter;
import kr.co.fastcampus.eatgo.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.Filter;


// gradle에서 starter-security 의존성 추가해주어서  port/login 으로 들어가면 로그인 페이지가나옴
// 우리가 만드는 api에서는 이 페이지가 나오지 않길 바라기 때문에 config를 해주는 것
@Configuration
@EnableWebSecurity
public class SecurityJavaConfig extends WebSecurityConfigurerAdapter {

    @Value("${jwt.secret}")    //어떤걸 여기서 쓸꺼야
    private String secret;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 여기서 필터가 필요한 이유는 무엇인가???
        Filter filter = new JwtAuthenticationFilter(authenticationManager(), jwtUtil());

        http
                .cors().disable()       //cross origin 처리
                .csrf().disable()       //http post처리
                .formLogin().disable()     //login페이지가 안뜨도록 disable
                .headers().frameOptions().disable()        //iframe사용 허용
                .and()      //대상의 초기화
                .addFilter(filter)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //따로 세선에 대해 관리해주지않고 계속해서 토큰을 받은걸 가지고 필터가 작업을 진행하도록한다 (stateless)
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtUtil jwtUtil(){
        //application.yml에서 key를 설정해줄수있다.
        return new JwtUtil(secret);
    }
}
