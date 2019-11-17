package com.immo.entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by olivier on 02/10/2019.
 */
@Entity
@Table(name = "property")
public class Property implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "code", unique = true)
    private String code;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email", unique = true)
    @Email
    private String email;
    @Column(name = "phone", unique = true)
    private String phone;

    @Column(name = "profession")
    private String profession;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "nber_piece")
    private String nberPiece;

    @Column(name = "nature_piece")
    private String naturePiece;

    @Column(name = "lieu_nais")
    private String lieuNais;

    @Column(name = "domicil")
    private String domicil;

    @Column(name = "country")
    private String country;

    @Column(name = "cpte_contribu")
    private String cpteContribu;

    @Column(name="created_date", updatable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date date = new Date();;

    @Column(name="birth_date")
    @Temporal(value = TemporalType.DATE)
    private Date birthDate;

    @ManyToOne
    @JoinColumn(name = "type_property_id")
    private TypeProperty typeProperty;

    @ManyToOne
    @JoinColumn(name = "civility_id")
    private Civility civility;
    @Lob
    @Column(name="image", unique =true, nullable = true,length = 50000000)
    private byte[] image;

    @Column(name="image_name", nullable = true )
    private String imageName;

    @Transient
    private String imageTransient;

    @Transient
    private String dateTransient;


    @Transient
    private String birthDateTransient;
    @Transient
    private String typeTransient;

    @Transient
    private String civilityTransient;

    @Transient
    private String countryTransient;

    @Transient
    private String natureTransient;

    @Transient
    private String action;
    @Transient
    private String checkboxe;

    public String getBirthDateTransient() {
        return birthDateTransient;
    }

    public void setBirthDateTransient(String birthDateTransient) {
        this.birthDateTransient = birthDateTransient;
    }

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getLieuNais() {
        return lieuNais;
    }

    public void setLieuNais(String lieuNais) {
        this.lieuNais = lieuNais;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNberPiece() {
        return nberPiece;
    }

    public void setNberPiece(String nberPiece) {
        this.nberPiece = nberPiece;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getNaturePiece() {
        return naturePiece;
    }

    public void setNaturePiece(String naturePiece) {
        this.naturePiece = naturePiece;
    }

    public String getDomicil() {
        return domicil;
    }

    public void setDomicil(String domicil) {
        this.domicil = domicil;
    }

    public String getCpteContribu() {
        return cpteContribu;
    }

    public void setCpteContribu(String cpteContribu) {
        this.cpteContribu = cpteContribu;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
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

    public TypeProperty getTypeProperty() {
        return typeProperty;
    }

    public void setTypeProperty(TypeProperty typeProperty) {
        this.typeProperty = typeProperty;
    }

    public String getTypeTransient() {
        return typeTransient;
    }

    public void setTypeTransient(String typeTransient) {
        this.typeTransient = typeTransient;
    }

    public Civility getCivility() {
        return civility;
    }

    public void setCivility(Civility civility) {
        this.civility = civility;
    }

    public String getCivilityTransient() {
        return civilityTransient;
    }

    public void setCivilityTransient(String civilityTransient) {
        this.civilityTransient = civilityTransient;
    }

    public String getCountryTransient() {
        return countryTransient;
    }

    public void setCountryTransient(String countryTransient) {
        this.countryTransient = countryTransient;
    }

    public String getNatureTransient() {
        return natureTransient;
    }

    public void setNatureTransient(String natureTransient) {
        this.natureTransient = natureTransient;
    }

    public String getCheckboxe() {
        return checkboxe;
    }

    public void setCheckboxe(String checkboxe) {
        this.checkboxe = checkboxe;
    }

    @Override
    public String toString() {
        return "Property{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", profession='" + profession + '\'' +
                ", nationality='" + nationality + '\'' +
                ", nberPiece='" + nberPiece + '\'' +
                ", naturePiece='" + naturePiece + '\'' +
                ", lieuNais='" + lieuNais + '\'' +
                ", domicil='" + domicil + '\'' +
                ", country='" + country + '\'' +
                ", cpteContribu='" + cpteContribu + '\'' +
                ", date=" + date +
                ", birthDate=" + birthDate +
                ", image=" + Arrays.toString(image) +
                ", imageName='" + imageName + '\'' +
                ", imageTransient='" + imageTransient + '\'' +
                ", dateTransient='" + dateTransient + '\'' +
                ", action='" + action + '\'' +
                '}';
    }
}
