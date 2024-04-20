package com.example.btl_tmdt.dao;

import com.example.btl_tmdt.model.Category;
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

    public Category toModel() {
        return new Category(category_id, category_name, category_description);
    }
}
