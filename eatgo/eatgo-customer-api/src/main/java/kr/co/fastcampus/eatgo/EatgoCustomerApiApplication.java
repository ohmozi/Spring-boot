package kr.co.fastcampus.eatgo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import sun.security.util.Password;

@SpringBootApplication
public class EatgoCustomerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EatgoCustomerApiApplication.class, args);
	}

}

//eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjI5MiwibmFtZSI6InRlc3RlciJ9.uoOEb8QouSZum_ZzT5iBgTycKUz5FwgcheSFjJrBy-c
//tester