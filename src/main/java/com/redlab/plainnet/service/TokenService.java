package com.redlab.plainnet.service;

import com.redlab.plainnet.entity.Role;
import com.redlab.plainnet.exception.TokenExpiredException;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface TokenService {
    String createToken(String username, List<Role> roles);
    String resolveToken(HttpServletRequest request);
    Authentication getAuthentication(String token);
    boolean validateToken(String token) throws TokenExpiredException;
}
