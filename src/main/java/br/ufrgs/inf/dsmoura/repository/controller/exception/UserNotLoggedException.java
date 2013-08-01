package br.ufrgs.inf.dsmoura.repository.controller.exception;

public class UserNotLoggedException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public UserNotLoggedException() {
		super();
	}
	
	public UserNotLoggedException(String message) {
		super(message);
	}
}
