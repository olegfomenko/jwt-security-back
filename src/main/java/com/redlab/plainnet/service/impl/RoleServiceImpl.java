package com.redlab.plainnet.service.impl;

import com.redlab.plainnet.entity.Role;
import com.redlab.plainnet.service.RoleService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Override
    public List<String> toStringList(List<Role> roles) {
        List<String> stringRoles = new ArrayList<>();

        roles.forEach(
                role -> stringRoles.add(role.name())
        );

        return stringRoles;
    }

    @Override
    public List<GrantedAuthority> toGrantedAuthorityList(List<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        roles.forEach(
                role -> authorities.add(new SimpleGrantedAuthority(role.name()))
        );

        return authorities;
    }
}
