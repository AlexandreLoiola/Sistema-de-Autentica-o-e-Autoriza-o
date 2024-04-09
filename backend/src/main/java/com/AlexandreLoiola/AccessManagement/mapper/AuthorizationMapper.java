package com.AlexandreLoiola.AccessManagement.mapper;

import com.AlexandreLoiola.AccessManagement.model.AuthorizationModel;
import com.AlexandreLoiola.AccessManagement.rest.dto.AuthorizationDto;
import com.AlexandreLoiola.AccessManagement.rest.form.AuthorizationForm;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper(componentModel = "spring")
public abstract class AuthorizationMapper {

    public static final AuthorizationMapper INSTANCE = Mappers.getMapper(AuthorizationMapper.class);

    public abstract AuthorizationDto modelToDto(AuthorizationModel model);
    @InheritInverseConfiguration
    public abstract AuthorizationModel dtoToModel(AuthorizationDto dto);
    public abstract AuthorizationModel formToModel(AuthorizationForm form);
    public abstract AuthorizationForm modelToForm(AuthorizationModel model);
    public abstract Set<AuthorizationDto> setModelToSetDto(Set<AuthorizationModel> models);
}
