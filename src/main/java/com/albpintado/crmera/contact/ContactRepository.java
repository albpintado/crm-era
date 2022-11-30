package com.albpintado.crmera.contact;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
  List<Contact> findByOpportunity_Id(Long id);
}
