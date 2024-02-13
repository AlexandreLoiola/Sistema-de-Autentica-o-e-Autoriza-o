package com.AlexandreLoiola.Access.Management.service;

import com.AlexandreLoiola.AccessManagement.model.RoleModel;
import com.AlexandreLoiola.AccessManagement.repository.RoleRepository;
import com.AlexandreLoiola.AccessManagement.rest.dto.RoleDto;
import com.AlexandreLoiola.AccessManagement.rest.form.RoleForm;
import com.AlexandreLoiola.AccessManagement.rest.form.RoleUpdateForm;
import com.AlexandreLoiola.AccessManagement.service.RoleService;
import com.AlexandreLoiola.AccessManagement.service.exceptions.role.RoleNotFoundException;
import com.AlexandreLoiola.AccessManagement.service.exceptions.role.RoleInsertException;
import com.AlexandreLoiola.AccessManagement.service.exceptions.role.RoleUpdateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.UUID;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {
    @InjectMocks
    RoleService roleService;
    @Mock
    RoleRepository roleRepository;
    RoleForm roleForm;
    RoleUpdateForm roleUpdateForm;
    RoleModel roleModel;
    RoleDto roleDto;

    @BeforeEach
    void setUp() {
        roleForm = new RoleForm("Administrador");
        roleDto = new RoleDto("Administrador", true);
        roleUpdateForm = new RoleUpdateForm("Novo Administrador");

        roleModel = new RoleModel();
        Date date = new Date();
        roleModel.setId(UUID.randomUUID());
        roleModel.setDescription("Administrador");
        roleModel.setIsActive(true);
        roleModel.setCreatedAt(date);
        roleModel.setUpdatedAt(date);
        roleModel.setVersion("1.0.0");
    }
    @Test
    void shouldFindAllRolesAreActives() {
        when(roleRepository.findByIsActiveTrue())
                .thenReturn(Collections.singletonList(roleModel));
        List<RoleDto> roleDtoList = roleService.getAllRoleDto();
        assertEquals(Collections.singletonList(roleModel).size(), roleDtoList.size());
        RoleDto returnedRoleDto = roleDtoList.get(0);
        assertEquals(roleModel.getDescription(), returnedRoleDto.getDescription());
    }
    @Test
    void shouldFindRoleDtoActiveByDescription() {
        when(roleRepository.findByDescriptionAndIsActiveTrue(roleModel.getDescription()))
                .thenReturn(Optional.ofNullable(roleModel));
        RoleDto returnedRoleDto = roleService.getRoleDtoByDescription(roleModel.getDescription());
        assertEquals(returnedRoleDto.getDescription(), roleModel.getDescription());
    }
    @Test
    void shouldFindRoleModelActiveByDescription() {
        when(roleRepository.findByDescriptionAndIsActiveTrue(roleModel.getDescription()))
                .thenReturn(Optional.ofNullable(roleModel));
        RoleModel returnedRoleModel = roleService.findRoleModelByDescription(roleModel.getDescription());
        assertEquals(returnedRoleModel, roleModel);
    }
    @Test
    void shouldInsertRoleWithSuccess() {
        when(roleRepository.save(any(RoleModel.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        RoleDto returnedRoleDto = roleService.insertRole(roleForm);
        assertEquals(roleForm.getDescription(), returnedRoleDto.getDescription());
        verify(roleRepository, times(1)).save(any(RoleModel.class));
    }
    @Test
    void shouldTryInsertRoleAndThrowRoleInsertException() {
        when(roleRepository.save(any(RoleModel.class))).thenThrow(DataIntegrityViolationException.class);
        assertThrows(RoleInsertException.class, () -> roleService.insertRole(roleForm));
    }
    @Test
    void shouldUpdateRoleWithSuccess() {
        RoleUpdateForm roleUpdateForm = new RoleUpdateForm("Novo Administrador");

        when(roleRepository.findByDescription(roleModel.getDescription()))
                .thenReturn(Optional.of(roleModel));
        when(roleRepository.save(any(RoleModel.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        RoleDto returnedRoleDto = roleService.updateRole(roleModel.getDescription(), roleUpdateForm);

        assertEquals(roleUpdateForm.getDescription(), returnedRoleDto.getDescription());
        verify(roleRepository, times(1)).save(any(RoleModel.class));
    }

    @Test
    void shouldTryUpdateRoleAndThrowRoleUpdateException() {
        RoleUpdateForm roleUpdateForm = new RoleUpdateForm("Novo Administrador");
        String description = "Administrador";
        when(roleRepository.findByDescription(description))
                .thenReturn(Optional.of(new RoleModel()));
        when(roleRepository.findByDescriptionAndIsActiveTrue(roleModel.getDescription()))
                .thenReturn(Optional.ofNullable(roleModel));
        when(roleRepository.save(any(RoleModel.class))).thenThrow(DataIntegrityViolationException.class);
        assertThrows(RoleUpdateException.class, () -> roleService.updateRole(roleModel.getDescription(), roleUpdateForm));
    }

    @Test
    void shouldTryUpdateRoleAndThrowNoSuchElementException() {
        RoleUpdateForm roleUpdateForm = new RoleUpdateForm("Novo Administrador");
        String description = "Administrador";
        when(roleRepository.findByDescription(description))
                .thenReturn(Optional.of(new RoleModel()));
        when(roleRepository.findByDescriptionAndIsActiveTrue(roleModel.getDescription()))
                .thenReturn(Optional.empty());
        assertThrows(RoleNotFoundException.class, () -> roleService.updateRole(roleModel.getDescription(), roleUpdateForm));
    }
}