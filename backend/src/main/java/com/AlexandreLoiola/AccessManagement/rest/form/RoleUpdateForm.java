package com.AlexandreLoiola.AccessManagement.rest.form;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;
import java.util.Set;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleUpdateForm {
    @NotNull(message = "The description field cannot be empty")
    @NotBlank(message = "The description field cannot be blank.")
    @Size(min = 3, max = 100, message = "The description must be between 3 and 100 characters.")
    private String description;

    private Set<AuthorizationForm> authorizations;
}