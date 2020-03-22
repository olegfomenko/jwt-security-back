package com.redlab.plainnet.service;

import com.redlab.plainnet.entity.User;
import com.redlab.plainnet.exception.CredentialsException;
import com.redlab.plainnet.exception.UserExistsException;

public interface UserService {
    User findByUsername(String username);
    User save(User user) throws CredentialsException;
    boolean isExists(String username);
}
