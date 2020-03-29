package kr.co.fastcampus.eatgo.domain;

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

    private Long level;

    public boolean isAdmin() {
        return level >=10;
    }

    public boolean isActive() {
        return true;
        //active가 아닌경우 테스트 만들기
    }
}
