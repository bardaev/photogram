package com.my.photogram.repository;

import com.my.photogram.entity.User;

public interface IUserRepository {

    User findUserByUsername(String name);

    User createUser(User user);
}
