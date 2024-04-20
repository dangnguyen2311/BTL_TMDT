package com.example.btl_tmdt.repository;

import com.example.btl_tmdt.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryRepo extends JpaRepository<Category, String> {
//    Category getCategoriesByCategoryName(String category_name);
    Category findCategoryByCategoryName(String categoryName);

}
