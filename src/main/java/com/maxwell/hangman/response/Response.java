package com.maxwell.hangman.response;

import java.util.ArrayList;
import java.util.List;

public class Response<T> {

	private T data;
	private List<T> listData;
	private String message;
	private List<String> errors;
	private Boolean status;

	public Response() {
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public List<T> getListData() {
		if (this.listData == null) {
			this.listData = new ArrayList<T>();
		}
		return listData;
	}

	public void setListData(List<T> listData) {
		this.listData = listData;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getErrors() {
		if (this.errors == null) {
			this.errors = new ArrayList<String>();
		}
		return errors;
	}

	public void setErros(List<String> errors) {
		this.errors = errors;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

}
