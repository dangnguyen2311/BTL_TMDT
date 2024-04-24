package com.example.btl_tmdt.service;


import com.example.btl_tmdt.dao.ProductInCartDao;
import com.example.btl_tmdt.model.Cart;
import com.example.btl_tmdt.model.Product;
import com.example.btl_tmdt.model.ProductInCart;
import com.example.btl_tmdt.model.User;
import com.example.btl_tmdt.repository.CartRepo;
import com.example.btl_tmdt.repository.ProductInCartRepo;
import com.example.btl_tmdt.repository.ProductInOrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductInCartService {
    @Autowired
    ProductInCartRepo productInCartRepo;

    @Autowired
    CartRepo cartRepo;

    @Autowired
    CartService cartService;
    @Autowired
    ProductService productService;

    public List<ProductInCart> getProductInCart(Cart cart) {
        return productInCartRepo.getProductInCartByCart(cart);
    }

    public void createProductInCart(ProductInCart productInCart) {
        Cart cart = cartService.getCartById(productInCart.getCart().getId());
        Product product = productService.getProductById(productInCart.getProduct().getProdId());
        productInCart.setCart(cart);
        productInCart.setProduct(product);
        productInCartRepo.save(productInCart);
    }

    public ProductInCart getProductInCartById(String id) {
        return productInCartRepo.findById(id).get();
    }

    public void deleteProductInCart(ProductInCart productInCart) {
        productInCartRepo.delete(productInCart);
    }


    public void updateProductInCart(String id, int quantity) {
        ProductInCart productInCart = productInCartRepo.findById(id).get();
        productInCart.setQuantity(quantity);

        productInCartRepo.save(productInCart);
    }


//    public List<ProductInCartDao> getProductInCartByUser(User user) {
//        productInCartRepo.
//    }
}
