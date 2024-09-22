package com.vivelibree.prueba.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vivelibree.prueba.domain.TokenUser;
import com.vivelibree.prueba.service.AuthenticationService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
class AuthenticationControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AuthenticationService authenticationServiceMock;

  @Test
  void testGetToken() throws Exception {
    // given
    LocalDate date = LocalDate.of(2024, 9, 22);
    String stringDate = date.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"));

    TokenUser tokenUser = new TokenUser();
    tokenUser.setToken("7df1kCtPfArsaJvAc0hHAYOgESu5zj8SZzZKFSRZVUf0VODCzX25Id1wwyPTWoLf");
    tokenUser.setDate(date);

    given(authenticationServiceMock.getToken()).willReturn(tokenUser);

    // when-then
    mockMvc
        .perform(MockMvcRequestBuilders.get("/get-token"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.token", is(tokenUser.getToken())))
        .andExpect(jsonPath("$.date", is(stringDate)));
  }
}
