package com.alby.ecommerce.controller;

import com.alby.ecommerce.model.Category;
import com.alby.ecommerce.service.CategoryService;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product/category")
public class AdminController {
    @Autowired
    CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<String> saveCategory(@RequestBody Category category, HttpSession session) {
        boolean existCategory = categoryService.existCategory(category.getName());
        if (existCategory) {
            session.setAttribute("errMsg", "The category already exists");
            return new ResponseEntity<>("The category already exists", HttpStatus.CONFLICT);
        } else {
            Category savedCategory = categoryService.saveCategory(category);
            if (ObjectUtils.isEmpty(savedCategory)) {
                session.setAttribute("errMsg", "Not saved! Internal server error");
                return new ResponseEntity<>("Not saved! Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                session.setAttribute("sucMsg", "Saved successfully");
                return new ResponseEntity<>("Saved successfully", HttpStatus.CREATED);
            }
        }
    }

    @GetMapping("/all")
    public List<Category> getAllCategory (){
        return categoryService.GET_CATEGORY_LIST();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean>  deleteCategory(@PathVariable("id") int id){
        Boolean deleteCategory = categoryService.deleteCategory(id);
        if(deleteCategory){
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping("/loadCategory/{id}")
    public Category getCategoryById(@PathVariable("id") int id){
        return categoryService.getCategoryById(id);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCategory(@RequestBody Category category){
        Category oldCategory = categoryService.getCategoryById(category.getId());
        if(oldCategory != null){
            oldCategory.setName(category.getName());
            Category updatedCategory = categoryService.saveCategory(oldCategory);
            if(updatedCategory != null){
                return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Category not found", HttpStatus.NOT_FOUND);
        }
    }
}