package com.hotel.mgmt.domain.room;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "ANH_HANG_PHONG")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SuiteImage implements Serializable {

    private static final long serialVersionUID = 771471401294363517L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_SEQ_ANH_HANG_PHONG")
    @SequenceGenerator(name = "ID_SEQ_ANH_HANG_PHONG", allocationSize = 1, initialValue = 1, sequenceName = "ID_SEQ_ANH_HANG_PHONG")
    @Column(name = "MA_ANH")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MA_HANG_PHONG")
    @JsonBackReference
    private Suite suite;

    @NotBlank
    @Size(max = 100)
    @Column(name = "DUONG_DAN")
    private String imageURL;

    @Column(name = "LA_ANH_DAI_DIEN")
    private boolean isMain;

    @Min(0)
    @Column(name = "THU_TU")
    private int index;

    public SuiteImage() {}

    public Integer getId() {
        return id;
    }

    public SuiteImage setId(Integer id) {
        this.id = id;
        return this;
    }

    public Suite getSuite() {
        return suite;
    }

    public SuiteImage setSuite(Suite suite) {
        this.suite = suite;
        return this;
    }

    public String getImageURL() {
        return imageURL;
    }

    public SuiteImage setImageURL(String imageURL) {
        this.imageURL = imageURL;
        return this;
    }

    public boolean isMain() {
        return isMain;
    }

    public int getIndex() {
        return index;
    }

    public SuiteImage setIndex(int index) {
        this.index = index;
        return this;
    }

    public SuiteImage setIsMain(boolean isMain) {
        this.isMain = isMain;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SuiteImage)) return false;
        SuiteImage that = (SuiteImage) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "SuiteImage{" +
            "id=" +
            id +
            ", suiteId=" +
            suite.getId() +
            ", imageURL='" +
            imageURL +
            '\'' +
            ", isMain=" +
            isMain +
            ", index=" +
            index +
            '}'
        );
    }
}
