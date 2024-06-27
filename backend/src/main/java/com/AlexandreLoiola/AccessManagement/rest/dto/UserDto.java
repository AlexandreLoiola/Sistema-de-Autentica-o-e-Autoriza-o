package com.AlexandreLoiola.AccessManagement.rest.dto;

import lombok.*;

import java.util.Objects;
import java.util.Set;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String email;
    private String username;
    private Set<RoleDto> roles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(email, userDto.email) &&
                Objects.equals(username, userDto.username) &&
                Objects.equals(roles, userDto.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, username, roles);
    }
}
