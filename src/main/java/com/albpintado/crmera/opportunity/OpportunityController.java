package com.albpintado.crmera.opportunity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/opportunity")
public class OpportunityController {

  @Autowired
  private OpportunityService opportunityService;

  @GetMapping
  public ResponseEntity<List<Opportunity>> getAll() {
    return this.opportunityService.getAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Opportunity> getOne(@PathVariable String id) {
    return this.opportunityService.getOne(id);
  }

  @PostMapping
  public ResponseEntity<Opportunity> create(@RequestBody OpportunityDto opportunityDto) {
    return this.opportunityService.create(opportunityDto);
  }

  @PostMapping("/{id}")
  public ResponseEntity<Opportunity> toggleCustomerStatus(@PathVariable String id) {
    return this.opportunityService.toggleCustomerStatus(id);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Opportunity> update(@PathVariable String id, @RequestBody OpportunityDto opportunityDto) {
    return this.opportunityService.update(id, opportunityDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> delete(@PathVariable String id) {
    return this.opportunityService.delete(id);
  }
}
