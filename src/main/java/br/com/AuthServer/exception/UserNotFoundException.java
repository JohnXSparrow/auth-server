package br.com.AuthServer.exception;

import br.com.AuthServer.exception.AuthServerException;

public class UserNotFoundException extends AuthServerException {
	
	private static final long serialVersionUID = -3504917001725795115L;

	public UserNotFoundException(String email) {
        super("User " + email + " not found.");
    }
}
