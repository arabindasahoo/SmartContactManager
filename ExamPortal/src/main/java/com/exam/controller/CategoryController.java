package com.exam.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.model.exam.Category;
import com.exam.service.CategoryService;

@RestController
@CrossOrigin("*")
@RequestMapping("/category")
public class CategoryController {
	

	
	@Autowired
	private CategoryService categoryService;
	
	//Add Category
	
	@PostMapping("/")
	public ResponseEntity<?> addCategory(@RequestBody Category category)
	{
		Category addCategory = categoryService.addCategory(category);
		return ResponseEntity.ok(addCategory);
	}
	
	//Get Category
	
	@GetMapping("/{categoryId}")
	public Category getCategory(@PathVariable("categoryId")Long categoryId) {
		
		return categoryService.getCotegory(categoryId);
	}
	
	//Get All categories
	
	@GetMapping("/")
	public ResponseEntity<?> getAllCategory()
	{
		Set<Category> categories = categoryService.getCategories();
		return ResponseEntity.ok(categories);
	}
	
	//Update Category
	@PutMapping("/")
	public Category updateCategory(@RequestBody Category category)
	{
		return categoryService.updateCategory(category);
	}

	//Delete category
	
	
	@DeleteMapping("/{categoryId}")
	public void deleteCategory(@PathVariable("categoryId")Long categoryId)
	{
		categoryService.deleteCategory(categoryId);
	}
	
}
