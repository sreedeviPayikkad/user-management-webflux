package io.springwebflux.usermanagement.mapstruct.mapper;

import io.springwebflux.usermanagement.entities.Department;
import io.springwebflux.usermanagement.entities.User;
import io.springwebflux.usermanagement.mapstruct.dto.DepartmentDTO;
import io.springwebflux.usermanagement.mapstruct.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserManagementObjectMapper {
    UserDTO userToUserDTO(User user);
    User userDTOToUser(UserDTO userDTO);
    DepartmentDTO departmentToDepartmentDTO(Department department);
    Department departmentDTOToDepartment(DepartmentDTO departmentDTO);
}
