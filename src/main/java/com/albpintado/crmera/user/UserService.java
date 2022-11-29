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

  public ResponseEntity<User> getOne(UserEmailDto userEmailDto) {
    Optional<User> userFromDb = this.repo.findOneByEmail(userEmailDto.getEmail());
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

  public ResponseEntity<User> update(UpdateUserDto updateUserDto) {
    Optional<User> userFromDB = this.repo.findOneByEmail(updateUserDto.getEmail());
    if (userFromDB.isPresent()) {
      User user = userFromDB.get();
      if (updateUserDto.getName() != null) user.setName(updateUserDto.getName());
      user.setEmail(updateUserDto.getNewEmail());
      user.setPassword(updateUserDto.getPassword());
      this.repo.save(user);
      return new ResponseEntity<>(user, HttpStatus.OK);
    }
    return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
  }

  public ResponseEntity<Object> delete(UserEmailDto userEmailDto) {
    Optional<User> userFromDb = this.repo.findOneByEmail(userEmailDto.getEmail());
    userFromDb.ifPresent(user -> this.repo.delete(user));
    return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
  }
}
