package com.redlab.plainnet.exception;

import org.springframework.security.core.AuthenticationException;

public class TokenExpiredException extends AuthenticationException {
    public TokenExpiredException() {
        super("Token is invalid!");
    }
}
