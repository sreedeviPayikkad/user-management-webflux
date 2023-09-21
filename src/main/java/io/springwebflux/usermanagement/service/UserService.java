package io.springwebflux.usermanagement.service;

import io.springwebflux.usermanagement.entities.Department;
import io.springwebflux.usermanagement.entities.User;
import io.springwebflux.usermanagement.mapstruct.dto.UserDepartmentDTO;
import io.springwebflux.usermanagement.repository.DepartmentRepository;
import io.springwebflux.usermanagement.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.function.BiFunction;

import static io.springwebflux.usermanagement.utilities.logging.LoggingUtil.logOnEach;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;

    public UserService(UserRepository userRepository, DepartmentRepository departmentRepository) {
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
    }


    //create a user
    public Mono<User> createUser(User user) {
        log.info("in the method createUser in UserService, trying to create user {}", user);
        return userRepository.save(user);
    }

    //find all users
    public Flux<User> getAllUsers() {
        log.info("in the method getAllUsers in userService getting all users");
        return userRepository.findAll();
    }


    public Mono<User> getUserById(long id) {
        log.info("in the method getUserById in userService getting user with id = {}", id);
        return userRepository.findById(id);
    }

    public Mono<User> updateUser(long userId, User user) {
        log.info(" in the method update user {} with {}", userId, user);
        return userRepository.findById(userId)
                .flatMap(dbUser -> {
                    dbUser.setName(user.getName());
                    dbUser.setAge(user.getAge());
                    dbUser.setSalary(user.getSalary());
                    return userRepository.save(dbUser);
                });
    }

    public Mono<User> deleteUser(long userId) {
        log.info("in the method update user {}", userId);
        return userRepository.findById(userId)
                .flatMap(existingUser -> userRepository.delete(existingUser)
                        .then(Mono.just(existingUser)));
    }

    public Flux<User> findUsersByAge(int age) {
        log.info("in the method findUsersByAge, age > {}", age);
        return userRepository.findByAge(age);

    }

    public Flux<User> fetchUsers(List<Long> userIds) {
        log.info("in the method fetchUsers, {}", userIds);
        return Flux.fromIterable(userIds)
                .parallel()
                .runOn(Schedulers.boundedElastic())
                .flatMap(this::getUserById).sequential();
    }

    public Mono<UserDepartmentDTO> fetchUserAndDepartment(long userId) {
        log.info("in the method fetchUserAndDepartment, {}", userId);
        Mono<User> user = getUserById(userId).subscribeOn(Schedulers.boundedElastic());
        Mono<Department> department = user
                .flatMap(u -> departmentRepository.findById(u.getDepartmentId()))
                .subscribeOn(Schedulers.boundedElastic()).subscribeOn(Schedulers.boundedElastic());

        return Mono.zip(user, department, buildUserDepartmentDTO);
    }

    private final BiFunction<User, Department, UserDepartmentDTO> buildUserDepartmentDTO = (o1, o2) -> {
        return UserDepartmentDTO.builder()
                .userName(o1.getName())
                .departmentName(o2.getName())
                .userId(o1.getId())
                .departmentId(o2.getId())
                .location(o2.getLocation())
                .age(o1.getAge())
                .salary(o1.getSalary())
                .build();
    };

}
