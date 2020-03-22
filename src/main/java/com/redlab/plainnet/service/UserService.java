package com.redlab.plainnet.service;

import com.redlab.plainnet.entity.UserEntity;
import com.redlab.plainnet.exception.CredentialsException;

public interface UserService {
    UserEntity findByUsername(String username);
    UserEntity save(UserEntity user) throws CredentialsException;
    UserEntity save(String username, String password) throws CredentialsException;
    boolean isExists(String username);
}
