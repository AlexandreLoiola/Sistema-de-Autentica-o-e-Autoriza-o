package com.AlexandreLoiola.Access.Management.service;

import com.AlexandreLoiola.AccessManagement.mapper.MethodMapper;
import com.AlexandreLoiola.AccessManagement.mapper.MethodMapperImpl;
import com.AlexandreLoiola.AccessManagement.model.MethodModel;
import com.AlexandreLoiola.AccessManagement.repository.MethodRepository;
import com.AlexandreLoiola.AccessManagement.rest.dto.MethodDto;
import com.AlexandreLoiola.AccessManagement.rest.form.MethodForm;
import com.AlexandreLoiola.AccessManagement.rest.form.MethodUpdateForm;
import com.AlexandreLoiola.AccessManagement.service.MethodService;
import com.AlexandreLoiola.AccessManagement.service.exceptions.method.MethodInsertException;
import com.AlexandreLoiola.AccessManagement.service.exceptions.method.MethodNotFoundException;
import com.AlexandreLoiola.AccessManagement.service.exceptions.method.MethodUpdateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MethodServiceTest {

    @InjectMocks
    private MethodService methodService;

    @Mock
    private MethodRepository methodRepository;

    @Spy
    private MethodMapper methodMapper = new MethodMapperImpl();

    @Mock
    private MethodModel methodModel;

    @Mock
    private MethodForm methodForm;

    @Mock
    private MethodDto methodDto;

    @Mock
    private MethodUpdateForm methodUpdateForm;

    @BeforeEach
    public void setup() {
        UUID id = UUID.randomUUID();
        Date date = new Date();
        methodModel = new MethodModel();
        methodModel.setId(id);
        methodModel.setDescription("description");
        methodModel.setIsActive(true);
        methodModel.setVersion(1);
        methodModel.setCreatedAt(date);
        methodModel.setUpdatedAt(date);

        methodForm = new MethodForm();
        methodForm.setDescription("description");

        methodDto = new MethodDto();
        methodDto.setDescription("description");

        methodUpdateForm = new MethodUpdateForm();
        methodUpdateForm.setDescription("description");
    }

    @Test
    void shouldFindAllMethodsSuccessfully() {
        // Given
        Set<MethodModel> methodModels = Collections.singleton(methodModel);
        when(methodRepository.findByIsActiveTrue()).thenReturn(methodModels);
        Set<MethodDto> expectedMethodDtos = new HashSet<>();
        for (MethodModel model : methodModels) {
            expectedMethodDtos.add(methodMapper.modelToDto(model));
        }

        // When
        Set<MethodDto> serviceResponse = methodService.getAllMethodDto();

        // Then
        assertEquals(expectedMethodDtos, serviceResponse);
        verify(methodRepository, times(1)).findByIsActiveTrue();
        verifyNoMoreInteractions(methodRepository);
    }

    @Test
    void shouldThrowMethodNotFoundExceptionWhenNoActiveMethods() {
        // Given
        when(methodRepository.findByIsActiveTrue()).thenReturn(Collections.emptySet());

        // When & Then
        assertThrows(MethodNotFoundException.class, () -> {
            methodService.getAllMethodDto();
        });
        verify(methodRepository, times(1)).findByIsActiveTrue();
        verifyNoMoreInteractions(methodRepository);
    }

    @Test
    void shouldFindMethodDtoSuccessfully() {
        // Given
        when(methodRepository.findByDescriptionAndIsActiveTrue(methodForm.getDescription())).thenReturn(Optional.of(methodModel));
        MethodDto expectedDto = methodMapper.modelToDto(methodModel);

        // When
        MethodDto serviceResponse = methodService.getMethodDtoByDescription(methodForm.getDescription());

        // Then
        assertEquals(expectedDto, serviceResponse);
        verify(methodRepository, times(1)).findByDescriptionAndIsActiveTrue(methodModel.getDescription());
        verifyNoMoreInteractions(methodRepository);
    }

    @Test
    void shouldFindMethodModelSuccessfully() {
        // Given
        when(methodRepository.findByDescriptionAndIsActiveTrue(methodModel.getDescription())).thenReturn(Optional.of(methodModel));

        // When
        MethodModel serviceResponse = methodService.findMethodModelByDescription(methodModel.getDescription());

        // Then
        assertEquals(methodModel, serviceResponse);
        verify(methodRepository, times(1)).findByDescriptionAndIsActiveTrue(methodModel.getDescription());
        verifyNoMoreInteractions(methodRepository);
    }

    @Test
    void shouldThrowMethodNotFoundException() {
        // Given
        String description = "nonexistentDescription";
        when(methodRepository.findByDescriptionAndIsActiveTrue(description)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(MethodNotFoundException.class, () -> {
            methodService.findMethodModelByDescription(description);
        });
        verify(methodRepository, times(1)).findByDescriptionAndIsActiveTrue(description);
        verifyNoMoreInteractions(methodRepository);
    }

    @Test
    void shouldInsertMethodSuccessfully() {
        // Given
        when(methodRepository.findByDescriptionAndIsActiveTrue(anyString())).thenReturn(Optional.empty());
        when(methodRepository.save(any(MethodModel.class))).thenReturn(methodModel);
        MethodDto expectedDto = methodMapper.modelToDto(methodModel);

        // When
        MethodDto serviceResponse = methodService.insertMethod(methodForm);

        // Then
        assertEquals(expectedDto, serviceResponse);
        verify(methodRepository, times(1)).findByDescriptionAndIsActiveTrue(anyString());
        verify(methodRepository, times(1)).save(any(MethodModel.class));
        verifyNoMoreInteractions(methodRepository);
    }


    @Test
    void shouldThrowMethodInsertExceptionWhenMethodAlreadyExists() {
        // Given
        when(methodRepository.findByDescriptionAndIsActiveTrue(methodForm.getDescription())).thenReturn(Optional.of(methodModel));

        // When & Then
        assertThrows(MethodInsertException.class, () -> {
            methodService.insertMethod(methodForm);
        });
        verify(methodRepository, times(1)).findByDescriptionAndIsActiveTrue(methodForm.getDescription());
        verifyNoMoreInteractions(methodRepository);
    }

    @Test
    void shouldThrowMethodInsertExceptionWhenDataIntegrityViolationOccurs() {
        // Given
        when(methodRepository.findByDescriptionAndIsActiveTrue(methodForm.getDescription())).thenReturn(Optional.empty());
        when(methodRepository.save(any(MethodModel.class))).thenThrow(new DataIntegrityViolationException(""));

        // When & Then
        assertThrows(MethodInsertException.class, () -> {
            methodService.insertMethod(methodForm);
        });
        verify(methodRepository, times(1)).findByDescriptionAndIsActiveTrue(methodForm.getDescription());
        verify(methodRepository, times(1)).save(any(MethodModel.class));
        verifyNoMoreInteractions(methodRepository);
    }

    @Test
    void shouldUpdateMethodSuccessfully() {
        // Given
        String description = "description";
        Date initialUpdatedAt = methodModel.getUpdatedAt();
        when(methodRepository.findByDescriptionAndIsActiveTrue(description)).thenReturn(Optional.of(methodModel));
        ArgumentCaptor<MethodModel> captor = ArgumentCaptor.forClass(MethodModel.class);
        when(methodRepository.save(captor.capture())).thenReturn(methodModel);

        // When
        methodService.updateMethod(description, methodUpdateForm);

        // Then
        MethodModel savedMethod = captor.getValue();
        assertTrue(savedMethod.getUpdatedAt().after(initialUpdatedAt));
        verify(methodRepository, times(1)).findByDescriptionAndIsActiveTrue(description);
        verify(methodRepository, times(1)).save(any(MethodModel.class));
        verifyNoMoreInteractions(methodRepository);
    }

    @Test
    void shouldThrowMethodUpdateExceptionWhenUpdateMethodFails() {
        // Given
        when(methodRepository.findByDescriptionAndIsActiveTrue(anyString())).thenReturn(Optional.of(methodModel));
        doThrow(DataIntegrityViolationException.class).when(methodRepository).save(any(MethodModel.class));

        // When & Then
        assertThrows(MethodUpdateException.class, () -> {
            methodService.updateMethod(methodForm.getDescription(), methodUpdateForm);
        });
        verify(methodRepository, times(1)).findByDescriptionAndIsActiveTrue(anyString());
        verify(methodRepository, times(1)).save(any(MethodModel.class));
        verifyNoMoreInteractions(methodRepository);
    }

    @Test
    void shouldDeleteMethodSuccessfully() {
        // Given
        String description = "description";
        Date initialUpdatedAt = methodModel.getUpdatedAt();
        when(methodRepository.findByDescriptionAndIsActiveTrue(description)).thenReturn(Optional.of(methodModel));
        ArgumentCaptor<MethodModel> captor = ArgumentCaptor.forClass(MethodModel.class);
        when(methodRepository.save(captor.capture())).thenReturn(methodModel);

        // When
        methodService.deleteMethod(description);

        // Then
        MethodModel savedMethod = captor.getValue();
        assertTrue(savedMethod.getUpdatedAt().after(initialUpdatedAt));
        assertFalse(methodModel.getIsActive());
        verify(methodRepository, times(1)).findByDescriptionAndIsActiveTrue(description);
        verify(methodRepository, times(1)).save(any(MethodModel.class));
        verifyNoMoreInteractions(methodRepository);
    }

    @Test
    void shouldThrowMethodUpdateExceptionWhenDeleteMethodFails() {
        // Given
        when(methodRepository.findByDescriptionAndIsActiveTrue(anyString())).thenReturn(Optional.of(methodModel));
        doThrow(DataIntegrityViolationException.class).when(methodRepository).save(any(MethodModel.class));

        // When & Then
        assertThrows(MethodUpdateException.class, () -> {
            methodService.deleteMethod(methodForm.getDescription());
        });

        verify(methodRepository, times(1)).findByDescriptionAndIsActiveTrue(anyString());
        verify(methodRepository, times(1)).save(any(MethodModel.class));
        verifyNoMoreInteractions(methodRepository);
    }
}