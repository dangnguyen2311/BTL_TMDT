package com.example.btl_tmdt.service;

import com.example.btl_tmdt.dao.UserDao;
import com.example.btl_tmdt.model.Cart;
import com.example.btl_tmdt.model.Order;
import com.example.btl_tmdt.model.User;
import com.example.btl_tmdt.repository.CartRepo;
import com.example.btl_tmdt.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    CartRepo cartRepo;
    @Autowired
    ProductInCartService productInCartService;
    @Autowired
    ProductInOrderService productInOrderService;
    @Autowired
    OrderService orderService;

    public void saveUser(User user) {
        userRepo.save(user);
        Cart cart = new Cart(user);
        cartRepo.save(cart);

//        userRepo.saveUser(user.getUserId(), user.getUserName(), user.getUserPhone(), user.getUserAddress());
    }

    public User getUserByEmail(String userEmail) {
        return userRepo.findUserByUserEmail(userEmail);
    }

    public void deleteUser(String id) {
        User userToDelete = userRepo.getUserByUserId(id);
        Cart cartToDelete = cartRepo.getCartByUser(userToDelete);
        List<Order> orderToDeleteList  = orderService.getOrderByUser(userToDelete);
        productInCartService.deleteProductInCartByUser(cartToDelete);
        productInOrderService.deleteProductInOrderByUser(orderToDeleteList);
        cartRepo.delete(cartToDelete);
        userRepo.deleteById(id);
    }

    public List<User> getUsers() {
        return userRepo.findAll();
    }

    public boolean checkExistedEmail(String userEmail) {
        return userRepo.existsByUserEmail(userEmail);
    }

    public User getUserByUserName(String username) {
        return userRepo.getUserByUserName(username);
    }

    public User getUserByUserId(String userId){
        return userRepo.findById(userId).get();
    }

    public void createUser(User user) {
        userRepo.save(user);
    }

    public void updateUser(UserDao userDao) {
        User userOld = userRepo.getUserByUserName(userDao.getUserName());
        userRepo.updateUserInfo(userOld.getUserId(), userDao.getUserFullName(), userDao.getUserPhone(), userDao.getUserAddress());
    }

//    public void editUser(String username, UserDao userDao) {
//        userRepo.updateUserByUserName(username);
//    }
}
