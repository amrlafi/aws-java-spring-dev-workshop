package com.amazonaws.lambda.io;

public class DDBEventOutput {


	private String result;
	private String message;

  public DDBEventOutput() {
  }
  
  public DDBEventOutput(String message) {
      setMessage(message);
  }
  
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}