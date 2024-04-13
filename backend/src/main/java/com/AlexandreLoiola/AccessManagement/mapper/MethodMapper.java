package com.AlexandreLoiola.AccessManagement.mapper;

import com.AlexandreLoiola.AccessManagement.model.MethodModel;
import com.AlexandreLoiola.AccessManagement.rest.dto.MethodDto;
import com.AlexandreLoiola.AccessManagement.rest.form.MethodForm;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper(componentModel = "spring")
public abstract class MethodMapper {

    public static final MethodMapper INSTANCE = Mappers.getMapper(MethodMapper.class);

    public abstract MethodDto modelToDto(MethodModel model);
    public abstract MethodModel formToModel(MethodForm form);
    public abstract Set<MethodDto> setModelToSetDto(Set<MethodModel> models);
}
