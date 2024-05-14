package com.example.btl_tmdt.repository;

import com.example.btl_tmdt.model.Cart;
import com.example.btl_tmdt.model.ProductInCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductInCartRepo extends JpaRepository<ProductInCart, String> {

    List<ProductInCart> getProductInCartByCart(Cart cart);

    void deleteAllByCart(Cart cart);
//    List<ProductInCart> getProductInCartBy
}
