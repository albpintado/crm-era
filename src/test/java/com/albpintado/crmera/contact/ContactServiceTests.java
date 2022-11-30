package com.albpintado.crmera.contact;

import com.albpintado.crmera.opportunity.Opportunity;
import com.albpintado.crmera.opportunity.OpportunityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Optional;

import static com.albpintado.crmera.utils.Utils.createLocalDate;
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
  private ContactRepository contactRepository;

  @Mock
  private OpportunityRepository opportunityRepository;

  private Opportunity createMockOpportunity() {
    Opportunity expectedOpportunity = new Opportunity();
    expectedOpportunity.setName("Solera");
    expectedOpportunity.setId(1L);
    expectedOpportunity.setEmail("hi@solera.com");
    expectedOpportunity.setIsCustomer(false);
    expectedOpportunity.setPhone("123456789");

    return expectedOpportunity;
  }

  private Contact createMockContact() {
    Opportunity expectedOpportunity = createMockOpportunity();
    ContactDto contactDto = createMockDto();

    Contact expectedContact = new Contact();
    expectedContact.setId(1L);
    expectedContact.setName(contactDto.getName());
    expectedContact.setDate(createLocalDate(contactDto.getDate()));
    expectedContact.setDetails(contactDto.getDetails());
    expectedContact.setMethod(ContactMethod.valueOf(contactDto.getMethod()));
    expectedContact.setOpportunity(expectedOpportunity);

    return expectedContact;
  }

  private ContactDto createMockDto() {
    ContactDto contactDto = new ContactDto();
    contactDto.setName("Call with Alberto");
    contactDto.setDetails("App development for supermarket");
    contactDto.setMethod("PHONE");
    contactDto.setDate("12-06-2022");
    contactDto.setOpportunityId(1L);

    return contactDto;
  }

  @Test
  public void WhenGetOneThatExists_ThenShouldReturnsAContactAndStatus200() {
    Contact expectedContact = createMockContact();

    Mockito.when(this.contactRepository.findById(any(Long.class))).thenReturn(Optional.of(expectedContact));

    ResponseEntity<Contact> actualResponse = this.service.getOne("1");

    assertThat(actualResponse.getStatusCode().value(), equalTo(200));
    assertThat(actualResponse.getBody()).usingRecursiveComparison().isEqualTo(expectedContact);
  }

  @Test
  public void WhenGetOneThatDoesNotExists_ThenShouldReturnsNullAndStatus204() {
    Mockito.when(this.contactRepository.findById(any(Long.class))).thenReturn(Optional.empty());

    ResponseEntity<Contact> actualResponse = this.service.getOne("12");

    assertThat(actualResponse.getStatusCode().value(), equalTo(204));
    assertThat(actualResponse.getBody(), is(nullValue()));
  }

  @Test
  public void WhenUpdateOneThatExists_ThenShouldReturnsContactUpdatedAndStatus200() {
    ContactDto contactDto = createMockDto();
    LocalDate dateFromDto = createLocalDate(contactDto.getDate());
    Contact oldContact = createMockContact();

    Mockito.when(this.contactRepository.findById(any(Long.class))).thenReturn(Optional.of(oldContact));

    ResponseEntity<Contact> actualResponse = this.service.update("1", contactDto);

    assertThat(actualResponse.getStatusCode().value(), equalTo(200));
    assertThat(actualResponse.getBody().getName(), equalTo(contactDto.getName()));
    assertThat(actualResponse.getBody().getDetails(), equalTo(contactDto.getDetails()));
    assertThat(actualResponse.getBody().getMethod(), equalTo(ContactMethod.valueOf(contactDto.getMethod())));
    assertThat(actualResponse.getBody().getDate(), equalTo(dateFromDto));
    assertThat(actualResponse.getBody().getOpportunity().getId(), equalTo(contactDto.getOpportunityId()));
  }

  @Test
  public void WhenUpdateOneThatDoesNotExists_ThenShouldReturnsNullAndStatus204() {
    ContactDto contactDto = createMockDto();

    Mockito.when(this.contactRepository.findById(any(Long.class))).thenReturn(Optional.empty());

    ResponseEntity<Contact> actualResponse = this.service.update("-1", contactDto);

    assertThat(actualResponse.getStatusCode().value(), equalTo(204));
    assertThat(actualResponse.getBody(), is(nullValue()));
  }

  @Test
  public void WhenUpdateOneThatExists_ThenShouldNotChangeAttributeIfIsNull() {
    ContactDto contactDto = createMockDto();
    contactDto.setName(null);

    Contact oldContact = createMockContact();

    Mockito.when(this.contactRepository.findById(any(Long.class))).thenReturn(Optional.of(oldContact));

    ResponseEntity<Contact> actualResponse = this.service.update("1", contactDto);
    assertThat(actualResponse.getBody().getName(), equalTo(oldContact.getName()));
  }

  @Test
  public void WhenDeleteThatExists_ThenShouldReturnsNullAndStatus200() {
    Contact expectedContact = createMockContact();

    Mockito.when(this.contactRepository.findById(any(Long.class))).thenReturn(Optional.of(expectedContact));

    ResponseEntity<Object> actualResponse = this.service.delete("1");

    assertThat(actualResponse.getStatusCode().value(), equalTo(200));
    assertThat(actualResponse.getBody(), is(nullValue()));
  }

  @Test
  public void WhenDeleteThatDoesNotExists_ThenShouldReturnsNullAndStatus204() {
    Mockito.when(this.contactRepository.findById(any(Long.class))).thenReturn(Optional.empty());

    ResponseEntity<Object> actualResponse = this.service.delete("1");

    assertThat(actualResponse.getStatusCode().value(), equalTo(204));
    assertThat(actualResponse.getBody(), is(nullValue()));
  }
    @Test
    public void WhenCreateOneWithCorrectDto_ReturnsContactCreatedAndStatus201() {
      ContactDto contactDto = createMockDto();
      Contact expectedContact = createMockContact();

      Mockito.when(opportunityRepository.findById(any(Long.class))).thenReturn(Optional.of(createMockOpportunity()));

      ResponseEntity<Contact> actualResponse = this.service.create(contactDto);
      actualResponse.getBody().setId(1L);

      assertThat(actualResponse.getStatusCode().value(), equalTo(201));
      assertThat(actualResponse.getBody()).usingRecursiveComparison().isEqualTo(expectedContact);
  }
}
