package com.AlexandreLoiola.Access.Management.service;

import com.AlexandreLoiola.Access.Management.builders.RoleBuilder;
import com.AlexandreLoiola.Access.Management.builders.UserBuilder;
import com.AlexandreLoiola.AccessManagement.mapper.UserMapper;
import com.AlexandreLoiola.AccessManagement.model.RoleModel;
import com.AlexandreLoiola.AccessManagement.model.UserModel;
import com.AlexandreLoiola.AccessManagement.repository.UserRepository;
import com.AlexandreLoiola.AccessManagement.rest.dto.UserDto;
import com.AlexandreLoiola.AccessManagement.rest.form.UserCreateForm;
import com.AlexandreLoiola.AccessManagement.rest.form.UserLoginForm;
import com.AlexandreLoiola.AccessManagement.rest.form.UserUpdateForm;
import com.AlexandreLoiola.AccessManagement.service.RoleService;
import com.AlexandreLoiola.AccessManagement.service.TokenService;
import com.AlexandreLoiola.AccessManagement.service.UserService;
import com.AlexandreLoiola.AccessManagement.service.exceptions.user.InvalidCredentials;
import com.AlexandreLoiola.AccessManagement.service.exceptions.user.UserInsertException;
import com.AlexandreLoiola.AccessManagement.service.exceptions.user.UserNotFoundException;
import com.AlexandreLoiola.AccessManagement.service.exceptions.user.UserUpdateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private static final String EMAIL = "user_email@example.com";

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;

    @Mock
    private TokenService tokenService;

    private UserModel userModel;
    private UserDto userDto;
    private UserCreateForm userCreateForm;
    private UserLoginForm userLoginForm;
    private UserUpdateForm userUpdateForm;
    private Set<UserModel> userModels;

    private RoleModel roleModelAssociated;

    @BeforeEach
    public void setUp() {
        userModel = UserBuilder.createUserModel();
        userDto = UserBuilder.createUserDto();
        userCreateForm = UserBuilder.createUserCreateForm();
        userLoginForm = UserBuilder.createUserLoginForm();
        userUpdateForm = UserBuilder.createUserUpdateForm();
        userModels = UserBuilder.createUserModelSet(5);

        roleModelAssociated = RoleBuilder.createRoleModel();
        roleModelAssociated.setDescription("Associado(a)");
    }

    @Test
    void shouldFindUserDetailsSuccessfully() {
        when(userRepository.findByEmailAndFetchRoles(anyString())).thenReturn(Optional.of(userModel));

        UserDetails serviceResponse = userService.loadUserByUsername(EMAIL);

        assertNotNull(serviceResponse);
        assertEquals(userModel, serviceResponse);
        verify(userRepository, times(1)).findByEmailAndFetchRoles(anyString());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldLoginSuccessfully() {
        userModel.setPassword(new BCryptPasswordEncoder().encode("Password123!"));
        when(userRepository.findByEmailAndFetchRoles(anyString())).thenReturn(Optional.of(userModel));

        String mockToken = "mocked.token.string";
        when(tokenService.generateToken(userModel)).thenReturn(mockToken);

        String token = userService.login(userLoginForm);

        assertNotNull(token);
        assertEquals(mockToken, token);
        verify(userRepository, times(1)).findByEmailAndFetchRoles(anyString());
        verify(tokenService, times(1)).generateToken(userModel);
        verifyNoMoreInteractions(userRepository, tokenService);
    }

    @Test
    public void shouldThrowInvalidCredentialsExceptionWhenEmailNotFound() {
        when(userRepository.findByEmailAndFetchRoles(anyString())).thenReturn(Optional.empty());

        assertThrows(InvalidCredentials.class, () -> {
            userService.login(userLoginForm);
        });

        verify(userRepository, times(1)).findByEmailAndFetchRoles(anyString());
        verify(tokenService, never()).generateToken(any());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldThrowInvalidCredentialsExceptionWhenPasswordDoesntMatch() {
        userModel.setPassword(new BCryptPasswordEncoder().encode("password123"));
        when(userRepository.findByEmailAndFetchRoles(anyString())).thenReturn(Optional.of(userModel));

        assertThrows(InvalidCredentials.class, () -> {
            userService.login(userLoginForm);
        });

        verify(userRepository, times(1)).findByEmailAndFetchRoles(anyString());
        verify(tokenService, never()).generateToken(any());
    }

    @Test
    void shouldFindUsersDtoSuccessfully() {
        when(userRepository.findByIsActiveTrueAndFetchRolesEagerly()).thenReturn(userModels);
        Set<UserDto> expectedDtos = new HashSet<>();
        for (UserModel user : userModels) {
            expectedDtos.add(UserMapper.INSTANCE.modelToDto(user));
        }

        Set<UserDto> serviceResponse = userService.getAllUserDto();

        assertNotNull(serviceResponse);
        assertEquals(expectedDtos, serviceResponse);
        verify(userRepository, times(1)).findByIsActiveTrueAndFetchRolesEagerly();
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenNoActiveRolesForDto() {
        when(userRepository.findByIsActiveTrueAndFetchRolesEagerly()).thenReturn(Collections.emptySet());

        assertThrows(UserNotFoundException.class, () -> {
            userService.getAllUserDto();
        });

        verify(userRepository, times(1)).findByIsActiveTrueAndFetchRolesEagerly();
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldFindUserDtoSuccessfully() {
        when(userRepository.findByEmailAndFetchRoles(anyString())).thenReturn(Optional.of(userModel));
        UserDto expectedDto = UserMapper.INSTANCE.modelToDto(userModel);

        UserDto serviceResponse = userService.getUserDtoByEmail(EMAIL);

        assertNotNull(serviceResponse);
        assertEquals(expectedDto, serviceResponse);
        verify(userRepository, times(1)).findByEmailAndFetchRoles(anyString());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldFindUserAndThrowsNotFoundException() {
        when(userRepository.findByEmailAndFetchRoles(anyString())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.getUserDtoByEmail(EMAIL);
        });

        verify(userRepository, times(1)).findByEmailAndFetchRoles(anyString());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldInsertUserSuccessfully() {
        when(userRepository.findByEmailAndFetchRoles(anyString())).thenReturn(Optional.empty());
        when(roleService.findRoleModelByDescription("Associado(a)")).thenReturn(roleModelAssociated);
        when(userRepository.save(any(UserModel.class))).thenReturn(userModel);
        userModel.setRoles(Collections.singleton(roleModelAssociated));
        UserDto expectedDto = UserMapper.INSTANCE.modelToDto(userModel);

        UserDto serviceResponse = userService.insertUser(userCreateForm);

        assertNotNull(serviceResponse);
        assertEquals(expectedDto, serviceResponse);
        assertTrue(userModel.isActive());
        assertEquals(userModel.getCreatedAt(), userModel.getUpdatedAt());

        verify(userRepository, times(1)).findByEmailAndFetchRoles(anyString());
        verify(roleService, times(1)).findRoleModelByDescription("Associado(a)");
        verify(userRepository, times(1)).save(any(UserModel.class));
        verifyNoMoreInteractions(userRepository, roleService);
    }

    @Test
    void shouldEncryptPasswordOnInsert() {
        when(userRepository.findByEmailAndFetchRoles(anyString())).thenReturn(Optional.empty());
        when(roleService.findRoleModelByDescription("Associado(a)")).thenReturn(roleModelAssociated);

        UserDto serviceResponse = userService.insertUser(userCreateForm);

        assertNotNull(serviceResponse);
        ArgumentCaptor<UserModel> userModelCaptor = ArgumentCaptor.forClass(UserModel.class);
        verify(userRepository, times(1)).save(userModelCaptor.capture());
        UserModel savedUser = userModelCaptor.getValue();

        assertNotEquals(userCreateForm.getPassword(), savedUser.getPassword());
        assertTrue(new BCryptPasswordEncoder().matches(userCreateForm.getPassword(), savedUser.getPassword()));

        verify(userRepository, times(1)).findByEmailAndFetchRoles(anyString());
        verify(roleService, times(1)).findRoleModelByDescription("Associado(a)");
        verifyNoMoreInteractions(userRepository, roleService);
    }

    @Test
    void shouldThrowUserInsertExceptionWhenMethodAlreadyExists() {
        when(userRepository.findByEmailAndFetchRoles(anyString())).thenReturn(Optional.of(userModel));

        assertThrows(UserInsertException.class, () -> {
            userService.insertUser(userCreateForm);
        });

        verify(userRepository, times(1)).findByEmailAndFetchRoles(anyString());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldThrowUserInsertExceptionWhenDataIntegrityViolationOccurs() {
        when(userRepository.findByEmailAndFetchRoles(anyString())).thenReturn(Optional.empty());
        when(roleService.findRoleModelByDescription("Associado(a)")).thenReturn(roleModelAssociated);
        when(userRepository.save(any(UserModel.class))).thenThrow(new DataIntegrityViolationException(""));

        assertThrows(UserInsertException.class, () -> {
            userService.insertUser(userCreateForm);
        });

        verify(userRepository, times(1)).findByEmailAndFetchRoles(anyString());
        verify(roleService, times(1)).findRoleModelByDescription("Associado(a)");
        verify(userRepository, times(1)).save(any(UserModel.class));
        verifyNoMoreInteractions(userRepository, roleService);
    }

    @Test
    void shouldUpdateUserSuccessfully() {
        Set<RoleModel> roleModels = RoleBuilder.createRoleModelSet(5);
        for (RoleModel roleModel : roleModels) {
            when(roleService.findRoleModelByDescription(roleModel.getDescription())).thenReturn(roleModel);
        }
        when(userRepository.findByEmailAndFetchRoles(anyString())).thenReturn(Optional.of(userModel));
        when(userRepository.save(any(UserModel.class))).thenReturn(userModel);
        UserDto expectedDto = UserMapper.INSTANCE.modelToDto(userModel);
        expectedDto.setRoles(RoleBuilder.createRoleDtoSet(5));

        UserDto serviceResponse = userService.updateUser(EMAIL, userUpdateForm);

        assertNotNull(serviceResponse);
        assertEquals(expectedDto, serviceResponse);
        assertTrue(userModel.isActive());
        assertTrue(userModel.getUpdatedAt().after(userModel.getCreatedAt()));

        verify(userRepository, times(1)).findByEmailAndFetchRoles(anyString());
        verify(userRepository, times(1)).save(any());
        verify(userRepository, times(1)).deleteUserRole(any(UUID.class));
        verify(roleService, times(5)).findRoleModelByDescription(anyString());
        verifyNoMoreInteractions(userRepository, roleService);
    }

    @Test
    void shouldThrowUserUpdateExceptionWhenDataIntegrityViolationOccurs() {
        when(userRepository.findByEmailAndFetchRoles(anyString())).thenReturn(Optional.of(userModel));
        when(userRepository.save(any(UserModel.class))).thenThrow(new DataIntegrityViolationException(""));

        assertThrows(UserUpdateException.class, () -> {
            userService.updateUser(EMAIL, userUpdateForm);
        });

        verify(userRepository, times(1)).findByEmailAndFetchRoles(anyString());
        verify(userRepository, times(1)).save(any());
        verify(userRepository, times(1)).deleteUserRole(any(UUID.class));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldThrowUserUpdateExceptionWhenClearingRolesFails() {
        doThrow(new DataIntegrityViolationException("")).when(userRepository).deleteUserRole(any(UUID.class));

        assertThrows(UserUpdateException.class, () -> {
            userService.clearUserRoles(userModel);
        });

        verify(userRepository, times(1)).deleteUserRole(any(UUID.class));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldDeleteUserSuccessfully() {
        when(userRepository.findByEmailAndFetchRoles(anyString())).thenReturn(Optional.of(userModel));
        when(userRepository.save(any(UserModel.class))).thenReturn(userModel);

        userService.deleteUser(EMAIL);

        assertFalse(userModel.isActive());
        assertTrue(userModel.getUpdatedAt().after(userModel.getCreatedAt()));

        verify(userRepository, times(1)).findByEmailAndFetchRoles(anyString());
        verify(userRepository, times(1)).save(any());
        verify(userRepository, times(1)).deleteUserRole(any(UUID.class));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldThrowDeleteUserExceptionWhenDataIntegrityViolationOccurs() {
        when(userRepository.findByEmailAndFetchRoles(anyString())).thenReturn(Optional.of(userModel));
        when(userRepository.save(any(UserModel.class))).thenThrow(new DataIntegrityViolationException(""));

        assertThrows(UserUpdateException.class, () -> {
            userService.deleteUser(EMAIL);
        });

        verify(userRepository, times(1)).findByEmailAndFetchRoles(anyString());
        verify(userRepository, times(1)).save(any());
        verify(userRepository, times(1)).deleteUserRole(any(UUID.class));
        verifyNoMoreInteractions(userRepository);
    }

}