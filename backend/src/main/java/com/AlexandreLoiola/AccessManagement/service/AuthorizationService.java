package com.AlexandreLoiola.AccessManagement.service;

import com.AlexandreLoiola.AccessManagement.model.AuthorizationModel;
import com.AlexandreLoiola.AccessManagement.repository.AuthorizationRepository;
import com.AlexandreLoiola.AccessManagement.rest.dto.AuthorizationDto;
import com.AlexandreLoiola.AccessManagement.rest.form.AuthorizationForm;
import com.AlexandreLoiola.AccessManagement.rest.form.AuthorizationUpdateForm;
import com.AlexandreLoiola.AccessManagement.service.exceptions.authorization.AuthorizationInsertException;
import com.AlexandreLoiola.AccessManagement.service.exceptions.authorization.AuthorizationNotFoundException;
import com.AlexandreLoiola.AccessManagement.service.exceptions.authorization.AuthorizationUpdateException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AuthorizationService {

    private final AuthorizationRepository authorizationRepository;

    public AuthorizationService(AuthorizationRepository authorizationRepository) {
        this.authorizationRepository = authorizationRepository;
    }

    public AuthorizationDto getAuthorizationDtoByDescription(String description) {
        AuthorizationModel authorizationModel = findAuthorizationModelByDescription(description);
        return convertModelToDto(authorizationModel);
    }

    public AuthorizationModel findAuthorizationModelByDescription(String description) {
        return authorizationRepository.findByDescriptionAndIsActiveTrue(description)
                .orElseThrow(() -> new AuthorizationNotFoundException(
                        String.format("The authorization ‘%s’ was not found", description)
                ));
    }

    public List<AuthorizationDto> getAllAuthorizationDto() {
        List<AuthorizationModel> authorizationModelList = authorizationRepository.findByIsActiveTrue();
        if (authorizationModelList.isEmpty()) {
            throw new AuthorizationNotFoundException("No active authorization was found");
        }
        return convertModelListToDtoList(authorizationModelList);
    }

    @Transactional
    public AuthorizationDto insertAuthorization(AuthorizationForm authorizationForm) {
        if (authorizationRepository.findByDescription(authorizationForm.getDescription()).isPresent()) {
            throw new AuthorizationInsertException(
                    String.format("The authorization ‘%s’ is already registered", authorizationForm.getDescription())
            );
        }
        try {
            AuthorizationModel authorizationModel = convertFormToModel(authorizationForm);
            Date date = new Date();
            authorizationModel.setCreatedAt(date);
            authorizationModel.setUpdatedAt(date);
            authorizationModel.setIsActive(true);
            authorizationModel.setVersion(1);
            authorizationRepository.save(authorizationModel);
            return convertModelToDto(authorizationModel);
        } catch (DataIntegrityViolationException err) {
            throw new AuthorizationInsertException(String.format("Failed to register the authorization ‘%s’. Check if the data is correct", authorizationForm.getDescription()));
        }
    }

    @Transactional
    public AuthorizationDto updateAuthorization(String description, AuthorizationUpdateForm authorizationUpdateForm) {
        try {
            AuthorizationModel authorizationModel = findAuthorizationModelByDescription(description);
            authorizationModel.setDescription(authorizationUpdateForm.getDescription());
            authorizationModel.setUpdatedAt(new Date());
            authorizationRepository.save(authorizationModel);
            return convertModelToDto(authorizationModel);
        } catch (DataIntegrityViolationException err) {
            throw new AuthorizationUpdateException(String.format("Failed to update the authorization ‘%s’. Check if the data is correct", description));
        }
    }

    @Transactional
    public void deleteAuthorization(String description) {
        try {
            AuthorizationModel authorizationModel = findAuthorizationModelByDescription(description);
            authorizationModel.setIsActive(false);
            authorizationModel.setUpdatedAt(new Date());
            authorizationRepository.save(authorizationModel);
        } catch (DataIntegrityViolationException err) {
            throw new AuthorizationUpdateException(String.format("Failed to update the authorization ‘%s’. Check if the data is correct", description));
        }
    }

    private AuthorizationDto convertModelToDto(AuthorizationModel authorizationModel) {
        AuthorizationDto authorizationDto = new AuthorizationDto();
        authorizationDto.setDescription(authorizationModel.getDescription());
        return authorizationDto;
    }

    private List<AuthorizationDto> convertModelListToDtoList(List<AuthorizationModel> list) {
        List<AuthorizationDto> authorizationDtoList = new ArrayList<>();
        for (AuthorizationModel authorizationModel : list) {
            AuthorizationDto authorizationDto = convertModelToDto(authorizationModel);
            authorizationDtoList.add(authorizationDto);
        }
        return authorizationDtoList;
    }

    private AuthorizationModel convertFormToModel(AuthorizationForm authorizationForm) {
        AuthorizationModel authorizationModel = new AuthorizationModel();
        authorizationModel.setDescription(authorizationForm.getDescription());
        return authorizationModel;
    }
}
