package com.fortalezasec.firewarning.customexceptions;

public class UserAlreadyExistsException extends Exception {

  private static final long serialVersionUID = 1L;

  public UserAlreadyExistsException(String msg) {
    super(msg);
  }

  public UserAlreadyExistsException(String msg, Throwable cause) {
    super(msg, cause);
  }

}
