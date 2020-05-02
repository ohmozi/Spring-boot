package com.study.ohmozi.javaone.infolist.domain;


import com.study.ohmozi.javaone.infolist.controller.dto.PersonDto;
import com.study.ohmozi.javaone.infolist.domain.dto.Birthday;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Builder
@Where(clause = "deleted = false")      // 디비 접속쿼리문을 삭제됏는지 아닌지를 다 바꿔주지 않아도된다.
/*
getter
setter
tostring
equalsandhashcode
를 포함하는 어노테이션 @Data
 */
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     //생성되는 id값이 충돌하지 않도록 선언
    private Long id;

    @NonNull            //@NotNull과 혼동하지 않기  ( @RequiredArgsConstructor )
    @NotEmpty
    @Column(nullable = false)
    private String name;

    private String hobby;

//    @NonNull
//    @NotEmpty
//    @Column(nullable = false)
//    private String bloodType;

    private String address;

    @Valid
    @Embedded
    private Birthday birthday;

    private String job;

    private String phoneNumber;

    @ColumnDefault("0")     //0은 false  기본적으로 생성하면 삭제가 안된상태
    private boolean deleted;

//    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
//    @ToString.Exclude
//    // orphanRemoval : 아래 속해있는것도 삭제가능해짐
//    // fetch : Lazy  : left join or right join이 아닌 select문이 나눠서 실행됨
//    private Block block;

    public Integer getAge(){        //생일은 매년 바뀌므로
        if(this.birthday != null){
            return LocalDate.now().getYear() - this.birthday.getYearOfBirthday() + 1;
        }else {
            return null;
        }
    }

    public boolean isBirthdayToday(){
        return LocalDate.now().equals(LocalDate.of(this.birthday.getYearOfBirthday(), this.birthday.getMonthOfBirthday(), this.birthday.getDayOfBirthday()));
    }

    public void set(PersonDto personDto) {
        if (!StringUtils.isEmpty(personDto.getHobby())) {
            this.setHobby(personDto.getHobby());
        }

        if (!StringUtils.isEmpty(personDto.getAddress())) {
            this.setAddress(personDto.getAddress());
        }

        if (!StringUtils.isEmpty(personDto.getJob())) {
            this.setJob(personDto.getJob());
        }

        if (!StringUtils.isEmpty(personDto.getPhoneNumber())) {
            this.setPhoneNumber(personDto.getPhoneNumber());
        }

        if (personDto.getBirthday() != null) {
            this.setBirthday(Birthday.of(personDto.getBirthday()));
        }
    }

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
