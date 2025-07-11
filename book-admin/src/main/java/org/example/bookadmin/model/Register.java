package org.example.bookadmin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.commonservice.enums.GenderConstant;

import java.io.Serializable;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class Register implements Serializable {

    private String username;
    private String email;
    private String password;
    private String thumbnail;
    private String phoneNumber;
    private String dateOfBirth;
    private String status;
    private GenderConstant gender;

}
