package com.example.btl_tmdt.repository;

import com.example.btl_tmdt.dao.ProductDao;
import com.example.btl_tmdt.model.Category;
import com.example.btl_tmdt.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
    Product getProductByProdId(String id);  //tuwj may sinh ra

    Optional<Product> findProductByProdName(String prodName);


    List<Product> getProductsByCategory(Category category);

    void deleteByProdId(String prodId);
//    void updateProductByProdId(String prodId, Product product);
}
