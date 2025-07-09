package org.example.bookservice.provider;

import org.example.commonservice.core.entity.UserEntity;

public interface AuthProvider {

    UserEntity findByUsername(String username);
}
