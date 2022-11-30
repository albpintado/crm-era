package com.albpintado.crmera.opportunity;

import com.albpintado.crmera.contact.Contact;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "opportunity")
public class Opportunity {
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

  @OneToMany(mappedBy = "opportunity")
  @JsonIgnore
  private List<Contact> contacts = new ArrayList<>();

  @Column(name = "is_customer", nullable = false, columnDefinition = "boolean default false")
  private Boolean isCustomer = false;

  @Column(name = "conversion_date")
  private LocalDateTime conversionDate;

  @ManyToOne(optional = false)
  @JoinColumn(name = "parent_opportunity", nullable = true)
  @JsonBackReference
  private Opportunity opportunity;

  public Opportunity getOpportunity() {
    return opportunity;
  }

  public void setOpportunity(Opportunity opportunity) {
    this.opportunity = opportunity;
  }

  public LocalDateTime getConversionDate() {
    return conversionDate;
  }

  public void setConversionDate(LocalDateTime conversionDate) {
    this.conversionDate = conversionDate;
  }

  public Boolean getIsCustomer() {
    return isCustomer;
  }

  public void setIsCustomer(Boolean isCustomer) {
    this.isCustomer = isCustomer;
  }

  public List<Contact> getContacts() {
    return contacts;
  }

  public void setContacts(List<Contact> contacts) {
    this.contacts = contacts;
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
