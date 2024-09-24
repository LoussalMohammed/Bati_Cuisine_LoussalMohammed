package org.app.Models.Entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Devis {


    private int id;
    private double monantEstime;
    private LocalDateTime dateEstime;
    private LocalDateTime dateValidite;
    private boolean accept;
    protected Projet projet;

    public Devis(int id, double monantEstime, LocalDateTime dateEstime,LocalDateTime dateValidite, boolean accept, Projet projet) {
        this.id = id;
        this.monantEstime = monantEstime;
        this.dateEstime = dateEstime;
        this.dateValidite = dateValidite;
        this.accept = accept;
        this.projet = projet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMonantEstime() {
        return monantEstime;
    }

    public void setMonantEstime(double monantEstime) {
        this.monantEstime = monantEstime;
    }

    public LocalDateTime getDateEstime() {
        return dateEstime;
    }

    public void setDateEstime(LocalDateTime dateEstime) {
        this.dateEstime = dateEstime;
    }

    public LocalDateTime getDateValidite() {
        return dateValidite;
    }

    public void setDateValidite(LocalDateTime dateValidite) {
        this.dateValidite = dateValidite;
    }

    public boolean getAcceptStatus() {
        return accept;
    }

    public void setAcceptStatus(boolean acceptStatus) {
        accept = acceptStatus;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }



}
