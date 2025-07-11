package org.example.bookadmin.provider;

import org.example.bookadmin.model.Login;
import org.example.bookadmin.model.Register;
import org.example.commonservice.core.entity.UserEntity;

public interface AuthProvider {

    UserEntity profile(String email);

    String forgotPassword(String email);

    void verify(String email, String code);

    void changePassword(String email, String password, String confirmPassword);

    UserEntity login(Login request);

    UserEntity register(Register request);
}
