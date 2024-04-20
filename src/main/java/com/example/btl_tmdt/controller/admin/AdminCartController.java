package com.example.btl_tmdt.controller.admin;

import com.example.btl_tmdt.service.CartService;
import com.example.btl_tmdt.service.ProductInCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/cart")
public class AdminCartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private ProductInCartService productInCartService;


}
