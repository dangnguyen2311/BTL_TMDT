package com.example.btl_tmdt.controller.admin;

import com.example.btl_tmdt.dao.CategoryDao;
import com.example.btl_tmdt.dao.UserDao;
import com.example.btl_tmdt.model.Category;
import com.example.btl_tmdt.model.User;
import com.example.btl_tmdt.service.CategoryService;
import com.example.btl_tmdt.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/category")
public class AdminCategoryController {
    @Autowired
    UserService userService;
    @Autowired
    CategoryService categoryService;

    @Autowired
    HttpSession session;
    public boolean checkUser(){
        User user = (User) userService.getUserByUserName((String) session.getAttribute("userName"));
        return user.getUserRole().equals("2");
    }
    @GetMapping("")
    public String getCategoryList(Model model){
        if(!checkUser()){
            model.addAttribute("userDao", new UserDao());
            return "client/login";
        }
        List<Category> categoryList = categoryService.getCategories();
        model.addAttribute("categories", categoryList);

        return "admin/category/categories";
    }

    @GetMapping("/add-category")
    public String addCategoryGet(Model model){
        if(!checkUser()){
            model.addAttribute("userDao", new UserDao());
            return "client/login";
        }
        CategoryDao categoryDao = new CategoryDao();
        model.addAttribute("categoryDao", categoryDao);
        return "admin/category/add-category";
    }

    @PostMapping("/add-category")
    public String addCategoryPost(@ModelAttribute CategoryDao categoryDao, Model model){

        Category category = categoryService.getCategoriesByname(categoryDao.getCategory_name());
        if(category != null){
            System.out.println("Category is already existed!");
            model.addAttribute("error", "Category is existed in System");
            return "admin/category/add-category";
        }
        categoryService.saveCategory(categoryDao);
        return "redirect:/admin/category";
    }

    @GetMapping("/edit-category/{id}")
    public String editCategoryGet(@PathVariable(name = "id") String id, Model model){
        if(!checkUser()){
            model.addAttribute("userDao", new UserDao());
            return "client/login";
        }
        CategoryDao categoryDaoToEdit = categoryService.getCategoryById(id).toDao();
        model.addAttribute("categoryDao", categoryDaoToEdit);
        return "admin/category/edit-category";

    }

    @PostMapping("/edit-category/{id}")
    public String editCategoryPost(@PathVariable(name = "id") String id, Model model, @ModelAttribute("categoryDao") CategoryDao categoryDao){

//        Category category = categoryService.getCategoriesByname(categoryDao.getCategory_name());
        Category categoryToEdit = categoryService.getCategoryById(id);
//        if (categoryToEdit != null) {
//            model.addAttribute("error", "Category is existed in System");
//            System.out.println("Category is existed in System");
//            return "/admin/category/edit-category";
//        }

        categoryService.editCategory(categoryToEdit, categoryDao.toModel());
        return "redirect:/admin/category";
    }

    @GetMapping("/delete-category/{id}")
    public String deleteCategory(Model model, @PathVariable(name = "id") String id) {
        if(!checkUser()){
            model.addAttribute("userDao", new UserDao());
            return "client/login";
        }
        categoryService.deleteCategory(categoryService.getCategoryById(id));
        return "redirect:/admin/category";
    }

}
