package com.example.btl_tmdt.model;

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
public class Product  implements Serializable{
//    @Serial
//    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "prod_id")
    private Integer prodId;
    @Column(name = "prod_name")
    private String prodName;
    @Column(name = "prod_description")
    private String prodDescription;
    @Column(name = "prod_price")
    private Float prodPrice;
    @Column(name = "prod_nsx")
    private String prodNsx;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


    @Column(name = "prod_img")
    private String prodImg;


    public String getProd_price() {
        return String.format("%.2f", this.prodPrice);
    }


}
