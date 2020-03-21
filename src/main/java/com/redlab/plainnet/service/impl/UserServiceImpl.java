package com.redlab.plainnet.service.impl;

import com.redlab.plainnet.dao.UserDAO;
import com.redlab.plainnet.entity.User;
import com.redlab.plainnet.exception.UserExistsException;
import com.redlab.plainnet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    public User save(User user) throws UserExistsException {
        if (isExists(user.getUsername())) {
            throw new UserExistsException();
        } else {
            userDAO.save(user);
            return user;
        }
    }

    @Override
    public boolean isExists(String username) {
        return userDAO.findByUsername(username) != null;
    }
}
