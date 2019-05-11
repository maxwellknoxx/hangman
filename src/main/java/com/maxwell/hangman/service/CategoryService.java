package com.maxwell.hangman.service;

import java.util.List;

import com.maxwell.hangman.entity.Category;

public interface CategoryService {

	
	List<Category> findAll();
	
	Category addCategory(Category category);
	
	Category updateCategory(Category category);
	
}
