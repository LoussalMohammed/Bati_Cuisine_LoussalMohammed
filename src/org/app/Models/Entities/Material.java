package org.app.Models.Entities;

public class Material {


    private double coutTransport;

    private double coefficientQualte;

    public Material(double coutTransport, double coefficientQualte) {
        this.coutTransport = coutTransport;
        this.coefficientQualte = coefficientQualte;
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

}
