package ru.itmentor.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmentor.spring.boot_security.demo.dao.UserDao;
import ru.itmentor.spring.boot_security.demo.entity.Role;
import ru.itmentor.spring.boot_security.demo.entity.User;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> getAllUser() {
        return userDao.getAllUser();
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        Set<Role> roles = new HashSet<>();
        user.getRoles().stream().forEach(
                role ->
                        roles.add(
                                userDao.getRoleByName(role.getName())));
//        Role userRole = userDao.getRoleByName("ROLE_USER");
//        Role adminRole = userDao.getRoleByName("ROLE_ADMIN");
//
//        roles.add(userRole);
//        roles.add(adminRole);
//
        user.setRoles(roles);
        userDao.saveUser(user);
    }
//    public void saveUser(User user) {
//        userDao.saveUser(user);
//    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userDao.deleteUser(id);
    }

    @Override
    public User getUserById(long id) {
        return userDao.getUserById(id);

    }

    @Override
    public User findByName(String username) {
        return userDao.findByName(username);
    }


    @Override
    @Transactional
    public void updateUser(int id, User updatedUser) {
        User user = userDao.getUserById((long) id);
        if (user != null) {
            // Получаем список текущих ролей пользователя
            Set<Role> currentRoles = user.getRoles();

            // Удаляем все текущие роли пользователя
            currentRoles.clear();

            // Создаем и сохраняем новые роли пользователя
            for (Role role : updatedUser.getRoles()) {
                // Выполняем сохранение роли без использования EntityManager
//                userDao.saveRole(role);
                // Привязываем роль к пользователю
                currentRoles.add(userDao.getRoleByName(role.getName()));
            }

            // Обновляем остальные поля пользователя
            user.setName(updatedUser.getName());
            user.setSurname(updatedUser.getSurname());
            user.setAge(updatedUser.getAge());
            user.setSalary(updatedUser.getSalary());
            user.setPassword(updatedUser.getPassword());

            // Обновляем пользователя в базе данных
            userDao.updateUser(id, user);
        } else {
            throw new NoSuchElementException("User not found");
        }
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found"));
        }
        return user;
    }

}








