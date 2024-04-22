package com.example.btl_tmdt.controller.client;

import com.example.btl_tmdt.dao.CategoryDao;
import com.example.btl_tmdt.dao.ProductDao;
import com.example.btl_tmdt.dao.ProductInCartDao;
import com.example.btl_tmdt.model.Cart;
import com.example.btl_tmdt.model.Product;
import com.example.btl_tmdt.model.ProductInCart;
import com.example.btl_tmdt.model.User;
import com.example.btl_tmdt.service.*;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CartController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductInCartService productInCartService;

    @GetMapping("/my-cart")
    public String getCartDetail (Model model, HttpSession session) {

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

        Collections.shuffle(productDaos);

        Double totalPrice = Double.valueOf(productInCartDaos.stream()
                .map(e -> e.getQuantity() * e.getProductDao().getProdPrice())
                .reduce(0.0F, Float::sum));

        model.addAttribute("categoryDaos", categoryDaos);
        model.addAttribute("productDaos", productDaos);

        model.addAttribute("cartDao", cart.toDao());
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("productInCartDaos", productInCartDaos);

        return "/client/cart";
    }

    @GetMapping("/add-to-cart")
    public String adDaoCart (Model model, HttpSession session,
                             @RequestParam("id") String id,
                             @RequestParam("quantity") int quantity) {

        User user = userService.getUserByUserName((String) session.getAttribute("userName"));

        if (user == null)
            return "redirect:/login";

        Cart cart = cartService.getCartByUser(user);
        Product product = productService.getProductById(id);

        ProductInCart productInCart = new ProductInCart(cart, product, 1);
        productInCart.setQuantity(quantity);

        productInCartService.createProductInCart(productInCart);
//        productInCartService.createNewCart();


        return "redirect:/my-cart";
    }

    @PostMapping("/update-cart/{id}")
    public String updateCart (@PathVariable("id") String id,
                              @RequestParam("quantity") int quantity){

        if (quantity == 0)
            productInCartService.deleteProductInCart(productInCartService.getProductInCartById(id));
        else productInCartService.updateProductInCart(id, quantity);

        return "redirect:/my-cart";
    }

}
