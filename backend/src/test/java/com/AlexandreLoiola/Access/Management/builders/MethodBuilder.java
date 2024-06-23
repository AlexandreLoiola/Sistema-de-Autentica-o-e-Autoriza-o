package com.AlexandreLoiola.Access.Management.builders;

import com.AlexandreLoiola.AccessManagement.model.AuthorizationModel;
import com.AlexandreLoiola.AccessManagement.model.MethodModel;
import com.AlexandreLoiola.AccessManagement.rest.dto.MethodDto;
import com.AlexandreLoiola.AccessManagement.rest.form.AuthorizationForm;
import com.AlexandreLoiola.AccessManagement.rest.form.MethodForm;
import com.AlexandreLoiola.AccessManagement.rest.form.MethodUpdateForm;

import java.util.*;

public class MethodBuilder {

    private static final UUID DEFAULT_ID = UUID.randomUUID();
    private static final String DEFAULT_DESCRIPTION = "method_description";
    private static final Date DEFAULT_DATE = new Date();
    private static final boolean DEFAULT_IS_ACTIVE = true;
    private static final Integer DEFAULT_VERSION = 1;
    private static final Set<AuthorizationModel> DEFAULT_AUTHORIZATIONMODEL = new HashSet<>();


    public static MethodModel createMethodModel() {
        return new MethodModel(DEFAULT_ID, DEFAULT_DESCRIPTION, DEFAULT_DATE, DEFAULT_DATE, DEFAULT_IS_ACTIVE, DEFAULT_VERSION, DEFAULT_AUTHORIZATIONMODEL);
    }

    public static MethodModel createMethodModel(UUID id, String description, Date createdDate, Date modifiedDate, boolean isActive, Integer version, Set<AuthorizationModel> authorizations) {
        return new MethodModel(id, description, createdDate, modifiedDate, isActive, version, authorizations);
    }

    public static MethodForm createMethodForm() {
        return new MethodForm(DEFAULT_DESCRIPTION);
    }

    public static MethodForm createMethodForm(String description) {
        return new MethodForm(description);
    }

    public static MethodUpdateForm createMethodUpdateForm() {
        return new MethodUpdateForm(DEFAULT_DESCRIPTION);
    }

    public static MethodUpdateForm createMethodUpdateForm(String description) {
        return new MethodUpdateForm(description);
    }

    public static MethodDto createMethodDto() {
        return new MethodDto(DEFAULT_DESCRIPTION);
    }

    public static MethodDto createMethodDto(String description) {
        return new MethodDto(description);
    }

    public static Set<MethodModel> createMethodModelSet(int count) {
        Set<MethodModel> methodModels = new HashSet<>();
        for (int i = 0; i < count; i++) {
            UUID id = UUID.randomUUID();
            String description = "description_" + i;
            Date date = new Date();
            boolean isActive = true;
            Integer version = 1;
            Set<AuthorizationModel> authorizations = null;
            methodModels.add(new MethodModel(id, description, date, date, isActive, version, authorizations));
        }
        return methodModels;
    }

    public static Set<MethodForm> createMethodFormSet(int count) {
        Set<MethodForm> methodForms = new HashSet<>();
        for (int i = 0; i < count; i++) {
            String description = "description_" + i;
            methodForms.add(new MethodForm(description));
        }
        return methodForms;
    }

    public static Set<MethodDto> createMethodDtoSet(int count) {
        Set<MethodDto> methodDtos = new HashSet<>();
        for (int i = 0; i < count; i++) {
            String description = "description_" + i;
            methodDtos.add(new MethodDto(description));
        }
        return methodDtos;
    }
}