package com.my.photogram.service;

import com.my.photogram.entity.User;
import com.my.photogram.repository.UserRepository;
import com.my.photogram.validation.UsernameExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerNewUser(User user) throws UsernameExistException {
        if (usernameExist(user.getUsername())) {
            throw new UsernameExistException("There is an account with that username: " + user.getUsername());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.createUser(user);
    }

    private boolean usernameExist(String username) {
        final User user = userRepository.findUserByUsername(username);
        return user != null;
    }
}
