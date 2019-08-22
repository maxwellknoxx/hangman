package com.maxwell.hangman.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

	@PostMapping(path = "/api/v1/word/words")
	public ResponseEntity<?> insert(@Valid @RequestBody Word word) {
		Response<Word> response = new Response<>();

		try {
			word = service.addWord(word);
			response.setData(word);
			response = responseUtils.setMessages(response, "Word " + word.getWord() + " has been added",
					"WordController", true);
		} catch (Exception e) {
			return new ResponseEntity<Boolean>(false, HttpStatus.OK);
		}

		return ResponseEntity.ok(response);
	}

	@GetMapping(path = "/api/v1/word/words")
	public ResponseEntity<?> findAll() {
		Response<Word> response = new Response<>();
		List<Word> listWords = new ArrayList<>();

		try {
			listWords = service.findAll();
			response.setListData(listWords);
			response = responseUtils.setMessages(response, "Resources have been found", "WordController", true);
		} catch (Exception e) {
			return new ResponseEntity<Boolean>(false, HttpStatus.OK);
		}

		return ResponseEntity.ok(response);
	}

	@GetMapping(path = "/api/v1/word/wordsByCategoryId/{id}")
	public ResponseEntity<?> findAllByCategoryId(@PathVariable(name = "id") Long id) {
		Response<Word> response = new Response<>();
		List<Word> listWords = new ArrayList<>();

		try {
			listWords = service.findByCategoryId(id);
			response.setListData(listWords);
			response = responseUtils.setMessages(response, "Resources have been found", "WordController", true);
		} catch (Exception e) {
			return new ResponseEntity<Boolean>(false, HttpStatus.OK);
		}

		return ResponseEntity.ok(response);
	}

	@GetMapping(path = "/api/v1/word/play/{id}")
	public ResponseEntity<?> play(@PathVariable(name = "id") Long id) {
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
			response = responseUtils.setMessages(response, "Playing word => " + playingWord.getPlayingWord(),
					"WordController", true);
		} catch (Exception e) {
			return new ResponseEntity<Boolean>(false, HttpStatus.OK);
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

	@PostMapping(path = "/api/v1/word/guessWord")
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
		if (playingWord.getPlayingWord().toUpperCase().equals(playingWord.getGuessWord().toUpperCase())) {
			playingWord.setWordCompleted(true);
			return true;
		}
		playingWord.setWordCompleted(false);
		return false;
	}

	public Boolean compareCurrentWord(PlayingWord playingWord) {
		if (playingWord.getPlayingWord().toUpperCase().equals(playingWord.getCurrentWord().toUpperCase())) {
			playingWord.setWordCompleted(true);
			return true;
		}
		playingWord.setWordCompleted(false);
		return false;
	}

	@PostMapping(path = "/api/v1/word/verifyLetter")
	public ResponseEntity<Response<PlayingWord>> verifyLetter(@Valid @RequestBody PlayingWord playingWord) {
		Response<PlayingWord> response = new Response<>();

		if (validatePlayedLetter(playingWord)) {
			playingWord = completeWord(playingWord);
			if (compareCurrentWord(playingWord)) {
				response = responseUtils.setMessages(response,
						"You are right, the word is " + playingWord.getPlayingWord(), "WordController", true);
			} else {
				response = responseUtils.setMessages(response, "The word has the letter " + playingWord.getLetter(),
						"WordController", true);
			}
		} else {
			response = responseUtils.setMessages(response,
					"The word does not have the letter " + playingWord.getLetter(), "WordController", false);
		}

		response.setData(playingWord);

		return ResponseEntity.ok(response);
	}

	public Boolean validatePlayedLetter(PlayingWord playingWord) {
		if (playingWord.getPlayingWord().toUpperCase().contains(playingWord.getLetter().toUpperCase())) {
			playingWord.setStatus(true);
			return true;
		}
		playingWord.setStatus(false);
		return false;
	}

	public PlayingWord completeWord(PlayingWord playingWord) {
		String currentWord = playingWord.getCurrentWord();
		char character = playingWord.getLetter().toUpperCase().charAt(0);
		for (int i = 0; i < playingWord.getPlayingWord().length(); i++) {
			if (playingWord.getPlayingWord().toUpperCase().charAt(i) == character) {
				currentWord = currentWord.substring(0, i) + character + currentWord.substring(i + 1);
				break;
			}
		}

		playingWord.setCurrentWord(currentWord);

		return playingWord;
	}

}
