package org.example.apiservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class ChangePasswordRQ {

    private String password;
    private String confirmPassword;

}
