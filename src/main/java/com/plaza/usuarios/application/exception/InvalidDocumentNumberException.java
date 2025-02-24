package com.plaza.usuarios.application.exception;

public class InvalidDocumentNumberException extends RuntimeException {
  public InvalidDocumentNumberException(String message) {
    super(message);
  }
}
