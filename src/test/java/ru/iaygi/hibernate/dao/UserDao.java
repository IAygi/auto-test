package ru.iaygi.hibernate.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import ru.iaygi.hibernate.model.User;

import java.util.List;

@Slf4j
public class UserDao {

    private final EntityManager entityManager;

    public UserDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // Create
    public void save(User user) {
        log.debug("Attempting to save user: {}", user);
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(user);
            transaction.commit();
            log.info("User saved successfully: {}", user);
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            log.error("Error while saving user: {}", user, e);
            throw e;
        }
    }

    // Read by ID
    public User findById(int id) {
        log.debug("Fetching user with ID: {}", id);
        User user = entityManager.find(User.class, id);
        if (user != null) {
            log.info("User fetched successfully: {}", user);
        } else {
            log.warn("No user found with ID: {}", id);
        }
        return user;
    }

    // Read all
    public List<User> findAll() {
        log.debug("Fetching all users");
        List<User> users = entityManager.createQuery("from User", User.class).getResultList();
        log.info("Fetched {} users", users.size());
        return users;
    }

    // Update
    public void update(User user) {
        log.debug("Attempting to update user: {}", user);
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(user);
            transaction.commit();
            log.info("User updated successfully: {}", user);
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            log.error("Error while updating user: {}", user, e);
            throw e;
        }
    }

    // Delete
    public void delete(User user) {
        log.debug("Attempting to delete user: {}", user);
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.remove(entityManager.contains(user) ? user : entityManager.merge(user));
            transaction.commit();
            log.info("User deleted successfully: {}", user);
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            log.error("Error while deleting user: {}", user, e);
            throw e;
        }
    }

//    public List<String> getUserNames() {
//        try (Session session = sessionFactory.openSession()) {
//            String hql = "select distinct u.name from User u where u.created > '2020-01-01'";
//            Query<String> query = session.createQuery(hql , String.class);
//            return query.list();
//        }
//    }

    public List<String> getUserNames() {
        String jpql = "select name from User";
        TypedQuery<String> query = entityManager.createQuery(jpql, String.class);
        return query.getResultList();
    }

    public List<User> getUsersByName(String name) {

        String jpql = "select u from User u where u.name = :name";
        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
        query.setParameter("name", name);

        return query.getResultList();
    }
}
