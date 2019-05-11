package com.maxwell.hangman.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxwell.hangman.entity.Word;
import com.maxwell.hangman.repository.WordRepository;
import com.maxwell.hangman.service.WordService;

@Service
public class WordServiceImpl implements WordService {
	
	@Autowired
	private WordRepository repository;

	@Override
	public List<Word> findAll() {
		return repository.findAll();
	}

	@Override
	public List<Word> findByCategoryId(Long id) {
		return repository.findByCategoryId(id);
	}

	@Override
	public Word addWord(Word word) {
		return repository.save(word);
	}

	@Override
	public Word updateWord(Word word) {
		return repository.save(word);
	}

}
