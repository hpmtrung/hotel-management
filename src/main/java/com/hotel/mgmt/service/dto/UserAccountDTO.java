package com.hotel.mgmt.service.dto;

import com.hotel.mgmt.domain.Account;

public class UserAccountDTO {

    private Long id;

    private String email;

    private String fullName;

    private String address;

    private String phone;

    private String langKey;

    private boolean activated;

    private String imageURL;

    private String authority;

    public UserAccountDTO() {}

    public UserAccountDTO(Account account) {
        this.id = account.getId();
        this.email = account.getEmail();
        this.fullName = account.getFullName();
        this.address = account.getAddress();
        this.phone = account.getPhone();
        this.langKey = account.getLangKey();
        this.activated = account.isActivated();
        this.imageURL = account.getImageURL();
        this.authority = account.getAuthority().getName();
    }

    public String getImageURL() {
        return imageURL;
    }

    public UserAccountDTO setImageURL(String imageURL) {
        this.imageURL = imageURL;
        return this;
    }

    public boolean isActivated() {
        return activated;
    }

    public UserAccountDTO setActivated(boolean activated) {
        this.activated = activated;
        return this;
    }

    public String getAuthority() {
        return authority;
    }

    public UserAccountDTO setAuthority(String authority) {
        this.authority = authority;
        return this;
    }

    public String getLangKey() {
        return langKey;
    }

    public UserAccountDTO setLangKey(String langKey) {
        this.langKey = langKey;
        return this;
    }

    public Long getId() {
        return id;
    }

    public UserAccountDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserAccountDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public UserAccountDTO setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public UserAccountDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public UserAccountDTO setPhone(String phone) {
        this.phone = phone;
        return this;
    }
}
