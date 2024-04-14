package com.example.btl_tmdt.controller.admin;

import com.example.btl_tmdt.service.ProductService;
import com.example.btl_tmdt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class OrderController {
    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;
}
