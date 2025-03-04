package com.plaza.usuarios.application.exception;

public class InvalidCellPhoneException extends RuntimeException {
    public InvalidCellPhoneException(String message) {
        super(message);
    }
}
