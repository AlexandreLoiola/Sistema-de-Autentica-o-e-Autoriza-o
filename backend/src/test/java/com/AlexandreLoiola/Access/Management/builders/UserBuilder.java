package com.AlexandreLoiola.Access.Management.builders;

import com.AlexandreLoiola.AccessManagement.model.RoleModel;
import com.AlexandreLoiola.AccessManagement.model.UserModel;
import com.AlexandreLoiola.AccessManagement.rest.dto.RoleDto;
import com.AlexandreLoiola.AccessManagement.rest.dto.UserDto;
import com.AlexandreLoiola.AccessManagement.rest.form.RoleForm;
import com.AlexandreLoiola.AccessManagement.rest.form.UserCreateForm;
import com.AlexandreLoiola.AccessManagement.rest.form.UserLoginForm;
import com.AlexandreLoiola.AccessManagement.rest.form.UserUpdateForm;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class UserBuilder {
    private static final UUID DEFAULT_ID = UUID.randomUUID();
    private static final String DEFAULT_USERNAME = "user_username";
    private static final String DEFAULT_EMAIL = "user_email@example.com";
    private static final String DEFAULT_PASSWORD = "Password123!";
    private static final Date DEFAULT_CREATED_AT = new Date();
    private static final Date DEFAULT_UPDATED_AT = new Date();
    private static final boolean DEFAULT_IS_ACTIVE = true;
    private static final long DEFAULT_VERSION = 1L;
    private static Set<RoleModel> DEFAULT_ROLESMODEL = RoleBuilder.createRoleModelSet(2);
    private static Set<RoleDto> DEFAULT_ROLESDTO = RoleBuilder.createRoleDtoSet(2);
    private static Set<RoleForm> DEFAULT_ROLESFORM = RoleBuilder.createRoleFormSet(2);

    public static UserModel createUserModel() {
        return new UserModel(DEFAULT_ID, DEFAULT_USERNAME, DEFAULT_EMAIL, DEFAULT_PASSWORD, DEFAULT_CREATED_AT, DEFAULT_UPDATED_AT, DEFAULT_IS_ACTIVE, DEFAULT_VERSION, DEFAULT_ROLESMODEL);
    }

    public static UserModel createUserModel(UUID id, String username, String email, String password, Date createdAt, Date updatedAt, boolean isActive, long version, Set<RoleModel> roles) {
        return new UserModel(id, username, email, password, createdAt, updatedAt, isActive, version, roles);
    }

    public static UserDto createUserDto() {
        return new UserDto(DEFAULT_EMAIL, DEFAULT_USERNAME, DEFAULT_ROLESDTO);
    }

    public static UserDto createUserDto(String email, String username, Set<RoleDto> roles) {
        return new UserDto(email, username, roles);
    }

    public static UserCreateForm createUserCreateForm() {
        return new UserCreateForm(DEFAULT_EMAIL, DEFAULT_USERNAME, DEFAULT_PASSWORD);
    }

    public static UserCreateForm createUserCreateForm(String email, String username, String password) {
        return new UserCreateForm(email, username, password);
    }

    public static UserLoginForm createUserLoginForm() {
        return new UserLoginForm(DEFAULT_EMAIL, DEFAULT_PASSWORD);
    }

    public static UserLoginForm createUserLoginForm(String email, String password) {
        return new UserLoginForm(email, password);
    }

    public static UserUpdateForm createUserUpdateForm() {
        return new UserUpdateForm(DEFAULT_EMAIL, DEFAULT_USERNAME, DEFAULT_ROLESFORM);
    }

    public static UserUpdateForm createUserUpdateForm(String email, String username, Set<RoleForm> roles) {
        return new UserUpdateForm(email, username, roles);
    }

    public static Set<UserModel> createUserModelSet(int count) {
        Set<UserModel> userModels = new HashSet<>();
        for (int i = 0; i < count; i++) {
            UUID id = UUID.randomUUID();
            String username = "user_username_" + i;
            String email = "user_email_" + i + "@example.com";
            String password = "Password123!";
            Date createdAt = new Date();
            Date updatedAt = new Date();
            boolean isActive = true;
            long version = 1L;
            Set<RoleModel> roles = RoleBuilder.createRoleModelSet(2);
            userModels.add(new UserModel(id, username, email, password, createdAt, updatedAt, isActive, version, roles));
        }
        return userModels;
    }

    public static Set<UserDto> createUserDtoSet(int count) {
        Set<UserDto> userDtos = new HashSet<>();
        for (int i = 0; i < count; i++) {
            String email = "user_email_" + i + "@example.com";
            String username = "user_username_" + i;
            Set<RoleDto> roles = RoleBuilder.createRoleDtoSet(2);
            userDtos.add(new UserDto(email, username, roles));
        }
        return userDtos;
    }

    public static Set<UserCreateForm> createUserCreateFormSet(int count) {
        Set<UserCreateForm> userCreateForms = new HashSet<>();
        for (int i = 0; i < count; i++) {
            String email = "user_email_" + i + "@example.com";
            String username = "user_username_" + i;
            String password = "Password123!";
            userCreateForms.add(new UserCreateForm(email, username, password));
        }
        return userCreateForms;
    }

    public static Set<UserLoginForm> createUserLoginFormSet(int count) {
        Set<UserLoginForm> userLoginForms = new HashSet<>();
        for (int i = 0; i < count; i++) {
            String email = "user_email_" + i + "@example.com";
            String password = "Password123!";
            userLoginForms.add(new UserLoginForm(email, password));
        }
        return userLoginForms;
    }

    public static Set<UserUpdateForm> createUserUpdateFormSet(int count) {
        Set<UserUpdateForm> userUpdateForms = new HashSet<>();
        for (int i = 0; i < count; i++) {
            String email = "user_email_" + i + "@example.com";
            String username = "user_username_" + i;
            Set<RoleForm> roles = RoleBuilder.createRoleFormSet(2);
            userUpdateForms.add(new UserUpdateForm(email, username, roles));
        }
        return userUpdateForms;
    }
}