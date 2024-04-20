package com.example.btl_tmdt.model;

import com.example.btl_tmdt.dao.CategoryDao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String categoryId;
    private String categoryName;
    private String categoryDescription;

    public CategoryDao toDao() {
        return new CategoryDao(categoryId, categoryName, categoryDescription);
    }
}
