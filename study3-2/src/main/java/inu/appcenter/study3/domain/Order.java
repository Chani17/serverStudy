package inu.appcenter.study3.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_id")
    private Long id;

    private int totalPrice;

    private int count;

    @ManyToOne(fetch = FetchType.LAZY)  // 지연로딩 fetch join 다른 데이터 까지 가져올 수 있는 것
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)  // EAGER : 연관된 것들은 전부 가져온다.
    @JoinColumn(name = "product_id")
    private Product product;

    public static Order createOrder(int count, Member member, Product product) {
        Order order = new Order();
        order.count = count;
        product.changeStockQuantity(count);
        order.totalPrice = count * product.getPrice();
        order.member = member;
        order.product = product;
        return order;
    }
}
