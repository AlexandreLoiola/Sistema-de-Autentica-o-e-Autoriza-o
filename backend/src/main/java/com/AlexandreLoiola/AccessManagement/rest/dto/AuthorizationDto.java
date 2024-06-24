package com.AlexandreLoiola.AccessManagement.rest.dto;

import lombok.*;

import java.util.Objects;
import java.util.Set;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizationDto {
    private String description;
    private String path;
    private Set<MethodDto> methods;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorizationDto that = (AuthorizationDto) o;
        return Objects.equals(description, that.description) &&
                Objects.equals(path, that.path) &&
                Objects.equals(methods, that.methods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, path, methods);
    }
}