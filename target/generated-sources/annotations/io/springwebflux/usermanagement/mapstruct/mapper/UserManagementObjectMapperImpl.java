package io.springwebflux.usermanagement.mapstruct.mapper;

import io.springwebflux.usermanagement.entities.Department;
import io.springwebflux.usermanagement.entities.User;
import io.springwebflux.usermanagement.mapstruct.dto.DepartmentDTO;
import io.springwebflux.usermanagement.mapstruct.dto.UserDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-21T14:59:00-0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 20.0.1 (Homebrew)"
)
@Component
public class UserManagementObjectMapperImpl implements UserManagementObjectMapper {

    @Override
    public UserDTO userToUserDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO.UserDTOBuilder userDTO = UserDTO.builder();

        userDTO.id( user.getId() );
        userDTO.name( user.getName() );
        userDTO.age( user.getAge() );
        userDTO.salary( user.getSalary() );
        userDTO.departmentId( user.getDepartmentId() );

        return userDTO.build();
    }

    @Override
    public User userDTOToUser(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( userDTO.getId() );
        user.name( userDTO.getName() );
        user.age( userDTO.getAge() );
        user.salary( userDTO.getSalary() );
        user.departmentId( userDTO.getDepartmentId() );

        return user.build();
    }

    @Override
    public DepartmentDTO departmentToDepartmentDTO(Department department) {
        if ( department == null ) {
            return null;
        }

        DepartmentDTO.DepartmentDTOBuilder departmentDTO = DepartmentDTO.builder();

        departmentDTO.id( department.getId() );
        departmentDTO.name( department.getName() );
        departmentDTO.location( department.getLocation() );

        return departmentDTO.build();
    }

    @Override
    public Department departmentDTOToDepartment(DepartmentDTO departmentDTO) {
        if ( departmentDTO == null ) {
            return null;
        }

        Department.DepartmentBuilder department = Department.builder();

        department.id( departmentDTO.getId() );
        department.name( departmentDTO.getName() );
        department.location( departmentDTO.getLocation() );

        return department.build();
    }
}
