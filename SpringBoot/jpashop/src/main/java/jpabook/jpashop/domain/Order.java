package jpabook.jpashop.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // protected 이므로 다른 패키지에서 접근 불가
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // ToOne 관계는 무조건 지연 로딩 LAZY 를 사용해야 한다
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // CascadeType : orderItems 들을 persist() 해준다
    private List <OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id") // 연관관계 주인
    private Delivery delivery;

    private LocalDateTime orderDate; // 주문 시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문 상태 [ORDER, CANCEL]

    // == 연관 관계 매서드 == //
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    // == 생성 메서드 == //
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) { // ... 문법은 여러 개를 보낼 수 있다
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);

        for(OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    // == 비즈니스 로직 == //
    // 주문 취소
    public void cancel() {
        if(delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);

        for(OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    // == 조회 로직 == //
    // 전체 가격 조회
    public int getTotalPrice() {
        int totalPrice = 0;

        for(OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

}
