package com.github.charlesluxinger.api.exception;

public class NotHTMLPageException extends RuntimeException {
	public NotHTMLPageException(String url, Throwable cause) {
		super("Some wrong with url: " + url, cause);
	}
}
