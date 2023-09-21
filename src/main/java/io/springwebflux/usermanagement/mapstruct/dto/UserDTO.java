package io.springwebflux.usermanagement.mapstruct.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("age")
    private int age;
    @JsonProperty("salary")
    private double salary;
    @JsonProperty("departmentId")
    private Long departmentId;
}
