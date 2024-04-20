package com.example.btl_tmdt.controller.client;

import com.example.btl_tmdt.model.Product;
import com.example.btl_tmdt.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/allProduct")
    public ModelAndView getAllProduct(){
        ModelAndView mv = new ModelAndView();
        List<Product> allProduct = productService.getProducts();
        mv.addObject(allProduct);
        mv.setViewName("product_in_cart");
        return mv;
    }


    
}
