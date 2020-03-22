package com.redlab.plainnet.security;

import com.redlab.plainnet.entity.User;
import com.redlab.plainnet.service.RoleService;
import com.redlab.plainnet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


public class CustomAuthenticationManager implements AuthenticationManager {
    private final UserService userService;
    private final RoleService roleService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public CustomAuthenticationManager(UserService userService,
                                       RoleService roleService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = passwordEncoder.encode(authentication.getCredentials().toString());

        User user = userService.findByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException("Username: " + username + " not found!");
        }

        if(user.getUsername().equals(username) && user.getPassword().equals(password)) {
            return new UsernamePasswordAuthenticationToken(
                    username,
                    password,
                    roleService.toGrantedAuthorityList(user.getRoles())
            );
        }

        throw new BadCredentialsException("Wrong password");
    }
}
