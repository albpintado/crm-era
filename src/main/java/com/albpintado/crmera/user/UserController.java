package com.albpintado.crmera.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
  @Autowired
  private UserService service;

  @GetMapping
  public List<User> getAll() {
    return this.service.getAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<User> getOne(@PathVariable String id) {
    return this.service.getOne(id);
  }

  @PostMapping
  public ResponseEntity<User> createOne(@RequestBody UserDto userDto) {
    return this.service.create(userDto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<User> update(@PathVariable String id, @RequestBody UserDto userDto) {
    return this.service.update(id, userDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> delete(@PathVariable String id) {
    return this.service.delete(id);
  }
}
