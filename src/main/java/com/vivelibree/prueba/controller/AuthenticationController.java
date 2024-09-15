package com.vivelibree.prueba.controller;

import com.vivelibree.prueba.domain.TokenUser;
import com.vivelibree.prueba.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController implements AuthenticationApi {

  private final AuthenticationService authenticationService;

  @Autowired
  public AuthenticationController(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @Override
  public TokenUser getToken() {
    return this.authenticationService.getToken();
  }
}
