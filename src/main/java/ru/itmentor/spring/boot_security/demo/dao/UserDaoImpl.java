package ru.itmentor.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.itmentor.spring.boot_security.demo.entity.Role;
import ru.itmentor.spring.boot_security.demo.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getAllUser() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Override
    public void saveUser(User user) {
        entityManager.merge(user);
        for (Role role : user.getRoles()) {
            entityManager.persist(role);
        }
    }

    @Override
    public User getUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void deleteUser(long id) {
        entityManager.remove(getUserById(id));

    }

    @Override
    public void updateUser(int id, User user) {
        entityManager.merge(user);
    }
    @Override
    public void updateRole(Role role) {
        entityManager.merge(role);
    }


    public User findByName(String name) {
        String query = "SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.name = :name";
        return entityManager.createQuery(query, User.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    public void saveRole(Role role) {

    }

    @Override
    public Role getRoleByName(String roleName) {
        String queryString = "SELECT r FROM Role r WHERE r.name = :roleName";
        TypedQuery<Role> query = entityManager.createQuery(queryString, Role.class);
        query.setParameter("roleName", roleName);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null; // Если роль не найдена, возвращаем null или выбрасываем исключение, в зависимости от вашей логики обработки.
        }
    }
}