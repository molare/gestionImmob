package com.immo.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by olivier on 02/10/2019.
 */
@Entity
@Table(name = "commune")
public class Commune implements Serializable {
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
    @JoinColumn(name="twon_id")
    private Twon twon;

    @Transient
    private String twonTransient;

    @Transient
    private String action;

    @Transient
    private String dateTransient;

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

    public String getDateTransient() {
        return dateTransient;
    }

    public void setDateTransient(String dateTransient) {
        this.dateTransient = dateTransient;
    }

    public String getCheckboxe() {
        return checkboxe;
    }

    public void setCheckboxe(String checkboxe) {
        this.checkboxe = checkboxe;
    }

    public Twon getTwon() {
        return twon;
    }

    public void setTwon(Twon twon) {
        this.twon = twon;
    }

    public String getTwonTransient() {
        return twonTransient;
    }

    public void setTwonTransient(String twonTransient) {
        this.twonTransient = twonTransient;
    }

    @Override
    public String toString() {
        return "Commune{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdDate=" + createdDate +
                ", action='" + action + '\'' +
                ", dateTransient='" + dateTransient + '\'' +
                '}';
    }
}
