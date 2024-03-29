package com.AlexandreLoiola.AccessManagement.service;

import com.AlexandreLoiola.AccessManagement.model.UserModel;
import com.AlexandreLoiola.AccessManagement.repository.UserRepository;
import com.AlexandreLoiola.AccessManagement.rest.dto.UserDto;
import com.AlexandreLoiola.AccessManagement.rest.form.UserForm;
import com.AlexandreLoiola.AccessManagement.rest.form.UserUpdateForm;
import com.AlexandreLoiola.AccessManagement.service.exceptions.user.UserInsertException;
import com.AlexandreLoiola.AccessManagement.service.exceptions.user.UserNotFoundException;
import com.AlexandreLoiola.AccessManagement.service.exceptions.user.UserUpdateException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto getUserDtoByDescription(String description) {
        UserModel userModel = findUserModelByDescription(description);
        return convertModelToDto(userModel);
    }

    public UserModel findUserModelByDescription(String description) {
        return userRepository.findByDescriptionAndIsActiveTrue(description)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("The user ‘%s’ was not found", description)
                ));
    }

    public List<UserDto> getAllUserDto() {
        List<UserModel> userModelList = userRepository.findByIsActiveTrue();
        if (userModelList.isEmpty()) {
            throw new UserNotFoundException("No active user was found");
        }
        return convertModelListToDtoList(userModelList);
    }

    @Transactional
    public UserDto insertUser(UserForm userForm) {
        if (userRepository.findByDescription(userForm.getDescription()).isPresent()) {
            throw new UserInsertException(
                    String.format("The user ‘%s’ is already registered", userForm.getDescription())
            );
        }
        try {
            UserModel userModel = convertFormToModel(userForm);
            Date date = new Date();
            userModel.setCreatedAt(date);
            userModel.setUpdatedAt(date);
            userModel.setIsActive(true);
            userModel.setVersion(1);
            userRepository.save(userModel);
            return convertModelToDto(userModel);
        } catch (DataIntegrityViolationException err) {
            throw new UserInsertException(String.format("Failed to register the user ‘%s’. Check if the data is correct", userForm.getDescription()));
        }
    }

    @Transactional
    public UserDto updateUser(String description, UserUpdateForm userUpdateForm) {
        try {
            UserModel userModel = findUserModelByDescription(description);
            userModel.setDescription(userUpdateForm.getDescription());
            userModel.setUpdatedAt(new Date());
            userRepository.save(userModel);
            return convertModelToDto(userModel);
        } catch (DataIntegrityViolationException err) {
            throw new UserUpdateException(String.format("Failed to update the user ‘%s’. Check if the data is correct", description));
        }
    }

    @Transactional
    public void deleteUser(String description) {
        try {
            UserModel userModel = findUserModelByDescription(description);
            userModel.setIsActive(false);
            userModel.setUpdatedAt(new Date());
            userRepository.save(userModel);
        } catch (DataIntegrityViolationException err) {
            throw new UserUpdateException(String.format("Failed to update the user ‘%s’. Check if the data is correct", description));
        }
    }

    private UserDto convertModelToDto(UserModel userModel) {
        UserDto userDto = new UserDto();
        userDto.setDescription(userModel.getDescription());
        return userDto;
    }

    private List<UserDto> convertModelListToDtoList(List<UserModel> list) {
        List<UserDto> userDtoList = new ArrayList<>();
        for (UserModel userModel : list) {
            UserDto userDto = convertModelToDto(userModel);
            userDtoList.add(userDto);
        }
        return userDtoList;
    }

    private UserModel convertFormToModel(UserForm userForm) {
        UserModel userModel = new UserModel();
        userModel.setDescription(userForm.getDescription());
        return userModel;
    }
}
