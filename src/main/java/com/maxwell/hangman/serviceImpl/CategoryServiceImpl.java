package com.maxwell.hangman.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxwell.hangman.entity.Category;
import com.maxwell.hangman.repository.categoryRepository;
import com.maxwell.hangman.service.CategoryService;

@Service
public class CategoryServiceImpl  implements CategoryService {
	
	@Autowired
	private categoryRepository repository;

	@Override
	public List<Category> findAll() {
		return repository.findAll();
	}

	@Override
	public Category addCategory(Category category) {
		return repository.save(category);
	}

	@Override
	public Category updateCategory(Category category) {
		return repository.save(category);
	}
	
	public Category findCategoryById(Long id) {
		return repository.findById(id).orElse(null);
	}

}
