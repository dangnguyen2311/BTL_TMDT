package com.example.btl_tmdt.service;

import com.example.btl_tmdt.repository.ProductInOrderRepo;
import com.example.btl_tmdt.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductInOrderService {
    @Autowired
    ProductInOrderRepo productInOrderRepo;
    @Autowired
    UserRepo userRepo;
}
