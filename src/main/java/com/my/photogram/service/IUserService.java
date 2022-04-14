package com.my.photogram.service;

import com.my.photogram.entity.User;
import com.my.photogram.validation.UsernameExistException;

public interface IUserService {

    User registerNewUser(User user) throws UsernameExistException;
    void updateUser(User user);
    User findUser(String username);
    void subscribe(User subscriber, User author);
    void unsubscribe(User subscriber, User author);
    boolean isSubscriber(User subscriber, User author);
    int countSubscribers(User user);
    int countSubscriptions(User user);
}
