package com.albpintado.crmera.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
  @Autowired
  private UserService service;

  @GetMapping(path = "/all")
  public List<User> getAll() {
    return this.service.getAll();
  }

  @GetMapping
  public ResponseEntity<User> getOne(@RequestBody UserEmailDto userEmailDto) {
    return this.service.getOne(userEmailDto);
  }

  @PostMapping
  public ResponseEntity<User> createOne(@RequestBody UserDto userDto) {
    return this.service.create(userDto);
  }

  @PutMapping
  public ResponseEntity<User> update(@RequestBody UserDto userDto) {
    return this.service.update(userDto);
  }

  @DeleteMapping
  public ResponseEntity<Object> delete(@RequestBody UserEmailDto userEmailDto) {
    return this.service.delete(userEmailDto);
  }
}
