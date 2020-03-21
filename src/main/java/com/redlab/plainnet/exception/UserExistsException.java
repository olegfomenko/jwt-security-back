package com.redlab.plainnet.exception;

public class UserExistsException extends Exception {
    public UserExistsException() {
        super("User already exists!");
    }
}
