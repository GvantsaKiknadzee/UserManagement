package com.halykbank.usermanagment.repository;

import com.halykbank.usermanagment.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    Set<User> findAll();
    Set<User> findAllByNameIgnoreCase(String name);
    Optional<User> findByEmailIgnoreCase(String email);
}
