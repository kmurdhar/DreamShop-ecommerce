package com.dailycodework.dreamshops.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.dailycodework.dreamshops.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
		
	Category findByName(String name);
	Category findBySubCategoryName(String subCategoryName);
	Category findByEndCategoryName(String endCategoryName);
	Category findByNameAndSubCategoryNameAndEndCategoryName(String name,String subCategoryName,String endCategoryName);
  boolean existsBySubCategoryName(String subCategoryName);
  boolean existsByName(String name);
  boolean existsByEndCategoryName(String endCategoryName);
  boolean existsBySubCategoryNameAndEndCategoryName(String subCategoryName,String endCategoryName);
  boolean existsByNameAndSubCategoryNameAndEndCategoryName(String name,String subCategoryName,String endCategoryName);
}
