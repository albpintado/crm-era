package com.albpintado.crmera.contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

  @Autowired
  private ContactRepository repo;

  public List<Contact> getAll() {
    return this.repo.findAll();
  }

  public ResponseEntity<Contact> getOne(String id) {
    Optional<Contact> userFromDb = this.repo.findById(Long.valueOf(id));
    if (userFromDb.isPresent()) {
      return new ResponseEntity<>(userFromDb.get(), HttpStatus.OK);
    }
    return null;
  }
}
