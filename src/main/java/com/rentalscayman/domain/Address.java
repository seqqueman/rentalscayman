package com.rentalscayman.domain;

import com.rentalscayman.domain.enumeration.AreaDisctrict;
import com.rentalscayman.domain.enumeration.ViaType;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Address.
 */
@Entity
@Table(name = "address")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Address implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type_of_via", nullable = false)
    private ViaType typeOfVia;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "area_disctrict", nullable = false)
    private AreaDisctrict areaDisctrict;

    @Column(name = "lat", precision = 21, scale = 2)
    private BigDecimal lat;

    @Column(name = "lon", precision = 21, scale = 2)
    private BigDecimal lon;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ViaType getTypeOfVia() {
        return typeOfVia;
    }

    public Address typeOfVia(ViaType typeOfVia) {
        this.typeOfVia = typeOfVia;
        return this;
    }

    public void setTypeOfVia(ViaType typeOfVia) {
        this.typeOfVia = typeOfVia;
    }

    public String getName() {
        return name;
    }

    public Address name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZipCode() {
        return zipCode;
    }

    public Address zipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public AreaDisctrict getAreaDisctrict() {
        return areaDisctrict;
    }

    public Address areaDisctrict(AreaDisctrict areaDisctrict) {
        this.areaDisctrict = areaDisctrict;
        return this;
    }

    public void setAreaDisctrict(AreaDisctrict areaDisctrict) {
        this.areaDisctrict = areaDisctrict;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public Address lat(BigDecimal lat) {
        this.lat = lat;
        return this;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getLon() {
        return lon;
    }

    public Address lon(BigDecimal lon) {
        this.lon = lon;
        return this;
    }

    public void setLon(BigDecimal lon) {
        this.lon = lon;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Address)) {
            return false;
        }
        return id != null && id.equals(((Address) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Address{" +
            "id=" + getId() +
            ", typeOfVia='" + getTypeOfVia() + "'" +
            ", number='" + getName() + "'" +
            ", zipCode='" + getZipCode() + "'" +
            ", areaDisctrict='" + getAreaDisctrict() + "'" +
            ", lat=" + getLat() +
            ", lon=" + getLon() +
            "}";
    }
}
