package com.vivelibree.prueba.service;

import com.vivelibree.prueba.domain.LoginUser;
import com.vivelibree.prueba.domain.TokenUser;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

  private final RestClient restClient;

  @Value("${auth.service.url}")
  private String authServiceUrl;

  @Autowired
  public AuthenticationServiceImpl(RestClient.Builder restClientBuilder) {
    this.restClient = restClientBuilder.build();
  }

  @Override
  public TokenUser getToken() {
    LoginUser loginUser = new LoginUser("auth-vivelibre", "password");
    String generateTokenEndpoint = authServiceUrl + "/token";
    TokenUser tokenUser = this.restClient
        .post()
        .uri(generateTokenEndpoint)
        .contentType(MediaType.APPLICATION_JSON)
        .body(loginUser)
        .retrieve()
        .onStatus(HttpStatusCode::isError, ((request, response) -> {
          throw new ResponseStatusException(response.getStatusCode(), response.getStatusText());
        }))
        .body(TokenUser.class);
    if (tokenUser != null) {
      tokenUser.setDate(LocalDate.now());
    }
    return tokenUser;
  }
}
