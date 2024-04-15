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
        // Given: Configuramos o repositório para retornar um conjunto de MethodModel quando o método findByIsActiveTrue for chamado.
        Set<MethodModel> methodModels = Collections.singleton(methodModel);
        when(methodRepository.findByIsActiveTrue()).thenReturn(methodModels);

        // Criamos um conjunto de MethodDto esperado a partir dos MethodModel retornados.
        Set<MethodDto> expectedMethodDtos = new HashSet<>();
        for (MethodModel model : methodModels) {
            expectedMethodDtos.add(methodMapper.modelToDto(model));
        }

        // When: Chamamos o método getAllMethodDto do serviço.
        Set<MethodDto> serviceResponse = methodService.getAllMethodDto();

        // Then: Verificamos se o conjunto de MethodDto retornado é igual ao conjunto esperado.
        assertEquals(expectedMethodDtos, serviceResponse);

        // Verificamos se o método do repositório foi chamado conforme esperado.
        verify(methodRepository, times(1)).findByIsActiveTrue();
        verifyNoMoreInteractions(methodRepository);
    }

    @Test
    void shouldThrowMethodNotFoundExceptionWhenNoActiveMethods() {
        // Given: Configuramos o repositório para retornar um conjunto vazio quando o método findByIsActiveTrue for chamado.
        when(methodRepository.findByIsActiveTrue()).thenReturn(Collections.emptySet());

        // When & Then: Esperamos que uma exceção seja lançada quando tentamos obter todos os MethodDto e não há métodos ativos.
        assertThrows(MethodNotFoundException.class, () -> {
            methodService.getAllMethodDto();
        });

        // Verificamos se o método do repositório foi chamado conforme esperado.
        verify(methodRepository, times(1)).findByIsActiveTrue();
        verifyNoMoreInteractions(methodRepository);
    }

    @Test
    void shouldFindMethodDtoSuccessfully() {
        // Given: Configuramos o repositório para retornar um MethodModel quando o método findByDescriptionAndIsActiveTrue for chamado.
        when(methodRepository.findByDescriptionAndIsActiveTrue(methodForm.getDescription())).thenReturn(Optional.of(methodModel));

        // Criamos um MethodDto esperado a partir do MethodModel retornado.
        MethodDto expectedDto = methodMapper.modelToDto(methodModel);

        // When: Chamamos o método getMethodDtoByDescription do serviço.
        MethodDto serviceResponse = methodService.getMethodDtoByDescription(methodForm.getDescription());

        // Then: Verificamos se o MethodDto retornado é igual ao esperado.
        assertEquals(expectedDto, serviceResponse);

        // Verificamos se o método do repositório foi chamado conforme esperado.
        verify(methodRepository, times(1)).findByDescriptionAndIsActiveTrue(methodModel.getDescription());
        verifyNoMoreInteractions(methodRepository);
    }

    @Test
    void shouldFindMethodModelSuccessfully() {
        // Given: Configuramos o repositório para retornar um MethodModel quando o método findByDescriptionAndIsActiveTrue for chamado.
        when(methodRepository.findByDescriptionAndIsActiveTrue(methodModel.getDescription())).thenReturn(Optional.of(methodModel));

        // When: Chamamos o método findMethodModelByDescription do serviço.
        MethodModel serviceResponse = methodService.findMethodModelByDescription(methodModel.getDescription());

        // Then: Verificamos se o MethodModel retornado é igual ao esperado.
        assertEquals(methodModel, serviceResponse);

        // Verificamos se o método do repositório foi chamado conforme esperado.
        verify(methodRepository, times(1)).findByDescriptionAndIsActiveTrue(methodModel.getDescription());
        verifyNoMoreInteractions(methodRepository);
    }

    @Test
    void shouldThrowMethodNotFoundException() {
        // Given: Configuramos o repositório para retornar vazio quando o método findByDescriptionAndIsActiveTrue for chamado.
        String description = "nonexistentDescription";
        when(methodRepository.findByDescriptionAndIsActiveTrue(description)).thenReturn(Optional.empty());

        // When & Then: Esperamos que uma exceção seja lançada quando tentamos encontrar um MethodModel com uma descrição que não existe.
        assertThrows(MethodNotFoundException.class, () -> {
            methodService.findMethodModelByDescription(description);
        });

        // Verificamos se o método do repositório foi chamado conforme esperado.
        verify(methodRepository, times(1)).findByDescriptionAndIsActiveTrue(description);
        verifyNoMoreInteractions(methodRepository);
    }

    @Test
    void shouldInsertMethodSuccessfully() {
        // Given: Configuramos o repositório para retornar vazio quando o método findByDescriptionAndIsActiveTrue for chamado e para salvar o MethodModel quando o método save for chamado.
        when(methodRepository.findByDescriptionAndIsActiveTrue(anyString())).thenReturn(Optional.empty());
        when(methodRepository.save(any(MethodModel.class))).thenReturn(methodModel);

        // Criamos um MethodDto esperado a partir do MethodModel salvo.
        MethodDto expectedDto = methodMapper.modelToDto(methodModel);

        // When: Chamamos o método insertMethod do serviço.
        MethodDto serviceResponse = methodService.insertMethod(methodForm);

        // Then: Verificamos se o MethodDto retornado é igual ao esperado.
        assertEquals(expectedDto, serviceResponse);

        // Verificamos se os métodos do repositório foram chamados conforme esperado.
        verify(methodRepository, times(1)).findByDescriptionAndIsActiveTrue(anyString());
        verify(methodRepository, times(1)).save(any(MethodModel.class));
        verifyNoMoreInteractions(methodRepository);
    }


    @Test
    void shouldThrowMethodInsertExceptionWhenMethodAlreadyExists() {
        // Given: Configuramos o repositório para retornar um MethodModel quando o método findByDescriptionAndIsActiveTrue for chamado.
        when(methodRepository.findByDescriptionAndIsActiveTrue(methodForm.getDescription())).thenReturn(Optional.of(methodModel));

        // When & Then: Esperamos que uma exceção seja lançada quando tentamos inserir um método que já existe.
        assertThrows(MethodInsertException.class, () -> {
            methodService.insertMethod(methodForm);
        });

        // Verificamos se os métodos do repositório foram chamados conforme esperado.
        verify(methodRepository, times(1)).findByDescriptionAndIsActiveTrue(methodForm.getDescription());
        verifyNoMoreInteractions(methodRepository);
    }

    @Test
    void shouldThrowMethodInsertExceptionWhenDataIntegrityViolationOccurs() {
        // Given: Configuramos o repositório para retornar vazio quando o método findByDescriptionAndIsActiveTrue for chamado e para lançar uma exceção quando o método save for chamado.
        when(methodRepository.findByDescriptionAndIsActiveTrue(methodForm.getDescription())).thenReturn(Optional.empty());
        when(methodRepository.save(any(MethodModel.class))).thenThrow(new DataIntegrityViolationException(""));

        // When & Then: Esperamos que uma exceção seja lançada quando ocorre uma violação de integridade de dados ao tentar inserir um método.
        assertThrows(MethodInsertException.class, () -> {
            methodService.insertMethod(methodForm);
        });

        // Verificamos se os métodos do repositório foram chamados conforme esperado.
        verify(methodRepository, times(1)).findByDescriptionAndIsActiveTrue(methodForm.getDescription());
        verify(methodRepository, times(1)).save(any(MethodModel.class));
        verifyNoMoreInteractions(methodRepository);
    }

    @Test
    void shouldUpdateMethodSuccessfully() {
        // Given: Configuramos o repositório para retornar um MethodModel quando o método findByDescriptionAndIsActiveTrue for chamado e para salvar o MethodModel quando o método save for chamado.
        String description = "description";
        Date initialUpdatedAt = methodModel.getUpdatedAt();
        when(methodRepository.findByDescriptionAndIsActiveTrue(description)).thenReturn(Optional.of(methodModel));
        ArgumentCaptor<MethodModel> captor = ArgumentCaptor.forClass(MethodModel.class);
        when(methodRepository.save(captor.capture())).thenReturn(methodModel);

        // When: Atualizamos o método.
        methodService.updateMethod(description, methodUpdateForm);

        // Then: Verificamos se o método foi atualizado com sucesso.
        MethodModel savedMethod = captor.getValue();
        assertTrue(savedMethod.getUpdatedAt().after(initialUpdatedAt));

        // Verificamos se os métodos do repositório foram chamados conforme esperado.
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