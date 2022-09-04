package ru.kata.spring.boot_security.demo.service;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

public interface UserService extends UserDetailsService {

  public void saveUser(User user);
  public void deleteUser(Long id);

  public List<User> getUsers();

  public User show(Long id);

}
