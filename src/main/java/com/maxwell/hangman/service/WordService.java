package com.maxwell.hangman.service;

import java.util.List;

import com.maxwell.hangman.entity.Word;

public interface WordService {
	
	List<Word> findAll();
	
	List<Word> findByCategoryId(Long id);
	
	Word addWord(Word word);
	
	Word updateWord(Word word);
	
}
