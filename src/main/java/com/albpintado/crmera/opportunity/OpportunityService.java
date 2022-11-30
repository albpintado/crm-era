package com.albpintado.crmera.opportunity;

import com.albpintado.crmera.contact.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OpportunityService {

  @Autowired
  private OpportunityRepository opportunityRepository;

  public ResponseEntity<Opportunity> getOne(String id) {
    Optional<Opportunity> userFromDb = this.opportunityRepository.findById(Long.valueOf(id));
    if (userFromDb.isPresent()) {
      return new ResponseEntity<>(userFromDb.get(), HttpStatus.OK);
    }
    return null;
  }
}
