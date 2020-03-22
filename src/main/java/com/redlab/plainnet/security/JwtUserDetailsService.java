package com.redlab.plainnet.security;

import com.redlab.plainnet.entity.UserEntity;
import com.redlab.plainnet.service.RoleService;
import com.redlab.plainnet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public JwtUserDetailsService(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws AuthenticationException {
        UserEntity userEntity = userService.findByUsername(username);

        if(userEntity == null) {
            throw new UsernameNotFoundException("Username: " + username + " not found!");
        }

        User user = new User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                roleService.toGrantedAuthorityList(userEntity.getRoles())
        );

        return user;
    }
}
