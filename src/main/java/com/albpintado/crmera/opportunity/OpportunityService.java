package com.albpintado.crmera.opportunity;

import com.albpintado.crmera.contact.Contact;
import com.albpintado.crmera.contact.ContactDto;
import com.albpintado.crmera.contact.ContactMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

import static com.albpintado.crmera.utils.Utils.createLocalDate;

@Service
public class OpportunityService {

  @Autowired
  private OpportunityRepository opportunityRepository;

  public ResponseEntity<Opportunity> getOne(String id) {
    Optional<Opportunity> userFromDb = this.opportunityRepository.findById(Long.valueOf(id));
    if (userFromDb.isPresent()) {
      return new ResponseEntity<>(userFromDb.get(), HttpStatus.OK);
    }
    return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
  }

  public ResponseEntity<Opportunity> update(String id, OpportunityDto opportunityDto) {
    Optional<Opportunity> contactToUpdateFromDb = this.opportunityRepository.findById(Long.valueOf(id));

    if (contactToUpdateFromDb.isPresent()) {
      Opportunity updatedOpportunity = updateOpportunityBeforeSaveOnDb(contactToUpdateFromDb.get(), opportunityDto);
      this.opportunityRepository.save(updatedOpportunity);

      return new ResponseEntity<>(updatedOpportunity, HttpStatus.OK);
    }

    return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
  }

  private Opportunity updateOpportunityBeforeSaveOnDb(Opportunity opportunityToUpdate, OpportunityDto opportunityDto) {
    if (opportunityDto.getName() != null) opportunityToUpdate.setName(opportunityDto.getName());
    if (opportunityDto.getEmail() != null) opportunityToUpdate.setEmail(opportunityDto.getEmail());
    if (opportunityDto.getPhone() != null) opportunityToUpdate.setPhone(opportunityDto.getPhone());

    return opportunityToUpdate;
  }
}
