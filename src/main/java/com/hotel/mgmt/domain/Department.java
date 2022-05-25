package com.hotel.mgmt.domain;

import com.hotel.mgmt.annotations.constraints.phone.PhoneConstraint;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "BO_PHAN")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MA_BP")
    private String id;

    @NotBlank
    @NaturalId
    @Size(max = 100)
    @Column(name = "TEN")
    private String name;

    @PhoneConstraint
    @Column(name = "HOTLINE")
    private String hotline;

    @OneToMany(mappedBy = "department", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Employee> employees = new HashSet<>();

    public Department() {}

    public String getId() {
        return id;
    }

    public Department setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Department setName(String name) {
        this.name = name;
        return this;
    }

    public String getHotline() {
        return hotline;
    }

    public Department setHotline(String hotline) {
        this.hotline = hotline;
        return this;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public Department setEmployees(Set<Employee> employees) {
        this.employees = employees;
        return this;
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
        employee.setDepartment(this);
    }

    public void removeEmployee(Employee employee) {
        employees.remove(employee);
        employee.setDepartment(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Department{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", hotline='" + hotline + '\'' + '}';
    }
}
