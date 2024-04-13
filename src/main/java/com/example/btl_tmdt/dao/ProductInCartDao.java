package com.example.btl_tmdt.dao;

import com.example.btl_tmdt.model.Cart;
import com.example.btl_tmdt.model.Order;
import com.example.btl_tmdt.model.Product;

public class ProductInCartDao {
    private String id;

    private Product product;

    private Cart cart;

    private Order order;

    private int quantity;
    private double totalPrice;
}
