package com.example.btl_tmdt.controller.admin;

import com.example.btl_tmdt.dao.CategoryDao;
import com.example.btl_tmdt.dao.ProductDao;
import com.example.btl_tmdt.dao.UserDao;
import com.example.btl_tmdt.model.Category;
import com.example.btl_tmdt.model.Product;
import com.example.btl_tmdt.model.User;
import com.example.btl_tmdt.service.CategoryService;
import com.example.btl_tmdt.service.ProductService;
import com.example.btl_tmdt.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/product")
public class AdminProductController {
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    UserService userService;
    @Autowired
    HttpSession session;
    public boolean checkUser(){
        User user = (User) userService.getUserByUserName((String) session.getAttribute("userName"));
        return user.getUserRole().equals("2");
    }

    @GetMapping("")
    public String getProducts(Model model){
        if(!checkUser()){
            model.addAttribute("userDao", new UserDao());
            return "client/login";
        }
        List<ProductDao> productDaoList = productService.getProducts().stream().map(Product::toDao).collect(Collectors.toList());
        model.addAttribute("productDaos", productDaoList);

        return "admin/product/products";
    }

    @GetMapping("/add-product")
    public String addProductGet(Model model){
//        productService.saveProd(productDao.toModel());
//        List<ProductDao> productDaoList = (List<ProductDao>) model.getAttribute("products");
//        assert productDaoList != null;
//        productDaoList.add(productDao);
//        model.addAttribute("products", productDaoList);
        if(!checkUser()){
            model.addAttribute("userDao", new UserDao());
            return "client/login";
        }
        ProductDao productDao = new ProductDao();
        List<CategoryDao> categoryDaos = categoryService.getCategories().stream().map(Category::toDao)
                .collect(Collectors.toList());

        model.addAttribute("categoryDaos", categoryDaos);
        model.addAttribute("productDao", productDao);
        return "admin/product/add-product";
    }

    @PostMapping("/add-product")
    public String addNewProductPost(Model model, @ModelAttribute(name = "productDao") ProductDao productDao,
                                    @RequestParam("pictureFile") MultipartFile pictureFile) {

        if(!checkUser()){
            model.addAttribute("userDao", new UserDao());
            return "client/login";
        }
        if (pictureFile.isEmpty()) {
            model.addAttribute("error", "Please upload picture file");
            return "redirect:/admin/product/add-product";
        }

        String fileName = StringUtils.cleanPath(pictureFile.getOriginalFilename());

        String UPLOAD_DIR = "D:\\File Java\\src\\Term_2_2023_2024\\BTL_TMDT\\src\\main\\resources\\static\\image\\";

        try {
            Path path = Paths.get(UPLOAD_DIR + fileName);
            Files.copy(pictureFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        productDao.setProdImg("\\image\\" + fileName);

        productService.createProduct(productDao.toModel());
        return "redirect:/admin/product";
    }

    @GetMapping("/edit-product/{id}")
    public String editProductGet(@PathVariable String id, Model model){
        if(!checkUser()){
            model.addAttribute("userDao", new UserDao());
            return "client/login";
        }
        ProductDao productDao = productService.getProductById(id).toDao();
        List<CategoryDao> categoryDaos = categoryService.getCategories().stream().map(Category::toDao).collect(Collectors.toList());
        model.addAttribute("productDao", productDao);
        model.addAttribute("categoryDaos", categoryDaos);
        return "admin/product/edit-product";
    }

    @GetMapping("/search")
    public String searchProd(@PathParam(value = "productName") String productname, Model model){
        if(!checkUser()){
            model.addAttribute("userDao", new UserDao());
            return "client/login";
        }
        List<ProductDao> productDaoListByName = productService.findProductByName(productname).stream().map(Product::toDao).toList();
        model.addAttribute("productDaos", productDaoListByName);
        return "admin/product/products";
    }

    @PostMapping("/edit-product/{id}")
    public String editProductPost(Model model, @PathVariable(name = "id") String id,
                                  @ModelAttribute(name = "productDao") ProductDao productDao,
                                  @RequestParam("pictureFile") MultipartFile pictureFile) {

        if (pictureFile.isEmpty()) {
            Product product = productService.getProductById(id);
            productDao.setProdImg(product.getProdImg());
        }

        else {
            String fileName = StringUtils.cleanPath(pictureFile.getOriginalFilename());


            String UPLOAD_DIR = "D:\\File Java\\src\\Term_2_2023_2024\\BTL_TMDT\\src\\main\\resources\\static\\image\\";

            try {
                Path path = Paths.get(UPLOAD_DIR + fileName);
//                Files.copy(pictureFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                e.printStackTrace();
            }

            productDao.setProdImg("\\image\\" + fileName);
        }

        productService.editProduct(productDao.toModel(), id);
        return "redirect:/admin/product";
    }

    @GetMapping("/delete-product/{id}")
    public String deleteProduct(@PathVariable(name = "id") String id, Model model){
        if(!checkUser()){
            model.addAttribute("userDao", new UserDao());
            return "client/login";
        }
        Product productToDelete = productService.getProductById(id);
        productService.deleteProductById(productToDelete);
//        model.addAttribute("products", productToDelete);
        return "redirect:/admin/product";
    }
    @PostMapping("search-product/{name}")
    public String searchProductByName(@PathVariable String name, Model model){
        List<ProductDao> productByNameList = productService.getProducts().stream().map(Product::toDao).toList()
                        .stream().filter(e -> e.getProdName().toLowerCase().contains(name.toLowerCase()))
                        .collect(Collectors.toList());

        model.addAttribute("productByNameList", productByNameList);
        return "admin/product/products";
    }

    @GetMapping("/products-category")
    public String getProductByCategory(@RequestBody CategoryDao categoryDao, Model model){
        if(!checkUser()){
            model.addAttribute("userDao", new UserDao());
            return "client/login";
        }
        List<ProductDao> productDaoList = productService.getProducts().stream()
                .map(Product::toDao).toList()
                .stream().filter(e -> e.getCategoryDao().equals(categoryDao)).toList();
        model.addAttribute("products", productDaoList);

        return "admin/product/products";
    }
}
