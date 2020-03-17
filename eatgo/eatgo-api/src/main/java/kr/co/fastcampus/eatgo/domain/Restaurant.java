package kr.co.fastcampus.eatgo.domain;

import lombok.*;
import org.springframework.beans.MutablePropertyValues;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Restaurant {
    @Id
    @GeneratedValue
    private Long id;
    //@Setter 변수에 선언하면 이 변수에 대해서만 생성
    @NotEmpty
    private String name;
    @NotEmpty
    private String address;
//    private String regionName;      //Seoul
//    private String categoryName;    //Korean
//    private String tagNames;        //#존맛탱
//    위처럼 변수가 추가되고 여러개일때 get,set함수를 선언하지 않아도 되며 생성자에서 변수 순서 및 어떤 변수가 있는지를 명시해줄수있어
//    Lombok이 사용된다
    @Transient      //임시로 통과하는 어노테이션
    private List<MenuItem> menuItems = new ArrayList<MenuItem>();

    public String getInformation() {        //인스턴스로 만들어진 애들은 자동으로 게터를 통해 생성
        return name + " in " + address;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = new ArrayList<>(menuItems);
    }

    public void updateInformation(String name, String address) {
        this.name = name;
        this.address =address;
    }
//    public Restaurant(String name, String address) {
//        this.name = name;
//        this.address = address;
//    }
//
//    //@Allargsconstructor
//    public Restaurant(Long id, String name, String address) {
//        this.id = id;
//        this.name = name;
//        this.address = address;
//    }
    //lombok setter로 사용가능
//    public void setId(long id) {
//        this.id = id;
//    }
    //lombok getter로 사용가능
//    public Long getId(){
//        return id;
//    }
//    public String getName() {
//        return name;
//    }
//    public String getAddress() {
//        return address;
//    }
//    public List<MenuItem> getMenuItems(){
//        return menuItems;
//    }
    public void addMenuItem(MenuItem menuItem) {
        menuItems.add(menuItem);
    }
}
