package com.example.btl_tmdt.service;


import com.example.btl_tmdt.model.Cart;
import com.example.btl_tmdt.model.ProductInCart;
import com.example.btl_tmdt.repository.CartRepo;
import com.example.btl_tmdt.repository.ProductInCartRepo;
import com.example.btl_tmdt.repository.ProductInOrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductInCartService {
    @Autowired
    ProductInCartRepo productInCartRepo;

    @Autowired
    CartRepo cartRepo;

    public List<ProductInCart> getProductInCart(Cart cart) {
        return productInCartRepo.getProductInCartByCart(cart);
    }

    public void createCartItem(ProductInCart productInCart) {
        productInCartRepo.save(productInCart);
    }

    public ProductInCart getProductInCartById(String id) {
        return productInCartRepo.findById(id).get();
    }

    public void deleteProductInCart(ProductInCart productInCart) {
        productInCartRepo.delete(productInCart);
    }
}
