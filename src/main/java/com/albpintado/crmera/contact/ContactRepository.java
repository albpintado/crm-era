package com.albpintado.crmera.contact;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
  List<Contact> findByOpportunityId(Long id);

  Page<Contact> findByNameContaining(String name, Pageable pageable);
}
