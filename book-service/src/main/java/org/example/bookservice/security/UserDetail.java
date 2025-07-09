package org.example.bookservice.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.example.commonservice.core.entity.PermissionEntity;
import org.example.commonservice.core.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor(staticName = "of")
@Builder
public class UserDetail implements UserDetails {

    private Integer id;
    private String username;
    private String gender;

    @JsonIgnore
    private String password;
    private String dob;
    private String thumbnail;
    private String phoneNumber;
    private Date createdAt;
    private Date updatedAt;
    private Integer roleId;
    private boolean status;

    public static UserDetail build(UserEntity user) {
        return new UserDetail(
            user.getId(),
            user.getUsername(),
            user.getGender().name(),
            user.getPassword(),
            user.getDateOfBirth(),
            user.getThumbnail(),
            user.getPhoneNumber(),
            user.getCreatedAt(),
            user.getUpdatedAt(),
            user.getRoleId(),
            user.isStatus(),
            user.getRole().getPermissions().stream().map(PermissionEntity::getName)
                .map(SimpleGrantedAuthority::new).collect(Collectors.toSet())
        );
    }

    public Collection<? extends GrantedAuthority> authorities;

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
        return true;
    }

}
