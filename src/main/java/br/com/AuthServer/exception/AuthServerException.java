package br.com.AuthServer.exception;

public class AuthServerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AuthServerException(String msg) {
        super(msg);
    }

    public AuthServerException(String msg, Throwable throwable) {
        super(msg);
    }

    public AuthServerException() {
    }
}
