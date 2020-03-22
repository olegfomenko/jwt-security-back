package com.redlab.plainnet.security;

import com.redlab.plainnet.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class JwtFilter extends GenericFilterBean {
    private final TokenService tokenService;

    @Autowired
    public JwtFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = tokenService.resolveToken((HttpServletRequest) request);
        logger.info("Token: " + token);

        if(token != null && tokenService.validateToken(token)) {
            Authentication authentication = tokenService.getAuthentication(token);

            logger.info("authentication: " + authentication.getName() + " " + authentication.getCredentials().toString());

            if(authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(request, response);
    }
}
