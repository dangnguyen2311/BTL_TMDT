package com.example.btl_tmdt.dao;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDao {
    private String category_id;
    private String category_name;
    private String category_description;
}
