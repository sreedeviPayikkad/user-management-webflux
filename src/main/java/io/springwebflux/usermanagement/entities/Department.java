package io.springwebflux.usermanagement.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("department")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    @Id
    private Long id;
    @NotEmpty(message = "Department name cannot be empty")
    @Size(min = 5, message = "Department name must be at least 5 characters long")
    private String name;
    @NotBlank(message = "location is required")
    @NotEmpty
    private String location;

}
