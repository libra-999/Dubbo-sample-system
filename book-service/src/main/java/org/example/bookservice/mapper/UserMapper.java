package org.example.bookservice.mapper;

import org.example.commonservice.core.entity.UserEntity;

public interface UserMapper {

    UserEntity findUserName(String username);

    UserEntity findAll(UserEntity customer);

    int findById(UserEntity customer);

    UserEntity insert(String username);

    int update(UserEntity customer);

    void delete(UserEntity customer);

}
