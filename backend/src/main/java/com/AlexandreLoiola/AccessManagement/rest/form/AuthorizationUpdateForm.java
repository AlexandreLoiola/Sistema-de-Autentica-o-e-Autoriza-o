package com.AlexandreLoiola.AccessManagement.rest.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizationUpdateForm {
    @NotNull(message = "O campo descrição não pode estar vazio")
    @NotBlank(message = "O campo descrição não pode ficar em branco.")
    @Size(min = 3, max = 100, message = "A descrição do papel de usuário deve ter entre 3 e 100 caracteres.")
    private String description;
}