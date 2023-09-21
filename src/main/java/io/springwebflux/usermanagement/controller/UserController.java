package io.springwebflux.usermanagement.controller;

import io.springwebflux.usermanagement.mapstruct.mapper.UserManagementObjectMapper;
import io.springwebflux.usermanagement.mapstruct.dto.UserDTO;
import io.springwebflux.usermanagement.mapstruct.dto.UserDepartmentDTO;
import io.springwebflux.usermanagement.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/v1/user")
public class UserController {
    private final UserService userService;
    private final UserManagementObjectMapper userManagementObjectMapper;

    public UserController(UserService userService, UserManagementObjectMapper userManagementObjectMapper) {
        this.userService = userService;
        this.userManagementObjectMapper = userManagementObjectMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        return userService.createUser(userManagementObjectMapper.userDTOToUser(userDTO))
                .map(userManagementObjectMapper::userToUserDTO);

    }

    @GetMapping
    public Flux<UserDTO> getUsers() {
        return userService.getAllUsers()
                .map(userManagementObjectMapper::userToUserDTO);

    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<UserDTO>> getUserById(@PathVariable long id) {
        return userService.getUserById(id)
                .map(userManagementObjectMapper::userToUserDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }

    @DeleteMapping("/{userId}")
    public Mono<ResponseEntity<Void>> deleteUserById(@PathVariable long userId) {
        return userService.deleteUser(userId)
                .map(r -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/age/{age}")
    public Flux<UserDTO> getUsersByAge(@PathVariable int age) {
        return userService.findUsersByAge(age).map(userManagementObjectMapper::userToUserDTO);
    }

    @PutMapping("/{userId}")
    public Mono<ResponseEntity<UserDTO>> updateUserById(@PathVariable long userId, @RequestBody UserDTO userDTO) {
        return userService
                .updateUser(userId, userManagementObjectMapper.userDTOToUser(userDTO))
                .map(userManagementObjectMapper::userToUserDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/search/id")
    public Flux<UserDTO> fetchUsersByIds(@RequestBody List<Long> ids) {
        return userService.fetchUsers(ids)
                .map(userManagementObjectMapper::userToUserDTO);
    }

    @GetMapping("/{userId}/department")
    public Mono<ResponseEntity<UserDepartmentDTO>> fetchUserAndDepartment(@PathVariable long userId) {
        return userService.fetchUserAndDepartment(userId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


}
