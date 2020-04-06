package kr.co.fastcampus.eatgo.filters;

import io.jsonwebtoken.Claims;
import kr.co.fastcampus.eatgo.utils.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    private JwtUtil jwtUtil;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        Authentication authentication = getAuthentication(request);     //아래 함수선언참고

        if (authentication != null){
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);      //싱글톤이기때문에 나눠서 선언해주자
        }

        chain.doFilter(request, response);      //다음작업으로 계속 연결이 된다. 있냐없냐에 상관없이 항상 실행

    }

    private Authentication getAuthentication(HttpServletRequest request){     //외부사용자가 하는 auth가 아니라 스프링에서 하는 auth
        // 헤더에서 데이터를 얻어야한다.
        String token = request.getHeader("Authorization");
        // 데이터가 없을 수 도있기 때문에
        if (token == null){     // 에러처리할 때 chain은 계속해서 진행되어야하는걸 생각하기
            return null;
        }

//        Authorization: Bearer TOKEN.asdfg  토큰을 가져오면 bearer이 붙어있어서 빼준게 claims
        Claims claims = jwtUtil.getClaims(token.substring("Bearer ".length()));

        Authentication authentication = new UsernamePasswordAuthenticationToken(claims, null);  //가장 자주 쓰는 기본틀
        return authentication;
    }
    //TODO: jwt분석 필요
}
