package com.immo.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by olivier on 02/10/2019.
 */
@Entity
@Table(name = "locative")
public class Locative implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /*@Column(name = "code", unique = true)
    private String code;*/

    @Column(name = "designation", unique = true)
    private String designation;

    @Column(name = "using_locative")
    private String usageLocative;

    @Column(name = "nber_room")
    private int nberRoom;

    @Column(name = "superficy")
    private double superficy;

    @Column(name = "amount")
    private double amount;

    @Column(name = "charge")
    private double charge;

    @Column(name="created_date", updatable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date date = new Date();

    @ManyToOne
    @JoinColumn(name = "bien_id")
    private Bien bien;

    @ManyToOne
    @JoinColumn(name = "typeLocative_id")
    private TypeLocative typeLocative;


    @ManyToOne
    @JoinColumn(name = "devis_id")
    private Devis devis;


    @Lob
    @Column(name="image", unique =true, nullable = true,length = 80000000)
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
    private String devisTransient;

    @Transient
    private String bienTransient;


    @Transient
    private String statutTransient;
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


    public double getSuperficy() {
        return superficy;
    }

    public void setSuperficy(double superficy) {
        this.superficy = superficy;
    }



    public String getTypeTransient() {
        return typeTransient;
    }

    public void setTypeTransient(String typeTransient) {
        this.typeTransient = typeTransient;
    }

    public TypeLocative getTypeLocative() {
        return typeLocative;
    }

    public void setTypeLocative(TypeLocative typeLocative) {
        this.typeLocative = typeLocative;
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

    public String getUsageLocative() {
        return usageLocative;
    }

    public void setUsageLocative(String usageLocative) {
        this.usageLocative = usageLocative;
    }

    public int getNberRoom() {
        return nberRoom;
    }

    public void setNberRoom(int nberRoom) {
        this.nberRoom = nberRoom;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getCharge() {
        return charge;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }

    public Bien getBien() {
        return bien;
    }

    public void setBien(Bien bien) {
        this.bien = bien;
    }

    public String getBienTransient() {
        return bienTransient;
    }

    public void setBienTransient(String bienTransient) {
        this.bienTransient = bienTransient;
    }

    public String getStatutTransient() {
        return statutTransient;
    }

    public void setStatutTransient(String statutTransient) {
        this.statutTransient = statutTransient;
    }

    public Devis getDevis() {
        return devis;
    }

    public void setDevis(Devis devis) {
        this.devis = devis;
    }

    public String getDevisTransient() {
        return devisTransient;
    }

    public void setDevisTransient(String devisTransient) {
        this.devisTransient = devisTransient;
    }

    @Override
    public String toString() {
        return "Locative{" +
                "id=" + id +
                ", designation='" + designation + '\'' +
                ", usageLocative='" + usageLocative + '\'' +
                ", nberRoom=" + nberRoom +
                ", superficy=" + superficy +
                ", amount=" + amount +
                ", charge=" + charge +
                ", date=" + date +
                ", bien=" + bien +
                ", typeLocative=" + typeLocative +
                ", devis=" + devis +
                ", image=" + Arrays.toString(image) +
                ", imageName='" + imageName + '\'' +
                ", commentary='" + commentary + '\'' +
                ", imageTransient='" + imageTransient + '\'' +
                ", dateTransient='" + dateTransient + '\'' +
                ", typeTransient='" + typeTransient + '\'' +
                ", devisTransient='" + devisTransient + '\'' +
                ", bienTransient='" + bienTransient + '\'' +
                ", statutTransient='" + statutTransient + '\'' +
                ", action='" + action + '\'' +
                ", checkboxe='" + checkboxe + '\'' +
                ", communeTransient='" + communeTransient + '\'' +
                '}';
    }
}
