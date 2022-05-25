package com.hotel.mgmt.service.dto;

import com.hotel.mgmt.annotations.constraints.phone.PhoneConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class PublicRegisterDTO {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String fullName;

    private String address;

    @NotBlank
    @PhoneConstraint
    private String phone;

    @NotBlank
    private String password;

    public PublicRegisterDTO() {}

    public String getEmail() {
        return email;
    }

    public PublicRegisterDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public PublicRegisterDTO setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public PublicRegisterDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public PublicRegisterDTO setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public PublicRegisterDTO setPassword(String password) {
        this.password = password;
        return this;
    }
}
