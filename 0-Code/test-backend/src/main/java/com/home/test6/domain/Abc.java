package com.home.test6.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Abc.
 */
@Entity
@Table(name = "abc")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Abc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "aaa")
    private String aaa;

    @Column(name = "bbb")
    private String bbb;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAaa() {
        return aaa;
    }

    public Abc aaa(String aaa) {
        this.aaa = aaa;
        return this;
    }

    public void setAaa(String aaa) {
        this.aaa = aaa;
    }

    public String getBbb() {
        return bbb;
    }

    public Abc bbb(String bbb) {
        this.bbb = bbb;
        return this;
    }

    public void setBbb(String bbb) {
        this.bbb = bbb;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Abc abc = (Abc) o;
        if (abc.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), abc.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Abc{" +
            "id=" + getId() +
            ", aaa='" + getAaa() + "'" +
            ", bbb='" + getBbb() + "'" +
            "}";
    }
}
