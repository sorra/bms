package com.iostate.bms.commons;

public class BadArgumentException extends RuntimeException {
  public BadArgumentException(String message) {
    super(message);
  }
}