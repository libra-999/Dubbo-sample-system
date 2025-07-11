package org.example.apiservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailResponse {

    private String email;
    private String username;
    private String dateOfBirth;
    private String phone;
    private String gender;
    private String thumbnail;
    private boolean status;
    private Date createdDate;
    private Date updatedDate;
    private String createdBy;
    private String updatedBy;

}
