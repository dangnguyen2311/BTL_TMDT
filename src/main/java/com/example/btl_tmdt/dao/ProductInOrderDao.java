package com.example.btl_tmdt.dao;

import com.example.btl_tmdt.model.Order;
import com.example.btl_tmdt.model.Product;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInOrderDao {
    private String id;
    private Order order;
    private Product product;
    private Integer quantity;
    private Double totalPrice;
}
