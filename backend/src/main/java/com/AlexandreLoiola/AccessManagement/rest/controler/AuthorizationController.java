package com.AlexandreLoiola.AccessManagement.rest.controler;

import com.AlexandreLoiola.AccessManagement.rest.dto.AuthorizationDto;
import com.AlexandreLoiola.AccessManagement.rest.form.AuthorizationForm;
import com.AlexandreLoiola.AccessManagement.rest.form.AuthorizationUpdateForm;
import com.AlexandreLoiola.AccessManagement.service.AuthorizationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/authorizations")
public class AuthorizationController {
    @Autowired
    private AuthorizationService authorizationService;

    @GetMapping
    public ResponseEntity<List<AuthorizationDto>> getAllAuthorization() {
        List<AuthorizationDto> authorizationDtoList = authorizationService.getAllAuthorizationDto();
        return ResponseEntity.ok().body(authorizationDtoList);
    }

    @GetMapping("/{description}")
    public ResponseEntity<AuthorizationDto> getAuthorizationById(
            @PathVariable("description") String description
    ) {
        AuthorizationDto authorizationDto = authorizationService.getAuthorizationDtoByDescription(description);
        return ResponseEntity.ok().body(authorizationDto);
    }

    @PostMapping
    public ResponseEntity<AuthorizationDto> insertAuthorization(
            @Valid @RequestBody AuthorizationForm authorizationForm
    ) {
        AuthorizationDto authorizationDto = authorizationService.insertAuthorization(authorizationForm);
        return ResponseEntity.ok().body(authorizationDto);
    }

    @PutMapping("/{description}")
    public ResponseEntity<AuthorizationDto> updateAuthorization(
            @PathVariable("description") String description,
            @Valid @RequestBody AuthorizationUpdateForm authorizationUpdateForm
    ) {
        AuthorizationDto authorizationDto = authorizationService.updateAuthorization(description, authorizationUpdateForm);
        return ResponseEntity.ok().body(authorizationDto);
    }

    @DeleteMapping("/{description}")
    public ResponseEntity<AuthorizationDto> deleteAuthorization(
            @PathVariable("description") String description
    ) {
        authorizationService.deleteAuthorization(description);
        return ResponseEntity.noContent().build();
    }
}