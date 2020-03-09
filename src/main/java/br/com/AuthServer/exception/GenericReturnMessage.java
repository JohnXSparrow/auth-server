package br.com.AuthServer.exception;

public class GenericReturnMessage {

	private String message;
	private String code;

	public String getMessage() {
		return message;
	}

	public String getCode() {
		return code;
	}

	public GenericReturnMessage(String code, String message) {
		this.code = code;
		this.message = message;
	}
}
