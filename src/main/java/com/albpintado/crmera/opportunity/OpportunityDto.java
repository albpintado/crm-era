package com.albpintado.crmera.opportunity;

public class OpportunityDto {
  private Long parentOpportunity;
  private String name;
  private String phone;
  private String email;

  private boolean isCustomer;

  public Long getParentOpportunity() {
    return parentOpportunity;
  }

  public void setParentOpportunity(Long parentOpportunity) {
    this.parentOpportunity = parentOpportunity;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public boolean isCustomer() {
    return isCustomer;
  }

  public void setCustomer(boolean customer) {
    isCustomer = customer;
  }
}
