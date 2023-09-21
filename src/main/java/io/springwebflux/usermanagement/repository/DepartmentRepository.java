package io.springwebflux.usermanagement.repository;

import io.springwebflux.usermanagement.entities.Department;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface DepartmentRepository extends ReactiveCrudRepository<Department, Long> {

}
