package com.example.btl_tmdt.model;

import com.example.btl_tmdt.dao.ProductInCartDao;
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

    private Product product;

    @OneToOne
    @JoinColumn(name = "id")
    private Cart cart;

//    @OneToOne
//    @JoinColumn(name = "order_id")
//    private Order order;
    private int quantity;
    private double totalPrice;


    public ProductInCartDao toDao(){
        return new ProductInCartDao(id, product.toDao(), cart.toDao(), quantity, totalPrice);
    }

}