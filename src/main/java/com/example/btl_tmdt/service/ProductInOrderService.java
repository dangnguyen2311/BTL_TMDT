package com.example.btl_tmdt.service;

import com.example.btl_tmdt.model.Order;
import com.example.btl_tmdt.model.ProductInOrder;
import com.example.btl_tmdt.repository.OrderRepo;
import com.example.btl_tmdt.repository.ProductInOrderRepo;
import com.example.btl_tmdt.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductInOrderService {
    @Autowired
    ProductInOrderRepo productInOrderRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    OrderRepo orderRepo;

    public List<ProductInOrder> getProductInOrder(Order order) {
        return  productInOrderRepo.getProductInOrdersByOrder(order);
//        return productInOrderRepo.findAllByOrder(order);
    }

    public void deleteOrder(Order order) {
        productInOrderRepo.getProductInOrdersByOrder(order);//??????
        orderRepo.delete(order);
    }

    public void createProductInOrder(ProductInOrder productInOrder) {
        Order order = orderRepo.findById(productInOrder.getOrder().getOrderId()).get();
        productInOrder.setOrder(order);
        productInOrderRepo.save(productInOrder);
    }

    public List<ProductInOrder> getByOrder(Order order) {
        return productInOrderRepo.findAllByOrder(order);

    }

    public void deleteProductInOrderByUser(List<Order> orderToDeleteList) {
        orderToDeleteList.forEach(order -> productInOrderRepo.deleteProductInOrderByOrder(order));
    }
}
