package com.immo.entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by olivier on 02/10/2019.
 */
@Entity
@Table(name = "enterprise")
public class Enterprise implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", updatable = false)
    private Date createdDate = new Date();
    @ManyToOne
    @JoinColumn(name = "enterprise_type_id")
    private EnterpriseType enterpriseType;

    @Column(name = "capital")
    private double capital;

    @Column(name = "sale_registre")
    private String saleRegistre;

    @Column(name = "email", unique = true)
    @Email
    private String email;

    @Column(name = "fixe_phone", unique = true)
    private String fixePhone;

    @Column(name = "phone", unique = true)
    private String phone;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @Lob
    @Column(name="image", unique =true, nullable = true,length = 80000000)
    private byte[] image;

    @Column(name="image_name", nullable = true )
    private String imageName;

    @Transient
    private String imageTransient;


    @Transient
    private String enterpriseTypeTransient;

    @Transient
    private String countryTransient;

    @Transient
    private String action;

    @Transient
    private String checkboxe;

    @Transient
    private String dateTransient;

    public String getDateTransient() {
        return dateTransient;
    }

    public void setDateTransient(String dateTransient) {
        this.dateTransient = dateTransient;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getCheckboxe() {
        return checkboxe;
    }

    public void setCheckboxe(String checkboxe) {
        this.checkboxe = checkboxe;
    }

    public EnterpriseType getEnterpriseType() {
        return enterpriseType;
    }

    public void setEnterpriseType(EnterpriseType enterpriseType) {
        this.enterpriseType = enterpriseType;
    }

    public double getCapital() {
        return capital;
    }

    public void setCapital(double capital) {
        this.capital = capital;
    }

    public String getSaleRegistre() {
        return saleRegistre;
    }

    public void setSaleRegistre(String saleRegistre) {
        this.saleRegistre = saleRegistre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFixePhone() {
        return fixePhone;
    }

    public void setFixePhone(String fixePhone) {
        this.fixePhone = fixePhone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageTransient() {
        return imageTransient;
    }

    public void setImageTransient(String imageTransient) {
        this.imageTransient = imageTransient;
    }

    public String getEnterpriseTypeTransient() {
        return enterpriseTypeTransient;
    }

    public void setEnterpriseTypeTransient(String enterpriseTypeTransient) {
        this.enterpriseTypeTransient = enterpriseTypeTransient;
    }

    public String getCountryTransient() {
        return countryTransient;
    }

    public void setCountryTransient(String countryTransient) {
        this.countryTransient = countryTransient;
    }
}
