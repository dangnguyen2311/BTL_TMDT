package com.example.btl_tmdt.service;

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
    }

    public User getUserByEmail(String email) {
        return userRepo.findUserByUserEmail(email);
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
}
