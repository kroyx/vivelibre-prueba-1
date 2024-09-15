package com.vivelibree.prueba.controller;

import com.vivelibree.prueba.domain.TokenUser;
import org.springframework.web.bind.annotation.GetMapping;

public interface AuthenticationApi {

  @GetMapping(path = "get-token")
  TokenUser getToken();
}
