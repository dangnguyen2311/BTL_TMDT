package com.example.btl_tmdt.controller.client;

import com.example.btl_tmdt.dao.CategoryDao;
import com.example.btl_tmdt.dao.ProductDao;
import com.example.btl_tmdt.dao.UserDao;
import com.example.btl_tmdt.model.Category;
import com.example.btl_tmdt.model.Product;
import com.example.btl_tmdt.model.User;
import com.example.btl_tmdt.service.CategoryService;
import com.example.btl_tmdt.service.ProductService;
import com.example.btl_tmdt.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @Autowired
    UserService userService;

    @Autowired
    HttpSession session;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;


    @GetMapping("")
    public String homeRedirect () {
        String userName = (String) session.getAttribute("userName");

        if(userName == null){
            return "redirect:/login";
        }

        return "redirect:/page/1";
    }

    @GetMapping("/page/{id}")
    public String home (Model model, HttpSession session,
                        @PathVariable("id") Integer id) {

        List<CategoryDao> categoryDaos = categoryService.getCategories()
                .stream().map(e -> e.toDao()).collect(Collectors.toList());

        List<ProductDao> listAllProductDao = productService.getProducts().stream()
                .map(e -> e.toDao()).collect(Collectors.toList());

        int pageNumber = listAllProductDao.size()/9;

        List<List<ProductDao>> listPage = new ArrayList<>();
        int index = 0;

        for (int i = 0; i < pageNumber; i++) {
            List<ProductDao> res = listAllProductDao.subList(index, index + 9);
            listPage.add(res);
            index += 9;
        }

        Collections.shuffle(listPage.get(id - 1));

        model.addAttribute("pageNumbers", pageNumber);
        model.addAttribute("categoryDaos", categoryDaos);
        model.addAttribute("productDaos", listPage.get(id - 1));


        return "/client/home";
    }

    @GetMapping("/login")
    public String getHomeGet(Model model){
        UserDao userDao = new UserDao();
        model.addAttribute("userDao", userDao);
        return "client/login";
    }

    @PostMapping("/login")
    public String getHomePost(@ModelAttribute("userDao") UserDao userDao, Model model){
        UserDao userDao1 = userService.getUserByEmail(userDao.getUserEmail()).toDao();
//        UserDao userDao2 =
        if(userDao1 == null){
            model.addAttribute("error", "Login failed");
            System.out.println("Login failed");
        }
        else if(userDao.getUserPass().equals(userDao1.getUserPass())){
            if(userDao1.getUserRole().equals("2")){
                return "redirect:/admin";
            }
            System.out.println("Login successfully");
            session.setAttribute("userName", userDao1.getUserName());
            model.addAttribute("userDao", userDao1);
            return "redirect:/page/1";
        }
        return "client/login";
    }

//    @GetMapping("/")

    @GetMapping("/register")
    public String registerGet (Model model) {

        UserDao userDao = new UserDao();

        List<CategoryDao> categoryDaos = categoryService.getCategories()
                .stream().map(Category::toDao).collect(Collectors.toList());

        List<ProductDao> productDaoS = productService.getProducts().stream()
                .map(Product::toDao).collect(Collectors.toList());

        model.addAttribute("userDao", userDao);
        model.addAttribute("categoryDaos", categoryDaos);
        model.addAttribute("productDaos", productDaoS);

        return "/client/register";
    }

    @PostMapping("/register")
    public String registerPost (Model model, @ModelAttribute(name = "userDao") UserDao userDao) {

        userDao.setUserRole("1");

        User user0 = userService.getUserByEmail(userDao.getUserEmail());
        User user1 = userService.getUserByUserName(userDao.getUserName());

        if (user0 != null || user1 != null) {
            model.addAttribute("error", "Email or username is existed in system");
            return "/client/register";
        }

        userService.createUser(userDao.toModel());

        return "redirect:/login";
    }


    @GetMapping("/logout")
    public String logout(){
        session.removeAttribute("userName");
        return "redirect:/";
    }

//    @GetMapping("/category/{name}")
//    public String getProductByCategory(Model model, @PathVariable(name = "name") String name){
//        CategoryDao categoryDaoByName = categoryService.getCategoriesByname(name).toDao();
//        List<ProductDao> productDaoListByCategory = productService.getProductsByCategory(categoryDaoByName.toModel()).stream().map(Product::toDao).toList();
//        List<CategoryDao> categoryDaos = categoryService.getCategories().stream().map(Category::toDao).collect(Collectors.toList());
//
//        model.addAttribute("categoryDaos", categoryDaos);
//        model.addAttribute("categoryDao", categoryDaoByName);
//        model.addAttribute("productDaos", productDaoListByCategory);
//        return "/client/home";
//    }

}
