package com.example.btl_tmdt.service;


import com.example.btl_tmdt.repository.ProductInCartRepo;
import com.example.btl_tmdt.repository.ProductInOrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductInCartService {
    @Autowired
    ProductInCartRepo productInCartRepo;
}
