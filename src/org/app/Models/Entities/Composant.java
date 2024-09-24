package org.app.Models.Entities;

import org.app.Models.Enums.TypeComposant;

import java.util.List;

public abstract class Composant {

    protected int id;
    protected String nom;
    protected TypeComposant typeComposant;

    protected double quantite;
    protected double coutUnitaire;

    protected double tauxTVA;


    public Composant(int id,String nom, TypeComposant typeComposant, double quantite, double coutUnitaire, double tauxTVA) {
        this.id = id;
        this.nom = nom;
        this.typeComposant = typeComposant;
        this.quantite = quantite;
        this.coutUnitaire = coutUnitaire;
        this.tauxTVA = tauxTVA;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

    public double getCoutUnitaire() {
        return coutUnitaire;
    }

    public void setCoutUnitaire(double coutUnitaire) {
        this.coutUnitaire = coutUnitaire;
    }

    public double getTauxTVA() {
        return tauxTVA;
    }

    public void getTauxTVA(double tauxTVA) {
        this.tauxTVA = tauxTVA;
    }

    public abstract double calculeCout();
}
