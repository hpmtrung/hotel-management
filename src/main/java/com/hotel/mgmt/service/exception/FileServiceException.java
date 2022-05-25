package com.hotel.mgmt.service.exception;

public class FileServiceException extends RuntimeException {

    private static final long serialVersionUID = -8413117375852736118L;

    public FileServiceException(String message) {
        super(message);
    }

    public FileServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
