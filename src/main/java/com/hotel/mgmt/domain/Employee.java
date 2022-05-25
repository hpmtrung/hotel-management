package com.hotel.mgmt.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "NHAN_VIEN")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Employee implements Serializable {

    private static final long serialVersionUID = -4288309231311242007L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "MA_NHAN_VIEN")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "MA_TAI_KHOAN")
    private Account account;

    @Column(name = "LA_NAM")
    private boolean isMale;

    @NotNull
    @Temporal(value = TemporalType.DATE)
    @Column(name = "NGAY_SINH")
    private Date birthDate;

    @ManyToOne
    @JoinColumn(name = "MA_BP", nullable = false)
    private Department department;

    public Employee() {}

    public Integer getId() {
        return id;
    }

    public Employee setId(Integer id) {
        this.id = id;
        return this;
    }

    public boolean isMale() {
        return isMale;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public Employee setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public Department getDepartment() {
        return department;
    }

    public Employee setDepartment(Department department) {
        this.department = department;
        return this;
    }

    public Employee setIsMale(boolean isMale) {
        this.isMale = isMale;
        return this;
    }

    public Account getAccount() {
        return account;
    }

    public Employee setAccount(Account account) {
        this.account = account;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee employee = (Employee) o;
        return id != null && Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "Employee{" +
            "id=" +
            id +
            ", accountId=" +
            (account != null ? account.getId() : "NULL") +
            ", isMale=" +
            isMale +
            ", birthDate=" +
            birthDate +
            ", department=" +
            department.getName() +
            '}'
        );
    }
}
