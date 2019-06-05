package com.maxwell.hangman.model;

public class PlayingWord {

	private String playingWord;
	private String letter;
	private String currentWord;
	private String guessWord;
	private Boolean status;
	private Boolean wordCompleted;

	public String getPlayingWord() {
		return playingWord;
	}

	public void setPlayingWord(String playingWord) {
		this.playingWord = playingWord;
	}

	public String getLetter() {
		return letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

	public String getCurrentWord() {
		return currentWord;
	}

	public void setCurrentWord(String currentWord) {
		this.currentWord = currentWord;
	}

	public String getGuessWord() {
		return guessWord;
	}

	public void setGuessWord(String guessWord) {
		this.guessWord = guessWord;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Boolean getWordCompleted() {
		return wordCompleted;
	}

	public void setWordCompleted(Boolean wordCompleted) {
		this.wordCompleted = wordCompleted;
	}

}
