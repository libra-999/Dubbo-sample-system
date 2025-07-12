package org.example.bookadmin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.example.commonservice.core.entity.UserEntity;

@Mapper
public interface AuthMapper {

    UserEntity findByUsername(@Param("username") String username);

    UserEntity findByEmail(String email);

    UserEntity findById(Integer id);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    int save(UserEntity user);

}
