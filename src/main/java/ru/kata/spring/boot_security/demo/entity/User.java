package ru.kata.spring.boot_security.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;


@Data
public class User {

  private Long id;

  private String email;

  private String password;

  private String firstName;

  private String lastName;

  private int age;

  private Collection<Role> roles;

  public void addRole(Role role) {
    this.roles.add(role);
  }

  public User() {

  }

  public User(String email, String password, String firstName, String lastName, int age,
      Collection<Role> roles) {
    this.email = email;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
    this.roles = roles;
  }

  public User(long id, String email, String password, String firstName, String lastName, int age,
      Collection<Role> roles) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
    this.roles = roles;
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", email='" + email + '\'' +
        ", password='" + password + '\'' +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", age=" + age +
        ", roles=" + roles +
        '}';
  }
}
