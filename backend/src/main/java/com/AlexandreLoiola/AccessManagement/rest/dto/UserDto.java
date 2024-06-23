package com.AlexandreLoiola.AccessManagement.rest.dto;

import lombok.*;

import java.util.Set;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String email;
    private String username;
    private Set<RoleDto> roles;
}
