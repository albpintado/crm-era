package com.albpintado.crmera.opportunity;

import org.assertj.core.api.Assertions;
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
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class OpportunityServiceTests {

  @InjectMocks
  private OpportunityService opportunityService;

  @Mock
  private OpportunityRepository opportunityRepository;

  @Test
  public void WhenGetOneThatExists_ThenShouldReturnsAnOpportunityAndStatus200() {
    Opportunity expectedOpportunity = createMockOpportunity();

    Mockito.when(this.opportunityRepository.findById(any(Long.class))).thenReturn(Optional.of(expectedOpportunity));

    ResponseEntity<Opportunity> actualResponse = this.opportunityService.getOne("1");

    assertThat(actualResponse.getStatusCode().value(), equalTo(200));
    Assertions.assertThat(actualResponse.getBody()).usingRecursiveComparison().isEqualTo(expectedOpportunity);
  }

  @Test
  public void WhenGetOneThatDoesNotExists_ThenShouldReturnsNullAndStatus204() {
    Mockito.when(this.opportunityRepository.findById(any(Long.class))).thenReturn(Optional.empty());

    ResponseEntity<Opportunity> actualResponse = this.opportunityService.getOne("12");

    assertThat(actualResponse.getStatusCode().value(), equalTo(204));
    assertThat(actualResponse.getBody(), is(nullValue()));
  }

  @Test
  public void WhenUpdateOneThatExists_ThenShouldReturnsOpportunityUpdatedAndStatus200() {
    OpportunityDto opportunityDto = createMockDto();
    Opportunity oldOpportunity = createMockOpportunity();

    Mockito.when(this.opportunityRepository.findById(any(Long.class))).thenReturn(Optional.of(oldOpportunity));

    ResponseEntity<Opportunity> actualResponse = this.opportunityService.update("1", opportunityDto);

    assertThat(actualResponse.getStatusCode().value(), equalTo(200));
    assertThat(actualResponse.getBody().getName(), equalTo(opportunityDto.getName()));
    assertThat(actualResponse.getBody().getPhone(), equalTo(opportunityDto.getPhone()));
    assertThat(actualResponse.getBody().getEmail(), equalTo(opportunityDto.getEmail()));
  }

  @Test
  public void WhenUpdateOneThatDoesNotExists_ThenShouldReturnsNullAndStatus204() {
    OpportunityDto opportunityDto = createMockDto();

    Mockito.when(this.opportunityRepository.findById(any(Long.class))).thenReturn(Optional.empty());

    ResponseEntity<Opportunity> actualResponse = this.opportunityService.update("-1", opportunityDto);

    assertThat(actualResponse.getStatusCode().value(), equalTo(204));
    assertThat(actualResponse.getBody(), is(nullValue()));
  }

  @Test
  public void WhenUpdateOneThatExists_ThenShouldNotChangeAttributeIfIsNull() {
    OpportunityDto opportunityDto = createMockDto();
    opportunityDto.setName(null);

    Opportunity oldOpportunity = createMockOpportunity();

    Mockito.when(this.opportunityRepository.findById(any(Long.class))).thenReturn(Optional.of(oldOpportunity));

    ResponseEntity<Opportunity> actualResponse = this.opportunityService.update("1", opportunityDto);
    assertThat(actualResponse.getBody().getName(), equalTo(oldOpportunity.getName()));
    assertThat(actualResponse.getBody().getEmail(), equalTo(oldOpportunity.getEmail()));
    assertThat(actualResponse.getBody().getPhone(), equalTo(oldOpportunity.getPhone()));
  }

  @Test
  public void WhenToggleCustomerStatusToTrue_ReturnsOpportunityUpdatedAndStatus200() {
    Opportunity opportunity = createMockOpportunity();
    Mockito.when(this.opportunityRepository.findById(any(Long.class))).thenReturn(Optional.of(opportunity));

    ResponseEntity<Opportunity> actualResponse = this.opportunityService.toggleCustomerStatus("1");

    verify(this.opportunityRepository).findById(1L);

    assertThat(actualResponse.getStatusCode().value(), equalTo(200));
    assertThat(actualResponse.getBody().getIsCustomer(), equalTo(true));
    assertThat(actualResponse.getBody().getConversionDate(), equalTo(LocalDate.now()));
  }

  @Test
  public void WhenToggleCustomerStatusToFalse_ReturnsOpportunityUpdatedAndStatus200() {
    Opportunity opportunity = createMockOpportunity();
    opportunity.setIsCustomer(true);
    opportunity.setConversionDate(LocalDate.now());
    Mockito.when(this.opportunityRepository.findById(any(Long.class))).thenReturn(Optional.of(opportunity));

    ResponseEntity<Opportunity> actualResponse = this.opportunityService.toggleCustomerStatus("1");

    verify(this.opportunityRepository).findById(1L);

    assertThat(actualResponse.getStatusCode().value(), equalTo(200));
    assertThat(actualResponse.getBody().getIsCustomer(), equalTo(false));
    assertThat(actualResponse.getBody().getConversionDate(), is(nullValue()));
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

  private OpportunityDto createMockDto() {
    OpportunityDto opportunityDto = new OpportunityDto();
    opportunityDto.setName("Call with Alberto");
    opportunityDto.setPhone("653123456");
    opportunityDto.setEmail("luis@solera.com");

    return opportunityDto;
  }
}
