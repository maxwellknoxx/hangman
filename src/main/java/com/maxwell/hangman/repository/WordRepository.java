package com.maxwell.hangman.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maxwell.hangman.entity.Word;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {
	
	List<Word> findAll();
	
	List<Word> findByCategoryId(Long id);

}
