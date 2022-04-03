package com.my.photogram.service;

import com.my.photogram.entity.User;
import com.my.photogram.validation.UsernameExistException;

public interface IUserService {

    User registerNewUser(User user) throws UsernameExistException;
    User findUser(String username);
}
