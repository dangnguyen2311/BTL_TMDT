package com.example.btl_tmdt.controller.client;

import com.example.btl_tmdt.model.*;
import com.example.btl_tmdt.service.*;
import com.example.btl_tmdt.dao.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller

public class CheckOutController {
    @Autowired
    OrderService orderService;
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductInCartService productInCartService;
    @Autowired
    ProductInOrderService productInOrderService;
    @Autowired
    CartService cartService;

    @GetMapping("/checkout")
    public String getCheckout(Model model, HttpSession session) {

        User user = userService.getUserByUserName((String) session.getAttribute("userName"));

        if (user == null)
            return "redirect:/login";

        Cart cart = cartService.getCartByUser(user);

        List<ProductInCartDao> productInCartDaos = productInCartService.getProductInCart(cart).stream()
                .map(ProductInCart::toDao).collect(Collectors.toList());

        List<CategoryDao> categoryDaos = categoryService.getCategories()
                .stream().map(e -> e.toDao()).collect(Collectors.toList());

        List<ProductDao> productDaos = productService.getProducts().stream()
                .map(e -> e.toDao()).collect(Collectors.toList());

        Double totalPrice = Double.valueOf(productInCartDaos.stream().
                map(e -> e.getQuantity() * e.getProductDao().getProdPrice())
                .reduce(0.0F, Float::sum));

        Collections.shuffle(productDaos);

        model.addAttribute("categoryDaos", categoryDaos);
        model.addAttribute("productDaos", productDaos);

        model.addAttribute("cartDao", cart.toDao());
        model.addAttribute("cartItemDaos", productInCartDaos);
        model.addAttribute("totalPrice", totalPrice);

        model.addAttribute("orderDao", new OrderDao());


        return "/client/checkout";
    }

    @PostMapping("/order")
    public String createNewOrder(Model model, HttpSession session,
                                 @ModelAttribute("orderDao") OrderDao orderDao) {

        User user = userService.getUserByUserName((String) session.getAttribute("userName"));
        Cart cart = cartService.getCartByUser(user);

        List<ProductInCart> productInCarts = productInCartService.getProductInCart(cart);

        Double totalPrice = Double.valueOf(productInCarts.stream()
                .map(e -> e.getQuantity() * Float.parseFloat(e.getProduct().getProdPrice()))
                .reduce(0.0F, Float::sum));

        orderDao.setUserDao(user.toDao());
        orderDao.setOrder_time(new Date());
        orderDao.setTotal(totalPrice + 50000.0);

        Order order = orderService.createOrder(orderDao.toModel());

        for (ProductInCart i : productInCarts) {
            ProductInOrder orderItem = new ProductInOrder(order, i.getProduct(), i.getQuantity());
            productInOrderService.createProductInOrder(orderItem);
            productInCartService.deleteProductInCart(i);
        }

        List<ProductInOrderDao> productInOrderDaos = productInOrderService.getByOrder(order).stream()
                .map(ProductInOrder::toDao).collect(Collectors.toList());

        model.addAttribute("orderDao", orderDao);
        model.addAttribute("productInOrderDaos", productInOrderDaos);


        return "redirect:/my-order";
    }

    @GetMapping("/my-order")
    public String listOrder(Model model, HttpSession session) {

        User user = userService.getUserByUserName((String) session.getAttribute("userName"));

        if (user == null)
            return "redirect:/login";


        List<CategoryDao> categoryDaos = categoryService.getCategories()
                .stream().map(Category::toDao).collect(Collectors.toList());

        List<OrderDao> orderDaos = orderService.getListOrderOfUser(user).stream()
                .map(Order::toDao).collect(Collectors.toList());

        model.addAttribute("categoryDaos", categoryDaos);
        model.addAttribute("orderDaos", orderDaos);

        return "/client/list-order";
    }

    @GetMapping("/my-order/detail/{id}")
    public String orderDetail(Model model, HttpSession session,
                              @PathVariable("id") String id) {

        User user = userService.getUserByUserName((String) session.getAttribute("userName"));

        if (user == null)
            return "redirect:/login";


        Order order = orderService.getOrderById(id);

        List<CategoryDao> categoryDaos = categoryService.getCategories()
                .stream().map(Category::toDao).collect(Collectors.toList());

        List<ProductInOrderDao> orderItemDaos = productInOrderService.getByOrder(order).stream()
                .map(ProductInOrder::toDao).collect(Collectors.toList());

        model.addAttribute("categoryDaos", categoryDaos);
        model.addAttribute("orderDao", order.toDao());
        model.addAttribute("orderItemDaos", orderItemDaos);

        return "/client/order-detail";
    }


}
