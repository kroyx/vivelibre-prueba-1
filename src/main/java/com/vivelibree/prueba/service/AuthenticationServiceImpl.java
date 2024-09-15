package com.vivelibree.prueba.service;

import com.vivelibree.prueba.domain.LoginUser;
import com.vivelibree.prueba.domain.TokenUser;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

  private final RestClient restClient;

  @Value("${auth.service.url}")
  private String authServiceUrl;

  @Autowired
  public AuthenticationServiceImpl(RestClient restClient) {
    this.restClient = restClient;
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
        .body(TokenUser.class);
    if (tokenUser != null) {
      tokenUser.setDate(LocalDate.now());
    }
    return tokenUser;
  }
}
