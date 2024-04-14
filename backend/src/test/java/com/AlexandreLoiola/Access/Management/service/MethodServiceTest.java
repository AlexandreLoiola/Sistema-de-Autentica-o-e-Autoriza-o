package com.AlexandreLoiola.Access.Management.service;

import com.AlexandreLoiola.AccessManagement.mapper.MethodMapper;
import com.AlexandreLoiola.AccessManagement.mapper.MethodMapperImpl;
import com.AlexandreLoiola.AccessManagement.model.MethodModel;
import com.AlexandreLoiola.AccessManagement.repository.MethodRepository;
import com.AlexandreLoiola.AccessManagement.rest.dto.MethodDto;
import com.AlexandreLoiola.AccessManagement.rest.form.MethodForm;
import com.AlexandreLoiola.AccessManagement.rest.form.MethodUpdateForm;
import com.AlexandreLoiola.AccessManagement.service.MethodService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

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
    }

    @Test
    void shouldFindAllMethodsSuccessfully() {
        Set<MethodModel> methodModels = Collections.singleton(methodModel);
        when(methodRepository.findByIsActiveTrue()).thenReturn(methodModels);
        Set<MethodDto> expectedMethodDtos = new HashSet<>();
        for (MethodModel model : methodModels) {
            expectedMethodDtos.add(methodMapper.modelToDto(model));
        }
        Set<MethodDto> serviceResponse = methodService.getAllMethodDto();
        assertEquals(expectedMethodDtos, serviceResponse);
        verify(methodRepository, times(1)).findByIsActiveTrue();
        verifyNoMoreInteractions(methodRepository);
    }

    @Test
    void shouldFindMethodDtoSuccessfully() {
        when(methodRepository.findByDescriptionAndIsActiveTrue(methodForm.getDescription())).thenReturn(Optional.of(methodModel));
        MethodDto expectedDto = methodMapper.modelToDto(methodModel);

        MethodDto serviceResponse = methodService.getMethodDtoByDescription(methodForm.getDescription());
        assertEquals(expectedDto, serviceResponse);
        verify(methodRepository, times(1)).findByDescriptionAndIsActiveTrue(methodModel.getDescription());
        verifyNoMoreInteractions(methodRepository);
    }

    @Test
    void shouldFindMethodModelSuccessfully() {
        when(methodRepository.findByDescriptionAndIsActiveTrue(methodModel.getDescription())).thenReturn(Optional.of(methodModel));
        MethodModel serviceResponse = methodService.findMethodModelByDescription(methodModel.getDescription());
        assertEquals(methodModel, serviceResponse);
        verify(methodRepository, times(1)).findByDescriptionAndIsActiveTrue(methodModel.getDescription());
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
    void shouldDeleteMethodSuccessfully() {
        String description = "description";
        MethodModel methodModel = mock(MethodModel.class);
        when(methodModel.getDescription()).thenReturn(description);
        when(methodRepository.findByDescriptionAndIsActiveTrue(description)).thenReturn(Optional.of(methodModel));
        when(methodRepository.save(any(MethodModel.class))).thenReturn(methodModel);
        methodService.deleteMethod(description);
        verify(methodModel, times(1)).setIsActive(false);
        verify(methodModel, times(1)).setUpdatedAt(any(Date.class));
        verify(methodRepository, times(1)).findByDescriptionAndIsActiveTrue(description);
        verify(methodRepository, times(1)).save(any(MethodModel.class));
    }

}