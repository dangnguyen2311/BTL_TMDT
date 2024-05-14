package com.example.btl_tmdt.controller.admin;

import com.example.btl_tmdt.dao.OrderDao;
import com.example.btl_tmdt.dao.ProductInOrderDao;
import com.example.btl_tmdt.dao.UserDao;
import com.example.btl_tmdt.model.Order;
import com.example.btl_tmdt.model.ProductInOrder;
import com.example.btl_tmdt.model.User;
import com.example.btl_tmdt.service.OrderService;
import com.example.btl_tmdt.service.ProductInOrderService;
import com.example.btl_tmdt.service.ProductService;
import com.example.btl_tmdt.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/order")
public class AdminOrderController {
    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Autowired
    ProductInOrderService productInOrderService;

    @Autowired
    OrderService orderService;

    @Autowired
    HttpSession session;

    public boolean checkUser(){
        User user = (User) userService.getUserByUserName((String) session.getAttribute("userName"));
        return user.getUserRole().equals("2");
    }

    @GetMapping("")
    public String getOrders(Model model){
        if(!checkUser()){
            model.addAttribute("userDao", new UserDao());
            return "client/login";
        }
        List<OrderDao> orderDaoList = orderService.getOrders();
        model.addAttribute("orderDaos", orderDaoList);
        return "admin/order/orders";
    }

    @GetMapping("/order-item/{id}")
    public String getProductInOrder (Model model, @PathVariable(name = "id") String id) {
        if(!checkUser()){
            model.addAttribute("userDao", new UserDao());
            return "client/login";
        }
        Order order = orderService.getOrderById(id);

        List<ProductInOrderDao> productInOrderDaos = productInOrderService.getProductInOrder(order).stream()
                .map(ProductInOrder::toDao).collect(Collectors.toList());

        System.out.println("Order: " + order.getOrderId());
        System.out.println("List product have " + productInOrderDaos.size() + " products");

        model.addAttribute("orderDao", order.toDao());
        model.addAttribute("productInOrderDaos", productInOrderDaos);

        return "/admin/order/items";
    }

    @GetMapping("/delete-order/{id}")
    public String deleteOrder (Model model,
                               @PathVariable(name = "id") String id) {
        if(!checkUser()){
            model.addAttribute("userDao", new UserDao());
            return "client/login";
        }
        Order order = orderService.getOrderById(id);

        productInOrderService.deleteOrder(order);

        return "redirect:/admin/orders";
    }

//    @PostMapping("/item")
//    public String getProductInOrderPost(@ModelAttribute)


}
