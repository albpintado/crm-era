package com.albpintado.crmera.opportunity;

import com.albpintado.crmera.contact.Contact;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static com.albpintado.crmera.utils.Utils.createLocalDate;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class OpportunityServiceTests {

  @InjectMocks
  private OpportunityService opportunityService;

  @Mock
  private OpportunityRepository opportunityRepository;

  @Test
  public void WhenGetOneThatExists_ThenShouldReturnsAContactAndStatus200() {
    Opportunity expectedOpportunity = createMockOpportunity();

    Mockito.when(this.opportunityRepository.findById(any(Long.class))).thenReturn(Optional.of(expectedOpportunity));

    ResponseEntity<Opportunity> actualResponse = this.opportunityService.getOne("1");

    assertThat(actualResponse.getStatusCode().value(), equalTo(200));
    Assertions.assertThat(actualResponse.getBody()).usingRecursiveComparison().isEqualTo(expectedOpportunity);
  }

  private Opportunity createMockOpportunity() {
    Opportunity expectedOpportunity = new Opportunity();
    expectedOpportunity.setName("Solera");
    expectedOpportunity.setId(1L);
    expectedOpportunity.setEmail("hi@solera.com");
    expectedOpportunity.setIsCustomer(false);
    expectedOpportunity.setPhone("123456789");
    expectedOpportunity.setConversionDate(createLocalDate("01-01-2023"));

    return expectedOpportunity;
  }
}
