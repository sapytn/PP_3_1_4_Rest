package ru.kata.spring.boot_security.demo.controller;


import java.security.Principal;
import java.util.Collection;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.Role;
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
    public String updateUser(@RequestParam("id") Long id, @RequestParam("firstName") String firstName,
        @RequestParam("lastName") String lastName, @RequestParam("age") int age, @RequestParam("email") String email,
        @RequestParam("password") String password, @RequestParam("role") Collection<Role> role) {
        User user = userService.showUser(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAge(age);
        user.setEmail(email);
        user.setPassword(password);
        user.setRoles(role);
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }


}
