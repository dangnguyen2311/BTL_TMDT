package com.example.btl_tmdt.service;

import com.example.btl_tmdt.dao.CategoryDao;
import com.example.btl_tmdt.model.Category;
import com.example.btl_tmdt.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepo categoryRepo;

    public List<Category> getCategories(){
        return categoryRepo.findAll();
    }
}
