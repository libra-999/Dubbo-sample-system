package org.example.bookservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.example.bookservice.mapper.UserMapper;
import org.example.bookservice.provider.UserProvider;
import org.example.commonservice.core.entity.UserEntity;
import org.example.commonservice.filter.PaginationQuery;
import org.example.commonservice.filter.Paging;

@Slf4j
@RequiredArgsConstructor
@DubboService
public class UserServiceFacade implements UserProvider {

    private final UserMapper userMapper;

    @Override
    public Paging<UserEntity> list(PaginationQuery query) {
        return null;
    }

    @Override
    public UserEntity view(Integer id) {
        return null;
    }

    @Override
    public UserEntity save(UserEntity entity) {
        return null;
    }

    @Override
    public UserEntity update(UserEntity entity, Integer id) {
        return null;
    }

    @Override
    public void delete(Integer entity) {

    }

    @Override
    public void deletedAll() {

    }
}
