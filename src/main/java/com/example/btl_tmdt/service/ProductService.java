package com.example.btl_tmdt.service;

import com.example.btl_tmdt.dao.ProductDao;
import com.example.btl_tmdt.model.Category;
import com.example.btl_tmdt.model.Product;
import com.example.btl_tmdt.repository.CategoryRepo;
import com.example.btl_tmdt.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepo prodRepo;
    @Autowired
    CategoryRepo categoryRepo;

    public void saveProd(Product product){
        prodRepo.save(product);
    }

    public void updateProdByProdId(String prodId, Product product){
        prodRepo.updateProductByProdId(prodId, product);
    }


    public List<Product> getProducts(){
        return prodRepo.findAll();

    }

    public void createProduct(Product Product) {
        Category category = categoryRepo.findById(Product.getCategory().getCategoryId()).get();
        Product.setCategory(category);
        prodRepo.save(Product);
    }


    public Product getProductById(String proId){
        return prodRepo.getProductByProdId(proId);
    }


    public void deleteProductById(Product product){
        prodRepo.delete(product);

    }

//    public void deleteProductById(Product product){
//        prodRepo.deleteById(product.getProdId());
//    }

    public Optional<ProductDao> findProductByName(String prodName){
        return prodRepo.findProductByProdName(prodName).map(Product::toDao);
    }
}
