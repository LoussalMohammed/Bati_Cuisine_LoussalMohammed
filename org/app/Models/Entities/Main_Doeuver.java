package org.app.Models.Entities;

public class Main_Doevre {

    private double tauxHoraire;

    private double heuresTravail;
    private double productiviteOuvrier;

    public Main_Doevre(double tauxHoraire, double heuresTravail, double productiviteOuvrier) {
        this.tauxHoraire = tauxHoraire;
        this.heuresTravail = heuresTravail;
        this.productiviteOuvrier = productiviteOuvrier;
    }

    public double getTauxHoraire() {
        return this.tauxHoraire;
    }

    public void setTauxHoraire(double tauxHoraire) {
        this.tauxHoraire = tauxHoraire;
    }

    public double setHeuresTravail() {
        return this.heuresTravail;
    }

    public void setHeuresTravail(double heuresTravail) {
        this.heuresTravail = heuresTravail;
    }

    public double setProductiviteOuvrier() {
        return this.productiviteOuvrier;
    }

    public void setProductiviteOuvrier(double productiviteOuvrier) {
        this.productiviteOuvrier = productiviteOuvrier;
    }
}
