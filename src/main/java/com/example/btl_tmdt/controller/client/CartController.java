package com.example.btl_tmdt.controller.client;

import com.example.btl_tmdt.model.Cart;
import com.example.btl_tmdt.model.Product;
import com.example.btl_tmdt.model.ProductInCart;
import com.example.btl_tmdt.service.CartService;
import com.example.btl_tmdt.service.ProductService;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CartController {
    @Autowired
    CartService cartService;

    @Autowired
    HttpSession session;

    @Autowired
    ProductService productService;

    @GetMapping("/cart")
    public ModelAndView getCart(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("product_in_cart");
        mv.addObject(cartService.getAllCart());

        return mv;
    }

    @GetMapping("/add-cart")
    public String addToCart(@PathParam("id") String id){
        Cart cart = (Cart) session.getAttribute("cart");

        if(cart == null){
            System.out.println("Cart class is null");
            Cart newCart = new Cart();
            session.setAttribute("cart", newCart);
        }
        Product product = productService.getProductById(id);
        ProductInCart productInCart  =new ProductInCart();
        productInCart.setProduct(product);
        productInCart.setQuantity(1);
        productInCart.setTotalPrice(Double.parseDouble(product.getProdPrice()));
        assert cart != null;
//        cart.getProductInCarts().add(productInCart);
        session.setAttribute("cart", cart);
        return "home";
    }

//    @GetMapping("/add-cart")
//    public void addProductToCart(@RequestParam String id){
//        ProductDetail productDetail =
//
//    }

}
