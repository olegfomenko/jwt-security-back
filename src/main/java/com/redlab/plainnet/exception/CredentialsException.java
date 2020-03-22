package com.redlab.plainnet.exception;

public class CredentialsException extends Exception {

    public CredentialsException() {
    }

    public CredentialsException(String message) {
        super(message);
    }

    public CredentialsException(String message, Throwable cause) {
        super(message, cause);
    }

    public CredentialsException(Throwable cause) {
        super(cause);
    }

    public CredentialsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
