package com.albpintado.crmera.opportunity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("opportunity")
public class OpportunityController {

  @Autowired
  private OpportunityRepository repo;

  @GetMapping
  public List<Opportunity> getAll() {
    return this.repo.findAll();
  }
}
