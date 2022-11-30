package com.albpintado.crmera.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

  @Autowired
  private UserRepository repo;

  private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  public List<User> getAll() {
    return this.repo.findAll();
  }

  public ResponseEntity<User> getOne(String id) {
    Optional<User> userFromDb = this.repo.findById(Long.valueOf(id));
    if (userFromDb.isPresent()) {
      return new ResponseEntity<>(userFromDb.get(), HttpStatus.OK);
    }
    return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
  }

  public ResponseEntity<User> create(UserDto userDto) {
    User user = createUserEntity(userDto);
    this.repo.save(user);
    return new ResponseEntity<>(user, HttpStatus.CREATED);
  }

  private User createUserEntity(UserDto userDto) {
    User user = new User();
    user.setName(userDto.getName());
    user.setEmail(userDto.getEmail());
    user.setPassword(passwordEncoder.encode(userDto.getPassword()));
    return user;
  }

  public ResponseEntity<User> update(String id, UserDto userDto) {
    Optional<User> userFromDB = this.repo.findById(Long.valueOf(id));
    if (userFromDB.isPresent()) {
      User user = userFromDB.get();
      if (userDto.getName() != null) user.setName(userDto.getName());
      if (userDto.getEmail() != null) user.setEmail(userDto.getEmail());
      if (userDto.getPassword() != null) user.setPassword(userDto.getPassword());
      this.repo.save(user);
      return new ResponseEntity<>(user, HttpStatus.OK);
    }
    return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
  }

  public ResponseEntity<Object> delete(String id) {
    Optional<User> userFromDb = this.repo.findById(Long.valueOf(id));
    userFromDb.ifPresent(user -> this.repo.delete(user));
    return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
  }
}
