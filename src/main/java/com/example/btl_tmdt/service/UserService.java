package com.example.btl_tmdt.service;

import com.example.btl_tmdt.dao.UserDao;
import com.example.btl_tmdt.model.User;
import com.example.btl_tmdt.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;

    public void saveUser(User user) {
        userRepo.save(user);
//        if(userRepo.findById(user.getUserId()).isEmpty()){
//            userRepo.save(user);
//        }
    }

    public User getUserByEmail(String userEmail) {
        return userRepo.findUserByUserEmail(userEmail);
    }

    public void deleteUser(String id) {
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

//    public void editUser(String username, UserDao userDao) {
//        userRepo.updateUserByUserName(username);
//    }
}
