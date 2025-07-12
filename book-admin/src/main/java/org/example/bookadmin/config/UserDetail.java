package org.example.bookadmin.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.commonservice.core.entity.PermissionEntity;
import org.example.commonservice.core.entity.RoleEntity;
import org.example.commonservice.core.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetail implements UserDetails {

    private boolean enable = true;

    private Integer id;
    private String username;
    private String email;
    @JsonIgnore
    private String password;
    private String dateOfBirth;
    private String thumbnail;
    private String phone;
    private boolean status;
    private String gender;
    private Integer roleId;
    private Date createdAt;
    private String createdBy;

    @JsonIgnore
    public Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities != null ? authorities : List.of();
    }

    public UserDetail(UserEntity entity) {
        id = entity.getId();
        username = entity.getUsername();
        email = entity.getEmail();
        password = entity.getPassword();
        dateOfBirth = entity.getDateOfBirth();
        gender = entity.getGender().toString();
        thumbnail = entity.getThumbnail();
        phone = entity.getPhoneNumber();
        status = entity.isStatus();
        createdBy = entity.getCreatedBy();
        createdAt = entity.getCreatedAt();
        roleId = entity.getRoleId();
        this.authorities = Optional.ofNullable(entity.getRole())
            .map(RoleEntity::getPermissions)
            .orElse(List.of())
            .stream()
            .map(PermissionEntity::getName)
            .map(SimpleGrantedAuthority::new)
            .toList();


    }

    public UserDetail(JwtAuthenticationToken token){
        Map<String , Object> map = token.getTokenAttributes();
        this.id = (Integer) map.get("id");
        this.username = (String) map.get("username");
        this.email = (String) map.get("email");
        this.password = (String) map.get("password");
        this.phone = (String) map.get("phone");
        this.dateOfBirth = (String) map.get("dateOfBirth");
        this.roleId = (Integer) map.get("roleId");
        String genStr = (String) map.get("gender");
        if (genStr != null) {this.gender = genStr;}

    }


    public static UserDetail build(UserEntity entity) {
        return new UserDetail(entity);
    }

    public static UserDetail build(JwtAuthenticationToken token) {
        return new UserDetail(token);
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enable;
    }

}
