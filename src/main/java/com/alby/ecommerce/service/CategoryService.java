package com.alby.ecommerce.service;

import com.alby.ecommerce.model.Category;

import java.util.List;


public interface CategoryService {
    Category saveCategory(Category category);
    Boolean existCategory(String name);
    Boolean deleteCategory(int id);
    List<Category> GET_CATEGORY_LIST();
    Category getCategoryById(int id);

}
