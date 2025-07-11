package org.example.bookadmin.mapper;

import org.example.commonservice.core.entity.UserEntity;

public interface AuthMapper {

    UserEntity findByUsername(String username);

    UserEntity findByEmail(String email);

    UserEntity findById(Integer id);

    UserEntity save(UserEntity user);

}
