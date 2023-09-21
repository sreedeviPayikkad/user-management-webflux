package io.springwebflux.usermanagement.mapstruct.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDTO {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    @NotEmpty(message = "Department name cannot be empty")
    @Size(min = 5, message = "Department name must be at least 5 characters long")
    private String name;
    @JsonProperty("location")
    private String location;
}
