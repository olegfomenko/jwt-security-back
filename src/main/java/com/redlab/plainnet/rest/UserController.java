package com.redlab.plainnet.rest;

import com.redlab.plainnet.entity.User;
import com.redlab.plainnet.exception.CredentialsException;
import com.redlab.plainnet.service.TokenService;
import com.redlab.plainnet.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

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
    public ResponseEntity registration(@RequestBody User user) {
        try {
            userService.save(user);
            return ResponseEntity.ok().build();
        } catch (CredentialsException e) {
            log.error(e.getMessage());
            return new ResponseEntity(Collections.singletonMap("msg", e.getMessage()), HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody User user) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        user = userService.findByUsername(user.getUsername());
        String token = tokenService.createToken(user.getUsername(), user.getRoles());
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public ResponseEntity check() {
        return ResponseEntity.ok().build();
    }
}
