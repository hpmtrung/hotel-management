package com.hotel.mgmt.domain;

import com.hotel.mgmt.annotations.constraints.personalId.PersonalIdConstraint;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "KHACH_HANG")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Customer implements Serializable {

    private static final long serialVersionUID = 6323731315928740441L;

    @Id
    @PersonalIdConstraint
    @Column(name = "CMND")
    private String personalId;

    @OneToOne
    @JoinColumn(name = "MA_TAI_KHOAN")
    private Account account;

    @Size(min = 13, max = 13)
    @Column(name = "MA_SO_THUE")
    private String taxCode;

    public Customer() {}

    public String getPersonalId() {
        return personalId;
    }

    public Customer setPersonalId(String personalId) {
        this.personalId = personalId;
        return this;
    }

    public Account getAccount() {
        return account;
    }

    public Customer setAccount(Account account) {
        this.account = account;
        return this;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public Customer setTaxCode(String taxCode) {
        this.taxCode = taxCode;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return Objects.equals(personalId, customer.personalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personalId);
    }

    @Override
    public String toString() {
        return (
            "Customer{" +
            "personalId='" +
            personalId +
            '\'' +
            ", accountId=" +
            (account != null ? account.getId() : "NULL") +
            ", taxCode='" +
            taxCode +
            '\'' +
            '}'
        );
    }
}
