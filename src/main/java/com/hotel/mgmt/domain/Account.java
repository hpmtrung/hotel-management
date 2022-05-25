package com.hotel.mgmt.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hotel.mgmt.annotations.constraints.phone.PhoneConstraint;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "TAI_KHOAN")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Account implements Serializable {

    private static final long serialVersionUID = -3219819588454675156L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_SEQ_TAI_KHOAN")
    @SequenceGenerator(name = "ID_SEQ_TAI_KHOAN", allocationSize = 1, initialValue = 1, sequenceName = "ID_SEQ_TAI_KHOAN")
    @Column(name = "MA_TAI_KHOAN")
    private Long id;

    @Email
    @NotBlank
    @NaturalId
    @Size(max = 100)
    @Column(name = "EMAIL", unique = true)
    private String email;

    @JsonIgnore
    @NotBlank
    @Column(name = "MAT_KHAU")
    private String hashedPassword;

    @NotBlank
    @Size(max = 100)
    @Column(name = "HO_TEN")
    private String fullName;

    @NotBlank
    @Size(max = 200)
    @Column(name = "DIA_CHI")
    private String address;

    @NotBlank
    @PhoneConstraint
    private String phone;

    @Column(name = "DA_KICH_HOAT")
    private boolean activated;

    @Column(name = "MA_KICH_HOAT")
    private String activationKey;

    @Column(name = "MA_DOI_MAT_KHAU")
    private String resetKey;

    @Column(name = "NGAYGIO_YEU_CAU_DOI_MAT_KHAU")
    private Instant resetDate;

    @Column(name = "NGON_NGU")
    private String langKey;

    @Size(max = 100)
    @Column(name = "DUONG_DAN_HINH")
    private String imageURL;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MA_NHOM_QUYEN", nullable = false)
    private Authority authority;

    public Account() {}

    public String getFullName() {
        return fullName;
    }

    public Account setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Account setAddress(String address) {
        this.address = address;
        return this;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public String getPhone() {
        return phone;
    }

    public boolean isActivated() {
        return activated;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public String getResetKey() {
        return resetKey;
    }

    public Instant getResetDate() {
        return resetDate;
    }

    public String getLangKey() {
        return langKey;
    }

    public String getImageURL() {
        return imageURL;
    }

    public Authority getAuthority() {
        return authority;
    }

    public Account setId(Long id) {
        this.id = id;
        return this;
    }

    public Account setEmail(String email) {
        this.email = email;
        return this;
    }

    public Account setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
        return this;
    }

    public Account setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public Account setActivated(boolean activated) {
        this.activated = activated;
        return this;
    }

    public Account setActivationKey(String activationKey) {
        this.activationKey = activationKey;
        return this;
    }

    public Account setResetKey(String resetKey) {
        this.resetKey = resetKey;
        return this;
    }

    public Account setResetDate(Instant resetDate) {
        this.resetDate = resetDate;
        return this;
    }

    public Account setLangKey(String langKey) {
        this.langKey = langKey;
        return this;
    }

    public Account setImageURL(String imageURL) {
        this.imageURL = imageURL;
        return this;
    }

    public Account setAuthority(Authority authority) {
        this.authority = authority;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return email.equals(account.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return (
            "Account{" +
            "id=" +
            id +
            ", email='" +
            email +
            '\'' +
            ", phone='" +
            phone +
            '\'' +
            ", activated=" +
            activated +
            '\'' +
            ", activationKey='" +
            activationKey +
            '\'' +
            ", resetKey='" +
            resetKey +
            '\'' +
            ", resetDate=" +
            resetDate +
            ", langKey='" +
            langKey +
            '\'' +
            ", imageURL='" +
            imageURL +
            '\'' +
            ", authority=" +
            authority +
            '}'
        );
    }
}
