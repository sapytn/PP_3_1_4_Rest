package ru.kata.spring.boot_security.demo.controller;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

@Controller
@RequestMapping("/user")
public class UserController {
  @Autowired
  private UserServiceImpl userService;

  @GetMapping
  public String showUser(Model model, Principal principal) {
    User user = userService.findByEmail(principal.getName());
    model.addAttribute("myUser", user);
    return "user-page";
  }
}
