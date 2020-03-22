package com.redlab.plainnet.exception;

public class UserExistsException extends CredentialsException {
    public UserExistsException() {
        super("User already exists!");
    }
}
