package ru.kata.spring.boot_security.demo.controller;


import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping
    public String showAllUser(Model model, Principal principal) {
        List<User> allUser = userService.getUsers();
        model.addAttribute("users", allUser);
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("myUser", user);
        User newUser = new User();
        model.addAttribute("newUser", newUser);
        model.addAttribute("listRoles",userService.listRoles());
        return "admin-panel";
    }

    @PostMapping()
    public String saveUser(@ModelAttribute("newUser") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/edit")
    public String updateUser(@ModelAttribute("userList") User user) {
        userService.updateUser(user);
        return "redirect:/admin";
    }


    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.showUser(id));
        model.addAttribute("listRoles",userService.listRoles());
        return "edit";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }


}
