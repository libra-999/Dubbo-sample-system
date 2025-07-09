package org.example.commonservice.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.example.commonservice.enums.GenderConstant;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserEntity extends BaseEntity {

    private String username;
    private String email;
    private String password;
    private GenderConstant gender;

    @JsonFormat(pattern = "yyyy/MM/dd")
    private String dateOfBirth;

    private String phoneNumber;
    private String thumbnail;
    private boolean status;
    private RoleEntity role;
    private Integer roleId;

}
