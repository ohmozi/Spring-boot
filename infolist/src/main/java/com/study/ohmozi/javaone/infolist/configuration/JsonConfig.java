package com.study.ohmozi.javaone.infolist.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.study.ohmozi.javaone.infolist.configuration.serializer.BirthdaySerializer;
import com.study.ohmozi.javaone.infolist.domain.dto.Birthday;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
public class JsonConfig {
    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(ObjectMapper objectMapper){
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);

        return converter;
    }

    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new BirthdayModule());      //우리가 만든 serializer를 등록한다.
        objectMapper.registerModule(new JavaTimeModule());      //localDate 타입이 복잡한 정보를 많이 가지고있기 때문에 간단한 구조로 변경해주는 모듈

        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        return objectMapper;
    }

    static class BirthdayModule extends SimpleModule{
        BirthdayModule(){
            super();        // 상속받은 클래스의 생성자 호출
            addSerializer(Birthday.class, new BirthdaySerializer());     //birthday 클래스 타입은 시리얼라이즈를 통해 시리얼라이즈를 하겠다.
        }
    }
}
