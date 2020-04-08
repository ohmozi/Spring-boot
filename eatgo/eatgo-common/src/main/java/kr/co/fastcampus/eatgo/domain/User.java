package kr.co.fastcampus.eatgo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity     //메뉴아이템 레포에 대한 구현이 없이도 JPA로 구현가능함
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private String email;

    @NotEmpty
    private String name;

    @NotNull
    private Long level;

    @NotEmpty
    private String password;

    private Long restaurantId;          //가게 주인인지 아닌지 여부

    public boolean isAdmin() {
        return level >=100;
    }

    public boolean isActive() {
        return level > 0;
    }

    public void deactivate() {
        level = 0L;
    }

    public void setRestaurantId(Long restaurantId){
        this.level=50L;
        this.restaurantId = restaurantId;
    }
    public boolean isRestaurantOwner() {
        return level==50L;
    }
}
