package com.AlexandreLoiola.Access.Management.service;

import com.AlexandreLoiola.Access.Management.builders.AuthorizationBuilder;
import com.AlexandreLoiola.Access.Management.builders.MethodBuilder;
import com.AlexandreLoiola.AccessManagement.mapper.AuthorizationMapper;
import com.AlexandreLoiola.AccessManagement.mapper.AuthorizationMapperImpl;
import com.AlexandreLoiola.AccessManagement.model.AuthorizationModel;
import com.AlexandreLoiola.AccessManagement.model.MethodModel;
import com.AlexandreLoiola.AccessManagement.repository.AuthorizationRepository;
import com.AlexandreLoiola.AccessManagement.rest.dto.AuthorizationDto;
import com.AlexandreLoiola.AccessManagement.rest.form.AuthorizationForm;
import com.AlexandreLoiola.AccessManagement.rest.form.AuthorizationUpdateForm;
import com.AlexandreLoiola.AccessManagement.service.AuthorizationService;
import com.AlexandreLoiola.AccessManagement.service.MethodService;
import com.AlexandreLoiola.AccessManagement.service.exceptions.authorization.AuthorizationInsertException;
import com.AlexandreLoiola.AccessManagement.service.exceptions.authorization.AuthorizationNotFoundException;
import com.AlexandreLoiola.AccessManagement.service.exceptions.authorization.AuthorizationUpdateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorizationServiceTest {

    private static final String DESCRIPTION = "authorization_description";

    @InjectMocks
    private AuthorizationService authorizationService;

    @Mock
    private MethodService methodService;

    @Mock
    private AuthorizationRepository authorizationRepository;

    @Spy
    private AuthorizationMapper authorizationMapper = new AuthorizationMapperImpl();

    private AuthorizationModel authorizationModel;
    private AuthorizationDto authorizationDto;
    private AuthorizationForm authorizationForm;
    private AuthorizationUpdateForm authorizationUpdateForm;
    private Set<AuthorizationModel> authorizationModels;

    @BeforeEach
    public void setUp() {
        authorizationModel = AuthorizationBuilder.createAuthorizationModel();
        authorizationDto = AuthorizationBuilder.createAuthorizationDto();
        authorizationForm = AuthorizationBuilder.createAuthorizationForm();
        authorizationUpdateForm = AuthorizationBuilder.createAuthorizationUpdateForm();
        authorizationModels = AuthorizationBuilder.createAuthorizationModelSet(5);
    }

    @Test
    void shouldFindAuthorizationsWithSuccessfully() {
        when(authorizationRepository.findByIsActiveTrueAndFetchMethodsEagerly()).thenReturn(authorizationModels);
        Set<AuthorizationDto> expectedAuthorizationDtos = new HashSet<>();
        for (AuthorizationModel authorization : authorizationModels) {
            expectedAuthorizationDtos.add(authorizationMapper.modelToDto(authorization));
        }

        Set<AuthorizationDto> serviceResponse = authorizationService.getAllAuthorizationDto();

        assertEquals(expectedAuthorizationDtos, serviceResponse);
        verify(authorizationRepository, times(1)).findByIsActiveTrueAndFetchMethodsEagerly();
    }

    @Test
    void shouldThrowAuthorizationNotFoundExceptionWhenNoActiveAuthorizations() {
        when(authorizationRepository.findByIsActiveTrueAndFetchMethodsEagerly()).thenReturn(Collections.emptySet());

        assertThrows(AuthorizationNotFoundException.class, () -> {
            authorizationService.getAllAuthorizationDto();
        });
        verify(authorizationRepository, times(1)).findByIsActiveTrueAndFetchMethodsEagerly();
        verifyNoMoreInteractions(authorizationRepository);
    }

    @Test
    void shouldFindAuthorizationDtoSuccessfully() {
        when(authorizationRepository.findByDescriptionAndFetchMethods(anyString())).thenReturn(Optional.of(authorizationModel));
        AuthorizationDto expectedDto = authorizationMapper.modelToDto(authorizationModel);

        AuthorizationDto serviceResponse = authorizationService.getAuthorizationDtoByDescription(DESCRIPTION);

        assertNotNull(serviceResponse);
        assertEquals(expectedDto, serviceResponse);
        verify(authorizationRepository, times(1)).findByDescriptionAndFetchMethods(anyString());
        verifyNoMoreInteractions(authorizationRepository);
    }

    @Test
    void shouldFindAuthorizationAndThrowsNotFoundException() {
        when(authorizationRepository.findByDescriptionAndFetchMethods(anyString())).thenReturn(Optional.empty());

        assertThrows(AuthorizationNotFoundException.class, () -> {
            authorizationService.getAuthorizationDtoByDescription(DESCRIPTION);
        });
        verify(authorizationRepository, times(1)).findByDescriptionAndFetchMethods(anyString());
        verifyNoMoreInteractions(authorizationRepository);
    }

    @Test
    void shouldInsertAuthorizationSuccessfully() {
        when(authorizationRepository.findByDescriptionAndFetchMethods(anyString())).thenReturn(Optional.empty());
        when(authorizationRepository.save(any(AuthorizationModel.class))).thenReturn(authorizationModel);
        AuthorizationDto expectedDto = authorizationMapper.modelToDto(authorizationModel);

        AuthorizationDto serviceResponse = authorizationService.insertAuthorization(authorizationForm);

        assertNotNull(serviceResponse);
        assertEquals(expectedDto, serviceResponse);
        assertTrue(authorizationModel.isActive());
        assertEquals(authorizationModel.getCreatedAt(), authorizationModel.getUpdatedAt());

        verify(authorizationRepository, times(1)).findByDescriptionAndFetchMethods(anyString());
        verify(authorizationRepository, times(1)).save(any());
        verifyNoMoreInteractions(authorizationRepository);
    }

    @Test
    void shouldThrowAuthorizationInsertExceptionWhenMethodAlreadyExists() {
        when(authorizationRepository.findByDescriptionAndFetchMethods(anyString())).thenReturn(Optional.of(authorizationModel));

        assertThrows(AuthorizationInsertException.class, () -> {
            authorizationService.insertAuthorization(authorizationForm);
        });

        verify(authorizationRepository, times(1)).findByDescriptionAndFetchMethods(anyString());
        verifyNoMoreInteractions(authorizationRepository);
    }

    @Test
    void shouldThrowAuthorizationInsertExceptionWhenDataIntegrityViolationOccurs() {
        when(authorizationRepository.findByDescriptionAndFetchMethods(anyString())).thenReturn(Optional.empty());
        when(authorizationRepository.save(any(AuthorizationModel.class))).thenThrow(new DataIntegrityViolationException(""));


        assertThrows(DataIntegrityViolationException.class, () -> {
            authorizationService.insertAuthorization(authorizationForm);
        });

        verify(authorizationRepository, times(1)).findByDescriptionAndFetchMethods(anyString());
        verify(authorizationRepository, times(1)).save(any(AuthorizationModel.class));
        verifyNoMoreInteractions(authorizationRepository);
    }

    @Test
    void shouldUpdateAuthorizationSuccessfully() {
        Set<MethodModel> methodModels = MethodBuilder.createMethodModelSet(5);
        for (MethodModel methodModel : methodModels) {
            when(methodService.findMethodModelByDescription(methodModel.getDescription())).thenReturn(methodModel);
        }
        when(authorizationRepository.findByDescriptionAndFetchMethods(anyString())).thenReturn(Optional.of(authorizationModel));
        when(authorizationRepository.save(any(AuthorizationModel.class))).thenReturn(authorizationModel);
        AuthorizationDto expectedDto = authorizationMapper.modelToDto(authorizationModel);

        AuthorizationDto serviceResponse = authorizationService.updateAuthorization(DESCRIPTION, authorizationUpdateForm);

        assertNotNull(serviceResponse);
        assertEquals(expectedDto, serviceResponse);
        assertTrue(authorizationModel.getUpdatedAt().after(authorizationModel.getCreatedAt()));
        assertTrue(authorizationModel.isActive());

        verify(authorizationRepository, times(1)).findByDescriptionAndFetchMethods(anyString());
        verify(authorizationRepository, times(1)).save(any(AuthorizationModel.class));
        verify(authorizationRepository, times(1)).deleteAuthorizationMethods(any(UUID.class));
        verifyNoMoreInteractions(authorizationRepository);
    }

    @Test
    void shouldThrowAuthorizationUpdateExceptionWhenDataIntegrityViolationOccurs() {
        when(authorizationRepository.findByDescriptionAndFetchMethods(anyString())).thenReturn(Optional.of(authorizationModel));
        when(authorizationRepository.save(any(AuthorizationModel.class))).thenThrow(new DataIntegrityViolationException(""));

        assertThrows(AuthorizationUpdateException.class, () -> {
            authorizationService.updateAuthorization(DESCRIPTION, authorizationUpdateForm);
        });

        verify(authorizationRepository, times(1)).findByDescriptionAndFetchMethods(anyString());
        verify(authorizationRepository, times(1)).save(any(AuthorizationModel.class));
        verify(authorizationRepository, times(1)).deleteAuthorizationMethods(any(UUID.class));
        verifyNoMoreInteractions(authorizationRepository);
    }

    @Test
    void shouldDeleteAuthorizationSuccessfully() {
        when(authorizationRepository.findByDescriptionAndFetchMethods(anyString())).thenReturn(Optional.of(authorizationModel));
        when(authorizationRepository.save(any(AuthorizationModel.class))).thenReturn(authorizationModel);

        authorizationService.deleteAuthorization(DESCRIPTION);

        assertFalse(authorizationModel.isActive());
        assertTrue(authorizationModel.getUpdatedAt().after(authorizationModel.getCreatedAt()));

        verify(authorizationRepository, times(1)).findByDescriptionAndFetchMethods(anyString());
        verify(authorizationRepository, times(1)).deleteAuthorizationMethods(any(UUID.class));
        verify(authorizationRepository, times(1)).save(any(AuthorizationModel.class));
        verifyNoMoreInteractions(authorizationRepository);
    }

    @Test
    void shouldThrowDeleteAuthorizationExceptionWhenDataIntegrityViolationOccurs() {
        when(authorizationRepository.findByDescriptionAndFetchMethods(anyString())).thenReturn(Optional.of(authorizationModel));
        when(authorizationRepository.save(any(AuthorizationModel.class))).thenThrow(new DataIntegrityViolationException(""));

        assertThrows(AuthorizationUpdateException.class, () -> {
            authorizationService.deleteAuthorization(DESCRIPTION);
        });

        verify(authorizationRepository, times(1)).findByDescriptionAndFetchMethods(anyString());
        verify(authorizationRepository, times(1)).deleteAuthorizationMethods(any(UUID.class));
        verify(authorizationRepository, times(1)).save(any(AuthorizationModel.class));
        verifyNoMoreInteractions(authorizationRepository);
    }
}