package com.example.btl_tmdt.repository;

import com.example.btl_tmdt.model.ProductInOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductInOrderRepo extends JpaRepository<ProductInOrder, String> {

}
