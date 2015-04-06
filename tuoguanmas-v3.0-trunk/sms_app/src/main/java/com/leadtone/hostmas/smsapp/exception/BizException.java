package com.leadtone.hostmas.smsapp.exception;


public class BizException extends RuntimeException {

	private static final long serialVersionUID = -8590960959405308426L;

	public BizException() {
		super();
	}

	public BizException(String message, Throwable cause) {
		super(message, cause);
	}

	public BizException(String message) {
		super(message);
	}

	public BizException(Throwable cause) {
		super(cause);
	}

}
