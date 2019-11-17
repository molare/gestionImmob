package com.immo.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by olivier on 02/10/2019.
 */
@Entity
@Table(name = "contrat")
public class Contrat implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "month_nber", nullable = false)
    private int monthNber;

    @Column(name = "amount")
    private double amount;

    @Column(name = "first_quittance")
    private double firstQuittance;

    @Column(name = "advance_month")
    private int advanceMonth;

    @Column(name = "agence_month")
    private int agenceMonth;

    @Column(name="created_date", updatable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date date = new Date();

    @Column(name="start_bail_date")
    @Temporal(value = TemporalType.DATE)
    private Date startBailDate;


    @Column(name = "rest_caution", nullable = false)
    private double restCaution;

    /*@Column(name="end_bail_date")
    @Temporal(value = TemporalType.DATE)
    private Date endBailDate;*/

    @ManyToOne
    @JoinColumn(name = "locative_id", nullable = false)
    private Locative locative;

    @ManyToOne
    @JoinColumn(name = "locater_id", nullable = false)
    private Locater locater;

    @ManyToOne
    @JoinColumn(name = "statutPay_id", nullable = false)
    private StatutPay statutPay;

    @ManyToOne
    @JoinColumn(name = "moyenPay_id", nullable = false)
    private MoyenPay moyenPay;


    @Lob
    @Column(name="image", unique =true, nullable = true,length = 80000000)
    private byte[] image;

    @Column(name="image_name", nullable = true)
    private String imageName;

    @Column(name = "status_contrat")
    private String statusContrat;

    @Column(name = "commentary")
    private String commentary;

    @Transient
    private String imageTransient;

    @Transient
    private String dateTransient;

    @Transient
    private String startBailDateTransient;

    @Transient
    private String endBailDateTransient;

    @Transient
    private String statutPayTransient;

    @Transient
    private String moyenPayTransient;

    @Transient
    private String locativeTransient;

    @Transient
    private String locaterTransient;

    @Transient
    private String action;

    @Transient
    private String checkboxe;

    @Transient
    private String bienTransient;

    @Transient
    private String communeTransient;

    @Transient
    private String restCautionTransient;

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

    public int getMonthNber() {
        return monthNber;
    }

    public void setMonthNber(int monthNber) {
        this.monthNber = monthNber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getFirstQuittance() {
        return firstQuittance;
    }

    public void setFirstQuittance(double firstQuittance) {
        this.firstQuittance = firstQuittance;
    }


    public int getAdvanceMonth() {
        return advanceMonth;
    }

    public void setAdvanceMonth(int advanceMonth) {
        this.advanceMonth = advanceMonth;
    }

    public int getAgenceMonth() {
        return agenceMonth;
    }

    public void setAgenceMonth(int agenceMonth) {
        this.agenceMonth = agenceMonth;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getStartBailDate() {
        return startBailDate;
    }

    public void setStartBailDate(Date startBailDate) {
        this.startBailDate = startBailDate;
    }

  /*  public Date getEndBailDate() {
        return endBailDate;
    }

    public void setEndBailDate(Date endBailDate) {
        this.endBailDate = endBailDate;
    }*/

    public Locative getLocative() {
        return locative;
    }

    public void setLocative(Locative locative) {
        this.locative = locative;
    }

    public Locater getLocater() {
        return locater;
    }

    public void setLocater(Locater locater) {
        this.locater = locater;
    }

    public StatutPay getStatutPay() {
        return statutPay;
    }

    public void setStatutPay(StatutPay statutPay) {
        this.statutPay = statutPay;
    }

    public MoyenPay getMoyenPay() {
        return moyenPay;
    }

    public void setMoyenPay(MoyenPay moyenPay) {
        this.moyenPay = moyenPay;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public String getImageTransient() {
        return imageTransient;
    }

    public void setImageTransient(String imageTransient) {
        this.imageTransient = imageTransient;
    }

    public String getDateTransient() {
        return dateTransient;
    }

    public void setDateTransient(String dateTransient) {
        this.dateTransient = dateTransient;
    }

    public String getStartBailDateTransient() {
        return startBailDateTransient;
    }

    public void setStartBailDateTransient(String startBailDateTransient) {
        this.startBailDateTransient = startBailDateTransient;
    }

    public String getEndBailDateTransient() {
        return endBailDateTransient;
    }

    public void setEndBailDateTransient(String endBailDateTransient) {
        this.endBailDateTransient = endBailDateTransient;
    }

    public String getStatutPayTransient() {
        return statutPayTransient;
    }

    public void setStatutPayTransient(String statutPayTransient) {
        this.statutPayTransient = statutPayTransient;
    }

    public String getMoyenPayTransient() {
        return moyenPayTransient;
    }

    public void setMoyenPayTransient(String moyenPayTransient) {
        this.moyenPayTransient = moyenPayTransient;
    }

    public String getLocativeTransient() {
        return locativeTransient;
    }

    public void setLocativeTransient(String locativeTransient) {
        this.locativeTransient = locativeTransient;
    }

    public String getLocaterTransient() {
        return locaterTransient;
    }

    public void setLocaterTransient(String locaterTransient) {
        this.locaterTransient = locaterTransient;
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

    public String getCommuneTransient() {
        return communeTransient;
    }

    public void setCommuneTransient(String communeTransient) {
        this.communeTransient = communeTransient;
    }

    public String getBienTransient() {
        return bienTransient;
    }

    public void setBienTransient(String bienTransient) {
        this.bienTransient = bienTransient;
    }

    public double getRestCaution() {
        return restCaution;
    }

    public void setRestCaution(double restCaution) {
        this.restCaution = restCaution;
    }

    public String getRestCautionTransient() {
        return restCautionTransient;
    }

    public void setRestCautionTransient(String restCautionTransient) {
        this.restCautionTransient = restCautionTransient;
    }

    public String getStatusContrat() {
        return statusContrat;
    }

    public void setStatusContrat(String statusContrat) {
        this.statusContrat = statusContrat;
    }

    @Override
    public String toString() {
        return "Contrat{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", monthNber=" + monthNber +
                ", amount=" + amount +
                ", firstQuittance=" + firstQuittance +
                ", advanceMonth=" + advanceMonth +
                ", agenceMonth=" + agenceMonth +
                ", date=" + date +
                ", startBailDate=" + startBailDate +
             //   ", endBailDate=" + endBailDate +
                ", locative=" + locative +
                ", locater=" + locater +
                ", statutPay=" + statutPay +
                ", moyenPay=" + moyenPay +
                ", image=" + Arrays.toString(image) +
                ", imageName='" + imageName + '\'' +
                ", commentary='" + commentary + '\'' +
                ", imageTransient='" + imageTransient + '\'' +
                ", dateTransient='" + dateTransient + '\'' +
                ", startBailDateTransient='" + startBailDateTransient + '\'' +
                ", endBailDateTransient='" + endBailDateTransient + '\'' +
                ", statutPayTransient='" + statutPayTransient + '\'' +
                ", moyenPayTransient='" + moyenPayTransient + '\'' +
                ", locativeTransient='" + locativeTransient + '\'' +
                ", locaterTransient='" + locaterTransient + '\'' +
                ", action='" + action + '\'' +
                ", checkboxe='" + checkboxe + '\'' +
                ", communeTransient='" + communeTransient + '\'' +
                '}';
    }
}
