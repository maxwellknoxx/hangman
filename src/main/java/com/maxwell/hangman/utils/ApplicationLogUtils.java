package com.maxwell.hangman.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ApplicationLogUtils {

	public ApplicationLogUtils() {
	}

	public void generateLog(String message, String currentClass) {
		StringBuilder sb = new StringBuilder();
		sb.append(getCurrentDateAndTime()).append(" [ ").append(message.toUpperCase()).append("] ").append(" [").append(currentClass.toUpperCase())
				.append("] \n");
		System.out.println(sb.toString());
	}

	public String getCurrentDateAndTime() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		return formatter.format(date);
	}

}
