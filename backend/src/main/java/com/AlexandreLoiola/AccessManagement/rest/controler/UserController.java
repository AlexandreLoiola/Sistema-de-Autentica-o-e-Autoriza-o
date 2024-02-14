package com.AlexandreLoiola.AccessManagement.rest.controler;


import com.AlexandreLoiola.AccessManagement.rest.dto.UserDto;
import com.AlexandreLoiola.AccessManagement.rest.form.UserForm;
import com.AlexandreLoiola.AccessManagement.rest.form.UserUpdateForm;
import com.AlexandreLoiola.AccessManagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUser() {
        List<UserDto> userDtoList = userService.getAllUserDto();
        return ResponseEntity.ok().body(userDtoList);
    }

    @GetMapping("/{description}")
    public ResponseEntity<UserDto> getUserById(
            @PathVariable("description") String description
    ) {
        UserDto userDto = userService.getUserDtoByDescription(description);
        return ResponseEntity.ok().body(userDto);
    }

    @PostMapping
    public ResponseEntity<UserDto> insertUser(
            @Valid @RequestBody UserForm userForm
    ) {
        UserDto userDto = userService.insertUser(userForm);
        return ResponseEntity.ok().body(userDto);
    }

    @PutMapping("/{description}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable("description") String description,
            @Valid @RequestBody UserUpdateForm userUpdateForm
    ) {
        UserDto userDto = userService.updateUser(description, userUpdateForm);
        return ResponseEntity.ok().body(userDto);
    }

    @DeleteMapping("/{description}")
    public ResponseEntity<UserDto> deleteUser(
            @PathVariable("description") String description
    ) {
        userService.deleteUser(description);
        return ResponseEntity.noContent().build();
    }
}