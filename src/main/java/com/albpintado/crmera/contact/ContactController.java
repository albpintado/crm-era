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

  @GetMapping("/opportunity/{id}")
  public List<Contact> getAllByOpportunity(@PathVariable String id) {
    return this.service.getAllByOpportunity(id);
  }

  @GetMapping("/opportunity/{id}/before-conversion")
  public ResponseEntity<List<Contact>> getAllByOpportunityBeforeConversion(@PathVariable String id) {
    return this.service.getAllByOpportunityBeforeConversion(id);
  }

  @PostMapping
  public ResponseEntity<Contact> create(@RequestBody ContactDto contactDto) {
    return this.service.create(contactDto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Contact> update(@PathVariable String id, @RequestBody ContactDto contactDto) {
    return this.service.update(id, contactDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> delete(@PathVariable String id) {
    return this.service.delete(id);
  }
}
