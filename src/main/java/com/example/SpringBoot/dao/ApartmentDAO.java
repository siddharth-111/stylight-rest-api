package com.example.SpringBoot.dao;


import javax.persistence.*;

@Entity
@Table(name = "Apartments")
public class ApartmentDAO {
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStreetNum() {
        return streetNum;
    }

    public void setStreetNum(String streetNum) {
        this.streetNum = streetNum;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "apartment_id")
    private long id;

    private String streetNum;

    private String street;

    private String city;

    private String state;

    private String zipCode;

    public LoginUserDetailsDAO getCreatedUser() {
        return user;
    }

    public void setCreatedUser(LoginUserDetailsDAO user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "id")
    private LoginUserDetailsDAO user;
}
