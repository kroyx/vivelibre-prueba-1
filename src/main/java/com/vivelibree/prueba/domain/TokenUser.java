package com.vivelibree.prueba.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TokenUser {
  private String token;

  @JsonFormat(pattern = "MMMM dd, YYYY")
  private LocalDate date;
}
