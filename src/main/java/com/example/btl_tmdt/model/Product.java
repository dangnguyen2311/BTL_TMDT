package com.example.btl_tmdt.model;

import com.example.btl_tmdt.dao.ProductDao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Entity
@Getter
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
//@Getter
//@Setter
public class Product  {
//    @Serial
//    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String prodId;
    private String prodName;
    private String prodDescription;
    @Getter
    private Float prodPrice;
    private String prodNsx;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private String prodImg;


    public String getProdPrice() {
        return String.format("%.2f", this.prodPrice);
    }


    public ProductDao toDao() {
        return new ProductDao(prodId, prodName, prodDescription, prodPrice, prodNsx, category.toDao(), prodImg);
    }
}
