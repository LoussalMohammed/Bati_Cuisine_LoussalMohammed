package org.app.Models.Entities;

import org.app.Models.Enums.TypeComposant;

import java.util.List;

public class Material extends Composant{


    private double coutTransport;

    private double coefficientQualte;

    public Material(int id, String nom, TypeComposant typeComposant, double quantite, double coutUnitaire, double tauxTVA, double coutTransport, double coefficientQualte) {
        super(id,nom, typeComposant, quantite, coutUnitaire, tauxTVA);
        this.coutTransport = coutTransport;
        this.coefficientQualte = coefficientQualte;
    }


    public double getCoutTransport() {
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

    @Override
    public double calculeCout() {
        return 0;
    }
}
