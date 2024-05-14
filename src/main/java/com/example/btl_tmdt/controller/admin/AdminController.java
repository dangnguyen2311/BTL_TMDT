package com.example.btl_tmdt.controller.admin;

import com.example.btl_tmdt.dao.OrderDao;
import com.example.btl_tmdt.dao.UserDao;
import com.example.btl_tmdt.model.Cart;
import com.example.btl_tmdt.model.Order;
import com.example.btl_tmdt.model.Product;
import com.example.btl_tmdt.model.User;
import com.example.btl_tmdt.repository.UserRepo;
import com.example.btl_tmdt.service.CartService;
import com.example.btl_tmdt.service.OrderService;
import com.example.btl_tmdt.service.ProductService;
import com.example.btl_tmdt.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;

    @Autowired
    HttpSession session;
    public boolean checkUser(){
        User user = (User) userService.getUserByUserName((String) session.getAttribute("userName"));
        return user.getUserRole().equals("2");
    }

    @GetMapping("")
    public String getAdminHome(Model model){
        if(!checkUser()){
            model.addAttribute("userDao", new UserDao());
            return "client/login";
        }
        List<Product> products = productService.getProducts();
        List<Cart> carts = cartService.getAllCart();
        List<User> users = userService.getUsers();
        List<OrderDao> orders = orderService.getOrders();
        model.addAttribute("numberOfProduct", products.size());
        model.addAttribute("numberOfCart", carts.size());
        model.addAttribute("numberOfUser", users.size());
        model.addAttribute("numberOfOrder", orders.size());

        return "admin/home";
    }

    @GetMapping("/login")
    public String adminLoginGet(Model model){

        return "admin/login";
    }

    @PostMapping("/login")
    public ResponseEntity<String> adminLoginPost(@RequestBody User user, Model model){
        User user1 = userService.getUserByUserName(user.getUserName());
        User user2 = userService.getUserByEmail(user.getUserEmail());

        if(user1 != null && user2 != null){
            if(user1.getUserPass().equals(user.getUserPass()) || user2.getUserPass().equals(user.getUserPass())){
                if(user.getUserRole().equals("1")) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Login failed, user is not admin");
                return ResponseEntity.ok("Login successfully");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Login failed");
    }

    @GetMapping("/logout")
    public String adminLogout(Model model){
        session.removeAttribute("userName");
        model.addAttribute("userDao", new UserDao());
        return "redirect:/login";
    }
}
