package com.maxwell.hangman.response;

import org.springframework.http.ResponseEntity;

import com.maxwell.hangman.utils.ApplicationLogUtils;


public class ResponseUtils {
	
	ApplicationLogUtils log = new ApplicationLogUtils();

	public <T> Response<T> setMessages(Response<T> response, String message, String currentClass, Boolean status) {
		response.setMessage(message);
		response.setStatus(status);
		log.generateLog(message, currentClass);
		return response;
	}

	public <T> ResponseEntity<Response<T>> setExceptionMessage(Response<T> response, Exception e, String currentClass) {
		e.printStackTrace();
		response.getErrors().add(e.getCause().toString());
		response.setStatus(false);
		log.generateLog(e.getCause().toString(), currentClass);
		return ResponseEntity.badRequest().body(response);
	}

}
