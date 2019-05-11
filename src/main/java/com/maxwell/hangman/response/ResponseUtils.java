package com.maxwell.hangman.response;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public class ResponseUtils {

	public <T> ResponseEntity<Response<T>> validate(Response<T> response, BindingResult result) {
		if (result.hasErrors()) {
			result.getAllErrors().forEach(errors -> response.getErrors().add(errors.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		return null;
	}

	public <T> Response<T> setMessages(Response<T> response, String message, Boolean status) {
		response.setMessage(message);
		response.setStatus(status);
		return response;
	}

	public <T> ResponseEntity<Response<T>> setExceptionMessage(Response<T> response, Exception e) {
		e.printStackTrace();
		response.getErrors().add(e.getCause().toString());
		response.setStatus(false);
		return ResponseEntity.badRequest().body(response);
	}

}
