package com.AlexandreLoiola.AccessManagement.service;

import com.AlexandreLoiola.AccessManagement.mapper.RoleMapper;
import com.AlexandreLoiola.AccessManagement.model.AuthorizationModel;
import com.AlexandreLoiola.AccessManagement.model.RoleModel;
import com.AlexandreLoiola.AccessManagement.repository.RoleRepository;
import com.AlexandreLoiola.AccessManagement.rest.dto.RoleDto;
import com.AlexandreLoiola.AccessManagement.rest.form.AuthorizationForm;
import com.AlexandreLoiola.AccessManagement.rest.form.RoleForm;
import com.AlexandreLoiola.AccessManagement.rest.form.RoleUpdateForm;
import com.AlexandreLoiola.AccessManagement.service.exceptions.role.RoleNotFoundException;
import com.AlexandreLoiola.AccessManagement.service.exceptions.role.RoleInsertException;
import com.AlexandreLoiola.AccessManagement.service.exceptions.role.RoleUpdateException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class RoleService {

    private final RoleMapper roleMapper;
    private final RoleRepository roleRepository;
    private final AuthorizationService authorizationService;


    public RoleService(RoleMapper roleMapper, RoleRepository roleRepository, AuthorizationService authorizationService) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
        this.authorizationService = authorizationService;
    }

    public RoleDto getRoleDtoByDescription(String description) {
        RoleModel roleModel = findRoleModelByDescription(description);
        return RoleMapper.INSTANCE.modelToDto(roleModel);
    }

    public RoleModel findRoleModelByDescription(String description) {
        RoleModel roleModel = roleRepository.findByDescriptionAndIsActiveTrue(description)
                .orElseThrow(() -> new RoleNotFoundException(
                        String.format("The role ‘%s’ was not found", description)
                ));
        Set<Object[]> results = roleRepository.findRoleWithAuthorizations(description);
        for (Object[] result : results) {
            String authorizationDescription = (String) result[1];
            AuthorizationModel authorizationModel = authorizationService.findAuthorizationModelByDescription(authorizationDescription);
            roleModel.getAuthorizations().add(authorizationModel);
        }
        return roleModel;
    }

    public Set<RoleDto> getAllRoleDto() {
        Set<RoleModel> roleModelSet = roleRepository.findByIsActiveTrue();
        if (roleModelSet.isEmpty()) {
            throw new RoleNotFoundException("No active role was found");
        }
        return RoleMapper.INSTANCE.setModelToSetDto(roleModelSet);
    }

    @Transactional
    public RoleDto insertRole(RoleForm roleForm) {
        if (roleRepository.findByDescription(roleForm.getDescription()).isPresent()) {
            throw new RoleInsertException(
                    String.format("The role ‘%s’ is already registered", roleForm.getDescription())
            );
        }
        Set<AuthorizationModel> authorizationModels = new HashSet<>();
        for (AuthorizationForm authorizationForm : roleForm.getAuthorizations()) {
            AuthorizationModel authorizationModel = authorizationService.findAuthorizationModelByDescription(authorizationForm.getDescription());
            authorizationModels.add(authorizationModel);
        }
        try {
            RoleModel roleModel = RoleMapper.INSTANCE.formToModel(roleForm);
            Date date = new Date();
            roleModel.setCreatedAt(date);
            roleModel.setUpdatedAt(date);
            roleModel.setIsActive(true);
            roleModel.setVersion(1);
            roleModel.setAuthorizations(authorizationModels);
            roleRepository.save(roleModel);
            return RoleMapper.INSTANCE.modelToDto(roleModel);
        } catch (DataIntegrityViolationException err) {
            throw new RoleInsertException(String.format("Failed to register the role ‘%s’. Check if the data is correct", roleForm.getDescription()));
        }
    }

    @Transactional
    public RoleDto updateRole(String description, RoleUpdateForm roleUpdateForm) {
        RoleModel roleModel = findRoleModelByDescription(description);
        roleModel.getAuthorizations().clear();
        roleRepository.deleteRoleAuthorization(roleModel.getId());
        Set<AuthorizationModel> authorizationModels = new HashSet<>();
        for (AuthorizationForm authorizationForm : roleUpdateForm.getAuthorizations()) {
            AuthorizationModel authorizationModel = authorizationService.findAuthorizationModelByDescription(authorizationForm.getDescription());
            authorizationModels.add(authorizationModel);
        }
        try {
            roleModel.setDescription(roleUpdateForm.getDescription());
            roleModel.setAuthorizations(authorizationModels);
            roleModel.setUpdatedAt(new Date());
            roleRepository.save(roleModel);
            return RoleMapper.INSTANCE.modelToDto(roleModel);
        } catch (DataIntegrityViolationException err) {
            throw new RoleUpdateException(String.format("Failed to update the role ‘%s’. Check if the data is correct", description));
        }
    }

    @Transactional
    public void deleteRole(String description) {
        try {
            RoleModel roleModel = findRoleModelByDescription(description);
            roleModel.setIsActive(false);
            roleModel.setUpdatedAt(new Date());
            roleRepository.save(roleModel);
        } catch (DataIntegrityViolationException err) {
            throw new RoleUpdateException(String.format("Failed to update the role ‘%s’. Check if the data is correct", description));
        }
    }
}
