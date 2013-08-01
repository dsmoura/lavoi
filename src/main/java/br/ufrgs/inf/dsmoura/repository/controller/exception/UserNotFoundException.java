package br.ufrgs.inf.dsmoura.repository.controller.exception;

public class UserNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public UserNotFoundException() {
		super();
	}
	
	public UserNotFoundException(String message) {
		super(message);
	}
}
