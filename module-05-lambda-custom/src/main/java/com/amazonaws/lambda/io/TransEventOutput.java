package com.amazonaws.lambda.io;

public class TransEventOutput {
	
	private String translated;
	private String error_message;

	public String getTranslated() {
		return translated;
	}
	public void setTranslated(String translated) {
		this.translated = translated;
	}
	public String getError_message() {
		return error_message;
	}
	public void setError_message(String error_message) {
		this.error_message = error_message;
	}

}
