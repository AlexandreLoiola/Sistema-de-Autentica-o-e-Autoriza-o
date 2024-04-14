package com.AlexandreLoiola.AccessManagement.service;

import com.AlexandreLoiola.AccessManagement.mapper.MethodMapper;
import com.AlexandreLoiola.AccessManagement.model.MethodModel;
import com.AlexandreLoiola.AccessManagement.repository.MethodRepository;
import com.AlexandreLoiola.AccessManagement.rest.dto.MethodDto;
import com.AlexandreLoiola.AccessManagement.rest.form.MethodForm;
import com.AlexandreLoiola.AccessManagement.rest.form.MethodUpdateForm;
import com.AlexandreLoiola.AccessManagement.service.exceptions.method.MethodInsertException;
import com.AlexandreLoiola.AccessManagement.service.exceptions.method.MethodNotFoundException;
import com.AlexandreLoiola.AccessManagement.service.exceptions.method.MethodUpdateException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class MethodService {

    private final MethodMapper methodMapper;
    private final MethodRepository methodRepository;

    public MethodService(MethodRepository methodRepository, MethodMapper methodMapper) {
        this.methodRepository = methodRepository;
        this.methodMapper = methodMapper;
    }

    public MethodDto getMethodDtoByDescription(String description) {
        MethodModel methodModel = findMethodModelByDescription(description);
        return MethodMapper.INSTANCE.modelToDto(methodModel);
    }

    public MethodModel findMethodModelByDescription(String description) {
        return methodRepository.findByDescriptionAndIsActiveTrue(description)
                .orElseThrow(() -> new MethodNotFoundException(
                        String.format("The method ‘%s’ was not found", description)
                ));
    }

    public Set<MethodDto> getAllMethodDto() {
        Set<MethodModel> methodModelSet = methodRepository.findByIsActiveTrue();
        if (methodModelSet.isEmpty()) {
            throw new MethodNotFoundException("No active user method was found");
        }
        return MethodMapper.INSTANCE.setModelToSetDto(methodModelSet);
    }

    @Transactional
    public MethodDto insertMethod(MethodForm methodForm) {
        if (methodRepository.findByDescriptionAndIsActiveTrue(methodForm.getDescription()).isPresent()) {
            throw new MethodInsertException(
                    String.format("The user method ‘%s’ is already registered", methodForm.getDescription())
            );
        }
        try {
            MethodModel methodModel = MethodMapper.INSTANCE.formToModel(methodForm);
            Date date = new Date();
            methodModel.setCreatedAt(date);
            methodModel.setUpdatedAt(date);
            methodModel.setIsActive(true);
            methodModel.setVersion(1);
            methodRepository.save(methodModel);
            return MethodMapper.INSTANCE.modelToDto(methodModel);
        } catch (DataIntegrityViolationException err) {
            throw new MethodInsertException(String.format("Failed to register the method ‘%s’. Check if the data is correct", methodForm.getDescription()));
        }
    }

    @Transactional
    public MethodDto updateMethod(String description, MethodUpdateForm methodUpdateForm) {
        try {
            MethodModel methodModel = findMethodModelByDescription(description);
            methodModel.setDescription(methodUpdateForm.getDescription());
            methodModel.setUpdatedAt(new Date());
            methodRepository.save(methodModel);
            return MethodMapper.INSTANCE.modelToDto(methodModel);
        } catch (DataIntegrityViolationException err) {
            throw new MethodUpdateException(String.format("Failed to update the user method ‘%s’. Check if the data is correct", description));
        }
    }

    @Transactional
    public void deleteMethod(String description) {
        try {
            MethodModel methodModel = findMethodModelByDescription(description);
            methodModel.setIsActive(false);
            methodModel.setUpdatedAt(new Date());
            methodRepository.save(methodModel);
        } catch (DataIntegrityViolationException err) {
            throw new MethodUpdateException(String.format("Failed to update the user method ‘%s’. Check if the data is correct", description));
        }
    }
}
