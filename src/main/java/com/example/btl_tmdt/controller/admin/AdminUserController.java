package com.example.btl_tmdt.controller.admin;

import com.example.btl_tmdt.dao.UserDao;
import com.example.btl_tmdt.model.User;
import com.example.btl_tmdt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("admin/users")
public class AdminUserController {
    @Autowired
    UserService userService;

    @GetMapping("")
    public String getAllUser(Model model){
        List<UserDao> userDaoList = userService.getUsers().stream().map(User::toDao).toList();
        model.addAttribute("users", userDaoList);

        return "admin/user/users";
    }

    @GetMapping("/add-user")
    public String addUserGet(Model model){
        UserDao userDao = new UserDao();
        model.addAttribute("userDao", userDao);

        return "admin/user/add-user";
    }
    @PostMapping("/add-user")
    public String addNewUserPost(Model model, @ModelAttribute(name = "userDao") UserDao userDao) {

        User user0 = userService.getUserByUserName(userDao.getUserName());
        User user1 = userService.getUserByEmail(userDao.getUserEmail());

        if (user0 != null || user1 != null) {
            model.addAttribute("error", "Username or Email is existed in System");
            return "/admin/user/add-user";
        }

        userService.saveUser(userDao.toModel());

        return "redirect:/admin/users";
    }

    @GetMapping("/edit-user/{id}")
    public String editUserGet(@PathVariable(name = "id") String id, Model model){
        UserDao userDao = userService.getUserByUserId(id).toDao();
        model.addAttribute("userDao", userDao);

        return "admin/user/edit-user";
    }

    @PostMapping("/edit-user/{id}")
    public String editUserPost(@ModelAttribute UserDao userDao, @PathVariable(name = "id") String id, Model model){
        UserDao userDao1 = userService.getUserByUserName(userDao.getUserName()).toDao();
        UserDao userDao2 = userService.getUserByEmail(userDao.getUserEmail()).toDao();

        if(userDao1 != null || userDao2 != null){
            System.out.println("Email or Username already existed");
            model.addAttribute("error", "Email or Username already existed");
            return "admin/user/edit-user";
        }

        userService.saveUser(userDao.toModel());
        return "redirect:/admin/users";
    }
}
