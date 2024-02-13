package com.AlexandreLoiola.AccessManagement.service;

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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MethodService {

    private final MethodRepository methodRepository;

    public MethodService(MethodRepository methodRepository) {
        this.methodRepository = methodRepository;
    }

    public MethodDto getMethodDtoByDescription(String description) {
        MethodModel methodModel = findMethodModelByDescription(description);
        return convertModelToDto(methodModel);
    }

    public MethodModel findMethodModelByDescription(String description) {
        return methodRepository.findByDescriptionAndIsActiveTrue(description)
                .orElseThrow(() -> new MethodNotFoundException(
                        String.format("The method ‘%s’ was not found", description)
                ));
    }

    public List<MethodDto> getAllMethodDto() {
        List<MethodModel> methodModelList = methodRepository.findByIsActiveTrue();
        if (methodModelList.isEmpty()) {
            throw new MethodNotFoundException("No active user method was found");
        }
        return convertModelListToDtoList(methodModelList);
    }

    @Transactional
    public MethodDto insertMethod(MethodForm methodForm) {
        if (methodRepository.findByDescription(methodForm.getDescription()).isPresent()) {
            throw new MethodInsertException(
                    String.format("The user method ‘%s’ is already registered", methodForm.getDescription())
            );
        }
        try {
            MethodModel methodModel = convertFormToModel(methodForm);
            Date date = new Date();
            methodModel.setCreatedAt(date);
            methodModel.setUpdatedAt(date);
            methodModel.setIsActive(true);
            methodModel.setVersion("1.0.0");
            methodRepository.save(methodModel);
            return convertModelToDto(methodModel);
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
            return convertModelToDto(methodModel);
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

    private MethodDto convertModelToDto(MethodModel methodModel) {
        MethodDto methodDto = new MethodDto();
        methodDto.setDescription(methodModel.getDescription());
        return methodDto;
    }

    private List<MethodDto> convertModelListToDtoList(List<MethodModel> list) {
        List<MethodDto> methodDtoList = new ArrayList<>();
        for (MethodModel methodModel : list) {
            MethodDto methodDto = convertModelToDto(methodModel);
            methodDtoList.add(methodDto);
        }
        return methodDtoList;
    }

    private MethodModel convertFormToModel(MethodForm methodForm) {
        MethodModel methodModel = new MethodModel();
        methodModel.setDescription(methodForm.getDescription());
        return methodModel;
    }
}
