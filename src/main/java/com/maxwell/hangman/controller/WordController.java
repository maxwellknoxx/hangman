package com.maxwell.hangman.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

@CrossOrigin(origins = "*")
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
			response = responseUtils.setMessages(response, "Resources have been found", "WordController", true);
		} catch (Exception e) {
			return responseUtils.setExceptionMessage(response, e, "WordController");
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
			response = responseUtils.setMessages(response, "Resources have been found", "WordController", true);
		} catch (Exception e) {
			return responseUtils.setExceptionMessage(response, e, "WordController");
		}

		return ResponseEntity.ok(response);
	}

	@GetMapping(path = "/api/word/play/{id}")
	public ResponseEntity<Response<PlayingWord>> play(@PathVariable(name = "id") Long id) {
		Response<PlayingWord> response = new Response<>();
		List<Word> listWords = new ArrayList<>();
		Word word = new Word();
		PlayingWord playingWord = new PlayingWord();

		try {
			listWords = service.findByCategoryId(id);
			int randomIndex = (int) (Math.random() * listWords.size());
			word = listWords.get(randomIndex);
			playingWord.setPlayingWord(word.getWord());
			playingWord.setCurrentWord(hiddeWord(playingWord.getPlayingWord()));
			playingWord.setStatus(false);
			playingWord.setWordCompleted(false);
			response.setData(playingWord);
			response = responseUtils.setMessages(response, "Resources have been found", "WordController", true);
		} catch (Exception e) {
			return responseUtils.setExceptionMessage(response, e, "WordController");
		}

		return ResponseEntity.ok(response);
	}

	public String hiddeWord(String word) {
		String currentWordTemporary = "";
		for (int i = 0; i < word.length(); i++) {
			currentWordTemporary = currentWordTemporary + "-";
		}
		return currentWordTemporary;
	}

	@PostMapping(path = "/api/word/guessWord")
	public ResponseEntity<Response<PlayingWord>> GuessWord(@Valid @RequestBody PlayingWord playingWord) {
		Response<PlayingWord> response = new Response<>();

		if (isCorrectWord(playingWord)) {
			playingWord.setWordCompleted(true);
			response = responseUtils.setMessages(response, "You are right, the word is " + playingWord.getPlayingWord(),
					"WordController", true);
		} else {
			playingWord.setWordCompleted(false);
			response = responseUtils.setMessages(response, "You are wrong", "WordController", false);
		}

		response.setData(playingWord);

		return ResponseEntity.ok(response);
	}

	public Boolean isCorrectWord(PlayingWord playingWord) {
		if (playingWord.getPlayingWord().equals(playingWord.getGuessWord().toUpperCase())) {
			playingWord.setWordCompleted(true);
			return true;
		}
		playingWord.setWordCompleted(false);
		return false;
	}

	@PostMapping(path = "/api/word/verifyLetter")
	public ResponseEntity<Response<PlayingWord>> verifyLetter(@Valid @RequestBody PlayingWord playingWord) {
		Response<PlayingWord> response = new Response<>();

		if (validatePlayedLetter(playingWord)) {
			playingWord = completeWord(playingWord);
			response = responseUtils.setMessages(response, "The word has the letter " + playingWord.getLetter(),
					"WordController", true);
		} else {
			response = responseUtils.setMessages(response,
					"The word does not have the letter " + playingWord.getLetter(), "WordController", false);
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
		String currentWord = playingWord.getCurrentWord();
		char character = playingWord.getLetter().charAt(0);
		for (int i = 0; i < playingWord.getPlayingWord().length(); i++) {
			if (playingWord.getPlayingWord().charAt(i) == character) {
				currentWord = currentWord.substring(0, i) + character + currentWord.substring(i + 1);
			}
		}

		playingWord.setCurrentWord(currentWord);

		return playingWord;
	}

}
