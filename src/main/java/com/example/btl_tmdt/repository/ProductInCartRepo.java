package com.example.btl_tmdt.repository;

import com.example.btl_tmdt.model.ProductInCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductInCartRepo extends JpaRepository<ProductInCart, String> {


}
