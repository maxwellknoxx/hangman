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
import com.maxwell.hangman.model.PlayingWord;
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
			listWords.forEach(word -> System.out.println(word.getWord()));
			response.setListData(listWords);
			response = responseUtils.setMessages(response, "Resources have been found", true);
		} catch (Exception e) {
			return responseUtils.setExceptionMessage(response, e);
		}

		return ResponseEntity.ok(response);
	}

	@GetMapping(path = "/api/word/play/{id}")
	public ResponseEntity<Response<Word>> play(@PathVariable(name = "id") Long id) {
		Response<Word> response = new Response<>();
		List<Word> listWords = new ArrayList<>();
		Word word = new Word();

		try {
			listWords = service.findByCategoryId(id);
			int randomIndex = (int) (Math.random() * listWords.size());
			word = listWords.get(randomIndex);
			response.setData(word);
			response = responseUtils.setMessages(response, "Resources have been found", true);
		} catch (Exception e) {
			return responseUtils.setExceptionMessage(response, e);
		}

		return ResponseEntity.ok(response);
	}
	
	@PostMapping(path = "/api/word/GuessWord")
	public ResponseEntity<Response<PlayingWord>> GuessWord(@Valid @RequestBody PlayingWord playingWord) {
		Response<PlayingWord> response = new Response<>();
		
		if(isCorrectWord(playingWord)) {
			playingWord.setStatus(true);
			response = responseUtils.setMessages(response, "You are right, the word is " + playingWord.getPlayingWord(), true);
		}  else  {
			playingWord.setStatus(false);
			response = responseUtils.setMessages(response, "You are wrong", false);
		}
		
		response.setData(playingWord);
		
		return ResponseEntity.ok(response);
	}
	
	public Boolean isCorrectWord(PlayingWord playingWord) {
		if(playingWord.getPlayingWord().equals(playingWord.getGuessWord().toUpperCase())) {
			return true;
		}
		
		return false;
	}

	@PostMapping(path = "/api/word/verifyLetter")
	public ResponseEntity<Response<PlayingWord>> verifyLetter(@Valid @RequestBody PlayingWord playingWord) {
		Response<PlayingWord> response = new Response<>();
		
		if(validatePlayedLetter(playingWord)) {
			playingWord = completeWord(playingWord);
			response = responseUtils.setMessages(response, "The word has the letter " + playingWord.getLetter(), true);
		}  else  {
			response = responseUtils.setMessages(response, "The word does not have the letter " + playingWord.getLetter(), false);
		}
		
		response.setData(playingWord);
		
		return ResponseEntity.ok(response);
	}

	public Boolean validatePlayedLetter(PlayingWord playingWord) {
		if (playingWord.getPlayingWord().contains(playingWord.getLetter().toUpperCase())) {
			playingWord.setStatus(true);
			return true;
		}
		playingWord.setStatus(false);
		return false;
	}
	
	public PlayingWord completeWord(PlayingWord playingWord) {
		int position = playingWord.getPlayingWord().indexOf(playingWord.getLetter());
		
		String word = playingWord.getCurrentWord().substring(0, position) + playingWord.getLetter() + playingWord.getCurrentWord().substring(position + 1);
		playingWord.setCurrentWord(word);
		
		return playingWord;
	}

	// remove
	@PostMapping(path = "/api/word/addWord")
	public ResponseEntity<Response<Word>> addWord(@Valid @RequestBody Word word, BindingResult result) {
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

	// remove
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
