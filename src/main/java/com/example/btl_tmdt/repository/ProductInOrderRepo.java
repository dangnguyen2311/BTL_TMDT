package com.example.btl_tmdt.repository;

import com.example.btl_tmdt.dao.OrderDao;
import com.example.btl_tmdt.model.Order;
import com.example.btl_tmdt.model.ProductInOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductInOrderRepo extends JpaRepository<ProductInOrder, String> {
    List<ProductInOrder> getProductInOrdersByOrder(Order order);
}
