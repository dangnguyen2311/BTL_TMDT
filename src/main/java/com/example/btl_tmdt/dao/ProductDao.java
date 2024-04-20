package com.example.btl_tmdt.dao;

import com.example.btl_tmdt.model.Category;
import com.example.btl_tmdt.model.Product;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDao {
    private String prodId;
    private String prodName;
    private String prodDescription;
    private Float prodPrice;
    private String prodNsx;

    private CategoryDao categoryDao;

    private String prodImg;

    public Product toModel(){
        return new Product(prodId, prodName, prodDescription, prodPrice, prodNsx, categoryDao.toModel(), prodImg);
    }
}
