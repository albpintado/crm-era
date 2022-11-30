package com.albpintado.crmera.contact;

public class ContactDto {
  private String name;
  private String date;
  private String details;
  private String method;
  private String opportunityName;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public String getOpportunityName() {
    return opportunityName;
  }

  public void setOpportunityName(String opportunityName) {
    this.opportunityName = opportunityName;
  }
}