package ru.kata.spring.boot_security.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public UserDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User getUserByEmail(String email) {
        return entityManager.createQuery(
                "select user from User user join fetch user.roles where user.email =:email", User.class)
                .setParameter("email", email)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public Optional<User> findByUsername(String name) {
        return entityManager.createQuery(
                        "SELECT user FROM User user JOIN FETCH user.roles WHERE user.username = :name", User.class)
                .setParameter("name", name)
                .getResultStream()
                .findFirst();

    }

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public User getUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public void removeUserById(Long id) {
        entityManager.remove(entityManager.find(User.class, id));
    }

    @Override
    public List<User> listUsers() {
        return entityManager.createQuery("select user from User user", User.class).getResultList();
    }
}
