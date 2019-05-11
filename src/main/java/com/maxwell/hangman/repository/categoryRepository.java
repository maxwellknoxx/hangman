package com.maxwell.hangman.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maxwell.hangman.entity.Category;

@Repository
public interface categoryRepository extends JpaRepository<Category, Long> {
	
	List<Category> findAll();
	
	Optional<Category> findById(Long id);

}
