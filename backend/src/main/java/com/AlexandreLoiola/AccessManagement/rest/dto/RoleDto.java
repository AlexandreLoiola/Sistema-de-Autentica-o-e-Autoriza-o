package com.AlexandreLoiola.AccessManagement.rest.dto;

import lombok.*;

import java.util.Objects;
import java.util.Set;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    private String description;
    private Set<AuthorizationDto> authorizations;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleDto that = (RoleDto) o;
        return Objects.equals(description, that.description) &&
                Objects.equals(authorizations, that.authorizations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, authorizations);
    }
}
