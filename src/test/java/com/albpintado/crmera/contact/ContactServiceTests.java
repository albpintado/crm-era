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
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ContactServiceTests {

  @InjectMocks
  private ContactService service;

  @Mock
  private ContactRepository repo;

  @Test
  public void WhenGetOneThatExists_ThenShouldReturnsAContactAndStatus200() {
    Opportunity expectedOpportunity = new Opportunity();
    expectedOpportunity.setId(1L);
    Contact expectedContact = new Contact();
    expectedContact.setId(1L);
    expectedContact.setName("Conversation with Alberto");
    expectedContact.setDate(LocalDate.of(2022, 12, 06));
    expectedContact.setDetails("App development for supermarket");
    expectedContact.setMethod(ContactMethod.EMAIL);
    expectedContact.setOpportunity(expectedOpportunity);

    Mockito.when(this.repo.findById(any(Long.class))).thenReturn(Optional.of(expectedContact));

    ResponseEntity<Contact> actualResponse = this.service.getOne("1");

    assertThat(actualResponse.getStatusCode().value(), equalTo(200));
    assertThat(actualResponse.getBody()).usingRecursiveComparison().isEqualTo(expectedContact);
  }
}
