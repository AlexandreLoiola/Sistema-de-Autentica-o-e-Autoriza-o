package com.AlexandreLoiola.AccessManagement.rest.controler;


import com.AlexandreLoiola.AccessManagement.rest.dto.RoleDto;
import com.AlexandreLoiola.AccessManagement.rest.form.RoleForm;
import com.AlexandreLoiola.AccessManagement.rest.form.RoleUpdateForm;
import com.AlexandreLoiola.AccessManagement.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<Set<RoleDto>> getAllRole() {
        Set<RoleDto> roleDtoSet = roleService.getAllRoleDto();
        return ResponseEntity.ok().body(roleDtoSet);
    }

    @GetMapping("/{description}")
    public ResponseEntity<RoleDto> getRoleById(
            @PathVariable("description") String description
    ) {
        RoleDto roleDto = roleService.getRoleDtoByDescription(description);
        return ResponseEntity.ok().body(roleDto);
    }

    @PostMapping
    public ResponseEntity<RoleDto> insertRole(
            @Valid @RequestBody RoleForm roleForm
    ) {
        RoleDto roleDto = roleService.insertRole(roleForm);
        return ResponseEntity.ok().body(roleDto);
    }

    @PutMapping("/{description}")
    public ResponseEntity<RoleDto> updateRole(
            @PathVariable("description") String description,
            @Valid @RequestBody RoleUpdateForm roleUpdateForm
    ) {
        RoleDto roleDto = roleService.updateRole(description, roleUpdateForm);
        return ResponseEntity.ok().body(roleDto);
    }

    @DeleteMapping("/{description}")
    public ResponseEntity<RoleDto> deleteRole(
            @PathVariable("description") String description
    ) {
        roleService.deleteRole(description);
        return ResponseEntity.noContent().build();
    }
}