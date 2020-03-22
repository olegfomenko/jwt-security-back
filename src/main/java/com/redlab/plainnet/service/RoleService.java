package com.redlab.plainnet.service;

import com.redlab.plainnet.entity.Role;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public interface RoleService {
    List<String> toStringList(List<Role> roles);
    List<GrantedAuthority> toGrantedAuthorityList(List<Role> roles);
}
