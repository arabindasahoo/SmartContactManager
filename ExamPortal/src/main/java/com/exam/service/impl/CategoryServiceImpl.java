package com.exam.service.impl;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.model.exam.Category;
import com.exam.repo.CategoryRepository;
import com.exam.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepo;
	
	@Override
	public Category addCategory(Category category) {
		return categoryRepo.save(category);
	}

	@Override
	public Category updateCategory(Category category) {
		return categoryRepo.save(category);
	}

	@Override
	public Set<Category> getCategories() {
		return new LinkedHashSet<>(categoryRepo.findAll());
	}

	@Override
	public Category getCotegory(Long categoryId) {
		return categoryRepo.findById(categoryId).get();
	}

	@Override
	public void deleteCategory(Long cid) {

		Category category = new Category();
		category.setId(cid);
		categoryRepo.delete(category);
	}

}
