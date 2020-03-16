package kr.co.fastcampus.eatgo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity     //메뉴아이템 레포에 대한 구현이 없이도 JPA로 구현가능함
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItem {

    @Id
    @GeneratedValue
    private Long id;
    private Long restaurantId;
    private String name;

//    public MenuItem(String name) {
//        this.name = name;
//    }
//    public String getName(){
//        return name;
//    }
}
