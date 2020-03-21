package com.redlab.plainnet.service;

import com.redlab.plainnet.entity.User;
import com.redlab.plainnet.exception.UserExistsException;

public interface UserService {
    User findByUsername(String username);
    User save(User user) throws UserExistsException;
    boolean isExists(String username);
}
