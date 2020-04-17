package com.study.ohmozi.javaone.infolist.domain;


import com.study.ohmozi.javaone.infolist.domain.dto.Birthday;
import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Builder
/*
getter
setter
tostring
equalsandhashcode
를 포함하는 어노테이션 @Data
 */
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull            //@NotNull과 혼동하지 않기  ( @RequiredArgsConstructor )
    private String name;

    @NonNull
    private Integer age;

    private String hoby;

    @NonNull
    private String bloodType;

    private String address;

    @Valid
    @Embedded
    private Birthday birthday;

    private String job;

    @ToString.Exclude
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @ToString.Exclude
    // orphanRemoval : 아래 속해있는것도 삭제가능해짐
    // fetch : Lazy  : left join or right join이 아닌 select문이 나눠서 실행됨
    private Block block;
    /*
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
    public int hashCode(){      //hashcode를 오버라이딩해서 사용하면
        //person1과 person2가 같음을 읽을 수 있다.
        //DB에서 동일한 객체를 가져오기 위해서는 반드시 동일한 해시코드를 사용해주어야한다
        return (name+age).hashCode();
    }
    */
    //equals와 hashcode의 중요성 이런식으로 구현하면 변수의 변경에 따라 구현이 누락될수있다.
    //setter getter차이 처럼


}
