package ru.kata.spring.boot_security.demo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

@RestController
@RequestMapping("/api")
public class AdminRestController {

  @Autowired
  private UserServiceImpl userService;

  @GetMapping("/users")
  public List<User> getAllUsers() {
    return userService.getUsers();
  }

  @GetMapping("/users/{id}")
  public ResponseEntity<User> getUser(@PathVariable Long id) {
    User user = userService.showUser(Long.valueOf(id));
    return user != null
        ? new ResponseEntity<>(user, HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @PostMapping("/users")
  public User addUser(@RequestBody User user) {
    userService.saveUser(user);
    return user;
  }

  @PutMapping("/users")
  public User editUser(@RequestBody User user) {
    userService.updateUser(user);
    return user;
  }

  @DeleteMapping("/users/{id}")
  public String deleteUser(@PathVariable int id) {
    userService.deleteUser(Long.valueOf(id));
    return "User with ID = " + id + "deleted";
  }
}
