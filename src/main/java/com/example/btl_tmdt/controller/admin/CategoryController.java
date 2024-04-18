package com.example.btl_tmdt.controller.admin;

import com.example.btl_tmdt.model.Category;
import com.example.btl_tmdt.service.CategoryService;
import com.example.btl_tmdt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {
    @Autowired
    UserService userService;
    @Autowired
    CategoryService categoryService;

    @GetMapping("/list")
    public String getCategoryList(Model model){
        List<Category> categoryList = categoryService.getCategories();
        model.addAttribute("categories", categoryList);

        return "admin/category/categories";
    }

//    @GetMapping("")

}
