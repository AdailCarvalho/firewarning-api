package com.fortalezasec.firewarning.Utils;

import com.fortalezasec.firewarning.customexceptions.CNPJInvalidoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.caelum.stella.validation.CNPJValidator;

@Service
public class CustomValidators {

  private CNPJValidator cnpjValidator;

  @Autowired
  public CustomValidators(CNPJValidator cnpjValidator) {
    this.cnpjValidator = cnpjValidator;
  }

  public void validarCnpj(String cnpj) {
    try {
      cnpjValidator.assertValid(cnpj);
    } catch (Exception e) {
      throw new CNPJInvalidoException("CNPJ recebido é invalido");
    }
  };
}
