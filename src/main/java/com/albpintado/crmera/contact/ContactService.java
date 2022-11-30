package com.albpintado.crmera.contact;

import com.albpintado.crmera.opportunity.Opportunity;
import com.albpintado.crmera.opportunity.OpportunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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

  public List<Contact> getAllByOpportunity(String id) {
    return this.contactRepository.findByOpportunity_Id(Long.valueOf(id));
  }

  public ResponseEntity<List<Contact>> getAllByOpportunityBeforeConversion(String id) {
    Optional<Opportunity> opportunityFromContact = this.opportunityRepository.findById(Long.valueOf(id));

    if (opportunityFromContact.isPresent()) {
      Opportunity opportunity = opportunityFromContact.get();
      List<Contact> allContactsFromOpportunity = this.contactRepository.findByOpportunity_Id(Long.valueOf(id));
      List<Contact> contactsBeforeConversion = new ArrayList<>();

      for (Contact contact : allContactsFromOpportunity) {
        if (contact.getDate().isBefore(opportunity.getConversionDate())) {
          contactsBeforeConversion.add(contact);
        }
      }

      return new ResponseEntity<>(contactsBeforeConversion, HttpStatus.OK);
    }
    return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
  }

  public ResponseEntity<Contact> create(ContactDto contactDto) {
    Contact contactToSave = createContactToSaveOnDb(contactDto);
    Optional<Opportunity> opportunityAssignedToContactFromDb = this.opportunityRepository.findById(contactDto.getOpportunityId());
    opportunityAssignedToContactFromDb.ifPresent(contactToSave::setOpportunity);

    this.contactRepository.save(contactToSave);
    return new ResponseEntity<>(contactToSave, HttpStatus.CREATED);
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

  private Contact createContactToSaveOnDb(ContactDto contactDto) {
    Contact contact = new Contact();
    contact.setName(contactDto.getName());
    contact.setDate(createLocalDate(contactDto.getDate()));
    contact.setDetails(contactDto.getDetails());
    contact.setMethod(ContactMethod.valueOf(contactDto.getMethod()));

    return contact;
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
