package kr.co.fastcampus.eatgo.domain;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantRepositoryImplTests {
    @Test       //나중에 JPA쓰면 지울 예정
    public void save(){
        RestaurantRepository repository = new RestaurantRepositoryImpl();

        int oldCount = repository.findAll().size();

        Restaurant restaurant = new Restaurant("BeRyong", "Busan");
        repository.save(restaurant);

        int newCount = repository.findAll().size();

        assertThat(newCount - oldCount, is(1));
    }

}