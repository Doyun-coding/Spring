package jpabook.jpashop.repository;

import jpabook.jpashop.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class  OrderSearch {
    private String memberName; // 회원의 이름
    private OrderStatus orderStatus; // 주문의 상태[ORDER, CANCEL]

}
