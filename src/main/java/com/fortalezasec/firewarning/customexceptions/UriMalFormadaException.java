package com.fortalezasec.firewarning.customexceptions;

public class UriMalFormadaException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public UriMalFormadaException(String msg) {
    super(msg);
  }

  public UriMalFormadaException(String msg, Throwable cause) {
    super(msg, cause);
  }
  
}
