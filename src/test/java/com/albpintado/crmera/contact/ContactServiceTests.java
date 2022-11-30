package com.albpintado.crmera.contact;

import com.albpintado.crmera.opportunity.Opportunity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ContactServiceTests {

  @InjectMocks
  private ContactService service;

  @Mock
  private ContactRepository repo;

  private Contact createContact() {
    Opportunity expectedOpportunity = new Opportunity();
    expectedOpportunity.setName("Solera");
    expectedOpportunity.setId(1L);

    Contact expectedContact = new Contact();
    expectedContact.setId(1L);
    expectedContact.setName("Conversation with Alberto");
    expectedContact.setDate(LocalDate.of(2022, 12, 6));
    expectedContact.setDetails("App development for supermarket");
    expectedContact.setMethod(ContactMethod.EMAIL);
    expectedContact.setOpportunity(expectedOpportunity);

    return expectedContact;
  }

  private ContactDto createDto() {
    ContactDto contactDto = new ContactDto();
    contactDto.setName("Call with Alberto");
    contactDto.setDetails("App development for supermarket");
    contactDto.setMethod("PHONE");
    contactDto.setDate("12-06-2022");
    contactDto.setOpportunityName("Solera");

    return contactDto;
  }

  private LocalDate createLocalDate(String stringDate) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
    formatter = formatter.withLocale(Locale.US);
    LocalDate date = LocalDate.parse(stringDate, formatter);

    return date;
  }

  @Test
  public void WhenGetOneThatExists_ThenShouldReturnsAContactAndStatus200() {
    Contact expectedContact = createContact();

    Mockito.when(this.repo.findById(any(Long.class))).thenReturn(Optional.of(expectedContact));

    ResponseEntity<Contact> actualResponse = this.service.getOne("1");

    assertThat(actualResponse.getStatusCode().value(), equalTo(200));
    assertThat(actualResponse.getBody()).usingRecursiveComparison().isEqualTo(expectedContact);
  }

  @Test
  public void WhenGetOneThatDoesNotExists_ThenShouldReturnsNullAndStatus204() {
    Mockito.when(this.repo.findById(any(Long.class))).thenReturn(Optional.empty());

    ResponseEntity<Contact> actualResponse = this.service.getOne("12");

    assertThat(actualResponse.getStatusCode().value(), equalTo(204));
    assertThat(actualResponse.getBody(), is(nullValue()));
  }

  @Test
  public void WhenUpdateOneThatExists_ThenShouldReturnsContactUpdatedAndStatus200() {
    ContactDto contactDto = createDto();
    LocalDate dateFromDto = createLocalDate(contactDto.getDate());
    Contact oldContact = createContact();

    Mockito.when(this.repo.findById(any(Long.class))).thenReturn(Optional.of(oldContact));

    ResponseEntity<Contact> actualResponse = this.service.update("1", contactDto);

    assertThat(actualResponse.getStatusCode().value(), equalTo(200));
    assertThat(actualResponse.getBody().getName(), equalTo(contactDto.getName()));
    assertThat(actualResponse.getBody().getDetails(), equalTo(contactDto.getDetails()));
    assertThat(actualResponse.getBody().getMethod(), equalTo(ContactMethod.valueOf(contactDto.getMethod())));
    assertThat(actualResponse.getBody().getDate(), equalTo(dateFromDto));
    assertThat(actualResponse.getBody().getOpportunity().getName(), equalTo(contactDto.getOpportunityName()));
  }

  @Test
  public void WhenUpdateOneThatDoesNotExists_ThenShouldReturnsNullAndStatus204() {
    ContactDto contactDto = createDto();

    Mockito.when(this.repo.findById(any(Long.class))).thenReturn(Optional.empty());

    ResponseEntity<Contact> actualResponse = this.service.update("-1", contactDto);

    assertThat(actualResponse.getStatusCode().value(), equalTo(204));
    assertThat(actualResponse.getBody(), is(nullValue()));
  }

  @Test
  public void WhenUpdateOneThatExists_ThenShouldNotChangeAttributeIfIsNull() {
    ContactDto contactDto = createDto();
    contactDto.setName(null);

    Contact oldContact = createContact();

    Mockito.when(this.repo.findById(any(Long.class))).thenReturn(Optional.of(oldContact));

    ResponseEntity<Contact> actualResponse = this.service.update("1", contactDto);
    assertThat(actualResponse.getBody().getName(), equalTo(oldContact.getName()));
  }

  @Test
  public void WhenDeleteThatExists_ThenShouldReturnsNullAndStatus200() {
    Contact expectedContact = createContact();

    Mockito.when(this.repo.findById(any(Long.class))).thenReturn(Optional.of(expectedContact));

    ResponseEntity<Object> actualResponse = this.service.delete("1");

    assertThat(actualResponse.getStatusCode().value(), equalTo(200));
    assertThat(actualResponse.getBody(), is(nullValue()));
  }
}
