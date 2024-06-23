package com.AlexandreLoiola.Access.Management.builders;

import com.AlexandreLoiola.AccessManagement.model.AuthorizationModel;
import com.AlexandreLoiola.AccessManagement.model.RoleModel;
import com.AlexandreLoiola.AccessManagement.model.UserModel;
import com.AlexandreLoiola.AccessManagement.rest.dto.AuthorizationDto;
import com.AlexandreLoiola.AccessManagement.rest.dto.RoleDto;
import com.AlexandreLoiola.AccessManagement.rest.form.AuthorizationForm;
import com.AlexandreLoiola.AccessManagement.rest.form.RoleForm;
import com.AlexandreLoiola.AccessManagement.rest.form.RoleUpdateForm;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class RoleBuilder {
    private static final UUID DEFAULT_ID = UUID.randomUUID();
    private static final String DEFAULT_DESCRIPTION = "role_description";
    private static final Date DEFAULT_DATE = new Date();
    private static final boolean DEFAULT_IS_ACTIVE = true;
    private static final long DEFAULT_VERSION = 1L;
    private static Set<AuthorizationModel> DEFAULT_AUTHORIZATIONSMODEL = AuthorizationBuilder.createAuthorizationModelSet(5);
    private static Set<UserModel> DEFAULT_USERMODEL = new HashSet<>();
    private static Set<AuthorizationDto> DEFAULT_AUTHORIZATIONSDTO = AuthorizationBuilder.createAuthorizationDtoSet(5);
    private static Set<AuthorizationForm> DEFAULT_AUTHORIZATIONSFORM = AuthorizationBuilder.createAuthorizationFormSet(5);

    public static RoleModel createRoleModel() {
        return new RoleModel(DEFAULT_ID, DEFAULT_DESCRIPTION, DEFAULT_DATE, DEFAULT_DATE, DEFAULT_IS_ACTIVE, DEFAULT_VERSION, DEFAULT_AUTHORIZATIONSMODEL, DEFAULT_USERMODEL);
    }

    public static RoleModel createRoleModel(UUID id, String description, Date createdAt, Date updatedAt, boolean isActive, long version, Set<AuthorizationModel> authorizations) {
        return new RoleModel(id, description, createdAt, updatedAt, isActive, version, authorizations, new HashSet<>());
    }

    public static RoleDto createRoleDto() {
        return new RoleDto(DEFAULT_DESCRIPTION, DEFAULT_AUTHORIZATIONSDTO);
    }

    public static RoleDto createRoleDto(String description, Set<AuthorizationDto> authorizations) {
        return new RoleDto(description, authorizations);
    }

    public static RoleForm createRoleForm() {
        return new RoleForm(DEFAULT_DESCRIPTION, DEFAULT_AUTHORIZATIONSFORM);
    }

    public static RoleForm createRoleForm(String description, Set<AuthorizationForm> authorizations) {
        return new RoleForm(description, authorizations);
    }

    public static RoleUpdateForm createRoleUpdateForm() {
        return new RoleUpdateForm(DEFAULT_DESCRIPTION, DEFAULT_AUTHORIZATIONSFORM);
    }

    public static RoleUpdateForm createRoleUpdateForm(String description, Set<AuthorizationForm> authorizations) {
        return new RoleUpdateForm(description, authorizations);
    }

    public static Set<RoleModel> createRoleModelSet(int count) {
        Set<RoleModel> roleModels = new HashSet<>();
        for (int i = 0; i < count; i++) {
            UUID id = UUID.randomUUID();
            String description = "role_description_" + i;
            Date date = new Date();
            boolean isActive = true;
            long version = 1L;
            Set<AuthorizationModel> authorizations = AuthorizationBuilder.createAuthorizationModelSet(5);
            Set<UserModel> users = new HashSet<>();
            roleModels.add(new RoleModel(id, description, date, date, isActive, version, authorizations, users));
        }
        return roleModels;
    }

    public static Set<RoleDto> createRoleDtoSet(int count) {
        Set<RoleDto> roleDtos = new HashSet<>();
        for (int i = 0; i < count; i++) {
            String description = "role_description_" + i;
            Set<AuthorizationDto> authorizations = AuthorizationBuilder.createAuthorizationDtoSet(5);
            roleDtos.add(new RoleDto(description, authorizations));
        }
        return roleDtos;
    }

    public static Set<RoleForm> createRoleFormSet(int count) {
        Set<RoleForm> roleForms = new HashSet<>();
        for (int i = 0; i < count; i++) {
            String description = "role_description_" + i;
            Set<AuthorizationForm> authorizations = AuthorizationBuilder.createAuthorizationFormSet(5);
            roleForms.add(new RoleForm(description, authorizations));
        }
        return roleForms;
    }

    public static Set<RoleUpdateForm> createRoleUpdateFormSet(int count) {
        Set<RoleUpdateForm> roleUpdateForms = new HashSet<>();
        for (int i = 0; i < count; i++) {
            String description = "role_description_" + i;
            Set<AuthorizationForm> authorizations = AuthorizationBuilder.createAuthorizationFormSet(5);
            roleUpdateForms.add(new RoleUpdateForm(description, authorizations));
        }
        return roleUpdateForms;
    }
}
