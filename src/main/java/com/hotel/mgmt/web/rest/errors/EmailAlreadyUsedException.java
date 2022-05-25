package com.hotel.mgmt.web.rest.errors;

public class EmailAlreadyUsedException extends EntityValidationException {

    private static final long serialVersionUID = 1L;

    public EmailAlreadyUsedException() {
        super("Email is already used", "account", "usedEmail");
    }
}
