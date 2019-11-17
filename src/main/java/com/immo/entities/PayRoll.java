package com.immo.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by olivier on 04/11/2019.
 */
@Entity
@Table(name = "pay_roll")
public class PayRoll implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", unique = false, nullable = false)
    private String name;

  /*  @Column(name = "start_date", unique = false, nullable = false)
    @Temporal(value=TemporalType.TIMESTAMP)
    private Date startDate;*/

    @Column(name = "end_date", unique = false, nullable = false)
    @Temporal(value=TemporalType.DATE)
    private Date endDate;


    @Column(name = "status_val")
    private int statusId;

    @Column(name="created_date", updatable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date date = new Date();

    @ManyToOne
    @JoinColumn(name = "contrat_id", nullable = false)
    private Contrat contrat;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "caution_statut", nullable = false)
    private int cautionStatut;

    @Transient
    private String dateTransient;

    @Transient
    private String startDateTransient;

    @Transient
    private String endDateTransient;

    @Transient
    private String contratTransient;

    @Transient
    private String locativeTransient;

    @Transient
    private String locaterTransient;



    @Transient
    private String statutTransient;

    @Transient
    private String amountTransient;

    @Transient
    private String action;

    @Transient
    private String reporting;

    @Transient
    private String checkboxe;

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

/*    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }*/

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateTransient() {
        return dateTransient;
    }

    public void setDateTransient(String dateTransient) {
        this.dateTransient = dateTransient;
    }

    public Contrat getContrat() {
        return contrat;
    }

    public void setContrat(Contrat contrat) {
        this.contrat = contrat;
    }

    public String getStatutTransient() {
        return statutTransient;
    }

    public void setStatutTransient(String statutTransient) {
        this.statutTransient = statutTransient;
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

    public String getStartDateTransient() {
        return startDateTransient;
    }

    public void setStartDateTransient(String startDateTransient) {
        this.startDateTransient = startDateTransient;
    }

    public String getEndDateTransient() {
        return endDateTransient;
    }

    public void setEndDateTransient(String endDateTransient) {
        this.endDateTransient = endDateTransient;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getContratTransient() {
        return contratTransient;
    }

    public void setContratTransient(String contratTransient) {
        this.contratTransient = contratTransient;
    }

    public String getAmountTransient() {
        return amountTransient;
    }

    public void setAmountTransient(String amountTransient) {
        this.amountTransient = amountTransient;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getReporting() {
        return reporting;
    }

    public void setReporting(String reporting) {
        this.reporting = reporting;
    }

    public int getCautionStatut() {
        return cautionStatut;
    }

    public void setCautionStatut(int cautionStatut) {
        this.cautionStatut = cautionStatut;
    }
}
