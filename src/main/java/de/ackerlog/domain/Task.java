package de.ackerlog.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Task.
 */
@Entity
@Table(name = "task")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "action", nullable = false)
    private String action;

    @Column(name = "action_date")
    private LocalDate actionDate;

    @NotNull
    @Column(name = "entry_date", nullable = false)
    private LocalDate entryDate;

    @Column(name = "jhi_type")
    private String type;

    @OneToOne    @JoinColumn(unique = true)
    private Nutrient nutrient;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private User user;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Field field;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public Task action(String action) {
        this.action = action;
        return this;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public LocalDate getActionDate() {
        return actionDate;
    }

    public Task actionDate(LocalDate actionDate) {
        this.actionDate = actionDate;
        return this;
    }

    public void setActionDate(LocalDate actionDate) {
        this.actionDate = actionDate;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public Task entryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
        return this;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public String getType() {
        return type;
    }

    public Task type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Nutrient getNutrient() {
        return nutrient;
    }

    public Task nutrient(Nutrient nutrient) {
        this.nutrient = nutrient;
        return this;
    }

    public void setNutrient(Nutrient nutrient) {
        this.nutrient = nutrient;
    }

    public User getUser() {
        return user;
    }

    public Task user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Field getField() {
        return field;
    }

    public Task field(Field field) {
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
        Task task = (Task) o;
        if (task.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), task.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Task{" +
            "id=" + getId() +
            ", action='" + getAction() + "'" +
            ", actionDate='" + getActionDate() + "'" +
            ", entryDate='" + getEntryDate() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
