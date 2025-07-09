package org.example.bookservice.service;

import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;
import org.example.bookservice.mapper.UserMapper;
import org.example.bookservice.provider.AuthProvider;
import org.example.commonservice.core.entity.UserEntity;
import org.example.commonservice.exception.UserException;

import java.util.Objects;

@DubboService
@RequiredArgsConstructor
public class AuthServiceFacade implements AuthProvider {

    private final UserMapper mapper;

    @Override
    public UserEntity findByUsername(String username) {
        if (Objects.isNull(mapper.findUserName(username))) {
            throw UserException.notFoundUsername();
        }
        return mapper.findUserName(username);
    }
}
