package jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("B") // single 테이블에서 디비를 구분하기 위해 넣는 값
@Getter @Setter
public class Book extends Item {

    private String author;
    private String isbn;

}
