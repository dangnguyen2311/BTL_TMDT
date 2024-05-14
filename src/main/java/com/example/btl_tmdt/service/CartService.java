package com.example.btl_tmdt.service;

import com.example.btl_tmdt.dao.UserDao;
import com.example.btl_tmdt.model.Cart;
import com.example.btl_tmdt.model.User;
import com.example.btl_tmdt.repository.CartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    CartRepo cartRepo;

    public List<Cart> getAllCart() {
        return cartRepo.findAll();
    }

    public Cart getCartById(String id) {
        return cartRepo.findById(id).get();
    }

    public Cart getCartByUser(User user) {
        Cart cart =  cartRepo.getCartByUser(user);
        if(cart == null){
            Cart newCart = new Cart();
            newCart.setUser(user);
            cartRepo.save(newCart);
            return newCart;
        }
        return cart;
    }

    public void addCart(UserDao userDao) {
        cartRepo.save(new Cart(userDao.toModel()));
    }
}
