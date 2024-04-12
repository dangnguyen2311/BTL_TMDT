package com.example.btl_tmdt.repository;

import com.example.btl_tmdt.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
    Product getProductByProdId(Integer id);  //tuwj may sinh ra
}
