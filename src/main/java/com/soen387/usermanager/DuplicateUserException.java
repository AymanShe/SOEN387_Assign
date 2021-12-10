package com.soen387.usermanager;

public class DuplicateUserException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DuplicateUserException() {

	}

	public DuplicateUserException(String message) {
		super(message);
	}

}
