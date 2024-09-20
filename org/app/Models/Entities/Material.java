package org.app.Models.Entities;

public class Material {

    private double quantite;

    private double coutTransport;

    private double coefficientQualte;
    private double coutUnitaire;

    public Material(double quantite, double coutTransport, double coefficientQualte, double coutUnitaire) {
        this.quantite = quantite;
        this.coutTransport = coutTransport;
        this.coefficientQualte = coefficientQualte;
        this.coutUnitaire = coutUnitaire;
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

    public double setCoutTransport() {
        return coutTransport;
    }

    public void setCoutTransport(double coutTransport) {
        this.coutTransport = coutTransport;
    }

    public double getCoefficientQualte() {
        return coefficientQualte;
    }

    public void setCoefficientQualte(double coefficientQualte) {
        this.coefficientQualte = coefficientQualte;
    }

    public double getCoutUnitaire() {
        return coutUnitaire;
    }

    public void setCoutUnitaire(double coutUnitaire) {
        this.coutUnitaire = coutUnitaire;
    }
}
