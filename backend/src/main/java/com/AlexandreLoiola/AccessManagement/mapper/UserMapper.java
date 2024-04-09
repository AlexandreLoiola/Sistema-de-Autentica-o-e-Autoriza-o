package com.AlexandreLoiola.AccessManagement.mapper;

import com.AlexandreLoiola.AccessManagement.model.UserModel;
import com.AlexandreLoiola.AccessManagement.rest.dto.UserDto;
import com.AlexandreLoiola.AccessManagement.rest.form.UserForm;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    public static final UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    public abstract UserDto modelToDto(UserModel model);
    public abstract UserModel dtoToModel(UserDto dto);
    public abstract UserModel formToModel(UserForm form);
    public abstract UserForm modelToForm(UserModel model);
    public abstract Set<UserDto> setModelToSetDto(Set<UserModel> models);
}
