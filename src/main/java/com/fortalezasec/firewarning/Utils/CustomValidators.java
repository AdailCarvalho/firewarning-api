package com.fortalezasec.firewarning.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.caelum.stella.validation.CNPJValidator;

@Component
public class CustomValidators {

  private CNPJValidator cnpjValidator;

  @Autowired
  public CustomValidators(CNPJValidator cnpjValidator) {
    this.cnpjValidator = cnpjValidator;
  }
  
  public boolean validarCnpj(String cnpj) {
    try {
      cnpjValidator.assertValid(cnpj);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  };
}
