package com.example.btl_tmdt.controller.admin;

import com.example.btl_tmdt.dao.CartDao;
import com.example.btl_tmdt.dao.ProductDao;
import com.example.btl_tmdt.dao.ProductInCartDao;
import com.example.btl_tmdt.dao.ProductInOrderDao;
import com.example.btl_tmdt.model.Cart;
import com.example.btl_tmdt.model.Product;
import com.example.btl_tmdt.model.ProductInCart;
import com.example.btl_tmdt.service.CartService;
import com.example.btl_tmdt.service.ProductInCartService;
import com.example.btl_tmdt.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/cart")
public class AdminCartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private ProductInCartService productInCartService;
    
    @Autowired
    private ProductService productService;

    @GetMapping("")
    public String cartList(Model model){
        List<CartDao> cartDaoList = cartService.getAllCart().stream().map(Cart::toDao).collect(Collectors.toList());
        model.addAttribute("cartDaos", cartDaoList);

        return "admin/cart/carts";
    }

    @GetMapping("/cart-item/{id}")
    public String listItemOfCart (Model model,
                                  @PathVariable(name = "id") String id) {

        List<ProductInCart> productInCarts = productInCartService.getProductInCart(
                cartService.getCartById(id));
        List<ProductInCartDao> productInCartDaos = productInCarts.stream()
                .map(ProductInCart::toDao).collect(Collectors.toList());
        List<Product> products = productService.getProducts();
        List<ProductDao> productDaos = new ArrayList<>();

        for (Product i : products) {
            int check = 1;
            for (ProductInCart j : productInCarts) {
                if (j.getProduct().getProdId().equals(i.getProdId())) {
                    check = 0;
                    break;
                }
            }
            if (check == 1)
                productDaos.add(i.toDao());
        }

        ProductInCartDao productInCartDao = new ProductInCartDao();
        productInCartDao.setQuantity(1);

        model.addAttribute("productInCartDao", productInCartDao);
        model.addAttribute("productInCartDaos", productInCartDaos);
        model.addAttribute("cartDao", cartService.getCartById(id).toDao());
        model.addAttribute("productDaos", productDaos);

        return "/admin/cart/items";
    }

    @PostMapping("/cart-item/{id}")
    public String addItemOfCart (Model model,
                                 @PathVariable(name = "id") String id
            , @ModelAttribute(name = "cartItemDao") ProductInCartDao cartItemDao) {

        cartItemDao.setCartDao(cartService.getCartById(id).toDao());
        cartItemDao.setProductDao(productService
                .getProductById(cartItemDao.getProductDao().getProdId()).toDao());
        productInCartService.createCartItem(cartItemDao.toModel());

        return "redirect:/admin/carts/cart-item/{id}";
    }

    @GetMapping("/delete-cart-item/{id}")
    public String deleteItemOfCart (Model model,
                                    RedirectAttributes redirectAttributes,
                                    @PathVariable(name = "id") String id) {

        ProductInCart cartItem = productInCartService.getProductInCartById(id);
        CartDao cartDao = cartItem.toDao().getCartDao();

        redirectAttributes.addAttribute("id", cartDao.getId());

        productInCartService.deleteProductInCart(cartItem);

        return "redirect:/admin/carts/cart-item/{id}";
    }

}
