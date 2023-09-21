package io.springwebflux.usermanagement.entities;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@Table("users")
public class User {
    @Id
    private Long id;
    @NotBlank(message = "name is required")
    private String name;
    private int age;
    private double salary;
    @Column("departmentId")
    private Long departmentId;

}
