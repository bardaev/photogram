package com.my.photogram.service;

import com.my.photogram.entity.SubscriberId;
import com.my.photogram.entity.Subscribers;
import com.my.photogram.entity.User;
import com.my.photogram.repository.ISubscribersRepository;
import com.my.photogram.repository.IUserRepository;
import com.my.photogram.validation.UsernameExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EntityManager em;

    @Autowired
    private ISubscribersRepository subscribersRepository;

    @Override
    public User registerNewUser(User user) throws UsernameExistException {
        if (usernameExist(user.getUsername())) {
            throw new UsernameExistException("There is an account with that username: " + user.getUsername());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void subscribe(User subscriber, User author) {
        Subscribers subscribers = new Subscribers();
        subscribers.setIdSubscriber(subscriber.getId());
        subscribers.setIdAuthor(author.getId());
        subscribersRepository.save(subscribers);
    }

    @Override
    public void unsubscribe(User subscriber, User author) {
        Optional<Subscribers> subscribers = subscribersRepository.findById(new SubscriberId(subscriber.getId(), author.getId()));
        subscribers.ifPresent(value -> subscribersRepository.delete(value));
    }

    @Override
    @Transactional
    public boolean isSubscriber(User subscriber, User author) {
        return em.find(Subscribers.class, new SubscriberId(subscriber.getId(), author.getId())) != null;
    }

    @Override
    public int countSubscribers(User user) {
        List<Subscribers> subscribers = em
                .createQuery("SELECT s from Subscribers s where s.idAuthor = :id", Subscribers.class)
                .setParameter("id", user.getId())
                .getResultList();

        return subscribers.size();
    }

    @Override
    public int countSubscriptions(User user) {
        List<Subscribers> subscribers = em
                .createQuery("SELECT s from Subscribers s where s.idSubscriber = :id", Subscribers.class)
                .setParameter("id", user.getId())
                .getResultList();

        return subscribers.size();
    }

    @Override
    public User findUser(String username) {
        Optional<User> user = userRepository.findUserByUsername(username);
        return user.orElse(null);
    }

    private boolean usernameExist(String username) {
        Optional<User> user = userRepository.findUserByUsername(username);
        return user.isPresent();
    }
}
