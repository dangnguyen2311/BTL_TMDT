package com.example.btl_tmdt.controller.admin;

import com.example.btl_tmdt.dao.UserDao;
import com.example.btl_tmdt.model.User;
import com.example.btl_tmdt.service.CartService;
import com.example.btl_tmdt.service.UserService;
import jakarta.servlet.http.HttpSession;
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
    @Autowired
    HttpSession session;
    public boolean checkUser(){
        User user = (User) userService.getUserByUserName((String) session.getAttribute("userName"));
        return user.getUserRole().equals("2");
    }

    @GetMapping("")
    public String getAllUser(Model model){
        if(!checkUser()){
            model.addAttribute("userDao", new UserDao());
            return "client/login";
        }
        List<UserDao> userDaoList = userService.getUsers().stream().map(User::toDao).toList();
        model.addAttribute("userDaos", userDaoList);

        return "admin/user/users";
    }

    @GetMapping("/add-user")
    public String addUserGet(Model model){
        if(!checkUser()){
            model.addAttribute("userDao", new UserDao());
            return "client/login";
        }
        UserDao userDao = new UserDao();
        model.addAttribute("userDao", userDao);

        return "admin/user/add-user";
    }
    @PostMapping("/add-user")
    public String addNewUserPost(Model model, @ModelAttribute(name = "userDao") UserDao userDao) {

        User userByUserName = userService.getUserByUserName(userDao.getUserName());
        User userByUserEmail = userService.getUserByEmail(userDao.getUserEmail());

        if (userByUserName != null || userByUserEmail != null) {
            model.addAttribute("error", "Username or Email is existed in System");
            return "/admin/user/add-user";
        }

        userService.saveUser(userDao.toModel());

        return "redirect:/admin/users";
    }

    @GetMapping("/edit-user/{id}")
    public String editUserGet(@PathVariable(name = "id") String id, Model model){
        if(!checkUser()){
            model.addAttribute("userDao", new UserDao());
            return "client/login";
        }
        UserDao userDao = userService.getUserByUserId(id).toDao();
        model.addAttribute("userDao", userDao);

        return "admin/user/edit-user";
    }

    @PostMapping("/edit-user/{id}")
    public String editUserPost(@ModelAttribute UserDao userDao, @PathVariable(name = "id") String id, Model model){
//        UserDao userDao1 = userService.getUserByUserName(userDao.getUserName()).toDao();
//        UserDao userDao2 = userService.getUserByEmail(userDao.getUserEmail()).toDao();

//        if(userDao1 != null || userDao2 != null){
//            System.out.println("Email or Username already existed");
//            model.addAttribute("error", "Email or Username already existed");
//            return "admin/user/edit-user";
//        }
//        System.out.println(userDao.getUserFullName());

        userService.updateUser(userDao);
        return "redirect:/admin/users";
    }

    @GetMapping("/delete-user/{id}")
    public String deleteUserGet(@PathVariable(name = "id") String id, Model model){

        userService.deleteUser(id);

        return "redirect:/admin/users";
    }

    @GetMapping("/home")
    public String getDashboard(Model model){
        return "admin/home";
    }
}
