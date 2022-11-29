package com.albpintado.crmera.user;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
public class UserServiceTests {
  @Autowired
  private UserService service;

  @Mock
  private UserRepository repo;

  @Test
  public void WhenGetOneThatExists_ThenShouldReturnsAUserAndStatus200() {
    UserEmailDto userEmailDto = new UserEmailDto();
    userEmailDto.setEmail("alberto@pintado.com");

    User expectedUser = new User();
    expectedUser.setId(1L);
    expectedUser.setName("Alberto Pintado");
    expectedUser.setEmail(userEmailDto.getEmail());
    expectedUser.setPassword("12345");

    Mockito.when(this.repo.findOneByEmail("alberto@pintado.com")).thenReturn(Optional.of(expectedUser));

    ResponseEntity<User> actualResponse = this.service.getOne(userEmailDto);

    assertThat(actualResponse.getStatusCode(), equalTo(200));
    assertThat(actualResponse.getBody().getId(), equalTo(expectedUser.getId()));
    assertThat(actualResponse.getBody().getName(), equalTo(expectedUser.getName()));
    assertThat(actualResponse.getBody().getEmail(), equalTo(expectedUser.getEmail()));
    assertThat(actualResponse.getBody().getPassword(), equalTo(expectedUser.getPassword()));
  }
}
