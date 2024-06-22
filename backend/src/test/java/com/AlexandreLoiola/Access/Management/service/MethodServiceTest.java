package com.AlexandreLoiola.Access.Management.service;

import com.AlexandreLoiola.Access.Management.builders.MethodBuilder;
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

    private static final String DESCRIPTION = "description";

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
        methodModel = MethodBuilder.createMethodModel();
        methodForm = MethodBuilder.createMethodForm();
        methodDto = MethodBuilder.createMethodDto();
        methodUpdateForm = MethodBuilder.createMethodUpdateForm();
    }

    @Test
    void shouldFindAllMethodsSuccessfully() {
        Set<MethodModel> methodModels = Collections.singleton(methodModel);
        when(methodRepository.findByIsActiveTrue()).thenReturn(methodModels);
        Set<MethodDto> expectedMethodDtos = new HashSet<>();
        for (MethodModel model : methodModels) {
            var a = methodMapper.modelToDto(model);
            expectedMethodDtos.add(a);
        }

        Set<MethodDto> serviceResponse = methodService.getAllMethodDto();

        assertEquals(expectedMethodDtos, serviceResponse);
        verify(methodRepository, times(1)).findByIsActiveTrue();
        verifyNoMoreInteractions(methodRepository);
    }

    @Test
    void shouldThrowMethodNotFoundExceptionWhenNoActiveMethods() {
        when(methodRepository.findByIsActiveTrue()).thenReturn(Collections.emptySet());

        assertThrows(MethodNotFoundException.class, () -> {
            methodService.getAllMethodDto();
        });
        verify(methodRepository, times(1)).findByIsActiveTrue();
        verifyNoMoreInteractions(methodRepository);
    }

    @Test
    void shouldFindMethodDtoSuccessfully() {
        when(methodRepository.findByDescriptionAndIsActiveTrue(anyString())).thenReturn(Optional.of(methodModel));
        MethodDto expectedDto = methodMapper.modelToDto(methodModel);

        MethodDto serviceResponse = methodService.getMethodDtoByDescription(DESCRIPTION);

        assertNotNull(serviceResponse);
        assertEquals(expectedDto, serviceResponse);
        assertEquals(DESCRIPTION, serviceResponse.getDescription());
        verify(methodRepository, times(1)).findByDescriptionAndIsActiveTrue(DESCRIPTION);
        verifyNoMoreInteractions(methodRepository);
    }

    @Test
    void shouldFindMethodModelSuccessfully() {
        when(methodRepository.findByDescriptionAndIsActiveTrue(DESCRIPTION)).thenReturn(Optional.of(methodModel));

        MethodModel serviceResponse = methodService.findMethodModelByDescription(DESCRIPTION);

        assertEquals(methodModel, serviceResponse);
        verify(methodRepository, times(1)).findByDescriptionAndIsActiveTrue(DESCRIPTION);
        verifyNoMoreInteractions(methodRepository);
    }

    @Test
    void shouldThrowMethodNotFoundException() {
        when(methodRepository.findByDescriptionAndIsActiveTrue(DESCRIPTION)).thenReturn(Optional.empty());

        assertThrows(MethodNotFoundException.class, () -> {
            methodService.findMethodModelByDescription(DESCRIPTION);
        });
        verify(methodRepository, times(1)).findByDescriptionAndIsActiveTrue(DESCRIPTION);
        verifyNoMoreInteractions(methodRepository);
    }

    @Test
    void shouldInsertMethodSuccessfully() {
        when(methodRepository.findByDescriptionAndIsActiveTrue(anyString())).thenReturn(Optional.empty());
        when(methodRepository.save(any(MethodModel.class))).thenReturn(methodModel);
        MethodDto expectedDto = methodMapper.modelToDto(methodModel);

        MethodDto serviceResponse = methodService.insertMethod(methodForm);

        assertEquals(expectedDto, serviceResponse);
        verify(methodRepository, times(1)).findByDescriptionAndIsActiveTrue(anyString());
        verify(methodRepository, times(1)).save(any(MethodModel.class));
        verifyNoMoreInteractions(methodRepository);
    }

    @Test
    void shouldThrowMethodInsertExceptionWhenMethodAlreadyExists() {
        when(methodRepository.findByDescriptionAndIsActiveTrue(DESCRIPTION)).thenReturn(Optional.of(methodModel));

        assertThrows(MethodInsertException.class, () -> {
            methodService.insertMethod(methodForm);
        });
        verify(methodRepository, times(1)).findByDescriptionAndIsActiveTrue(DESCRIPTION);
        verifyNoMoreInteractions(methodRepository);
    }

    @Test
    void shouldThrowMethodInsertExceptionWhenDataIntegrityViolationOccurs() {
        when(methodRepository.findByDescriptionAndIsActiveTrue(DESCRIPTION)).thenReturn(Optional.empty());
        when(methodRepository.save(any(MethodModel.class))).thenThrow(new DataIntegrityViolationException(""));

        assertThrows(MethodInsertException.class, () -> {
            methodService.insertMethod(methodForm);
        });
        verify(methodRepository, times(1)).findByDescriptionAndIsActiveTrue(DESCRIPTION);
        verify(methodRepository, times(1)).save(any(MethodModel.class));
        verifyNoMoreInteractions(methodRepository);
    }

    @Test
    void shouldUpdateMethodSuccessfully() {
        Date initialUpdatedAt = methodModel.getUpdatedAt();
        when(methodRepository.findByDescriptionAndIsActiveTrue(DESCRIPTION)).thenReturn(Optional.of(methodModel));
        ArgumentCaptor<MethodModel> captor = ArgumentCaptor.forClass(MethodModel.class);
        when(methodRepository.save(captor.capture())).thenReturn(methodModel);

        methodService.updateMethod(DESCRIPTION, methodUpdateForm);

        MethodModel savedMethod = captor.getValue();
        assertTrue(savedMethod.getUpdatedAt().after(initialUpdatedAt));
        verify(methodRepository, times(1)).findByDescriptionAndIsActiveTrue(DESCRIPTION);
        verify(methodRepository, times(1)).save(any(MethodModel.class));
        verifyNoMoreInteractions(methodRepository);
    }

    @Test
    void shouldThrowMethodUpdateExceptionWhenUpdateMethodFails() {
        when(methodRepository.findByDescriptionAndIsActiveTrue(anyString())).thenReturn(Optional.of(methodModel));
        doThrow(DataIntegrityViolationException.class).when(methodRepository).save(any(MethodModel.class));

        assertThrows(MethodUpdateException.class, () -> {
            methodService.updateMethod(DESCRIPTION, methodUpdateForm);
        });
        verify(methodRepository, times(1)).findByDescriptionAndIsActiveTrue(anyString());
        verify(methodRepository, times(1)).save(any(MethodModel.class));
        verifyNoMoreInteractions(methodRepository);
    }

    @Test
    void shouldDeleteMethodSuccessfully() {
        Date initialUpdatedAt = methodModel.getUpdatedAt();
        when(methodRepository.findByDescriptionAndIsActiveTrue(DESCRIPTION)).thenReturn(Optional.of(methodModel));
        ArgumentCaptor<MethodModel> captor = ArgumentCaptor.forClass(MethodModel.class);
        when(methodRepository.save(captor.capture())).thenReturn(methodModel);

        methodService.deleteMethod(DESCRIPTION);

        MethodModel savedMethod = captor.getValue();
        assertTrue(savedMethod.getUpdatedAt().after(initialUpdatedAt));
        verify(methodRepository, times(1)).findByDescriptionAndIsActiveTrue(DESCRIPTION);
        verify(methodRepository, times(1)).save(any(MethodModel.class));
        verifyNoMoreInteractions(methodRepository);
    }

    @Test
    void shouldThrowMethodUpdateExceptionWhenDeleteMethodFails() {
        when(methodRepository.findByDescriptionAndIsActiveTrue(anyString())).thenReturn(Optional.of(methodModel));
        doThrow(DataIntegrityViolationException.class).when(methodRepository).save(any(MethodModel.class));

        assertThrows(MethodUpdateException.class, () -> {
            methodService.deleteMethod(DESCRIPTION);
        });

        verify(methodRepository, times(1)).findByDescriptionAndIsActiveTrue(anyString());
        verify(methodRepository, times(1)).save(any(MethodModel.class));
        verifyNoMoreInteractions(methodRepository);
    }
}