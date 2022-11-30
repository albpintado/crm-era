package com.albpintado.crmera.contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
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
    return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
  }

  public ResponseEntity<Contact> update(String id, ContactDto contactDto) {
    Optional<Contact> contactFromDb = this.repo.findById(Long.valueOf(id));
    if (contactFromDb.isPresent()) {
      Contact contact = contactFromDb.get();
      if (contactDto.getName() != null) contact.setName(contactDto.getName());
      contact.setDetails(contactDto.getDetails());
      contact.setMethod(ContactMethod.valueOf(contactDto.getMethod()));

      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
      formatter = formatter.withLocale(Locale.US);
      LocalDate date = LocalDate.parse(contactDto.getDate(), formatter);
      if (contactDto.getDate() != null) contact.setDate(date);

      this.repo.save(contact);
      return new ResponseEntity<>(contact, HttpStatus.OK);
    }
    return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
  }
}
