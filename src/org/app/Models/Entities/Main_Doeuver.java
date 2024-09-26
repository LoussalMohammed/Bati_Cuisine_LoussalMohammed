package org.app.Models.Entities;

import org.app.Models.Enums.TypeComposant;

import java.util.List;

public class Main_Doeuver extends Composant{

    private double productiviteOuvrier;
    private Integer projetId;

    public Main_Doeuver(int id, String nom, TypeComposant typeComposant, double quantite, double coutUnitaire, double tauxTVA, double productiviteOuvrier, Integer projetId) {
        super(id,nom, typeComposant, quantite, coutUnitaire, tauxTVA, projetId);
        this.productiviteOuvrier = productiviteOuvrier;
    }

    public double getProductiviteOuvrier() {
        return this.productiviteOuvrier;
    }

    public void setProductiviteOuvrier(double productiviteOuvrier) {
        this.productiviteOuvrier = productiviteOuvrier;
    }

    @Override
    public double calculeCout() {

        double percentageToAdd = (productiviteOuvrier - 1.0) * 100;
        double coutUnitaire = this.coutUnitaire + (this.coutUnitaire * (percentageToAdd/100));
        setCoutUnitaire(coutUnitaire);
        return (coutUnitaire + (coutUnitaire * tauxTVA / 100)) * quantite;
    }

    @Override
    public double calculCoutWithoutTva(){

        double percentageToAdd = (productiviteOuvrier - 1.0) * 100;
        double coutUnitaire = this.coutUnitaire + (this.coutUnitaire * (percentageToAdd/100));
        setCoutUnitaire(coutUnitaire);
        return coutUnitaire * quantite;
    }
}
