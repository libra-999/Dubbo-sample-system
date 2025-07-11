package org.example.bookadmin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.example.bookadmin.mapper.AuthMapper;
import org.example.bookadmin.model.Login;
import org.example.bookadmin.model.Register;
import org.example.bookadmin.provider.AuthProvider;
import org.example.commonservice.core.entity.UserEntity;

@Slf4j
@DubboService
@RequiredArgsConstructor
public class AuthServiceFacade implements AuthProvider {

    private final AuthMapper mapper;

    @Override
    public UserEntity profile(String email) {
        return null;
    }

    @Override
    public void changePassword(String email, String password, String confirmPassword) {

    }

    @Override
    public void verify(String email, String code) {

    }

    @Override
    public String forgotPassword(String email) {
        return null;
    }

    @Override
    public UserEntity login(Login request) {
        return null;
    }

    @Override
    public UserEntity register(Register request) {
        return null;
    }
}
