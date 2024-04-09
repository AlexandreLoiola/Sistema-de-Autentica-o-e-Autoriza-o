package com.AlexandreLoiola.AccessManagement.mapper;

import com.AlexandreLoiola.AccessManagement.model.RoleModel;
import com.AlexandreLoiola.AccessManagement.rest.dto.RoleDto;
import com.AlexandreLoiola.AccessManagement.rest.form.RoleForm;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper(componentModel = "spring")
public abstract class RoleMapper {

    public static final RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    public abstract RoleDto modelToDto(RoleModel model);
    public abstract RoleModel dtoToModel(RoleDto dto);
    public abstract RoleModel formToModel(RoleForm form);
    public abstract RoleForm modelToForm(RoleModel model);
    public abstract Set<RoleDto> setModelToSetDto(Set<RoleModel> models);
}
