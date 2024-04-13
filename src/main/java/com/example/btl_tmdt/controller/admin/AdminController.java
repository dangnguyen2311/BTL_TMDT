package com.example.btl_tmdt.controller.admin;

import com.example.btl_tmdt.model.User;
import com.example.btl_tmdt.repository.UserRepo;
import com.example.btl_tmdt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String adminLoginGet(Model model){

        return "admin/login";
    }

    @PostMapping("/login")
    public ResponseEntity<String> adminLoginPost(@RequestBody User user, Model model){
        User user1 = userService.getUserByUserName(user.getUserName());
        User user2 = userService.getUserByEmail(user.getUserEmail());

        if(user1 != null && user2 != null){
            if(user1.getUserPass().equals(user.getUserPass()) || user2.getUserPass().equals(user.getUserPass())){
                if(user.getUserRole().equals("1")) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Login failed, user is not admin");
                return ResponseEntity.ok("Login successfully");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Login failed");
    }
}
