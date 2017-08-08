package com.alab.expenseTracker.security.entities;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class AbstractEntityClass implements EntityObject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    private Date dateCreated;
    private Date dateUpdated;

    public Date getDateCreated() {
        return dateCreated;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    @Override
    public void setId(Integer id) {

        this.id = id;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    @Override
    public Integer getId() {

        return id;
    }
}
