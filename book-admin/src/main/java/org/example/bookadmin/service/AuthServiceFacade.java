package org.example.bookadmin.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.example.bookadmin.config.UserDetail;
import org.example.bookadmin.mapper.AuthMapper;
import org.example.bookadmin.model.Login;
import org.example.bookadmin.model.Register;
import org.example.bookadmin.provider.AuthProvider;
import org.example.commonservice.core.entity.UserEntity;
import org.example.commonservice.utils.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.example.commonservice.utils.response.ControllerHandler.toJsonString;

@Slf4j
@DubboService
@RequiredArgsConstructor
public class AuthServiceFacade implements AuthProvider {

    private final AuthMapper mapper;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @SneakyThrows
    @Transactional
    @Override
    public Map<String, String> login(Login request) {

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtil.generateJwtToken(request.getUsername(), List.of("admin"));
        UserDetail userDetails = (UserDetail) authentication.getPrincipal();

        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        map.put("user", toJsonString(userDetails));
        return map;
    }

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
    public UserEntity register(Register request) {
        return null;
    }
}
