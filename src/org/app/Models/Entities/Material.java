package org.app.Models.Entities;

import org.app.Models.Enums.TypeComposant;

import java.util.List;

public class Material extends Composant{


    private double coutTransport;

    private double coefficientQualite;
    private Integer projetId;

    public Material(int id, String nom, TypeComposant typeComposant, double quantite, double coutUnitaire, double tauxTVA, double coutTransport, double coefficientQualite, Integer projetId) {
        super(id,nom, typeComposant, quantite, coutUnitaire, tauxTVA, projetId);
        this.coutTransport = coutTransport;
        this.coefficientQualite = coefficientQualite;
    }


    public double getCoutTransport() {
        return coutTransport;
    }

    public void setCoutTransport(double coutTransport) {
        this.coutTransport = coutTransport;
    }

    public double getCoefficientQualite() {
        return coefficientQualite;
    }

    public void setCoefficientQualite(double coefficientQualte) {
        this.coefficientQualite = coefficientQualte;
    }

    @Override
    public double calculeCout() {
        double percentageToAdd = (coefficientQualite - 1.0) * 100;
        double coutUnitaire = this.coutUnitaire + (this.coutUnitaire * (percentageToAdd/100));
        setCoutUnitaire(coutUnitaire);
            return ((coutUnitaire + (coutUnitaire * tauxTVA / 100)) * quantite) + coutTransport;
    }

    @Override
    public double calculCoutWithoutTva() {
        double percentageToAdd = (coefficientQualite - 1.0) * 100;
        double coutUnitaire = this.coutUnitaire + (this.coutUnitaire * (percentageToAdd/100));
        setCoutUnitaire(coutUnitaire);
            return (coutUnitaire * quantite) + coutTransport;
    }
}
