package inu.appcenter.study3.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    private String name;

    private int stockQuantity;

    private int price;

    public static Product createProduct(String name, int stockQuantity, int price) {
        Product product = new Product();
        product.name = name;
        product.stockQuantity = stockQuantity;
        product.price = price;
        return product;
    }

    public void changeStockQuantity(int count) {
        this.stockQuantity -= count;
    }
}
