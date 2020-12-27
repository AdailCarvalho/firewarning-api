package com.fortalezasec.firewarning.CustomExceptions;

public class TypeDoNotExistsException extends Exception {

  private static final long serialVersionUID = 1L;

  public TypeDoNotExistsException(String msg) {
    super(msg);
  }

  public TypeDoNotExistsException(String msg, Throwable cause) {
    super(msg, cause);
  }

}
