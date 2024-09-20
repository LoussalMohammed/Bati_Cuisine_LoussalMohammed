package org.app.Models.Entities;

import org.app.Models.Enums.TypeComposant;

public abstract class Composant {

    protected String nom;
    protected TypeComposant typeComposant;

    public Composant(String nom, TypeComposant typeComposant) {
        this.nom = nom;
        this.typeComposant = typeComposant;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public TypeComposant getTypeComposant() {
        return typeComposant;
    }

    public void setTypeComposant(TypeComposant typeComposant) {
        this.typeComposant = typeComposant;
    }

    public abstract double calculeCout();
}
