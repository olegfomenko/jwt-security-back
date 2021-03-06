package com.redlab.plainnet.rest;

import com.redlab.plainnet.dto.LoginRequest;
import com.redlab.plainnet.dto.LoginResponse;
import com.redlab.plainnet.entity.UserEntity;
import com.redlab.plainnet.exception.CredentialsException;
import com.redlab.plainnet.service.TokenService;
import com.redlab.plainnet.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager, TokenService tokenService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ResponseEntity registration(@RequestBody LoginRequest request) throws CredentialsException {
        try {
            userService.save(request.getUsername(), request.getPassword());
            return ResponseEntity.ok().build();
        } catch (CredentialsException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserEntity user = userService.findByUsername(request.getUsername());
        String token = tokenService.createToken(user.getUsername(), user.getRoles());
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public ResponseEntity check(Authentication authentication) throws InterruptedException {
        Thread.sleep(1000);
        String user = authentication.getName();
        return ResponseEntity.ok(user);
    }
}
