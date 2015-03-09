package io.github.apemanzilla.kwallet;

public class BadLoginException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2396378534026314589L;

	public BadLoginException() {
		super("Bad login");
	}
}
