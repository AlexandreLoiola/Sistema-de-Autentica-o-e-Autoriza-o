package com.AlexandreLoiola.AccessManagement.rest.controler;

import com.AlexandreLoiola.AccessManagement.rest.dto.MethodDto;
import com.AlexandreLoiola.AccessManagement.rest.form.MethodForm;
import com.AlexandreLoiola.AccessManagement.rest.form.MethodUpdateForm;
import com.AlexandreLoiola.AccessManagement.service.MethodService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/methods")
public class MethodController {
    @Autowired
    private MethodService methodService;

    @GetMapping
    public ResponseEntity<List<MethodDto>> getAllMethod() {
        List<MethodDto> methodDtoList = methodService.getAllMethodDto();
        return ResponseEntity.ok().body(methodDtoList);
    }

    @GetMapping("/{description}")
    public ResponseEntity<MethodDto> getMethodById(
            @PathVariable("description") String description
    ) {
        MethodDto methodDto = methodService.getMethodDtoByDescription(description);
        return ResponseEntity.ok().body(methodDto);
    }

    @PostMapping
    public ResponseEntity<MethodDto> insertMethod(
            @Valid @RequestBody MethodForm methodForm
    ) {
        MethodDto methodDto = methodService.insertMethod(methodForm);
        return ResponseEntity.ok().body(methodDto);
    }

    @PutMapping("/{description}")
    public ResponseEntity<MethodDto> updateMethod(
            @PathVariable("description") String description,
            @Valid @RequestBody MethodUpdateForm methodUpdateForm
    ) {
        MethodDto methodDto = methodService.updateMethod(description, methodUpdateForm);
        return ResponseEntity.ok().body(methodDto);
    }

    @DeleteMapping("/{description}")
    public ResponseEntity<MethodDto> deleteMethod(
            @PathVariable("description") String description
    ) {
        methodService.deleteMethod(description);
        return ResponseEntity.noContent().build();
    }
}