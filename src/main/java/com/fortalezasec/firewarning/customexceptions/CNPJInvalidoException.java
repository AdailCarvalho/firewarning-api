package com.fortalezasec.firewarning.customexceptions;

public class CNPJInvalidoException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public CNPJInvalidoException(String msg) {
    super(msg);
  }

  public CNPJInvalidoException(String msg, Throwable cause) {
    super(msg, cause);
  }
  
}
