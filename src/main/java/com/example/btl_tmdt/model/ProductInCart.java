package com.example.btl_tmdt.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_in_cart")
public class ProductInCart  {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne
    @JoinColumn(name = "prod_id")
    private Product product;

    @OneToOne
    @JoinColumn(name = "id")
    private Cart cart;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "total_price")
    private double totalPrice;

//    @ManyToOne
//    private Cart cart;
}