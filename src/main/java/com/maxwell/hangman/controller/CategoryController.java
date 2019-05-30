package com.maxwell.hangman.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

	@GetMapping(path = "/api/category/findAll")
	public ResponseEntity<Response<Category>> findAll() {
		Response<Category> response = new Response<>();
		List<Category> listCategories = new ArrayList<>();

		try {
			listCategories = service.findAll();
			response.setListData(listCategories);
			response = responseUtils.setMessages(response, "Resources have been found", true);
		} catch (Exception e) {
			return responseUtils.setExceptionMessage(response, e);
		}

		return ResponseEntity.ok(response);
	}

	@PostMapping(path = "/api/category/addCategory")
	public ResponseEntity<Response<Category>> addCategory(@Valid @RequestBody Category category, BindingResult result) {
		Response<Category> response = new Response<>();
		Category categoryFromDB = new Category();
		
		try {
			categoryFromDB = service.addCategory(category);
			response.setData(categoryFromDB);
			response = responseUtils.setMessages(response, category.getCategoryName() + " has been added", true);
		} catch (Exception e) {
			return responseUtils.setExceptionMessage(response, e);
		}
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(path = "/api/category/findCategoryById/{id}")
	public ResponseEntity<Response<Category>> findAllByCategoryId(@PathVariable(name = "id") Long id) {
		Response<Category> response = new Response<>();
		Category category = new Category();

		try {
			category = service.findCategoryById(id);
			response.setData(category);
			response = responseUtils.setMessages(response, "Resources have been found", true);
		} catch (Exception e) {
			return responseUtils.setExceptionMessage(response, e);
		}

		return ResponseEntity.ok(response);
	}
	
	@PostMapping(path = "/api/category/updateCategory")
	public ResponseEntity<Response<Category>> updateCategory(@Valid @RequestBody Category category, BindingResult result) {
		Response<Category> response = new Response<>();
		Category categoryFromDB = new Category();
		
		try {
			categoryFromDB = service.updateCategory(category);
			response.setData(categoryFromDB);
			response = responseUtils.setMessages(response, category.getCategoryName() + " has been updated", true);
		} catch (Exception e) {
			return responseUtils.setExceptionMessage(response, e);
		}
		
		return ResponseEntity.ok(response);
	}

}
