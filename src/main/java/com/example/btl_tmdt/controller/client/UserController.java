package com.example.btl_tmdt.controller.client;

import com.example.btl_tmdt.dao.CategoryDao;
import com.example.btl_tmdt.dao.ProductDao;
import com.example.btl_tmdt.dao.UserDao;
import com.example.btl_tmdt.model.Category;
import com.example.btl_tmdt.model.Product;
import com.example.btl_tmdt.model.User;
import com.example.btl_tmdt.repository.ProductRepo;
import com.example.btl_tmdt.repository.UserRepo;
import com.example.btl_tmdt.request.UserRequest;
import com.example.btl_tmdt.service.CategoryService;
import com.example.btl_tmdt.service.ProductService;
import com.example.btl_tmdt.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Autowired
    HttpSession session;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/to-register")
    public String toregister(){
        return "register";
    }

    @GetMapping("/home")
    public String home(Model model){
        String userName = (String) session.getAttribute("userName");

        if(userName == null){
            System.out.println("userDao is null");
            return "redirect:/login";
        }
        else{

            List<ProductDao> productDaos = productService.getProducts().stream().map(Product::toDao).collect(Collectors.toList());
            model.addAttribute("productDaos", productDaos);
            List<CategoryDao> categoryDaos = categoryService.getCategories().stream().map(Category::toDao).collect(Collectors.toList());
            model.addAttribute("categoryDaos", categoryDaos);
//            session.setAttribute("products", productService.getProducts());

        }
        return "client/home";
    }

    @GetMapping("/category/{name}")
    public String getProductOfCategory (Model model, HttpSession session,
                                        @PathVariable(name = "name") String name){

        CategoryDao categoryDao = categoryService.getCategoriesByname(name).toDao();

        List<ProductDao> productDaoS = productService.getProducts().stream()
                .filter(e -> e.getCategory().getCategoryName().equals(name)).collect(Collectors.toList())
                .stream().map(e -> e.toDao()).collect(Collectors.toList());

        List<CategoryDao> categoryDaos = categoryService.getCategories()
                .stream().map(e -> e.toDao()).collect(Collectors.toList());

        Collections.shuffle(productDaoS);

        model.addAttribute("categoryDaos", categoryDaos);
        model.addAttribute("categoryDao", categoryDao);
        model.addAttribute("productDaos", productDaoS);

        return "/client/home";

    }


//    @PostMapping("/login")
//    public ModelAndView login(@PathParam("email") String email, @PathParam("password") String password) {
//        ModelAndView mv = new ModelAndView();
//
//        User user = userService.getUserByEmail(email);
////        List<User> userList = userService.getUsers();
////        for(User u: userList) System.out.println(u);
//        System.out.println(user);
//        if(user == null){
//            System.out.println("Can't find user in database");
//            mv.setViewName("login");
//            return mv;
//        }
//        else if (user.getUserPass().equals(password)) {
////            mv.addObject("user", user);
////            mv.addObject("products", productService.getProducts());
//            UserRequest userRequest = new UserRequest();
//            userRequest.setUserName(user.getUserName());
//            userRequest.setUserEmail(user.getUserEmail());
//            userRequest.setUserPass(user.getUserPass());
//            userRequest.setUserPhone(user.getUserPhone());
//            userRequest.setUserRole(user.getUserRole());
//            session.setAttribute("user", userRequest);
////            session.setAttribute("products", productService.getProducts());
//
//            System.out.println("Login successfully !!!");
//            mv.setViewName("redirect:/home");
//            return mv;
//        }
//        mv.setViewName("redirect:/index");
//        return mv;
////        return "redirect:/login";
//    }



//    @PostMapping("/register")
////    public @ResponseBody User register(@PathParam("user_name") String user_name, @PathParam("user_phone") String user_phone, @PathParam("user_email") String user_email, @PathParam("user_pass") String user_pass) {
//    public @ResponseBody User register(@PathParam("user_name") String user_name, @PathParam("user_phone") String user_phone, @PathParam("user_email") String user_email, @PathParam("user_pass") String user_pass) {
//            User user = new User();
//        user.setUserName(user_name);
//        user.setUserEmail(user_email);
//        user.setUserPass(user_pass);
//        user.setUserPhone(user_phone);
//        user.setUserRole("1");
//
//        if (userService.checkExistedEmail(user_email)) {
//            return null;
//        }
//        userService.saveUser(user);
////        session.setAttribute();
//        return user;
//    }

    @GetMapping("/getUsers")
    public @ResponseBody List<User> getUsers() {
        return userService.getUsers();
    }

    @DeleteMapping("/delete/{id}")
    public @ResponseBody String deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
        return "User deleted";
    }

    @PostMapping("/add-user")
    public String addUser(@RequestParam String username, @RequestParam String password, @RequestParam String email, @RequestParam String role, @RequestParam String phone){
        List<User> userList = userService.getUsers();
        boolean isUserExist = userList.stream().anyMatch(user -> username.equals(user.getUserName()));
        if(isUserExist){
            System.out.println("User " + username +" is existed");
        }
        else{
            User user = new User();
            user.setUserName(username);
            user.setUserPass(password);
            user.setUserEmail(email);
            user.setUserRole(role);
            user.setUserPhone(phone);
            userService.saveUser(user);
            System.out.println("Added new User to the Database");
        }
//        String id = @GeneratedValue(strategy = GenerationType.UUID)
        return "index";
    }

    @GetMapping("/user-detail")
    public String userDetail(Model model){
        String userName = (String) session.getAttribute("userName");

        if(userName == null){
            System.out.println("userDao is null");
            return "redirect:/login";
        }
        else{
            UserDao userDao = (UserDao) userService.getUserByUserName(userName).toDao();
            List<ProductDao> productDaos = productService.getProducts().stream().map(Product::toDao).collect(Collectors.toList());
            model.addAttribute("productDaos", productDaos);
            List<CategoryDao> categoryDaos = categoryService.getCategories().stream().map(Category::toDao).collect(Collectors.toList());
            model.addAttribute("categoryDaos", categoryDaos);
//            session.setAttribute("products", productService.getProducts());
            model.addAttribute("userDao", userDao);

        }
        return "client/profile_user";
    }
    @GetMapping("/user-detail/save-user")
    public String saveEditUserGet(Model model, @RequestParam("fullName") String fullName,
                                  @RequestParam("phone") String phone,
                                  @RequestParam("address") String address){
        String userName = (String) session.getAttribute("userName");
        UserDao userDao = userService.getUserByUserName(userName).toDao();
        System.out.println("full name: " + fullName + "phone: "+ phone + "address: " + address);
        model.addAttribute("userName", userDao);
        return "client/profile_user";
    }
    @PostMapping("/user-detail/save-user")
    public String saveEditUserPost(Model model,    @RequestParam("fullName") String fullName,
                                               @RequestParam("phone") String phone,
                                               @RequestParam("address") String address){
        String userName = (String) session.getAttribute("userName");
        UserDao userDao = userService.getUserByUserName(userName).toDao();
        System.out.println(fullName+phone+address);
        userDao.setUserFullName(fullName);
        userDao.setUserPhone(phone);
        userDao.setUserAddress(address);
        userService.updateUser(userDao);

        model.addAttribute("userName", userName);
        model.addAttribute("userDao", userDao);
        return "client/profile_user";
    }
}
