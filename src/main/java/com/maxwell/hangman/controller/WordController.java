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

import com.maxwell.hangman.entity.Word;
import com.maxwell.hangman.response.Response;
import com.maxwell.hangman.response.ResponseUtils;
import com.maxwell.hangman.serviceImpl.WordServiceImpl;

@CrossOrigin("*")
@RestController
public class WordController {

	@Autowired
	private WordServiceImpl service;
	
	ResponseUtils responseUtils = new ResponseUtils();

	@GetMapping(path = "/api/word/findAll")
	public ResponseEntity<Response<Word>> findAll() {
		Response<Word> response = new Response<>();
		List<Word> listWords = new ArrayList<>();

		try {
			listWords = service.findAll();
			response.setListData(listWords);
			response = responseUtils.setMessages(response, "Resources have been found", true);
		} catch (Exception e) {
			return responseUtils.setExceptionMessage(response, e);
		}

		return ResponseEntity.ok(response);
	}
	
	@GetMapping(path = "/api/word/findAllByCategoryId/{id}")
	public ResponseEntity<Response<Word>> findAllByCategoryId(@PathVariable(name = "id") Long id) {
		Response<Word> response = new Response<>();
		List<Word> listWords = new ArrayList<>();

		try {
			listWords = service.findByCategoryId(id);
			response.setListData(listWords);
			response = responseUtils.setMessages(response, "Resources have been found", true);
		} catch (Exception e) {
			return responseUtils.setExceptionMessage(response, e);
		}

		return ResponseEntity.ok(response);
	}
	
	@PostMapping(path = "/api/word/addWord")
	public ResponseEntity<Response<Word>> addCategory(@Valid @RequestBody Word word, BindingResult result) {
		Response<Word> response = new Response<>();
		Word wordFromDB = new Word();
		
		try {
			wordFromDB = service.addWord(word);
			response.setData(wordFromDB);
			response = responseUtils.setMessages(response, word.getWord() + " has been added", true);
		} catch (Exception e) {
			return responseUtils.setExceptionMessage(response, e);
		}
		
		return ResponseEntity.ok(response);
	}
	
	@PostMapping(path = "/api/word/updateWord")
	public ResponseEntity<Response<Word>> updateWord(@Valid @RequestBody Word word, BindingResult result) {
		Response<Word> response = new Response<>();
		Word wordFromDB = new Word();
		
		try {
			wordFromDB = service.updateWord(word);
			response.setData(wordFromDB);
			response = responseUtils.setMessages(response, word.getWord() + " has been updated", true);
		} catch (Exception e) {
			return responseUtils.setExceptionMessage(response, e);
		}
		
		return ResponseEntity.ok(response);
	}
	
}
