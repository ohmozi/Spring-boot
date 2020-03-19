package kr.co.fastcampus.eatgo.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue
    private Long id;

    private Long restaurantId;

    @NotEmpty
    private String name;

    @NotNull        //empty는 스트링일때 사용
    private Integer score;

    @NotEmpty
    private String description;

}
