package com.AlexandreLoiola.AccessManagement.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    private String description;
    private Set<AuthorizationDto> authorizations;
}
