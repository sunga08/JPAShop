package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name="member_id")
    private Long id;

    private String name;

    @Embedded //내장타입
    private Address address;

    @OneToMany(mappedBy = "member") //order 테이블에 있는 member 필드에 의해서 매핑 된 것
    private List<Order> orders = new ArrayList<>(); //=> 여기에 값을 넣는다고 fk가 변경되지 않음
}
