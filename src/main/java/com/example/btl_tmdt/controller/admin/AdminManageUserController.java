//package com.example.btl_tmdt.controller.admin;
//
//
//import com.example.btl_tmdt.dao.UserDao;
//import com.example.btl_tmdt.model.User;
//import com.example.btl_tmdt.repository.UserRepo;
//import com.example.btl_tmdt.service.UserService;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.net.http.HttpResponse;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Controller
//@RequestMapping("/admin/users")
//public class AdminManageUserController {
//
//    @Autowired
//    private UserService userService;
//
//    @GetMapping("/list")
//    public String getUserList(Model model){
//        List<User> userList = userService.getUsers();
//        List<UserDao> userDaoList = userList.stream().map(User::toDao).toList();
//        model.addAttribute("users",userDaoList);
//        return "client/product-detail";
//    }
//
//    @GetMapping("/add-user")
//    public String addUserGet(Model model){
//        UserDao userDao = new UserDao();
//
//
//        return "admin/user/add-user";
//    }
//
//    @PostMapping("/add-user") // liên quan đến đoạn trên
//    public String addUserPost(@RequestBody UserDao userDao, Model model){
//        User user = userService.getUserByEmail(userDao.getUserEmail());
//        if(user == null){
//            System.out.println("Email already exist");
//        }
//
//        userService.saveUser(user);
//        return "admin/user/add-user";
//    }
//
//    @DeleteMapping("/delete-user")
//    public String deleteUser(@RequestParam(name = "username") String username, Model model){
//        User user = userService.getUserByUserName(username);
//
//        if(user == null){
//            System.out.println("User to delete is not existed");
//        }
//        assert user != null;
//        userService.deleteUser(user.getUserId());
//
//        return "admin/user/users";
//
//    }
//
//    @GetMapping("/edit-user/{username}")
//    public String editUserGet(Model model, @PathVariable(name = "username") String username){
//        UserDao userDao = userService.getUserByUserName(username).toDao();
//        model.addAttribute("userDao", userDao);
//
//
//        return "admin/user/users";
//    }
//
//    @PostMapping("/edit-user/{username}")
//    public String editUserPost(Model model, @ModelAttribute("userDao") UserDao userDao,@PathVariable("username") String username){
//        User user1 = userService.getUserByUserName(username);
//        User user2 = userService.getUserByEmail(userDao.getUserEmail());
//
//        if(user1 != null) userService.deleteUser(user1.getUserId());
//        if(user2 != null) userService.deleteUser(user2.getUserId());
//
//        if(user1 == null || user2 == null){
//            System.out.println("Email or username already exists");
//            return "/admin/user/edit-user";
//        }
//
//        userDao.setUserPass(userService.getUserByUserName(username).getUserPass());
//        userService.saveUser(userDao.toModel());
//
//        return "admin/user/users";
//    }
//
//    @GetMapping("/delete-user/{userId}")
//    public String deleteUser(@PathVariable("userId") String userId){
//        userService.deleteUser(userId);
//        return "admin/user/users";
//    }
//
//}
