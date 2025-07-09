package org.example.bookservice.provider;


import org.example.commonservice.core.entity.UserEntity;
import org.example.commonservice.filter.PaginationQuery;
import org.example.commonservice.filter.Paging;

public interface UserProvider {

    Paging<UserEntity> list(PaginationQuery query);

    UserEntity view(Integer id);

    UserEntity save(UserEntity entity);

    UserEntity update(UserEntity entity, Integer id);

    void delete(Integer entity);

    void deletedAll();
}

