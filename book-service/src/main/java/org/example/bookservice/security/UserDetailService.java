package org.example.bookservice.security;

import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.example.bookservice.provider.AuthProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {

    @DubboReference
    private AuthProvider repository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        return UserDetail.build(repository.findByUsername(username));
    }
}
