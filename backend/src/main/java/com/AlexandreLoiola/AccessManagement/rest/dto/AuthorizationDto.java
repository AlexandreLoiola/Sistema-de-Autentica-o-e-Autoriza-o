package com.AlexandreLoiola.AccessManagement.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizationDto {
    private String description;
    private String path;
    private Set<MethodDto> methods;
}