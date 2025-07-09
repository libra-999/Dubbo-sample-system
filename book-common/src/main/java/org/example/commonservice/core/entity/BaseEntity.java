package org.example.commonservice.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseEntity implements Serializable {

    @Id
    private Integer id;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deletedAt;

    private String createdBy;

    private String updatedBy;

    private String deletedBy;

}
