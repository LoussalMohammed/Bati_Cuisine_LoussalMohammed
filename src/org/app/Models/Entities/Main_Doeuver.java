package org.app.Models.Entities;

import org.app.Models.Enums.TypeComposant;

import java.util.List;

public class Main_Doeuver extends Composant{

    private double productiviteOuvrier;

    public Main_Doeuver(int id, String nom, TypeComposant typeComposant, double quantite, double coutUnitaire, double tauxTVA, double productiviteOuvrier) {
        super(id,nom, typeComposant, quantite, coutUnitaire, tauxTVA);
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
        return 0;
    }
}
