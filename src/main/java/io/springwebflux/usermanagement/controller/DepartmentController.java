package io.springwebflux.usermanagement.controller;

import io.springwebflux.usermanagement.mapstruct.mapper.UserManagementObjectMapper;
import io.springwebflux.usermanagement.mapstruct.dto.DepartmentDTO;
import io.springwebflux.usermanagement.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/department")
@Slf4j

public class DepartmentController {
    private static final String ENTITY_NAME = "Department";
    private final UserManagementObjectMapper userManagementObjectMapper;
    private final DepartmentService departmentService;

    public DepartmentController(UserManagementObjectMapper userManagementObjectMapper, DepartmentService departmentService) {
        this.userManagementObjectMapper = userManagementObjectMapper;
        this.departmentService = departmentService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<DepartmentDTO> create(@RequestBody @Valid DepartmentDTO departmentDTO) {
        return departmentService.createDepartment(userManagementObjectMapper.
                        departmentDTOToDepartment(departmentDTO))
                .map(userManagementObjectMapper::departmentToDepartmentDTO);


    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<DepartmentDTO>> getDepartmentById(@PathVariable @Valid long id) {
        return departmentService.getDepartmentById(id)
                .map(userManagementObjectMapper::departmentToDepartmentDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
