package com.AlexandreLoiola.AccessManagement.rest.dto;

import lombok.*;

import java.util.Set;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizationDto {
    private String description;
    private String path;
    private Set<MethodDto> methods;
}