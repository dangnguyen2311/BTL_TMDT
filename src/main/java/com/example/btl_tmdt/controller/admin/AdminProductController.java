package com.example.btl_tmdt.controller.admin;

import com.example.btl_tmdt.dao.CategoryDao;
import com.example.btl_tmdt.dao.ProductDao;
import com.example.btl_tmdt.model.Category;
import com.example.btl_tmdt.model.Product;
import com.example.btl_tmdt.service.CategoryService;
import com.example.btl_tmdt.service.ProductService;
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

    @GetMapping("")
    public String getProducts(Model model){
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
        ProductDao productDao = productService.getProductById(id).toDao();
        List<CategoryDao> categoryDaos = categoryService.getCategories().stream().map(Category::toDao).collect(Collectors.toList());
        model.addAttribute("productDao", productDao);
        model.addAttribute("categoryDaos", categoryDaos);
        return "admin/product/edit-product";
    }

    @PostMapping("/edit-product/{id}")
    public String editProductPost(@ModelAttribute ProductDao productDao, Model model, @PathVariable String id){
//        productService.saveProd(productDao.toModel());
        productService.updateProdByProdId(id, productDao.toModel());

        return "admin/product/products";
    }

    @GetMapping("/delete-product/{id}")
    public String deleteProduct(@PathVariable(name = "id") String id, Model model){
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
        List<ProductDao> productDaoList = productService.getProducts().stream()
                .map(Product::toDao).toList()
                .stream().filter(e -> e.getCategoryDao().equals(categoryDao)).toList();
        model.addAttribute("products", productDaoList);

        return "admin/product/products";
    }
}
