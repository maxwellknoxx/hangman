package com.maxwell.hangman.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.maxwell.hangman.exception.ResourceNotFoundException;
import com.maxwell.hangman.entity.Category;
import com.maxwell.hangman.response.Response;
import com.maxwell.hangman.response.ResponseUtils;
import com.maxwell.hangman.serviceImpl.CategoryServiceImpl;

@CrossOrigin(origins = "*")
@RestController
public class CategoryController {

	@Autowired
	private CategoryServiceImpl service;

	ResponseUtils responseUtils = new ResponseUtils();

	@GetMapping(path = "/api/v1/category/helloword")
	public ResponseEntity<?> helloword(){
		return new ResponseEntity<String>("Hello, World", HttpStatus.OK);
	}

	@GetMapping(path = "/api/v1/category/categories")
	public ResponseEntity<Response<Category>> findAll() {
		Response<Category> response = new Response<>();
		List<Category> listCategories = new ArrayList<>();

		try {
			listCategories = service.findAll();
			response.setListData(listCategories);
			response = responseUtils.setMessages(response, "Resources have been found", "CategoryController", true);
		} catch (Exception e) {
			throw new ResourceNotFoundException("Something went wrong! " + e.getMessage());
		}

		return ResponseEntity.ok(response);
	}

	@GetMapping(path = "/api/v1/category/categoriesById/{id}")
	public ResponseEntity<Response<Category>> findAllByCategoryId(@PathVariable(name = "id") Long id) {
		Response<Category> response = new Response<>();
		Category category = new Category();

		try {
			category = service.findCategoryById(id);
			response.setData(category);
			response = responseUtils.setMessages(response, "Resources have been found", "CategoryController", true);
		} catch (Exception e) {
			throw new ResourceNotFoundException("Something went wrong! " + e.getMessage());
		}

		return ResponseEntity.ok(response);
	}

}
