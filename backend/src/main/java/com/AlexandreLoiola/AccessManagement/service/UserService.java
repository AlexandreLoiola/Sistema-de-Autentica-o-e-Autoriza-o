package com.AlexandreLoiola.AccessManagement.service;

import com.AlexandreLoiola.AccessManagement.mapper.UserMapper;
import com.AlexandreLoiola.AccessManagement.model.AuthorizationModel;
import com.AlexandreLoiola.AccessManagement.model.RoleModel;
import com.AlexandreLoiola.AccessManagement.model.UserModel;
import com.AlexandreLoiola.AccessManagement.repository.UserRepository;
import com.AlexandreLoiola.AccessManagement.rest.dto.UserDto;
import com.AlexandreLoiola.AccessManagement.rest.form.*;
import com.AlexandreLoiola.AccessManagement.service.exceptions.user.*;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    private final RoleService roleService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final TokenService tokenService;

    private final BlockLoginAttemptsService blockLoginAttemptsService;

    public UserService(UserRepository userRepository, UserMapper userMapper, RoleService roleService, TokenService tokenService, BlockLoginAttemptsService blockLoginAttemptsService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.userMapper = userMapper;
        this.tokenService = tokenService;
        this.blockLoginAttemptsService = blockLoginAttemptsService;
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
        UserModel userModel = userRepository.findByEmailAndIsActiveTrue(email)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("The user ‘%s’ was not found", email)
                ));
        Set<Object[]> results = userRepository.findUserWithRoles(email);
        for (Object[] result : results) {
            String roleDescription = (String) result[1];
            RoleModel roleModel = roleService.findRoleModelByDescription(roleDescription);
            userModel.getRoles().add(roleModel);
        }
        return userModel;
    }

    public String login(UserLoginForm userLoginForm, String ipAddress) {
        try {
            userRepository.findByEmail(userLoginForm.getEmail()).orElseThrow(
                    () -> new InvalidCredentials("Invalid login credentials")
            );
            UserModel userModel = findUserModelByEmail(userLoginForm.getEmail());
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (!(passwordEncoder.matches(userLoginForm.getPassword(), userModel.getPassword()))) {
                throw new InvalidCredentials(
                        "Invalid login credentials",
                        blockLoginAttemptsService,
                        userLoginForm.getEmail(),
                        userLoginForm.getPassword(),
                        ipAddress
                );
            }
            String token = tokenService.generateToken(userModel);
            return token;
        } catch (InvalidCredentials err) {
            throw new InvalidCredentials("Invalid login credentials");
        }
    }

    public Set<UserDto> getAllUserDto() {
        Set<UserModel> userModelSet = userRepository.findByIsActiveTrue();
        if (userModelSet.isEmpty()) {
            throw new UserNotFoundException("No active user was found");
        }
        return userMapper.INSTANCE.setModelToSetDto(userModelSet);
    }

    @Transactional
    public UserDto insertUser(UserCreateForm userForm) {
        if (userRepository.findByEmail(userForm.getEmail()).isPresent()) {
            throw new UserInsertException(
                    String.format("The user ‘%s’ is already registered", userForm.getEmail())
            );
        }
        Set<RoleModel> roleModels = new HashSet<>();
        roleModels.add(roleService.findRoleModelByDescription("Associado(a)"));
        try {
            UserModel userModel = userMapper.INSTANCE.formToModel(userForm);
            userModel.setPassword(new BCryptPasswordEncoder().encode(userForm.getPassword()));
            Date date = new Date();
            userModel.setCreatedAt(date);
            userModel.setUpdatedAt(date);
            userModel.setActive(true);
            userModel.setVersion(1);
            userModel.setRoles(roleModels);
            userRepository.save(userModel);
            return userMapper.INSTANCE.modelToDto(userModel);
        } catch (DataIntegrityViolationException err) {
            throw new UserInsertException(String.format("Failed to register the user ‘%s’. Check if the data is correct", userForm.getEmail()));
        }
    }

    @Transactional
    public UserDto updateUser(String Email, UserUpdateForm userUpdateForm) {
        UserModel userModel = findUserModelByEmail(Email);
        userModel.getRoles().clear();
        userRepository.deleteUserRole(userModel.getId());
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
    public void deleteUser(String Email) {
        try {
            UserModel userModel = findUserModelByEmail(Email);
            userModel.setActive(false);
            userModel.setUpdatedAt(new Date());
            userRepository.save(userModel);
        } catch (DataIntegrityViolationException err) {
            throw new UserUpdateException(String.format("Failed to update the user ‘%s’. Check if the data is correct", Email));
        }
    }
}