package com.maxwell.hangman.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class WorldController {

	@GetMapping(path = "/api/v1/world/worlds")
	public ResponseEntity<?> allWorlds() {
		return new ResponseEntity<String>("Oi, eu sou Goku", HttpStatus.OK);
	}

}
