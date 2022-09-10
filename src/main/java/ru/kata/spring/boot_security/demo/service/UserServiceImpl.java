package ru.kata.spring.boot_security.demo.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Transactional
  @Override
  public void saveUser(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);
  }

  @Transactional
  @Override
  public void updateUser(User user) {
    if (!showUser(user.getId()).getPassword().equals(user.getPassword())) {
      user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
    userRepository.save(user);
  }

  @Transactional
  @Override
  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }

  @Transactional
  @Override
  public List<User> getUsers() {
    return userRepository.findAll();
  }

  @Transactional
  @Override
  public User showUser(Long id) {
    return userRepository.getReferenceById(id);
  }

  @Transactional
  public User findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Transactional
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = findByEmail(email);
    if(user == null) {
      throw new UsernameNotFoundException(String.format("User '%s' not found", email));
    }
    return new org.springframework.security.core.userdetails.User(user.getEmail(),
        user.getPassword(), mapRolesAuthorities(user.getRoles()));
  }

  private Collection<? extends GrantedAuthority> mapRolesAuthorities(Collection<Role> roles) {
    return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
  }

  @Transactional
  public List<Role> listRoles() {
    return roleRepository.findAll();
  }
}
