package io.springwebflux.usermanagement.service;

import io.springwebflux.usermanagement.entities.Department;
import io.springwebflux.usermanagement.repository.DepartmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import static io.springwebflux.usermanagement.utilities.logging.LoggingUtil.logOnEach;

@Service
@Slf4j
@Validated
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Mono<Department> createDepartment(Department department) {
        log.info("in the method create department in department service, details = {}", department);
        return departmentRepository.save(department).doOnError(r -> log.info("{}", r.getMessage()));
    }

    public Mono<Department> getDepartmentById(long id) {
        log.info("in method get all department details in department service, for id = {}", id);
        return departmentRepository.findById(id);
    }

}
