package org.example.commonservice.core.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class RoleEntity extends BaseEntity{

    private Integer id;
    private String name;
    private String code;
    private List<PermissionEntity> permissions;

}
