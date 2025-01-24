package ru.iaygi.hibernate.test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.*;
import ru.iaygi.hibernate.dao.UserDao;
import ru.iaygi.hibernate.model.User;
import ru.iaygi.hibernate.util.JpaUtil;

import java.util.List;

import static ru.iaygi.common.FakeData.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserDaoTest {

    private static UserDao userDao;
    private EntityManager entityManager;
    private User user;

    @BeforeAll
    void setup() {
        entityManager = JpaUtil.getEntityManager();
        userDao = new UserDao(entityManager);
    }

    @AfterAll
    static void tearDown() {
        JpaUtil.close();
    }

    @BeforeEach
    void init() {
        user = new User();
        user.setLogin(login());
        user.setName(firstName());
        user.setSurname(lastName());
        user.setAge(anyNumber(18, 88));
        user.setCity(cityName());

        userDao.save(user);
        Assertions.assertNotEquals(0, user.getId());
    }

//    @AfterEach
//    void clear() {
//        userDao.delete(user);
//        User deletedUser = userDao.findById(user.getId());
//        Assertions.assertNull(deletedUser);
//    }

    @Test
    void getNames() {
        List<String> getUsers = userDao.getUserNames();
        for (String userName : getUsers) {
            System.out.println("============= userName = " + userName);
        }
    }

    @RepeatedTest(10)
    void testFindById() {
        Assertions.assertNotNull(user);
//        Assertions.assertEquals("john_doe", user.getLogin());
    }

    @Test
    void testFindAll() {
        List<User> users = userDao.findAll();
        Assertions.assertFalse(users.isEmpty());
        for (User user : users) {
            System.out.println("============= login = " + user.getLogin());
            System.out.println("============= age = " + user.getAge());
        }
    }

    @Test
    void testUpdate() {
        String city = cityName();
        user.setCity(city);
        userDao.update(user);

        User updatedUser = userDao.findById(user.getId());
        Assertions.assertEquals(city, updatedUser.getCity());
    }

    @Test
    void testDelete() {
        User user = userDao.findById(1);
        userDao.delete(user);

        User deletedUser = userDao.findById(1);
        Assertions.assertNull(deletedUser);
    }

    @Test
    void getUserByName() {
        String name = "Антон";
        List<User> users = userDao.getUsersByName(name);
        for (User user : users) {
            System.out.println("============= name = " + user.getName());
        }
    }

    @Test
    void criteriaJpaOrderBy() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root);
        query.orderBy(builder.desc(root.get("age")), builder.asc(root.get("login")));

        List<User> users = entityManager.createQuery(query).getResultList();
        for (User user : users) {
            System.out.println("============= login = " + user.getLogin());
            System.out.println("============= age = " + user.getAge());
            System.out.println("- - -");
        }
    }

    @Test
    void criteriaJpaTest() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root).where(builder.equal(root.get("name"), "Леонид"));

        List<User> users = entityManager.createQuery(query).getResultList();
        System.out.println("============= name = " + user.getName());
    }
}
