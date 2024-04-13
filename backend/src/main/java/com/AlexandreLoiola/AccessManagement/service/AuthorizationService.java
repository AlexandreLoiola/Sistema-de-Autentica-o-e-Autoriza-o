package com.AlexandreLoiola.AccessManagement.service;

import com.AlexandreLoiola.AccessManagement.mapper.AuthorizationMapper;
import com.AlexandreLoiola.AccessManagement.model.AuthorizationModel;
import com.AlexandreLoiola.AccessManagement.model.MethodModel;
import com.AlexandreLoiola.AccessManagement.repository.AuthorizationRepository;
import com.AlexandreLoiola.AccessManagement.rest.dto.AuthorizationDto;
import com.AlexandreLoiola.AccessManagement.rest.form.AuthorizationForm;
import com.AlexandreLoiola.AccessManagement.rest.form.AuthorizationUpdateForm;
import com.AlexandreLoiola.AccessManagement.rest.form.MethodForm;
import com.AlexandreLoiola.AccessManagement.service.exceptions.authorization.AuthorizationInsertException;
import com.AlexandreLoiola.AccessManagement.service.exceptions.authorization.AuthorizationNotFoundException;
import com.AlexandreLoiola.AccessManagement.service.exceptions.authorization.AuthorizationUpdateException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthorizationService {

    private final AuthorizationRepository authorizationRepository;
    private final MethodService methodService;
    private final AuthorizationMapper authorizationMapper;

    public AuthorizationService(AuthorizationRepository authorizationRepository, MethodService methodService, AuthorizationMapper authorizationMapper) {
        this.authorizationRepository = authorizationRepository;
        this.methodService = methodService;
        this.authorizationMapper = authorizationMapper;
    }

    public AuthorizationDto getAuthorizationDtoByDescription(String description) {
        AuthorizationModel authorizationModel = findAuthorizationModelByDescription(description);
        return AuthorizationMapper.INSTANCE.modelToDto(authorizationModel);
    }

    public AuthorizationModel findAuthorizationModelByDescription(String description) {
        AuthorizationModel authorizationModel = authorizationRepository.findByDescriptionAndIsActiveTrue(description)
                .orElseThrow(() -> new AuthorizationNotFoundException(
                        String.format("The authorization ‘%s’ was not found", description)
                ));
        Set<Object[]> results = authorizationRepository.findAuthorizationWithMethods(description);
        for (Object[] result : results) {
            String methodDescription = (String) result[1];
            MethodModel methodModel = methodService.findMethodModelByDescription(methodDescription);
            authorizationModel.getMethods().add(methodModel);
        }
        return authorizationModel;
    }

    public Set<AuthorizationDto> getAllAuthorizationDto() {
        Set<AuthorizationModel> authorizationModelSet = authorizationRepository.findByIsActiveTrue();
        if (authorizationModelSet.isEmpty()) {
            throw new AuthorizationNotFoundException("No active authorization was found");
        }
        for (AuthorizationModel authorizationModel : authorizationModelSet) {
            Set<Object[]> results = authorizationRepository.findAuthorizationWithMethods(authorizationModel.getDescription());
            for (Object[] result : results) {
                String methodDescription = (String) result[1];
                MethodModel methodModel = new MethodModel();
                methodModel.setDescription(methodDescription);
                authorizationModel.getMethods().add(methodModel);
            }
        }
        return AuthorizationMapper.INSTANCE.setModelToSetDto(authorizationModelSet);
    }

    @Transactional
    public AuthorizationDto insertAuthorization(AuthorizationForm authorizationForm) {
        if (authorizationRepository.findByDescription(authorizationForm.getDescription()).isPresent()) {
            throw new AuthorizationInsertException(
                    String.format("The authorization ‘%s’ is already registered", authorizationForm.getDescription())
            );
        }
        Set<MethodModel> methodModels = new HashSet<>();
        for (MethodForm methodForm : authorizationForm.getMethods()) {
            MethodModel methodModel = methodService.findMethodModelByDescription(methodForm.getDescription());
            methodModels.add(methodModel);
        }
        try {
            AuthorizationModel authorizationModel = AuthorizationMapper.INSTANCE.formToModel(authorizationForm);
            Date date = new Date();
            authorizationModel.setCreatedAt(date);
            authorizationModel.setUpdatedAt(date);
            authorizationModel.setIsActive(true);
            authorizationModel.setMethods(methodModels);
            authorizationModel.setVersion(1);
            authorizationRepository.save(authorizationModel);
            return AuthorizationMapper.INSTANCE.modelToDto(authorizationModel);
        } catch (DataIntegrityViolationException err) {
            throw new AuthorizationInsertException(String.format("Failed to register the authorization ‘%s’. Check if the data is correct", authorizationForm.getDescription()));
        }
    }


    @Transactional
    public AuthorizationDto updateAuthorization(String description, AuthorizationUpdateForm authorizationUpdateForm) {
        AuthorizationModel authorizationModel = findAuthorizationModelByDescription(description);
        authorizationModel.getMethods().clear();
        authorizationRepository.deleteAuthorizationMethods(authorizationModel.getId());
        Set<MethodModel> methodModels = new HashSet<>() ;
        for (MethodForm methodForm : authorizationUpdateForm.getMethods()) {
            MethodModel methodModel = methodService.findMethodModelByDescription(methodForm.getDescription());
            methodModels.add(methodModel);
        }
        try {
            authorizationModel.setDescription(authorizationUpdateForm.getDescription());
            authorizationModel.setPath(authorizationUpdateForm.getPath());
            authorizationModel.setMethods(methodModels);
            authorizationModel.setUpdatedAt(new Date());
            authorizationRepository.save(authorizationModel);
            return AuthorizationMapper.INSTANCE.modelToDto(authorizationModel);
        } catch (DataIntegrityViolationException err) {
            throw new AuthorizationUpdateException(String.format("Failed to update the authorization ‘%s’. Check if the data is correct", authorizationUpdateForm.getDescription()));
        }
    }

    @Transactional
    public void deleteAuthorization(String description) {
        AuthorizationModel authorizationModel = findAuthorizationModelByDescription(description);
        authorizationModel.getMethods().clear();
        authorizationRepository.deleteAuthorizationMethods(authorizationModel.getId());
        try {
            authorizationModel.setIsActive(false);
            authorizationModel.setUpdatedAt(new Date());
            authorizationRepository.save(authorizationModel);
        } catch (DataIntegrityViolationException err) {
            throw new AuthorizationUpdateException(String.format("Failed to update the authorization ‘%s’. Check if the data is correct", description));
        }
    }
}