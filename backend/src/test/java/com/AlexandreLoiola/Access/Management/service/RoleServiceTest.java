package com.AlexandreLoiola.Access.Management.service;

import com.AlexandreLoiola.Access.Management.builders.AuthorizationBuilder;
import com.AlexandreLoiola.Access.Management.builders.RoleBuilder;
import com.AlexandreLoiola.AccessManagement.mapper.RoleMapper;
import com.AlexandreLoiola.AccessManagement.model.AuthorizationModel;
import com.AlexandreLoiola.AccessManagement.model.RoleModel;
import com.AlexandreLoiola.AccessManagement.repository.RoleRepository;
import com.AlexandreLoiola.AccessManagement.rest.dto.RoleDto;
import com.AlexandreLoiola.AccessManagement.rest.form.RoleForm;
import com.AlexandreLoiola.AccessManagement.rest.form.RoleUpdateForm;
import com.AlexandreLoiola.AccessManagement.service.AuthorizationService;
import com.AlexandreLoiola.AccessManagement.service.RoleService;
import com.AlexandreLoiola.AccessManagement.service.exceptions.role.RoleInsertException;
import com.AlexandreLoiola.AccessManagement.service.exceptions.role.RoleNotFoundException;
import com.AlexandreLoiola.AccessManagement.service.exceptions.role.RoleUpdateException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {
    private static final String DESCRIPTION = "role_description";

    @InjectMocks
    private RoleService roleService;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private AuthorizationService authorizationService;

    private RoleModel roleModel;
    private RoleDto roleDto;
    private RoleForm roleForm;
    private RoleUpdateForm roleUpdateForm;
    private Set<RoleModel> roleModels;

    @BeforeEach
    public void setUp() {
        roleModel = RoleBuilder.createRoleModel();
        roleDto = RoleBuilder.createRoleDto();
        roleForm = RoleBuilder.createRoleForm();
        roleUpdateForm = RoleBuilder.createRoleUpdateForm();
        roleModels = RoleBuilder.createRoleModelSet(5);
    }

    @Test
    void shouldFindARolesDtoWithSuccessfully() {
        when(roleRepository.findByIsActiveTrueAndFetchAuthorizationsEagerly()).thenReturn(roleModels);
        Set<RoleDto> expectedRoleDtos = new HashSet<>();
        for (RoleModel role : roleModels) {
            expectedRoleDtos.add(RoleMapper.INSTANCE.modelToDto(role));
        }

        Set<RoleDto> serviceResponse = roleService.getAllRoleDto();

        assertNotNull(serviceResponse);
        assertEquals(expectedRoleDtos, serviceResponse);
        verify(roleRepository, times(1)).findByIsActiveTrueAndFetchAuthorizationsEagerly();
        verifyNoMoreInteractions(roleRepository);
    }

    @Test
    void shouldThrowRoleNotFoundExceptionWhenNoActiveRolesForDto() {
        when(roleRepository.findByIsActiveTrueAndFetchAuthorizationsEagerly()).thenReturn(Collections.emptySet());

        assertThrows(RoleNotFoundException.class, () -> {
           roleService.getAllRoleDto();
        });
        verify(roleRepository, times(1)).findByIsActiveTrueAndFetchAuthorizationsEagerly();
        verifyNoMoreInteractions(roleRepository);
    }

    @Test
    void shouldFindAllRoleModelsSuccessfully() {
        when(roleRepository.findByIsActiveTrueAndFetchAuthorizationsEagerly()).thenReturn(roleModels);

        Set<RoleModel> serviceResponse = roleService.findAllRoleModels();

        assertNotNull(serviceResponse);
        assertEquals(roleModels, serviceResponse);
        verify(roleRepository, times(1)).findByIsActiveTrueAndFetchAuthorizationsEagerly();
        verifyNoMoreInteractions(roleRepository);
    }


    @Test
    void shouldThrowRoleNotFoundExceptionWhenNoActiveRolesForModels() {
        when(roleRepository.findByIsActiveTrueAndFetchAuthorizationsEagerly()).thenReturn(Collections.emptySet());

        assertThrows(RoleNotFoundException.class, () -> {
            roleService.findAllRoleModels();
        });

        verify(roleRepository, times(1)).findByIsActiveTrueAndFetchAuthorizationsEagerly();
        verifyNoMoreInteractions(roleRepository);
    }


    @Test
    void shouldFindRoleDtoSuccessfully() {
        when(roleRepository.findByDescriptionAndFetchAuthorizations(anyString())).thenReturn(Optional.of(roleModel));
        RoleDto expectedDto = RoleMapper.INSTANCE.modelToDto(roleModel);

        RoleDto serviceResponse = roleService.getRoleDtoByDescription(DESCRIPTION);

        assertNotNull(serviceResponse);
        assertEquals(expectedDto, serviceResponse);
        verify(roleRepository, times(1)).findByDescriptionAndFetchAuthorizations(anyString());
        verifyNoMoreInteractions(roleRepository);
    }

    @Test
    void shouldFindRoleAndThrowsNotFoundException() {
        when(roleRepository.findByDescriptionAndFetchAuthorizations(anyString())).thenReturn(Optional.empty());

        assertThrows(RoleNotFoundException.class, () -> {
            roleService.getRoleDtoByDescription(DESCRIPTION);
        });
        verify(roleRepository, times(1)).findByDescriptionAndFetchAuthorizations(anyString());
        verifyNoMoreInteractions(roleRepository);
    }

    @Test
    void shouldInsertRoleSuccessfully() {
        when(roleRepository.findByDescriptionAndFetchAuthorizations(anyString())).thenReturn(Optional.empty());
        when(roleRepository.save(any(RoleModel.class))).thenReturn(roleModel);
        RoleDto expectedDto = RoleMapper.INSTANCE.modelToDto(roleModel);

        RoleDto serviceResponse = roleService.insertRole(roleForm);

        assertNotNull(serviceResponse);
        assertEquals(expectedDto, serviceResponse);
        assertTrue(roleModel.isActive());
        assertEquals(roleModel.getCreatedAt(), roleModel.getUpdatedAt());

        verify(roleRepository, times(1)).findByDescriptionAndFetchAuthorizations(anyString());
        verify(roleRepository, times(1)).save(any());
        verifyNoMoreInteractions(roleRepository);
    }

    @Test
    void shouldThrowRoleInsertExceptionWhenMethodAlreadyExists() {
        when(roleRepository.findByDescriptionAndFetchAuthorizations(anyString())).thenReturn(Optional.of(roleModel));

        assertThrows(RoleInsertException.class, () -> {
           roleService.insertRole(roleForm);
        });

        verify(roleRepository, times(1)).findByDescriptionAndFetchAuthorizations(anyString());
        verifyNoMoreInteractions(roleRepository);
    }

    @Test
    void shouldThrowRoleInsertExceptionWhenDataIntegrityViolationOccurs() {
        when(roleRepository.findByDescriptionAndFetchAuthorizations(anyString())).thenReturn(Optional.empty());
        when(roleRepository.save(any(RoleModel.class))).thenThrow(new DataIntegrityViolationException(""));

        assertThrows(RoleInsertException.class, () -> {
            roleService.insertRole(roleForm);
        });

        verify(roleRepository, times(1)).findByDescriptionAndFetchAuthorizations(anyString());
        verify(roleRepository, times(1)).save(any());
        verifyNoMoreInteractions(roleRepository);
    }

    @Test
    void shouldUpdateRoleSuccessfully() {
        Set<AuthorizationModel> authorizationModels = AuthorizationBuilder.createAuthorizationModelSet(5);
        for (AuthorizationModel authorizationModel : authorizationModels) {
            when(authorizationService.findAuthorizationModelByDescription(authorizationModel.getDescription())).thenReturn(authorizationModel);
        }
        when(roleRepository.findByDescriptionAndFetchAuthorizations(anyString())).thenReturn(Optional.of(roleModel));
        when(roleRepository.save(any(RoleModel.class))).thenReturn(roleModel);
        RoleDto expectedDto = RoleMapper.INSTANCE.modelToDto(roleModel);
        expectedDto.setAuthorizations(AuthorizationBuilder.createAuthorizationDtoSet(5));

        RoleDto serviceResponse = roleService.updateRole(DESCRIPTION, roleUpdateForm);

        assertNotNull(serviceResponse);
        assertEquals(expectedDto, serviceResponse);
        assertTrue(roleModel.isActive());
        assertTrue(roleModel.getUpdatedAt().after(roleModel.getCreatedAt()));

        verify(roleRepository, times(1)).findByDescriptionAndFetchAuthorizations(anyString());
        verify(roleRepository, times(1)).save(any());
        verify(roleRepository, times(1)).deleteRoleAuthorization(any(UUID.class));
        verifyNoMoreInteractions(roleRepository);
    }

    @Test
    void shouldThrowRoleUpdateExceptionWhenDataIntegrityViolationOccurs() {
        when(roleRepository.findByDescriptionAndFetchAuthorizations(anyString())).thenReturn(Optional.of(roleModel));
        when(roleRepository.save(any(RoleModel.class))).thenThrow(new DataIntegrityViolationException(""));

        assertThrows(RoleUpdateException.class, () -> {
            roleService.updateRole(DESCRIPTION, roleUpdateForm);
        });

        verify(roleRepository, times(1)).findByDescriptionAndFetchAuthorizations(anyString());
        verify(roleRepository, times(1)).save(any());
        verify(roleRepository, times(1)).deleteRoleAuthorization(any(UUID.class));
        verifyNoMoreInteractions(roleRepository);
    }

    @Test
    void shouldDeleteRoleSuccessfully() {
        when(roleRepository.findByDescriptionAndFetchAuthorizations(anyString())).thenReturn(Optional.of(roleModel));
        when(roleRepository.save(any(RoleModel.class))).thenReturn(roleModel);

        roleService.deleteRole(DESCRIPTION);

        assertFalse(roleModel.isActive());
        assertTrue(roleModel.getUpdatedAt().after(roleModel.getCreatedAt()));

        verify(roleRepository, times(1)).findByDescriptionAndFetchAuthorizations(anyString());
        verify(roleRepository, times(1)).save(any());
        verify(roleRepository, times(1)).deleteRoleAuthorization(any(UUID.class));
        verifyNoMoreInteractions(roleRepository);
    }

    @Test
    void shouldThrowDeleteRoleExceptionWhenDataIntegrityViolationOccurs() {
        when(roleRepository.findByDescriptionAndFetchAuthorizations(anyString())).thenReturn(Optional.of(roleModel));
        when(roleRepository.save(any(RoleModel.class))).thenThrow(new DataIntegrityViolationException(""));

        assertThrows(RoleUpdateException.class, () -> {
           roleService.deleteRole(DESCRIPTION);
        });

        verify(roleRepository, times(1)).findByDescriptionAndFetchAuthorizations(anyString());
        verify(roleRepository, times(1)).save(any());
        verify(roleRepository, times(1)).deleteRoleAuthorization(any(UUID.class));
        verifyNoMoreInteractions(roleRepository);
    }
}