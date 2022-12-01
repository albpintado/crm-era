package com.albpintado.crmera.opportunity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OpportunityService {

  @Autowired
  private OpportunityRepository opportunityRepository;

  public ResponseEntity<List<Opportunity>> getAll() {
    return new ResponseEntity<>(this.opportunityRepository.findAll(), HttpStatus.OK);
  }

  public ResponseEntity<Opportunity> getOne(String id) {
    Optional<Opportunity> userFromDb = this.opportunityRepository.findById(Long.valueOf(id));
    if (userFromDb.isPresent()) {
      return new ResponseEntity<>(userFromDb.get(), HttpStatus.OK);
    }
    return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
  }

  public ResponseEntity<Opportunity> create(OpportunityDto opportunityDto) {
    Opportunity opportunityToSave = createOpportunityToSaveOnDb(opportunityDto);

    this.opportunityRepository.save(opportunityToSave);
    return new ResponseEntity<>(opportunityToSave, HttpStatus.CREATED);
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

  public ResponseEntity<Object> delete(String id) {
    Optional<Opportunity> opportunityFromDb = this.opportunityRepository.findById(Long.valueOf(id));
    if (opportunityFromDb.isPresent()) {
      this.opportunityRepository.delete(opportunityFromDb.get());
      return new ResponseEntity<>(null, HttpStatus.OK);
    }
    return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
  }

  private Opportunity createOpportunityToSaveOnDb(OpportunityDto opportunityDto) {
    Opportunity opportunity = new Opportunity();
    opportunity.setName(opportunityDto.getName());
    opportunity.setPhone(opportunityDto.getPhone());
    opportunity.setEmail(opportunityDto.getEmail());
    if (opportunityDto.getParentOpportunity() != null)
      opportunity.setOpportunity(getOpportunityFromDb(opportunityDto.getParentOpportunity()));
    opportunity.setIsCustomer(opportunityDto.isCustomer());
    if (opportunityDto.isCustomer()) opportunity.setConversionDate(LocalDate.now());

    return opportunity;
  }

  private Opportunity getOpportunityFromDb(Long id) {
    Optional<Opportunity> opportunityFromDb = this.opportunityRepository.findById(id);
    if (opportunityFromDb.isPresent()) return opportunityFromDb.get();
    return null;
  }

  private Opportunity updateOpportunityBeforeSaveOnDb(Opportunity opportunityToUpdate, OpportunityDto opportunityDto) {
    if (opportunityDto.getName() != null) opportunityToUpdate.setName(opportunityDto.getName());
    if (opportunityDto.getEmail() != null) opportunityToUpdate.setEmail(opportunityDto.getEmail());
    if (opportunityDto.getPhone() != null) opportunityToUpdate.setPhone(opportunityDto.getPhone());

    return opportunityToUpdate;
  }
}
