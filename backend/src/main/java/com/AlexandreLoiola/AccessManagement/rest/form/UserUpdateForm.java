package com.AlexandreLoiola.AccessManagement.rest.form;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;
import java.util.Set;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateForm {
    @NotNull(message = "The description field cannot be empty")
    @NotBlank(message = "The description field cannot be blank.")
    @Size(min = 3, max = 100, message = "The description must be between 3 and 100 characters.")
    @Email(message = "The email must be a valid email address.")
    private String email;

    @NotNull(message = "The username field cannot be empty")
    @NotBlank(message = "The username field cannot be blank.")
    @Size(min = 3, max = 100, message = "The username must be between 3 and 100 characters.")
    private String username;

    private Set<RoleForm> roles;
}