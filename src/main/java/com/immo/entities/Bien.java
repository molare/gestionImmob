package com.immo.entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by olivier on 02/10/2019.
 */
@Entity
@Table(name = "bien")
public class Bien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "code", unique = true)
    private String code;

    @Column(name = "designation")
    private String designation;

    @Column(name = "nber_foncier")
    private String nberFoncier;

    @Column(name = "nber_ilot")
    private String nberIlot;

    @Column(name = "nber_lot")
    private String nberlot;

    @Column(name = "superficy")
    private double superficy;

    @Column(name = "acquisition_cost")
    private double acquisitionCost;

    @Column(name="created_date", updatable = false)
    @Temporal(value = TemporalType.DATE)
    private Date date = new Date();;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne
    @JoinColumn(name = "typeBien_id")
    private TypeBien typeBien;


    @Lob
    @Column(name="image", unique =true, nullable = true,length = 50000000)
    private byte[] image;

    @Column(name="image_name", nullable = true)
    private String imageName;

    @Column(name = "commentary")
    private String commentary;

    @Transient
    private String imageTransient;

    @Transient
    private String dateTransient;

    @Transient
    private String typeTransient;

    @Transient
    private String cityTransient;

    @Transient
    private String propertyTransient;

    @Transient
    private String action;

    @Transient
    private String checkboxe;

    @Transient
    private String communeTransient;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDateTransient() {
        return dateTransient;
    }

    public void setDateTransient(String dateTransient) {
        this.dateTransient = dateTransient;
    }


    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getNberFoncier() {
        return nberFoncier;
    }

    public void setNberFoncier(String nberFoncier) {
        this.nberFoncier = nberFoncier;
    }

    public String getNberIlot() {
        return nberIlot;
    }

    public void setNberIlot(String nberIlot) {
        this.nberIlot = nberIlot;
    }

    public String getNberlot() {
        return nberlot;
    }

    public void setNberlot(String nberlot) {
        this.nberlot = nberlot;
    }

    public double getSuperficy() {
        return superficy;
    }

    public void setSuperficy(double superficy) {
        this.superficy = superficy;
    }

    public double getAcquisitionCost() {
        return acquisitionCost;
    }

    public void setAcquisitionCost(double acquisitionCost) {
        this.acquisitionCost = acquisitionCost;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getTypeTransient() {
        return typeTransient;
    }

    public void setTypeTransient(String typeTransient) {
        this.typeTransient = typeTransient;
    }

    public String getCityTransient() {
        return cityTransient;
    }

    public void setCityTransient(String cityTransient) {
        this.cityTransient = cityTransient;
    }

    public String getPropertyTransient() {
        return propertyTransient;
    }

    public void setPropertyTransient(String propertyTransient) {
        this.propertyTransient = propertyTransient;
    }

    public TypeBien getTypeBien() {
        return typeBien;
    }

    public void setTypeBien(TypeBien typeBien) {
        this.typeBien = typeBien;
    }

    public String getCheckboxe() {
        return checkboxe;
    }

    public void setCheckboxe(String checkboxe) {
        this.checkboxe = checkboxe;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public String getCommuneTransient() {
        return communeTransient;
    }

    public void setCommuneTransient(String communeTransient) {
        this.communeTransient = communeTransient;
    }

    @Override
    public String toString() {
        return "Bien{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", designation='" + designation + '\'' +
                ", nberFoncier='" + nberFoncier + '\'' +
                ", nberIlot='" + nberIlot + '\'' +
                ", nberlot='" + nberlot + '\'' +
                ", superficy='" + superficy + '\'' +
                ", acquisitionCost=" + acquisitionCost +
                ", date=" + date +
                ", property=" + property +
                ", city=" + city +
                ", image=" + Arrays.toString(image) +
                ", imageName='" + imageName + '\'' +
                ", imageTransient='" + imageTransient + '\'' +
                ", dateTransient='" + dateTransient + '\'' +
                ", typeTransient='" + typeTransient + '\'' +
                ", cityTransient='" + cityTransient + '\'' +
                ", propertyTransient='" + propertyTransient + '\'' +
                ", action='" + action + '\'' +
                '}';
    }
}
