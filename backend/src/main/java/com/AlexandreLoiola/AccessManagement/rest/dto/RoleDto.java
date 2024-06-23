package com.AlexandreLoiola.AccessManagement.rest.dto;

import lombok.*;

import java.util.Set;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    private String description;
    private Set<AuthorizationDto> authorizations;
}
