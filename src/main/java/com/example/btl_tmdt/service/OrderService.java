package com.example.btl_tmdt.service;


import com.example.btl_tmdt.dao.OrderDao;
import com.example.btl_tmdt.dao.ProductDao;
import com.example.btl_tmdt.repository.OrderRepo;
import com.example.btl_tmdt.repository.ProductInOrderRepo;
import com.example.btl_tmdt.repository.ProductRepo;
import com.example.btl_tmdt.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.example.btl_tmdt.model.*;

@Service
public class OrderService {
    @Autowired
    OrderRepo orderRepo;

    @Autowired
    ProductInOrderRepo productInOrderRepo;
    @Autowired
    UserRepo userRepo;

    public List<OrderDao> getOrders(){

        return orderRepo.findAll().stream().map(Order::toDao).collect(Collectors.toList());
    }

    public Order getOrderById(String id) {
        return orderRepo.findByOrderId(id);
    }

    public Order createOrder(Order order) {
        User user = userRepo.findUserByUserName(order.getUser().getUserName());
        order.setUser(user);
        return orderRepo.save(order);
    }

    public List<Order> getListOrderOfUser(User user) {
        return orderRepo.findByUser(user);
    }

    public List<Order> getOrderByUser(User userToDelete) {
        return orderRepo.findByUser(userToDelete);
    }

//    public List<ProductDao> getProductsInOder(String order_id){
//        OrderDao orderDao = orderRepo.getOrdersByOrder_id(order_id).toDao();
//        List<ProductInOrder> productsInOrder = productInOrderRepo.getProductInOrdersByOrder(orderDao.toModel());
//        List<ProductDao> products =
//    }
}
