package com.AlexandreLoiola.Access.Management.builders;

import com.AlexandreLoiola.AccessManagement.model.AuthorizationModel;
import com.AlexandreLoiola.AccessManagement.model.MethodModel;
import com.AlexandreLoiola.AccessManagement.model.RoleModel;
import com.AlexandreLoiola.AccessManagement.rest.dto.AuthorizationDto;
import com.AlexandreLoiola.AccessManagement.rest.dto.MethodDto;
import com.AlexandreLoiola.AccessManagement.rest.form.AuthorizationForm;
import com.AlexandreLoiola.AccessManagement.rest.form.AuthorizationUpdateForm;
import com.AlexandreLoiola.AccessManagement.rest.form.MethodForm;

import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.HashSet;

public class AuthorizationBuilder {

    private static final UUID DEFAULT_ID = UUID.randomUUID();
    private static final String DEFAULT_DESCRIPTION = "authorization_description";
    private static final String DEFAULT_PATH = "authorization_path";
    private static final Date DEFAULT_DATE = new Date();
    private static final boolean DEFAULT_IS_ACTIVE = true;
    private static final Integer DEFAULT_VERSION = 1;
    private static Set<MethodModel> DEFAULT_METHODSMODEL = MethodBuilder.createMethodModelSet(5);
    private static Set<RoleModel> DEFAULT_ROLESMODEL = null;
    private static Set<MethodForm> DEFAULT_METHODSFORM = MethodBuilder.createMethodFormSet(5);
    private static Set<MethodDto> DEFAULT_METHODSDTO = MethodBuilder.createMethodDtoSet(5);

    public static AuthorizationModel createAuthorizationModel() {
        return new AuthorizationModel(DEFAULT_ID, DEFAULT_DESCRIPTION, DEFAULT_PATH, DEFAULT_DATE, DEFAULT_DATE, DEFAULT_IS_ACTIVE, DEFAULT_VERSION, DEFAULT_METHODSMODEL, DEFAULT_ROLESMODEL);
    }

    public static AuthorizationModel createAuthorizationModel(UUID id, String description, String path, Date createdDate, Date updatedDate, Boolean isActive, Integer version, Set<MethodModel> methods, Set<RoleModel> roles) {
        return new AuthorizationModel(id, description, path, createdDate, updatedDate, isActive, version, methods, roles);
    }

    public static AuthorizationDto createAuthorizationDto() {
        return new AuthorizationDto(DEFAULT_DESCRIPTION, DEFAULT_PATH, DEFAULT_METHODSDTO);
    }

    public static AuthorizationDto createAuthorizationDto(String description, String path, Set<MethodDto> methods) {
        return new AuthorizationDto(description, path, methods);
    }

    public static AuthorizationForm createAuthorizationForm() {
        return new AuthorizationForm(DEFAULT_DESCRIPTION, DEFAULT_PATH, DEFAULT_METHODSFORM);
    }

    public static AuthorizationForm createAuthorizationForm(String description, String path, Set<MethodForm> methods) {
        return new AuthorizationForm(description, path, methods);
    }

    public static AuthorizationUpdateForm createAuthorizationUpdateForm() {
        return new AuthorizationUpdateForm(DEFAULT_DESCRIPTION, DEFAULT_PATH, DEFAULT_METHODSFORM);
    }

    public static AuthorizationUpdateForm createAuthorizationUpdateForm(String description, String path, Set<MethodForm> methods) {
        return new AuthorizationUpdateForm(description, path, methods);
    }

    public static Set<AuthorizationModel> createAuthorizationModelSet(int count) {
        Set<AuthorizationModel> authorizationModels = new HashSet<>();
        for (int i = 0; i < count; i++) {
            UUID id = UUID.randomUUID();
            String description = "authorization_description_" + i;
            String path = "authorization_path_" + i;
            Date date = new Date();
            boolean isActive = true;
            Integer version = 1;
            Set<MethodModel> methods = MethodBuilder.createMethodModelSet(5);
            Set<RoleModel> roles = new HashSet<>();
            authorizationModels.add(new AuthorizationModel(id, description, path, date, date, isActive, version, methods, roles));
        }
        return authorizationModels;
    }

    public static Set<AuthorizationDto> createAuthorizationDtoSet(int count) {
        Set<AuthorizationDto> authorizationDtos = new HashSet<>();
        for (int i = 0; i < count; i++) {
            String description = "authorization_description_" + i;
            String path = "authorization_path_" + i;
            Set<MethodDto> methods = MethodBuilder.createMethodDtoSet(5);
            authorizationDtos.add(new AuthorizationDto(description, path, methods));
        }
        return authorizationDtos;
    }

    public static Set<AuthorizationForm> createAuthorizationFormSet(int count) {
        Set<AuthorizationForm> authorizationForms = new HashSet<>();
        for (int i = 0; i < count; i++) {
            String description = "authorization_description_" + i;
            String path = "authorization_path_" + i;
            Set<MethodForm> methods = MethodBuilder.createMethodFormSet(5);
            authorizationForms.add(new AuthorizationForm(description, path, methods));
        }
        return authorizationForms;
    }
}