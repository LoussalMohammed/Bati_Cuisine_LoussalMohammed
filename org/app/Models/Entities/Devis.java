package org.app.Models.Entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Devis {

    private double monantEstime;
    private LocalDateTime dateEstime;
    private LocalDateTime dateValidite;
    private boolean accept;

    public Devis(double monantEstime, LocalDateTime dateEstime,LocalDateTime dateValidite, boolean accept) {
        this.monantEstime = monantEstime;
        this.dateEstime = dateEstime;
        this.dateValidite = dateValidite;
        this.accept = accept;
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



}
