package com.my.photogram.repository;

import com.my.photogram.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;

@Repository
public class UserRepository implements IUserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User findUserByUsername(String name) {
        Query query = entityManager.createNativeQuery("SELECT * FROM users WHERE username = :username", User.class);
        query.setParameter("username", name);
        User result;
        try {
            result = (User) query.getSingleResult();
        } catch (NoResultException e) {
            result = null;
        }
        return result;
    }

    @Transactional
    @Override
    public User createUser(User user) {
        try {
            entityManager.persist(user);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }
}
