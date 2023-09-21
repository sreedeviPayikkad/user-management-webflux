package io.springwebflux.usermanagement.repository;

import io.springwebflux.usermanagement.entities.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Long> {
    String GET_USERS_BASED_ON_AGE = "select * from users where age >= $1";

    @Query(GET_USERS_BASED_ON_AGE)
    Flux<User> findByAge(int age);
}
