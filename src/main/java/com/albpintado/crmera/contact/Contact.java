package com.albpintado.crmera.contact;

import com.albpintado.crmera.customer.Customer;
import com.albpintado.crmera.opportunity.Opportunity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "contact")
public class Contact {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "date", nullable = false)
  private LocalDateTime date;

  @Column(name = "details")
  private String details;

  @Enumerated(EnumType.STRING)
  @Column(name = "method", nullable = false)
  private ContactMethod method;

  @ManyToOne
  @JoinColumn(name = "opportunity_id", nullable = false, unique = true)
  private Opportunity opportunity;

  public Opportunity getOpportunity() {
    return opportunity;
  }

  public void setOpportunity(Opportunity opportunity) {
    this.opportunity = opportunity;
  }

  public ContactMethod getMethod() {
    return method;
  }

  public void setMethod(ContactMethod method) {
    this.method = method;
  }

  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public void setDate(LocalDateTime date) {
    this.date = date;
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