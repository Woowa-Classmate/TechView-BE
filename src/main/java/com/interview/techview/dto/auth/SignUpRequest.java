package com.interview.techview.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequest {

    @NotBlank(message = "{auth.email.required}")
    @Email(message = "{auth.email.invalid}")
    private String email;

    @NotBlank(message = "{auth.name.required}")
    private String name;

    @NotBlank(message = "{auth.password.required}")
    @Size(min = 8, max = 20, message = "{auth.password.length}")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+=-]).+$",
            message = "{auth.password.invalid}"
    )
    private String password;
}
