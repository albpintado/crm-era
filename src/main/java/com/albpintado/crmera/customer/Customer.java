package com.albpintado.crmera.customer;

import com.albpintado.crmera.contact.Contact;
import com.albpintado.crmera.opportunity.Opportunity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer")
public class Customer {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "phone", nullable = false)
  private String phone;

  @Column(name = "email", nullable = false)
  private String email;

  @OneToMany(mappedBy = "customer")
  private List<Opportunity> opportunities = new ArrayList<>();

  @OneToMany(mappedBy = "customer")
  private List<Contact> contacts = new ArrayList<>();

  public List<Contact> getContacts() {
    return contacts;
  }

  public void setContacts(List<Contact> contacts) {
    this.contacts = contacts;
  }

  public List<Opportunity> getOpportunities() {
    return opportunities;
  }

  public void setOpportunities(List<Opportunity> opportunities) {
    this.opportunities = opportunities;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

}