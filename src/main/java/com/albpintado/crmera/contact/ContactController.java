package com.albpintado.crmera.contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/contacts")
public class ContactController {

  @Autowired
  private ContactService service;

  @GetMapping
  public List<Contact> getAll() {
    return this.service.getAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Contact> getOne(@PathVariable String id) {
    return this.service.getOne(id);
  }
}