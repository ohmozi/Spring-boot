package com.study.ohmozi.javaone.infolist.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
@NoArgsConstructor
@Data       //data를 달아주기 전에는 해당 birthday클래스가 해시코드로 제공이되지만 후에는 값들이 잘나옴을 확인
public class Birthday {
    private Integer yearOfBirthday;

//    @Min(1)
//    @Max(12)
//    LocalDate 객체에서 값을 얻어옴으로써 valid를 선언해주지 않아도 유효함
    private Integer monthOfBirthday;

    private Integer dayOfBirthday;

    public Birthday(LocalDate birthday){
        this.yearOfBirthday = birthday.getYear();
        this.monthOfBirthday = birthday.getMonthValue();
        this.dayOfBirthday = birthday.getDayOfMonth();
    }
}
