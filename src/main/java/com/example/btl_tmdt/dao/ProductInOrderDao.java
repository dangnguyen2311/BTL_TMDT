package com.example.btl_tmdt.dao;

import com.example.btl_tmdt.model.Order;
import com.example.btl_tmdt.model.Product;
import com.example.btl_tmdt.model.ProductInCart;
import com.example.btl_tmdt.model.ProductInOrder;
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
    private OrderDao orderDao;
    private ProductDao productDao;
    private Integer quantity;
    private Double totalPrice;

    public ProductInOrder toModel(){
        return new ProductInOrder(id, orderDao.toModel(), productDao.toModel(), quantity, totalPrice);
    }
}
