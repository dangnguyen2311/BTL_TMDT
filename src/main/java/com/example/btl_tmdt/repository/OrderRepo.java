package com.example.btl_tmdt.repository;

import com.example.btl_tmdt.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepo extends JpaRepository<Order, String> {



}
