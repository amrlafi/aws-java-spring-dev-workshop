package com.amazonaws.lambda.io;

public class RekoEventOutput {
	private String text;
	private String error_message;

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getError_message() {
		return error_message;
	}
	public void setError_message(String error_message) {
		this.error_message = error_message;
	}
}