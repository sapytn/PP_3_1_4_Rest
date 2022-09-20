package ru.kata.spring.boot_security.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;


@Data
public class Role {
  private Long id;

  private String name;

  public Role(){

  }

  public Role(String name) {
    this.name = name;
  }

  public Role(Long id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return this.name;
  }
}