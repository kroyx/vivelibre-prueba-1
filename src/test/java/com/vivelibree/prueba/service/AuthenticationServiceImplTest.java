package com.vivelibree.prueba.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withUnauthorizedRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vivelibree.prueba.domain.LoginUser;
import com.vivelibree.prueba.domain.TokenUser;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.server.ResponseStatusException;

@TestPropertySource("/application-test.properties")
@RestClientTest(AuthenticationServiceImpl.class)
class AuthenticationServiceImplTest {

  @Value("${auth.service.url}")
  String authServiceUrl;

  @Autowired
  MockRestServiceServer mockServer;

  @Autowired
  AuthenticationServiceImpl authenticationService;

  @Autowired
  ObjectMapper objectMapper;

  @Test
  void testGetTokenCorrectUser() throws JsonProcessingException {
    // given
    TokenUser data = new TokenUser();
    data.setToken("7df1kCtPfArsaJvAc0hHAYOgESu5zj8SZzZKFSRZVUf0VODCzX25Id1wwyPTWoLf");

    // when
    mockServer
        .expect(requestTo(authServiceUrl + "/token"))
        .andRespond(withSuccess(objectMapper.writeValueAsString(data), MediaType.APPLICATION_JSON));

    // then
    TokenUser tokenUser = this.authenticationService.getToken();
    assertThat(tokenUser, notNullValue());
    assertThat(tokenUser.getToken(), is(data.getToken()));
    assertThat(tokenUser.getDate(), notNullValue());
    assertThat(tokenUser.getDate(), is(LocalDate.now()));
  }

  @Test
  void testGetTokenCorrectUserReturnsEmptyResponse() {
    // given
    // when
    mockServer
        .expect(requestTo(authServiceUrl + "/token"))
        .andRespond(withSuccess());

    // then
    TokenUser tokenUser = this.authenticationService.getToken();
    assertThat(tokenUser, nullValue());
  }

  @Test()
  void testGetTokenBadCredentials() {
    // given
    // when
    mockServer
        .expect(requestTo(authServiceUrl + "/token"))
        .andRespond(withUnauthorizedRequest());

    // then
    assertThrows(ResponseStatusException.class, () -> this.authenticationService.getToken());
  }
}
