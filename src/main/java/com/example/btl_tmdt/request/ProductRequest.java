package com.example.btl_tmdt.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest implements Serializable {
//    @Serial
//    private static final long serialVersionUID = 1L;
    private Integer prodId;
    private String prodName;
    private String prodDescription;
    private Float prodPrice;
    private String prodNsx;
    private String prodCategory;
    private String prodImg;
}
