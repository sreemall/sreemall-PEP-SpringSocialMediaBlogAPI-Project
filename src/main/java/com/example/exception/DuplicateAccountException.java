package com.example.exception;

public class DuplicateAccountException extends RuntimeException {
  public DuplicateAccountException(String username) {
      super("Account already exists for username: " + username);
  }
}