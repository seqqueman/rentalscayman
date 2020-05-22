package com.rentalscayman.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A UserRegistered.
 */
@Entity
@Table(name = "user_registered")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserRegistered implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(name = "advertiser")
    private Boolean advertiser;

    @Column(name = "phone")
    private String phone;

    @Column(name = "number_of_ads")
    private Integer numberOfAds;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @OneToOne
    @JoinColumn(unique = true)
    private Company company;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isAdvertiser() {
        return advertiser;
    }

    public UserRegistered advertiser(Boolean advertiser) {
        this.advertiser = advertiser;
        return this;
    }

    public void setAdvertiser(Boolean advertiser) {
        this.advertiser = advertiser;
    }

    public String getPhone() {
        return phone;
    }

    public UserRegistered phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getNumberOfAds() {
        return numberOfAds;
    }

    public UserRegistered numberOfAds(Integer numberOfAds) {
        this.numberOfAds = numberOfAds;
        return this;
    }

    public void setNumberOfAds(Integer numberOfAds) {
        this.numberOfAds = numberOfAds;
    }

    public User getUser() {
        return user;
    }

    public UserRegistered user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Company getCompany() {
        return company;
    }

    public UserRegistered company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserRegistered)) {
            return false;
        }
        return id != null && id.equals(((UserRegistered) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserRegistered{" +
            "id=" + getId() +
            ", advertiser='" + isAdvertiser() + "'" +
            ", phone='" + getPhone() + "'" +
            ", numberOfAds=" + getNumberOfAds() +
            "}";
    }
}
