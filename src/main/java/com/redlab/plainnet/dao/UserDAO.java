package com.redlab.plainnet.dao;

import com.redlab.plainnet.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
}
