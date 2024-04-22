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


    @GetMapping("/")
    public String getHome(Model model){
        model.addAttribute("userDao", new UserDao());
        return "client/login";
    }

    @PostMapping("/login")
    public String getHomePost(@ModelAttribute("userDao") UserDao userDao, Model model){
        UserDao userDao1 = userService.getUserByEmail(userDao.getUserEmail()).toDao();
//        UserDao userDao2 =
        if(userDao1 == null){
            model.addAttribute("error", "Login failed");

        }
        else if(userDao.getUserPass().equals(userDao1.getUserPass())){
            System.out.println("Login successfully");
            session.setAttribute("userName", userDao1.getUserName());
            model.addAttribute("userDao", userDao1);
            return "redirect:/home";
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

}
