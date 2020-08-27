package com.rentalscayman.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;

/**
 * A Feature.
 */
@Entity
@Table(name = "feature")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
public class Feature implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @NotNull
    @Column(name = "number_bedrooms", nullable = false)
    private Integer numberBedrooms;

    @NotNull
    @Column(name = "number_bathroom", nullable = false)
    private Integer numberBathroom;

    @Column(name = "full_kitchen")
    private Boolean fullKitchen;

    @Column(name = "elevator")
    private Boolean elevator;

    @Column(name = "parking")
    private Boolean parking;

    @Column(name = "air_conditionair")
    private Boolean airConditionair;

    @Column(name = "backyard")
    private Boolean backyard;

    @Column(name = "pool")
    private Boolean pool;

    @Column(name = "m_2")
    private Integer m2;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "id")
    @JsonIgnore
    private Advertisment advertisment;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumberBedrooms() {
        return numberBedrooms;
    }

    public Feature numberBedrooms(Integer numberBedrooms) {
        this.numberBedrooms = numberBedrooms;
        return this;
    }

    public void setNumberBedrooms(Integer numberBedrooms) {
        this.numberBedrooms = numberBedrooms;
    }

    public Integer getNumberBathroom() {
        return numberBathroom;
    }

    public Feature numberBathroom(Integer numberBathroom) {
        this.numberBathroom = numberBathroom;
        return this;
    }

    public void setNumberBathroom(Integer numberBathroom) {
        this.numberBathroom = numberBathroom;
    }

    public Boolean isFullKitchen() {
        return fullKitchen;
    }

    public Feature fullKitchen(Boolean fullKitchen) {
        this.fullKitchen = fullKitchen;
        return this;
    }

    public void setFullKitchen(Boolean fullKitchen) {
        this.fullKitchen = fullKitchen;
    }

    public Boolean isElevator() {
        return elevator;
    }

    public Feature elevator(Boolean elevator) {
        this.elevator = elevator;
        return this;
    }

    public void setElevator(Boolean elevator) {
        this.elevator = elevator;
    }

    public Boolean isParking() {
        return parking;
    }

    public Feature parking(Boolean parking) {
        this.parking = parking;
        return this;
    }

    public void setParking(Boolean parking) {
        this.parking = parking;
    }

    public Boolean isAirConditionair() {
        return airConditionair;
    }

    public Feature airConditionair(Boolean airConditionair) {
        this.airConditionair = airConditionair;
        return this;
    }

    public void setAirConditionair(Boolean airConditionair) {
        this.airConditionair = airConditionair;
    }

    public Boolean isBackyard() {
        return backyard;
    }

    public Feature backyard(Boolean backyard) {
        this.backyard = backyard;
        return this;
    }

    public void setBackyard(Boolean backyard) {
        this.backyard = backyard;
    }

    public Boolean isPool() {
        return pool;
    }

    public Feature pool(Boolean pool) {
        this.pool = pool;
        return this;
    }

    public void setPool(Boolean pool) {
        this.pool = pool;
    }

    public Integer getm2() {
        return m2;
    }

    public Feature m2(Integer m2) {
        this.m2 = m2;
        return this;
    }

    public void setm2(Integer m2) {
        this.m2 = m2;
    }

    public Advertisment getAdvertisment() {
        return advertisment;
    }

    public Feature advertisment(Advertisment advertisment) {
        this.advertisment = advertisment;
        return this;
    }

    public void setAdvertisment(Advertisment advertisment) {
        this.advertisment = advertisment;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Feature)) {
            return false;
        }
        return id != null && id.equals(((Feature) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Feature{" +
            "id=" + getId() +
            ", numberBedrooms=" + getNumberBedrooms() +
            ", numberBathroom=" + getNumberBathroom() +
            ", fullKitchen='" + isFullKitchen() + "'" +
            ", elevator='" + isElevator() + "'" +
            ", parking='" + isParking() + "'" +
            ", airConditionair='" + isAirConditionair() + "'" +
            ", backyard='" + isBackyard() + "'" +
            ", pool='" + isPool() + "'" +
            ", m2=" + getm2() +
            "}";
    }
}
