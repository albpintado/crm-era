package com.albpintado.crmera.contact;

import com.albpintado.crmera.opportunity.Opportunity;
import com.albpintado.crmera.opportunity.OpportunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.albpintado.crmera.utils.Utils.createLocalDate;

@Service
public class ContactService {

  @Autowired
  private ContactRepository contactRepository;

  @Autowired
  private OpportunityRepository opportunityRepository;

  public List<Contact> getAll() {
    return this.contactRepository.findAll();
  }

  public ResponseEntity<Contact> getOne(String id) {
    Optional<Contact> userFromDb = this.contactRepository.findById(Long.valueOf(id));
    if (userFromDb.isPresent()) {
      return new ResponseEntity<>(userFromDb.get(), HttpStatus.OK);
    }
    return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
  }

  public ResponseEntity<Contact> create(ContactDto contactDto) {
    Contact contact = new Contact();
    contact.setName(contactDto.getName());
    contact.setDate(createLocalDate(contactDto.getDate()));
    contact.setDetails(contactDto.getDetails());
    contact.setMethod(ContactMethod.valueOf(contactDto.getMethod()));

    Optional<Opportunity> opportunityFromDb = this.opportunityRepository.findById(contactDto.getOpportunityId());
    opportunityFromDb.ifPresent(contact::setOpportunity);

    this.contactRepository.save(contact);
    return new ResponseEntity<>(contact, HttpStatus.CREATED);
  }

  public ResponseEntity<Contact> update(String id, ContactDto contactDto) {
    Optional<Contact> contactToUpdateFromDb = this.contactRepository.findById(Long.valueOf(id));

    if (contactToUpdateFromDb.isPresent()) {
      Contact updatedContact = updateContactBeforeSaveOnDb(contactToUpdateFromDb.get(), contactDto);
      this.contactRepository.save(updatedContact);

      return new ResponseEntity<>(updatedContact, HttpStatus.OK);
    }

    return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
  }

  public ResponseEntity<Object> delete(String id) {
    Optional<Contact> contactFromDb = this.contactRepository.findById(Long.valueOf(id));
    if (contactFromDb.isPresent()) {
      this.contactRepository.delete(contactFromDb.get());
      return new ResponseEntity<>(null, HttpStatus.OK);
    }
    return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
  }

  private Contact updateContactBeforeSaveOnDb(Contact contactToUpdate, ContactDto contactDto) {
    if (contactDto.getName() != null) contactToUpdate.setName(contactDto.getName());
    if (contactDto.getName() != null) contactToUpdate.setDetails(contactDto.getDetails());
    if (contactDto.getName() != null) contactToUpdate.setMethod(ContactMethod.valueOf(contactDto.getMethod()));

    LocalDate date = createLocalDate(contactDto.getDate());
    if (contactDto.getDate() != null) contactToUpdate.setDate(date);

    return contactToUpdate;
  }
}
