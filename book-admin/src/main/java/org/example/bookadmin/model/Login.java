package org.example.bookadmin.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Builder
public class Login implements Serializable {

    @NotBlank(message = "Username must be required")
    @NotNull(message = "Username must be required")
    private String username;

    @NotBlank(message = "Password must be required")
    @NotNull(message = "Password must be required")
    private String password;
}
