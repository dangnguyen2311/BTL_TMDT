package com.example.btl_tmdt.controller.client;

import com.example.btl_tmdt.dao.*;
import com.example.btl_tmdt.model.Category;
import com.example.btl_tmdt.model.Product;
import com.example.btl_tmdt.service.CategoryService;
import com.example.btl_tmdt.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/search")
    public String searchProduct (Model model, HttpSession session,
                                 @RequestParam(value = "productName", required = false) String productName){

        List<CategoryDao> categoryDaos = categoryService.getCategories()
                .stream().map(Category::toDao).collect(Collectors.toList());

        List<ProductDao> productDao = productService.getProducts().stream()
                .map(Product::toDao).toList().stream()
                .filter(e -> e.getProdName().toLowerCase().contains(productName.toLowerCase()))
                .collect(Collectors.toList());

        model.addAttribute("categoryDaos", categoryDaos);
        model.addAttribute("productDaos", productDao);

        return "/client/home";

    }

    @GetMapping("/product-detail/{id}")
    public String getProduct (Model model, HttpSession session,
                              @PathVariable(name = "id") String id){

        List<CategoryDao> categoryDaos = categoryService.getCategories()
                .stream().map(Category::toDao).collect(Collectors.toList());

        List<ProductDao> productDaoS = productService.getProducts().stream()
                .map(Product::toDao).collect(Collectors.toList());

        ProductDao productDao = productService.getProductById(id).toDao();

        model.addAttribute("productDao", productDao);
        model.addAttribute("categoryDaos", categoryDaos);
        model.addAttribute("productDaos", productDaoS);

        return "/client/product-detail";

    }


    
}
