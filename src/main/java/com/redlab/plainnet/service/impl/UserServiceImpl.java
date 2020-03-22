package com.redlab.plainnet.service.impl;

import com.redlab.plainnet.dao.UserDAO;
import com.redlab.plainnet.entity.Role;
import com.redlab.plainnet.entity.UserEntity;
import com.redlab.plainnet.exception.BadCredentialsException;
import com.redlab.plainnet.exception.CredentialsException;
import com.redlab.plainnet.exception.UserExistsException;
import com.redlab.plainnet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, BCryptPasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    public UserEntity save(UserEntity user) throws CredentialsException {
        if(user.getUsername() == null || user.getPassword() == null) {
            throw new BadCredentialsException();
        }

        user.getRoles().add(Role.ROLE_USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (isExists(user.getUsername())) {
            throw new UserExistsException();
        } else {
            userDAO.save(user);
            return user;
        }
    }

    @Override
    public UserEntity save(String username, String password) throws CredentialsException {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword(password);
        return save(userEntity);
    }

    @Override
    public boolean isExists(String username) {
        return userDAO.findByUsername(username) != null;
    }
}
