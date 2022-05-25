package com.hotel.mgmt.service.exception;

public class InvalidPasswordServiceException extends RuntimeException {

    private static final long serialVersionUID = -1712915378391375416L;

    public InvalidPasswordServiceException() {
        super("Incorrect password");
    }
}
