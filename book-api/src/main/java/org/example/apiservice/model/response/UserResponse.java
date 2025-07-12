package org.example.apiservice.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponse {

    private String id;
    private String email;
    private String username;
    private String dateOfBirth;
    private String phone;
    private String gender;
    private Date createdAt;
    private String createdBy;

}
