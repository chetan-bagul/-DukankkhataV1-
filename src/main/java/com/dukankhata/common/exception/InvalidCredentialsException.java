package com.dukankhata.common.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() { super("Invalid mobile number or password"); }
}
