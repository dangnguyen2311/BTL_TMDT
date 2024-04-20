package com.example.btl_tmdt.controller.admin;

import com.example.btl_tmdt.dao.CategoryDao;
import com.example.btl_tmdt.model.Category;
import com.example.btl_tmdt.service.CategoryService;
import com.example.btl_tmdt.service.UserService;
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

    @GetMapping("")
    public String getCategoryList(Model model){
        List<Category> categoryList = categoryService.getCategories();
        model.addAttribute("categories", categoryList);

        return "admin/category/categories";
    }

    @GetMapping("/add-category")
    public String addCategoryGet(Model model){
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
        CategoryDao categoryDaoToEdit = categoryService.getCategoryById(id).toDao();
        model.addAttribute("categoryDao", categoryDaoToEdit);
        return "admin/category/edit-category";

    }

    @PostMapping("/edit-category/{id}")
    public String editCategoryPost(@PathVariable(name = "id") String id, Model model, @ModelAttribute("categoryDao") CategoryDao categoryDao){
        Category category = categoryService.getCategoriesByname(categoryDao.getCategory_name());

        if (category != null) {
            model.addAttribute("error", "Category is existed in System");
            System.out.println("Category is existed in System");
            return "/admin/category/edit-category";
        }
        categoryService.editCategory(categoryDao.toModel());
        return "redirect:/admin/category";
    }

    @GetMapping("/delete-category/{id}")
    public String deleteCategory(Model model, @PathVariable(name = "id") String id) {
        categoryService.deleteCategory(categoryService.getCategoryById(id));
        return "redirect:/admin/category/";
    }

}
