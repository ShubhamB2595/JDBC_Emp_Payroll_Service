package com.exception;

public class CustomException extends Exception {

	exceptionType type;

	public CustomException(String message, exceptionType type) {

		super(message);
		this.type = type;
	}

	public enum exceptionType {
		CHECK_THE_QUERY;
	}
}
