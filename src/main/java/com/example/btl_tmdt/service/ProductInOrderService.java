package com.example.btl_tmdt.service;

import com.example.btl_tmdt.dao.ProductInOrderDao;
import com.example.btl_tmdt.model.Order;
import com.example.btl_tmdt.model.ProductInOrder;
import com.example.btl_tmdt.repository.OrderRepo;
import com.example.btl_tmdt.repository.ProductInOrderRepo;
import com.example.btl_tmdt.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductInOrderService {
    @Autowired
    ProductInOrderRepo productInOrderRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    OrderRepo orderRepo;

    public List<ProductInOrder> getProductInOrder(Order order) {
        return productInOrderRepo.getProductInOrdersByOrder(order);
    }

    public void deleteOrder(Order order) {
        productInOrderRepo.getProductInOrdersByOrder(order);//??????
        orderRepo.delete(order);
    }
}
