package com.example.btl_tmdt.repository;

import com.example.btl_tmdt.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepo extends JpaRepository<Cart, String> {

}
