package com.albpintado.crmera.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
  @InjectMocks
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

    Mockito.when(this.repo.findOneByEmail(any(String.class))).thenReturn(Optional.of(expectedUser));

    ResponseEntity<User> actualResponse = this.service.getOne(userEmailDto);

    assertThat(actualResponse.getStatusCode().value(), equalTo(200));
    assertThat(actualResponse.getBody().getId(), equalTo(expectedUser.getId()));
    assertThat(actualResponse.getBody().getName(), equalTo(expectedUser.getName()));
    assertThat(actualResponse.getBody().getEmail(), equalTo(expectedUser.getEmail()));
    assertThat(actualResponse.getBody().getPassword(), equalTo(expectedUser.getPassword()));
  }

  @Test
  public void WhenGetOneThatDoesNotExists_ThenShouldReturnsNullAndStatus204() {
    UserEmailDto userEmailDto = new UserEmailDto();
    userEmailDto.setEmail("alberto@lozano.com");

    Mockito.when(this.repo.findOneByEmail(any(String.class))).thenReturn(Optional.empty());

    ResponseEntity<User> actualResponse = this.service.getOne(userEmailDto);

    assertThat(actualResponse.getStatusCode().value(), equalTo(204));
    assertThat(actualResponse.getBody(), is(nullValue()));
  }

  @Test
  public void WhenCreateOneWithCorrectDto_ReturnsUserCreated() {
    UserDto userDto = new UserDto();
    userDto.setName("Alberto Pintado");
    userDto.setEmail("alberto@pintado.com");
    userDto.setPassword("12345");

    User expectedUser = new User();
    expectedUser.setId(1L);
    expectedUser.setId(1L);
    expectedUser.setName(userDto.getName());
    expectedUser.setEmail(userDto.getEmail());
    expectedUser.setPassword(userDto.getPassword());

    ResponseEntity<User> actualResponse = this.service.create(userDto);

    assertThat(actualResponse.getStatusCode().value(), equalTo(201));
    assertThat(actualResponse.getBody().getName(), is(expectedUser.getName()));
    assertThat(actualResponse.getBody().getEmail(), is(expectedUser.getEmail()));
  }
}
