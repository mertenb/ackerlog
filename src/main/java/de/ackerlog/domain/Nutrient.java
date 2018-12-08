package de.ackerlog.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Nutrient.
 */
@Entity
@Table(name = "nutrient")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Nutrient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Column(name = "n")
    private Integer n;

    @Column(name = "p")
    private Integer p;

    @Column(name = "k")
    private Integer k;

    @Column(name = "note")
    private String note;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private User user;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private Field field;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Nutrient name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getN() {
        return n;
    }

    public Nutrient n(Integer n) {
        this.n = n;
        return this;
    }

    public void setN(Integer n) {
        this.n = n;
    }

    public Integer getP() {
        return p;
    }

    public Nutrient p(Integer p) {
        this.p = p;
        return this;
    }

    public void setP(Integer p) {
        this.p = p;
    }

    public Integer getK() {
        return k;
    }

    public Nutrient k(Integer k) {
        this.k = k;
        return this;
    }

    public void setK(Integer k) {
        this.k = k;
    }

    public String getNote() {
        return note;
    }

    public Nutrient note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public User getUser() {
        return user;
    }

    public Nutrient user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Field getField() {
        return field;
    }

    public Nutrient field(Field field) {
        this.field = field;
        return this;
    }

    public void setField(Field field) {
        this.field = field;
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
        Nutrient nutrient = (Nutrient) o;
        if (nutrient.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), nutrient.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Nutrient{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", n=" + getN() +
            ", p=" + getP() +
            ", k=" + getK() +
            ", note='" + getNote() + "'" +
            "}";
    }
}
