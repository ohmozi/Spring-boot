package com.study.ohmozi.javaone.infolist.domain;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull            //@NotNull과 혼동하지 않기  ( @RequiredArgsConstructor )
    private String name;

    @NonNull
    private Integer age;

    private String hoby;

    private String bloodType;

    private String address;

    private LocalDate birthday;

    private String job;

    @ToString.Exclude
    private String phoneNumber;

    @Override
    public boolean equals(Object o) {           // 객체가 다르면 equals가 false가 나오는데 내용이 같고 객체가 다르더라도 true가 나오도록
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) &&
                Objects.equals(name, person.name) &&
                Objects.equals(age, person.age) &&
                Objects.equals(hoby, person.hoby) &&
                Objects.equals(bloodType, person.bloodType) &&
                Objects.equals(address, person.address) &&
                Objects.equals(birthday, person.birthday) &&
                Objects.equals(job, person.job) &&
                Objects.equals(phoneNumber, person.phoneNumber);
    }

}
