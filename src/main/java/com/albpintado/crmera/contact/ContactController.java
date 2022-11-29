package com.albpintado.crmera.contact;

import com.albpintado.crmera.user.User;
import com.albpintado.crmera.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("contacts")
public class ContactController {

  @Autowired
  private ContactService service;

  @GetMapping(path = "/all")
  public List<Contact> getAll() {
    return this.service.getAll();
  }
}
