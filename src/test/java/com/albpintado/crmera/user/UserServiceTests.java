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
  public void WhenCreateOneWithCorrectDto_ReturnsUserCreatedAndStatus200() {
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

  @Test
  public void WhenUpdateOneThatExists_ThenShouldReturnsUserUpdatedAndStatus200() {
    String oldName = "Alberto Pintado";
    String oldEmail = "alberto@pintado.com";
    String oldPassword = "12345";

    UpdateUserDto updateUserDto = new UpdateUserDto();
    updateUserDto.setName("Luis Pintado");
    updateUserDto.setEmail(oldEmail);
    updateUserDto.setNewEmail("luis@pintado.com");
    updateUserDto.setPassword("54321");

    User oldUser = new User();
    oldUser.setName(oldName);
    oldUser.setEmail(oldEmail);
    oldUser.setPassword(oldPassword);

    Mockito.when(this.repo.findOneByEmail(any(String.class))).thenReturn(Optional.of(oldUser));

    ResponseEntity<User> actualResponse = this.service.update(updateUserDto);

    assertThat(actualResponse.getStatusCode().value(), equalTo(200));
    assertThat(actualResponse.getBody().getName(), equalTo(updateUserDto.getName()));
    assertThat(actualResponse.getBody().getEmail(), equalTo(updateUserDto.getNewEmail()));
    assertThat(actualResponse.getBody().getPassword(), equalTo(updateUserDto.getPassword()));
  }

  @Test
  public void WhenUpdateOneThatDoesNotExists_ThenShouldReturnsNullAndStatus204() {
    UpdateUserDto updateUserDto = new UpdateUserDto();
    updateUserDto.setName("Alberto Pintado");
    updateUserDto.setEmail("alberto@pintado.com");
    updateUserDto.setNewEmail("luis@pintado.com");
    updateUserDto.setPassword("12345");

    Mockito.when(this.repo.findOneByEmail(any(String.class))).thenReturn(Optional.empty());

    ResponseEntity<User> actualResponse = this.service.update(updateUserDto);

    assertThat(actualResponse.getStatusCode().value(), equalTo(204));
    assertThat(actualResponse.getBody(), is(nullValue()));
  }

  @Test
  public void WhenUpdateOneThatExists_ThenShouldChangeNameIfIsNotNull() {
    String oldName = "Alberto Pintado";
    String oldEmail = "alberto@pintado.com";
    String oldPassword = "12345";

    UpdateUserDto updateUserDto = new UpdateUserDto();
    updateUserDto.setEmail(oldEmail);
    updateUserDto.setNewEmail("luis@pintado.com");
    updateUserDto.setPassword("54321");

    User oldUser = new User();
    oldUser.setName(oldName);
    oldUser.setEmail(oldEmail);
    oldUser.setPassword(oldPassword);

    Mockito.when(this.repo.findOneByEmail(any(String.class))).thenReturn(Optional.of(oldUser));

    ResponseEntity<User> actualResponse = this.service.update(updateUserDto);
    assertThat(actualResponse.getBody().getName(), equalTo(oldName));
  }

  @Test
  public void WhenUpdateOneThatExists_ThenShouldChangeEmailIfIsNotNull() {
    String oldName = "Alberto Pintado";
    String oldEmail = "alberto@pintado.com";
    String oldPassword = "12345";

    UpdateUserDto updateUserDto = new UpdateUserDto();
    updateUserDto.setName("Luis Pintado");
    updateUserDto.setEmail(oldEmail);
    updateUserDto.setPassword("54321");

    User oldUser = new User();
    oldUser.setName(oldName);
    oldUser.setEmail(oldEmail);
    oldUser.setPassword(oldPassword);

    Mockito.when(this.repo.findOneByEmail(any(String.class))).thenReturn(Optional.of(oldUser));

    ResponseEntity<User> actualResponse = this.service.update(updateUserDto);
    assertThat(actualResponse.getBody().getEmail(), equalTo(oldEmail));
  }
}
