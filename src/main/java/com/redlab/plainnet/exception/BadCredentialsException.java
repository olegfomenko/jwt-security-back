package com.redlab.plainnet.exception;

public class BadCredentialsException extends CredentialsException {
    public BadCredentialsException() {
        super("Bad credentials!");
    }
}
