package com.my.photogram.repository;

import com.my.photogram.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IUserRepository extends CrudRepository<User, Long> {

    Optional<User> findUserByUsername(String name);

}
