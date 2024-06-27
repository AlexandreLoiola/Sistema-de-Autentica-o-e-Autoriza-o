package com.AlexandreLoiola.AccessManagement.service;

import com.AlexandreLoiola.AccessManagement.mapper.UserMapper;
import com.AlexandreLoiola.AccessManagement.model.RoleModel;
import com.AlexandreLoiola.AccessManagement.model.UserModel;
import com.AlexandreLoiola.AccessManagement.repository.UserRepository;
import com.AlexandreLoiola.AccessManagement.rest.dto.UserDto;
import com.AlexandreLoiola.AccessManagement.rest.form.*;
import com.AlexandreLoiola.AccessManagement.service.exceptions.user.InvalidCredentials;
import com.AlexandreLoiola.AccessManagement.service.exceptions.user.UserInsertException;
import com.AlexandreLoiola.AccessManagement.service.exceptions.user.UserNotFoundException;
import com.AlexandreLoiola.AccessManagement.service.exceptions.user.UserUpdateException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    private final RoleService roleService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TokenService tokenService;

    public UserService(UserRepository userRepository, UserMapper userMapper, RoleService roleService, TokenService tokenService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.userMapper = userMapper;
        this.tokenService = tokenService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return findUserModelByEmail(email);
    }

    public UserDto getUserDtoByEmail(String Email) {
        UserModel userModel = findUserModelByEmail(Email);
        return userMapper.INSTANCE.modelToDto(userModel);
    }

    @Transactional
    public UserModel findUserModelByEmail(String email) {
        return userRepository.findByEmailAndFetchRoles(email)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("The user ‘%s’ was not found", email)
                ));
    }

    public String login(UserLoginForm userLoginForm) {
        UserModel userModel = userRepository.findByEmailAndFetchRoles(userLoginForm.getEmail())
                .orElseThrow(() -> new InvalidCredentials("Invalid login credentials"));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!(passwordEncoder.matches(userLoginForm.getPassword(), userModel.getPassword()))) {
            throw new InvalidCredentials("Invalid login credentials");
        }
        String token = tokenService.generateToken(userModel);
        return token;
    }

    public Set<UserDto> getAllUserDto() {
        Set<UserModel> userModelSet = userRepository.findByIsActiveTrueAndFetchRolesEagerly();
        if (userModelSet.isEmpty()) {
            throw new UserNotFoundException("No active user was found");
        }
        return userMapper.INSTANCE.setModelToSetDto(userModelSet);
    }

    @Transactional
    public UserDto insertUser(UserCreateForm userForm) {
        try {
            findUserModelByEmail(userForm.getEmail());
            throw new UserInsertException(String.format("The user ‘%s’ is already registered", userForm.getEmail()));
        } catch (UserNotFoundException ignored) {
        }
        try {
            UserModel userModel = UserMapper.INSTANCE.formToModel(userForm);
            userModel.setPassword(new BCryptPasswordEncoder().encode(userForm.getPassword()));
            Date date = new Date();
            userModel.setCreatedAt(date);
            userModel.setUpdatedAt(date);
            userModel.setActive(true);
            userModel.setVersion(1);
            userModel.setRoles(Collections.singleton(roleService.findRoleModelByDescription("Associado(a)")));
            userRepository.save(userModel);
            return UserMapper.INSTANCE.modelToDto(userModel);
        } catch (DataIntegrityViolationException err) {
            throw new UserInsertException(String.format("Failed to register the user ‘%s’. Check if the data is correct", userForm.getEmail()));
        }
    }

    public void clearUserRoles(UserModel userModel) {
        try {
            userModel.getRoles().clear();
            userRepository.deleteUserRole(userModel.getId());
        } catch (DataIntegrityViolationException err) {
            throw new UserUpdateException(String.format("Failed to clear roles for user '%s'. Check if the data is correct", userModel.getEmail()));
        }
    }

    @Transactional
    public UserDto updateUser(String Email, UserUpdateForm userUpdateForm) {
        UserModel userModel = findUserModelByEmail(Email);
        clearUserRoles(userModel);
        Set<RoleModel> roleModels = new HashSet<>();
        for (RoleForm roleForm : userUpdateForm.getRoles()) {
            RoleModel roleModel = roleService.findRoleModelByDescription(roleForm.getDescription());
            roleModels.add(roleModel);
        }
        try {
            userModel.setEmail(userUpdateForm.getEmail());
            userModel.setUsername(userUpdateForm.getUsername());
            userModel.setUpdatedAt(new Date());
            userModel.setRoles(roleModels);
            userRepository.save(userModel);
            return userMapper.INSTANCE.modelToDto(userModel);
        } catch (DataIntegrityViolationException err) {
            throw new UserUpdateException(String.format("Failed to update the user ‘%s’. Check if the data is correct", Email));
        }
    }

    @Transactional
    public void deleteUser(String email) {
        UserModel userModel = findUserModelByEmail(email);
        clearUserRoles(userModel);
        try {
            userModel.setActive(false);
            userModel.setUpdatedAt(new Date());
            userRepository.save(userModel);
        } catch (DataIntegrityViolationException err) {
            throw new UserUpdateException(String.format("Failed to update the user ‘%s’. Check if the data is correct", email));
        }
    }
}