package com.rentalscayman.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rentalscayman.domain.enumeration.PropertyType;
import com.rentalscayman.domain.enumeration.TypeAdvertisment;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Advertisment.
 */
@Entity
@Table(name = "advertisment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Advertisment implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    //    @NotNull
    @Column(name = "create_at", nullable = false)
    private LocalDate createAt;

    //    @NotNull
    @Column(name = "modified_at", nullable = false)
    private LocalDate modifiedAt;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type_ad", nullable = false)
    private TypeAdvertisment typeAd;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "property_type", nullable = false)
    private PropertyType propertyType;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @NotNull
    @Column(name = "price", precision = 21, scale = 2, nullable = false)
    private BigDecimal price;

    //    @NotNull
    @Column(name = "reference", nullable = false)
    private String reference;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private Address address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private Feature feature;

    @OneToMany(mappedBy = "advertisment", cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Image> images = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "advertisments", allowSetters = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Advertisment description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public Advertisment createAt(LocalDate createAt) {
        this.createAt = createAt;
        return this;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public LocalDate getModifiedAt() {
        return modifiedAt;
    }

    public Advertisment modifiedAt(LocalDate modifiedAt) {
        this.modifiedAt = modifiedAt;
        return this;
    }

    public void setModifiedAt(LocalDate modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public TypeAdvertisment getTypeAd() {
        return typeAd;
    }

    public Advertisment typeAd(TypeAdvertisment typeAd) {
        this.typeAd = typeAd;
        return this;
    }

    public void setTypeAd(TypeAdvertisment typeAd) {
        this.typeAd = typeAd;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public Advertisment propertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
        return this;
    }

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }

    public Boolean isActive() {
        return active;
    }

    public Advertisment active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Advertisment price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getReference() {
        return reference;
    }

    public Advertisment reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Address getAddress() {
        return address;
    }

    public Advertisment address(Address address) {
        this.address = address;
        return this;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Feature getFeature() {
        return feature;
    }

    public Advertisment feature(Feature feature) {
        this.feature = feature;
        return this;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
    }

    public Set<Image> getImages() {
        return images;
    }

    public Advertisment images(Set<Image> images) {
        this.images = images;
        return this;
    }

    public Advertisment addImages(Image image) {
        this.images.add(image);
        image.setAdvertisment(this);
        return this;
    }

    public Advertisment removeImages(Image image) {
        this.images.remove(image);
        image.setAdvertisment(null);
        return this;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    public User getUser() {
        return user;
    }

    public Advertisment user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @PrePersist
    public void prePersist() {
        createAt = LocalDate.now();
        modifiedAt = createAt;
        reference = UUID.randomUUID().toString().replace("-", "").substring(4);
    }

    @PreUpdate
    public void preUpdate() {
        modifiedAt = LocalDate.now();
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Advertisment)) {
            return false;
        }
        return id != null && id.equals(((Advertisment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Advertisment{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", createAt='" + getCreateAt() + "'" +
            ", modifiedAt='" + getModifiedAt() + "'" +
            ", typeAd='" + getTypeAd() + "'" +
            ", propertyType='" + getPropertyType() + "'" +
            ", active='" + isActive() + "'" +
            ", price=" + getPrice() +
            ", reference='" + getReference() + "'" +
            "}";
    }
}
