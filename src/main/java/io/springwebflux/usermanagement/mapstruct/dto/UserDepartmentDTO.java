package io.springwebflux.usermanagement.mapstruct.dto;

import lombok.Builder;

@Builder
public record UserDepartmentDTO(long userId, String userName, int age, double salary, long departmentId,
                                String departmentName, String location) {
}
