package com.example.btl_tmdt.dao;

import com.example.btl_tmdt.model.Cart;
import com.example.btl_tmdt.model.Order;
import com.example.btl_tmdt.model.Product;
import com.example.btl_tmdt.model.ProductInCart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInCartDao {
    private String id;

    private ProductDao productDao;

    private CartDao cartDao;

    private int quantity;
    private double totalPrice;

    public ProductInCart toModel(){
        return new ProductInCart(id, productDao.toModel(), cartDao.toModel(), quantity, totalPrice);
    }
}
