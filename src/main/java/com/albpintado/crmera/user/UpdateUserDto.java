package com.albpintado.crmera.user;

public class UpdateUserDto {
  private String email;

  private String newEmail;
  private String password;
  private String name;

  public String getEmail() {
    return email;
  }

  public String getNewEmail() {
    return newEmail;
  }

  public String getPassword() {
    return password;
  }

  public String getName() {
    return name;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setNewEmail(String newEmail) {
    this.newEmail = newEmail;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setName(String name) {
    this.name = name;
  }
}