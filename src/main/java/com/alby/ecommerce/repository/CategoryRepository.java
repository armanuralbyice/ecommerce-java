package com.alby.ecommerce.repository;

import com.alby.ecommerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

     Boolean existsByName(String name);
}
