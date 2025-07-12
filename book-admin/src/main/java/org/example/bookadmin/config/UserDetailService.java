package org.example.bookadmin.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookadmin.mapper.AuthMapper;
import org.example.commonservice.core.entity.UserEntity;
import org.example.commonservice.exception.UserException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserDetailService implements UserDetailsService {

    private final AuthMapper repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = repository.findByUsername(username);
        if (userEntity == null) {
            throw UserException.notFoundUsername();
        }
        return UserDetail.build(userEntity);
    }
}
