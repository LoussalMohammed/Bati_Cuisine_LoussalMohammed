package org.app.Models.Entities;

public class Main_Doeuver {

    private double productiviteOuvrier;

    public Main_Doeuver(double tauxHoraire, double heuresTravail, double productiviteOuvrier) {

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
