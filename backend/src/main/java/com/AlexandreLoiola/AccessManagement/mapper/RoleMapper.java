package com.AlexandreLoiola.AccessManagement.mapper;

import com.AlexandreLoiola.AccessManagement.model.AuthorizationModel;
import com.AlexandreLoiola.AccessManagement.model.RoleModel;
import com.AlexandreLoiola.AccessManagement.rest.dto.RoleDto;
import com.AlexandreLoiola.AccessManagement.rest.form.RoleForm;
import com.AlexandreLoiola.AccessManagement.service.AuthorizationService;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

@Mapper(componentModel = "spring")
public abstract class RoleMapper {

    public static final RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    public abstract RoleDto modelToDto(RoleModel model);
    public abstract RoleModel formToModel(RoleForm form);
    public abstract Set<RoleDto> setModelToSetDto(Set<RoleModel> models);

}
