package org.example.apiservice.controller.auth;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.example.apiservice.mapper.AuthControllerMapper;
import org.example.apiservice.model.request.ChangePasswordRQ;
import org.example.apiservice.model.response.AuthResponse;
import org.example.apiservice.model.response.UserDetailResponse;
import org.example.apiservice.model.response.UserResponse;
import org.example.bookadmin.model.Login;
import org.example.bookadmin.model.Register;
import org.example.bookadmin.provider.AuthProvider;
import org.example.commonservice.filter.HttpBodyResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static org.example.commonservice.utils.response.ControllerHandler.responseCreated;
import static org.example.commonservice.utils.response.ControllerHandler.responseSucceed;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/v1.0.0/api")
public class AuthController {

    @DubboReference
    private final AuthProvider authProvider;
    private final AuthControllerMapper mapper;

    @Operation(summary = "login")
    @PostMapping("/login")
    public ResponseEntity<HttpBodyResponse<AuthResponse>> login(@RequestBody @Validated Login request) {
        return responseSucceed(mapper.from(authProvider.login(request)));
    }

    @Operation(summary = "register")
    @PostMapping("/register")
    public ResponseEntity<HttpBodyResponse<UserResponse>> register(@RequestBody @Validated Register request) {
        return responseCreated(mapper.to(authProvider.register(request)));
    }

    @Operation(summary = "profile")
    @GetMapping("/profile/{email}")
    public ResponseEntity<HttpBodyResponse<UserDetailResponse>> profile(@PathVariable String email) {
        return responseSucceed(mapper.view(authProvider.profile(email)));
    }

    @Operation(summary = "forgot Password")
    @PatchMapping("/forgot-password")
    public ResponseEntity<HttpBodyResponse<Map<String, String>>> forgotPassword(@RequestParam String email) {
        Map<String, String> map = new HashMap<>();
        String code = authProvider.forgotPassword(email);
        map.put("email", email);
        map.put("code", code);
        return responseSucceed(map);
    }

    @Operation(summary = "Verify")
    @PatchMapping("/verify")
    public ResponseEntity<?> verify(@RequestParam String email, @RequestParam String code) {
        authProvider.verify(email, code);
        return responseSucceed();
    }

    @Operation(summary = "Change Password")
    @PatchMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestParam String email, @RequestBody ChangePasswordRQ request) {
        authProvider.changePassword(email, request.getPassword(), request.getConfirmPassword());
        return responseSucceed();
    }
}
