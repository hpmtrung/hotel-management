package com.hotel.mgmt.domain.room;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "CHINH_SACH_HOAN_HUY")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RefundPolicy implements Serializable {

    private static final long serialVersionUID = -5122743268840696636L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @NaturalId
    @Size(max = 100)
    @Column(name = "TEN")
    private String name;

    @NotBlank
    @Size(max = 500)
    @Column(name = "NOI_DUNG")
    private String content;

    public RefundPolicy() {}

    public Integer getId() {
        return id;
    }

    public RefundPolicy setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public RefundPolicy setName(String name) {
        this.name = name;
        return this;
    }

    public String getContent() {
        return content;
    }

    public RefundPolicy setContent(String content) {
        this.content = content;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RefundPolicy that = (RefundPolicy) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return (
            "RefundPolicy{" +
            "id=" +
            id +
            ", name='" +
            name +
            '\'' +
            ", content='" +
            (content.length() > 100 ? content.substring(0, 100) + "..." : content) +
            '\'' +
            '}'
        );
    }
}
