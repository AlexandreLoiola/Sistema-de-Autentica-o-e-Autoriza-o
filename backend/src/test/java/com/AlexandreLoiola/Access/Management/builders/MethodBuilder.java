package com.AlexandreLoiola.Access.Management.builders;

import com.AlexandreLoiola.AccessManagement.model.MethodModel;
import com.AlexandreLoiola.AccessManagement.rest.dto.MethodDto;
import com.AlexandreLoiola.AccessManagement.rest.form.MethodForm;
import com.AlexandreLoiola.AccessManagement.rest.form.MethodUpdateForm;

import java.util.Date;
import java.util.UUID;

public class MethodBuilder {

    private static final UUID DEFAULT_ID = UUID.randomUUID();
    private static final String DEFAULT_DESCRIPTION = "description";
    private static final Date DEFAULT_DATE = new Date();
    private static final boolean DEFAULT_IS_ACTIVE = true;
    private static final Integer DEFAULT_VERSION = 1;

    public static MethodModel createMethodModel() {
        return new MethodModel(DEFAULT_ID, DEFAULT_DESCRIPTION, DEFAULT_DATE, DEFAULT_DATE, DEFAULT_IS_ACTIVE, DEFAULT_VERSION, null);
    }

    public static MethodModel createMethodModel(UUID id, String description, Date createdDate, Date modifiedDate, boolean isActive, Integer version) {
        return new MethodModel(id, description, createdDate, modifiedDate, isActive, version, null);
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
}
