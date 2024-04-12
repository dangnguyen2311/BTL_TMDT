package com.example.btl_tmdt.service;

import com.example.btl_tmdt.model.Product;
import com.example.btl_tmdt.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepo prodRepo;

    public void saveProd(Product product){
        prodRepo.save(product);
    }

    public List<Product> getProducts(){
        return prodRepo.findAll();

    }

//    public Optional<Product> getProductById(Integer id){
//        return prodRepo.findById(id);
//    }

    public Product getProductById(Integer proId){
        return prodRepo.getProductByProdId(proId);
    }

    public boolean isExist(Product product){
        return prodRepo.existsById(product.getProdId());
    }

    public void deleteProductById(Integer id){
        prodRepo.deleteById(id);

    }
    public void deleteProductById(Product product){
        prodRepo.deleteById(product.getProdId());
    }
}
